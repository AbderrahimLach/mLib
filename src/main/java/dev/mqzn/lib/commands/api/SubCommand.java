package dev.mqzn.lib.commands.api;

import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public interface SubCommand {

    String getName();

    String getPermission();

    String getDescription();

    String getUsage();

    int getPosition();

    SubCommandRequirement[] getRequirements();


    final class SubCommandRequirement {


        private final Map<Integer, BiConsumer<CommandSender, CommandArg[]>> actions;
        private final SubCommand subCommand;

        public SubCommandRequirement(SubCommand subCommand) {
            this.subCommand = subCommand;
            this.actions = new HashMap<>();

        }

        public SubCommandRequirement(SubCommand subCommand, int argLength, BiConsumer<CommandSender, CommandArg[]> actions) {
            this(subCommand);
            this.actions.put(argLength, actions);
        }

        public SubCommandRequirement(SubCommand subCommand, Map<Integer, BiConsumer<CommandSender, CommandArg[]>> actions) {
            this.subCommand = subCommand;
            this.actions = actions;
        }


        public SubCommand getSubCommand() {
            return subCommand;
        }

        public Map<Integer, BiConsumer<CommandSender, CommandArg[]>> getActions() {
            return actions;
        }


        public void addAction(int argLegnth, BiConsumer<CommandSender, CommandArg[]> actions) {
            this.actions.put(argLegnth, actions);
        }


        public void execute(CommandSender sender, CommandArg[] args) {
            BiConsumer<CommandSender, CommandArg[]> action = this.getActions().get(args.length);

            if(action != null) {
                action.accept(sender, args);
            }

        }



    }

}
