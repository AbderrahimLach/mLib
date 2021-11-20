package dev.mqzn.lib.commands.api;

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

        execute(sender, Arrays.stream(args).map(arg -> {
            int index = indexOf(arg, args);
            if(index == -1) return null;
            return new CommandArg(index,
                    CommandManager.getInstance()
                            .getArgumentParser(CommandManager.getInstance().getClazzType(arg))
                            .parse(arg), arg);
        }).collect(Collectors.toList()));

        return true;
    }

    private int indexOf(String arg, String[] args) {
        for (int i = 0; i < args.length; i++) {
            if(args[i].equalsIgnoreCase(arg)) {
                return i;
            }
        }
        return -1;
    }

    public void execute(CommandSender sender, List<CommandArg> args) {
        args.removeIf(Objects::isNull);
        Optional<Requirement> found = getRequirementUsed(requirements, args);

        if(!found.isPresent()) {
            this.sendAllUsages(sender);
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

        Optional<Requirement> rq =
                this.getRequirementUsed(subCommand.getRequirements(), args);

        if(rq.isPresent()) {
            rq.get().execute(sender, args);
        }else {
            executeChildren(subCommand, sender, args);
        }




    }

    private void executeChildren(SubCommand subCommand, CommandSender sender, List<CommandArg> args) {

        Optional<Requirement> subReqSafe = this.getRequirementUsed(subCommand.getChildren(), args);

        if(subReqSafe.isPresent()) {
            subReqSafe.get().execute(sender, args);
            return;
        }

        //base case
        Optional<SubCommand> subChildUsed = getChildUsed(subCommand, args);

        if(!subCommand.isParent() || !subChildUsed.isPresent()) {
            subCommand.sendUsage(this, sender);
            return;
        }

        executeChildren(subChildUsed.get(), sender, args);
    }

    private Optional<SubCommand> getChildUsed(SubCommand parent, List<CommandArg> args) {

        Set<? extends SubCommand> children = parent.getChildren();

        int pos = parent.getPosition();
        assert pos > 0 &&
                pos <= args.size()-1;

        SubCommand childSafe = null;

        for (int start = pos+1; start <= args.size()-1; start++) {

            for(SubCommand child : children) {

                if (child.getPosition() == start && child.getName()
                        .equalsIgnoreCase(args.get(start).getArgument())) {

                    childSafe = child;
                    break;
                }
            }
        }

        return Optional.ofNullable(childSafe);
    }




    private Optional<Requirement> getRequirementUsed(Set<? extends Requirement> storedReqs, List<CommandArg> args) {

        Requirement rq = null;
        requirements : for (Requirement requirement : storedReqs) {

            if (!requirement.getCriteria().test(args)) continue;

            if(requirement instanceof SubCommand) {

                SubCommand subCommand = (SubCommand)requirement;
                int pos = subCommand.getPosition();
                if(!args.isEmpty() && pos >= 0 &&
                        pos <= args.size()-1 &&
                        args.get(pos).getArgument()
                                .equalsIgnoreCase(subCommand.getName())) {

                    rq = requirement;
                    break;
                }


            }

            for (UsageArg argEntry : requirement.getArgParses()) {

                CommandArg arg = args.get(argEntry.getPosition());

                ArgumentParser<?> parser = CommandManager.getInstance()
                        .getArgumentParser(argEntry.getTypeClass());

                if(!Objects.equals(arg.getParsedArg(), parser.parse(arg.getArgument()) )) {
                    continue requirements;
                }

            }

            //it matches :DDD
            rq = requirement;

        }

        return Optional.ofNullable(rq);
    }


    protected void addRequirement(Requirement.Criteria criteria, Requirement.Executor executor, UsageArg... usageArgs) {
        Requirement requirement = Requirement.of(criteria, executor);
        for(UsageArg arg : usageArgs) {
            requirement.setArg(arg);
        }
        requirements.add(requirement);
    }

    protected void addRequirement(Requirement requirement) {
        requirements.add(requirement);
    }

    public void sendAllUsages(CommandSender sender) {

        for(Requirement rq : requirements)
            if (rq instanceof SubCommand) {
                ((SubCommand) rq).sendUsage(this, sender);
            } else {
                sender.sendMessage(rq.getUsage(this));
            }

    }

}
