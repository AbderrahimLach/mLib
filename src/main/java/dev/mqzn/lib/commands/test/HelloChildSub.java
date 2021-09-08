package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.SubCommand;
import dev.mqzn.lib.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class HelloChildSub implements SubCommand {

    @Override
    public String getName() {
        return "hello";
    }

    @Override
    public String getPermission() {
        return "hello.perm";
    }

    @Override
    public String getDescription() {
        return "desc";
    }

    @Override
    public String getUsage() {
        return "&c/Subs First <player> second <value> hello";
    }

    @Override
    public int getPosition() {
        return 4;
    }

    @Override
    public SubCommandRequirement[] getRequirements() {
        SubCommandRequirement req = new SubCommandRequirement(this);

        req.addAction(5, (sender, args)-> {
            Player player = Bukkit.getPlayer((String) args[1].getParsedArg());
            boolean value = Boolean.parseBoolean(args[3].getArgument());

            assert  player != null;

            player.sendMessage(FormatUtils.color("&aHELLO BUDDY :DDDD"));
            sender.sendMessage(FormatUtils.color("&eVALUE FROM CMD: " + (value ? "&aTRUE" : "&cFALSE")));

        });

        return new SubCommandRequirement[] {
                req
        };
    }
}
