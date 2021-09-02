package dev.mqzn.lib.commands.api;

public interface ParentSubCommand extends SubCommand{


    SubCommand[] getChildSubCommands();

}
