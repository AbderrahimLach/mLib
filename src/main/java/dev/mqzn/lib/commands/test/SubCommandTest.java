package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.CommandArg;
import dev.mqzn.lib.commands.api.Requirement;
import dev.mqzn.lib.commands.api.SubCommand;

import java.util.function.Predicate;

public class SubCommandTest extends SubCommand {

    public SubCommandTest(OpenMenuCommand command, Predicate<CommandArg[]> argLength) {
        super(command, argLength);
    }

    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getPermission() {
        return "test.sub.perm";
    }

    @Override
    public String getDescription() {
        return "Testing new Sub commands system";
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public Requirement[] setRequirements() {

        return new Requirement[]{
                new Requirement(this.getCommand(), arg -> arg.length == 1,
                        (sender, args)-> sender.sendMessage("HELLO, TEST WORKED"))
        };
    }

}
