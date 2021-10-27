package dev.mqzn.lib.commands.api;

import dev.mqzn.lib.mLib;
import dev.mqzn.lib.managers.CommandManager;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public abstract class MCommand extends Command {

    static String NO_PERMISSION, ONLY_PLAYER;

    static {
        NO_PERMISSION = Translator.color("&cAccess denied to the command");
        ONLY_PLAYER = Translator.color("&cOnly players can do this !");
    }

    private final String permission;
    private final Requirement[] requirements;

    public MCommand(String name, String permission, String desc, String usage, String... aliases) {
        super(name, desc, usage, Arrays.asList(aliases));
        this.permission = permission;
        requirements = setRequirements();
    }

    public MCommand(String name, String desc, String usage, String... aliases) {
        super(name, desc, usage, Arrays.asList(aliases));
        this.permission = null;
        requirements = setRequirements();
    }


    @Override
    public String getPermission() {
        return this.permission == null ? "command."
                + this.getName().toLowerCase() : this.permission;
    }


    public abstract Requirement[] setRequirements();

    public boolean execute(CommandSender sender, String label, String[] args) {

        if(!allowConsole() && !(sender instanceof Player)) {
            sender.sendMessage(ONLY_PLAYER);
            return true;
        }

        if(!sender.hasPermission(this.getPermission())) {
            sender.sendMessage(NO_PERMISSION);
            return true;
        }


        CommandArg[] commandArgs = new CommandArg[args.length];

        for (int i = 0; i < args.length; i++) {

            String arg = args[i];
            Class<?> clazz = CommandManager.getClazzType(arg);

            commandArgs[i] = new CommandArg(i,
                    mLib.getInstance().getCommandManager()
                            .getArgumentParser(clazz).parse(arg), arg);

        }


        execute(sender, commandArgs);

        return true;
    }

    public void execute(CommandSender sender, CommandArg... args) {

        Optional<Requirement> found = getRequirementUsed(requirements, args);

        if(!found.isPresent()) {

            sender.sendMessage("&9/" + this.getName() + " &eUsages: ");
            for(Requirement reqs : requirements) {
                if(reqs.getArgsCondition().test(args)) {
                    sender.sendMessage(Translator.color("&7&l- " + reqs.getUsage()));
                }
            }
            return;
        }
        Requirement req = found.get();

        if(req.getActions() != null && !(req instanceof SubCommand)) {
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

    private void executeChildren(SubCommand subCommand, CommandSender sender, CommandArg... args) {

        Optional<Requirement> subReqSafe = this.getRequirementUsed(this.reqsToArray(subCommand.getRequirements()), args);

        if(subReqSafe.isPresent()) {
            subReqSafe.get().execute(sender, args);
            return;
        }

        //base case
        if(!subCommand.isParent()) {
            subCommand.sendUsage(sender, args);
            return;
        }

        Optional<SubCommand> subChildUsed = getChildUsed(subCommand, args);
        if(!subChildUsed.isPresent()) {
            subCommand.sendUsage(sender, args);
            return;
        }

        executeChildren(subChildUsed.get(), sender, args);
    }

    private Optional<SubCommand> getChildUsed(SubCommand parent, CommandArg... args) {

        Set<? extends SubCommand> children = parent.getChildren();
        assert parent.getPosition() < args.length;

        SubCommand childSafe = null;

        for (int start = parent.getPosition(); start < args.length; start++) {
            for(SubCommand child : children) {
                if (child.getPosition() == start
                        && child.getName().equalsIgnoreCase(args[start].getArgument())) {
                    childSafe = child;
                    break;
                }
            }
        }

        return Optional.ofNullable(childSafe);
    }




    private Optional<Requirement> getRequirementUsed(Requirement[] storedReqs, CommandArg[] args) {

        Requirement rq = null;
        requirements :
        for (Requirement requirement : storedReqs) {

            if (!requirement.getArgsCondition().test(args)) continue;

            if(requirement instanceof SubCommand) {

                SubCommand subCommand = (SubCommand)requirement;
                for (int i = 0; i < args.length; i++) {
                    if(subCommand.getPosition() == i
                            && args[i].getArgument().equalsIgnoreCase(subCommand.getName())) {
                        rq = subCommand;
                        break requirements;
                    }
                }

            }

            for (Map.Entry<Integer, Argument> argEntry : requirement.getArgParses().entrySet()) {

                CommandArg arg = args[argEntry.getKey()];
                Argument wanted = argEntry.getValue();
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

    public abstract boolean allowConsole();

    private Requirement[] reqsToArray(Set<Requirement> reqsSet) {

        int i = 0;
        Requirement[] arr = new Requirement[reqsSet.size()];
        for(Requirement req : reqsSet) {
            arr[i] = req;
            i++;
        }
        return arr;
    }


}
