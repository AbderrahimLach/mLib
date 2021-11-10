package dev.mqzn.lib.commands.api;

import dev.mqzn.lib.mLib;
import dev.mqzn.lib.managers.CommandManager;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public abstract class MCommand extends Command {

    static String NO_PERMISSION, ONLY_PLAYER;

    static {
        NO_PERMISSION = Translator.color("&cAccess denied to the command");
        ONLY_PLAYER = Translator.color("&cOnly players can do this !");
    }

    private final String permission;
    private final boolean allowConsole;
    private final Set<Requirement> requirements;

    public MCommand(String name, String permission, String desc, String usage, boolean allowConsole, String... aliases) {
        super(name, Translator.color(desc), usage, Arrays.asList(aliases));
        this.permission = permission;
        this.allowConsole = allowConsole;
        requirements = new HashSet<>();
        this.setRequirements();
    }

    public MCommand(String name, String desc, String usage, boolean allowConsole, String... aliases) {
        super(name, desc, usage, Arrays.asList(aliases));
        this.permission = null;
        this.allowConsole = allowConsole;
        requirements = new HashSet<>();
        this.setRequirements();
    }


    @Override
    public String getPermission() {
        return this.permission == null ? "command."
                + this.getName().toLowerCase() : this.permission;
    }


    public abstract void setRequirements();

    public boolean execute(CommandSender sender, String label, String[] args) {

        if(!allowConsole && !(sender instanceof Player)) {
            sender.sendMessage(ONLY_PLAYER);
            return true;
        }

        if(!sender.hasPermission(this.getPermission())) {
            sender.sendMessage(NO_PERMISSION);
            return true;
        }

        execute(sender, Arrays.stream(args).map(arg -> new CommandArg(indexOf(arg, args),
                            mLib.getInstance().getCommandManager()
                        .getArgumentParser(CommandManager.getClazzType(arg))
                        .parse(arg), arg)).collect(Collectors.toList()));

        return true;
    }

    private int indexOf(String arg, String[] args) {
        return Arrays.asList(args).indexOf(arg);
    }

    public void execute(CommandSender sender, List<CommandArg> args) {

        Optional<Requirement> found = getRequirementUsed(requirements, args);

        if(!found.isPresent()) {

            sender.sendMessage("&9/" + this.getName() + " &eUsages: ");
            for(Requirement reqs : requirements) {
                if(reqs.getCriteria().test(args)) {
                    sender.sendMessage(Translator.color("&7&l- " + reqs.getUsage(this)));
                }
            }
            return;
        }
        Requirement req = found.get();

        if(req.getExecutor() != null && !(req instanceof SubCommand)) {
            req.execute(sender, args);
            return;
        }

        SubCommand subCommand = (SubCommand) req;
        if(!sender.hasPermission(subCommand.getPermission())) {
            sender.sendMessage(NO_PERMISSION);
            return;
        }

        executeChildren(subCommand, sender, args);

    }

    private void executeChildren(SubCommand subCommand, CommandSender sender, List<CommandArg> args) {

        Optional<Requirement> subReqSafe = this.getRequirementUsed(subCommand.getRequirements(), args);

        if(subReqSafe.isPresent()) {
            subReqSafe.get().execute(sender, args);
            return;
        }

        //base case
        if(!subCommand.isParent()) {
            subCommand.sendUsage(this, sender, args);
            return;
        }

        Optional<SubCommand> subChildUsed = getChildUsed(subCommand, args);
        if(!subChildUsed.isPresent()) {
            subCommand.sendUsage(this, sender, args);
            return;
        }

        executeChildren(subChildUsed.get(), sender, args);
    }

    private Optional<SubCommand> getChildUsed(SubCommand parent, List<CommandArg> args) {

        Set<? extends SubCommand> children = parent.getChildren();
        assert parent.getPosition() < args.size();

        SubCommand childSafe = null;

        for (int start = parent.getPosition(); start < args.size(); start++) {
            for(SubCommand child : children) {
                if (child.getPosition() == start
                        && child.getName().equalsIgnoreCase(args.get(start).getArgument())) {
                    childSafe = child;
                    break;
                }
            }
        }

        return Optional.ofNullable(childSafe);
    }




    private Optional<Requirement> getRequirementUsed(Set<Requirement> storedReqs, List<CommandArg> args) {

        Requirement rq = null;
        requirements :
        for (Requirement requirement : storedReqs) {

            if (!requirement.getCriteria().test(args)) continue;

            if(requirement instanceof SubCommand) {

                SubCommand subCommand = (SubCommand)requirement;
                for (int i = 0; i < args.size(); i++) {
                    if(subCommand.getPosition() == i
                            && args.get(i).getArgument().equalsIgnoreCase(subCommand.getName())) {
                        rq = subCommand;
                        break requirements;
                    }
                }

            }

            for (Map.Entry<Integer, UsageArg> argEntry : requirement.getArgParses().entrySet()) {

                CommandArg arg = args.get(argEntry.getKey());
                UsageArg wanted = argEntry.getValue();
                ArgumentParser<?> parser = mLib.getInstance().getCommandManager()
                        .getArgumentParser(wanted.getTypeClass());

                if(!arg.getParsedArg().equals(parser.parse(arg.getArgument()))) {
                    break requirements;
                }

            }

            //it matches :DDD
            rq = requirement;

        }

        return Optional.ofNullable(rq);
    }


    public void addRequirement(Requirement.Criteria criteria, Requirement.Executor executor) {
        requirements.add(Requirement.of(criteria, executor));
    }

}
