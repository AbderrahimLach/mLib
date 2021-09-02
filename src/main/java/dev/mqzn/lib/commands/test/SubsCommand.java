package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.BigCommand;
import dev.mqzn.lib.commands.api.SubCommand;

import java.util.HashMap;
import java.util.Map;

public class SubsCommand extends BigCommand {
    public SubsCommand() {
        super("subs", "testing subs", "&c/Subs help");
    }

    @Override
    public SubCommand[] setSubCommands() {
        return new SubCommand[] {
                new FirstSub()
        };
    }

    @Override
    public Map<Integer, Class<?>> getArgParses() {
        return new HashMap<>();
    }

    @Override
    public boolean allowConsole() {
        return true;
    }

}
