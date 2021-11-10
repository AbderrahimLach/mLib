package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.MCommand;
import dev.mqzn.lib.utils.Translator;

public class TestCommand extends MCommand {

    public TestCommand() {
        super("test", "test.perms", "test shit", "/test",
                true);
    }

    @Override
    public void setRequirements() {
        this.addRequirement((commandArgs -> commandArgs.size() == 0), ((sender, args) -> sender.sendMessage(Translator.color("&5WORKED"))));
    }


}
