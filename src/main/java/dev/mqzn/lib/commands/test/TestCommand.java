package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.MCommand;

public class TestCommand extends MCommand {

    public TestCommand() {
        super("test", "test.perms",
                "test shit", "/test",
                true);
    }

    @Override
    public void setRequirements() {

        //HERE we register our SubCommand in this command
        this.addRequirement(new KillSubCommand());

    }

}
