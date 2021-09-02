package dev.mqzn.lib.commands.api;

import dev.mqzn.lib.utils.FormatUtils;
import org.bukkit.command.CommandSender;
import dev.mqzn.lib.commands.api.SubCommand.SubCommandRequirement;
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

        long n = System.currentTimeMillis();
        if(args.length == 0) {
            sender.sendMessage(FormatUtils.color(this.getUsage()));
            return;
        }

        if(args.length == 1 && args[0].getArgument().equalsIgnoreCase("help")) {
            //TODO send help for SubCommands
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

        Optional<SubCommandRequirement> firstReq = findRequirement(sub, args);

        if(!firstReq.isPresent()) {

            if(sub instanceof ParentSubCommand) {

                ParentSubCommand parent = (ParentSubCommand)sub;
                LinkedHashSet<SubCommand> childUsed = this.getChildrenUsed(parent, args);

                if(childUsed.isEmpty())  {
                    sender.sendMessage(FormatUtils.color(sub.getUsage()));
                    return;
                }

                for(SubCommand child : childUsed) {

                    if(child instanceof ParentSubCommand) {
                        throw new IllegalArgumentException("SubCommand is a child of a parent sub command and " +
                                "is a parent sub command at the same time !");
                    }

                    Optional<SubCommandRequirement> req = findRequirement(child, args);
                    if(!req.isPresent()) {
                        sender.sendMessage(FormatUtils.color(child.getUsage()));
                        break;
                    }

                    req.get().execute(sender, args);

                    System.out.println("TOOK " + (System.currentTimeMillis()-n) + "MS, To execute the sub child " + child.getName());
                }



            }else {
                sender.sendMessage(FormatUtils.color(sub.getUsage()));
            }

            return;
        }

        firstReq.get().execute(sender, args);

        System.out.println("TOOK " + (System.currentTimeMillis()-n) + "MS");
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

    private LinkedHashSet<SubCommand> getChildrenUsed(ParentSubCommand parent, CommandArg... args) {

        SubCommand[] children = parent.getChildSubCommands();
        LinkedHashSet<SubCommand> results = new LinkedHashSet<>();

        assert parent.getPosition() < args.length;
        for (int start = parent.getPosition(); start < args.length; start++) {
            for(SubCommand child : children) {
                if (child.getPosition() == start
                        && child.getName().equalsIgnoreCase(args[start].getArgument())) {
                    results.add(child);
                }
            }
        }

        return results;
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
