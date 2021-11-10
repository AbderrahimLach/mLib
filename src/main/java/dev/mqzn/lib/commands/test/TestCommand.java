package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.MCommand;
import dev.mqzn.lib.commands.api.Requirement;

import java.util.Set;

public class TestCommand extends MCommand {

    public TestCommand() {
        super("test", "test.perms", "test shit", "/test",
                true, "tez");
    }

    @Override
    public Set<Requirement> setRequirements() {
        return null;
    }


}
