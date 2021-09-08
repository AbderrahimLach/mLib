package dev.mqzn.lib.commands.api;

import dev.mqzn.lib.commands.api.SubCommand.SubCommandRequirement;
import dev.mqzn.lib.utils.FormatUtils;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;

public abstract class BigCommand extends MCommand {

    private final LinkedHashSet<SubCommand> subCommands;

    public BigCommand(String name, String desc, String usage, String... aliases) {
        super(name, desc, usage, aliases);
        subCommands = new LinkedHashSet<>();
        subCommands.addAll(Arrays.asList(setSubCommands()));
    }

    public abstract SubCommand[] setSubCommands();

    public LinkedHashSet<SubCommand> getSubCommands() {
        return subCommands;
    }

    public void addSubCommand(SubCommand command) {
        subCommands.add(command);
    }

    public void removeSubCommand(SubCommand subCommand) {
        subCommands.remove(subCommand);
    }

    public void removeSubCommand(String subCommandName) {
        subCommands.removeIf(sub -> sub.getName().equalsIgnoreCase(subCommandName));
    }

    public boolean hasSubCommand(SubCommand sub) {
       return subCommands.contains(sub);
    }

    public boolean hasSubCommand(String subCommandName) {
        return !subCommands.isEmpty()
                && subCommands.stream().anyMatch(sub ->
                sub.getName().equalsIgnoreCase(subCommandName));
    }

    @Override
    public void execute(CommandSender sender, CommandArg... args) {

        if(args.length == 0) {
            sender.sendMessage(FormatUtils.color(this.getUsage()));
            return;
        }

        if(args.length == 1 && args[0].getArgument().equalsIgnoreCase("help")) {
            //TODO send help for SubCommands using iteration
            return;
        }

        LinkedHashSet<SubCommand> subs = getSubCommands();
        if(subs.isEmpty()) return;


        Optional<SubCommand> subCommand = getSubCommand(args);
        if(!subCommand.isPresent()) {
            sender.sendMessage(FormatUtils.color(this.getUsage()));
            return;
        }

        SubCommand sub = subCommand.get();

        if(!sender.hasPermission(sub.getPermission())) {
            sender.sendMessage(NO_PERMISSION);
            return;
        }

        executeChildren(sub, sender, args);

    }

    private void executeChildren(SubCommand sub, CommandSender sender,  CommandArg[] args) {

        Optional<SubCommandRequirement> firstReq = findRequirement(sub, args);

        if(firstReq.isPresent()) {
            firstReq.get().execute(sender, args);
            return;
        }

        if(!(sub instanceof ParentSubCommand)) {
            // base case
            sender.sendMessage(FormatUtils.color(sub.getUsage()));
            return;
        }

        ParentSubCommand parent = (ParentSubCommand)sub;

        Optional<SubCommand> childUsed = this.getChildUsed(parent, args);
        if(!childUsed.isPresent()) {
            sender.sendMessage(FormatUtils.color(parent.getUsage()));
            return;
        }

        executeChildren(childUsed.get(), sender, args);
    }


    private Optional<SubCommand> getSubCommand(CommandArg... args) {
        SubCommand sc = null;
        for(SubCommand subCommand : getSubCommands()) {

            int pos = subCommand.getPosition();
            if(pos >= args.length) continue;

            if(args[pos] != null && args[pos].getArgument()
                    .equalsIgnoreCase(subCommand.getName())) {
                sc = subCommand;
                break;
            }
        }

        return Optional.ofNullable(sc);
    }

    private Optional<SubCommand> getChildUsed(ParentSubCommand parent, CommandArg... args) {

        SubCommand[] children = parent.getChildSubCommands();
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


    private Optional<SubCommandRequirement> findRequirement(SubCommand sub, CommandArg... args) {

        SubCommandRequirement scReq = null;
        for(SubCommandRequirement requirement : sub.getRequirements()) {
            if(requirement.getActions().get(args.length) != null) {
                scReq = requirement;
                break;
            }
        }

        return Optional.ofNullable(scReq);
    }






}
