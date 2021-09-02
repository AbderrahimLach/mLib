package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.SubCommand;
import dev.mqzn.lib.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class SecondSub implements SubCommand {


    @Override
    public String getName() {
        return "second";
    }

    @Override
    public String getPermission() {
        return "subs.second";
    }

    @Override
    public String getDescription() {
        return "second sub";
    }

    @Override
    public String getUsage() {
        return "&c/Subs first <player> second <value>";
    }

    @Override
    public int getPosition() {
        return 2;
    }

    @Override
    public SubCommandRequirement[] getRequirements() {

        SubCommandRequirement req = new SubCommandRequirement(this);

        req.addAction(4, (sender, args)-> {
            Player player = Bukkit.getPlayer((String) args[1].getParsedArg());
            boolean value = Boolean.parseBoolean(args[3].getArgument());
            if(player != null) {
                player.sendMessage(ChatColor.BLUE + "Second Sub Requirement action worked !");
            }else {
                sender.sendMessage(ChatColor.RED + args[1].getArgument() + " is Offline");
            }

            sender.sendMessage(FormatUtils.color("&eVALUE FROM CMD: " + (value ? "&aTRUE" : "&cFALSE")));
        });

        return new SubCommandRequirement[] {
                req
        };
    }


}
