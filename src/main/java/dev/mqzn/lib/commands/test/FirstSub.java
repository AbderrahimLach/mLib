package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.ParentSubCommand;
import dev.mqzn.lib.commands.api.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class FirstSub implements ParentSubCommand {

    @Override
    public String getName() {
        return "first";
    }

    @Override
    public String getPermission() {
        return "sub.first";
    }

    @Override
    public String getDescription() {
        return "send first shit";
    }

    @Override
    public String getUsage() {
        return "&c /Subs First <player>";
    }

    @Override
    public int getPosition() {
        return 0;
    }

    @Override
    public SubCommandRequirement[] getRequirements() {
        SubCommandRequirement req = new SubCommandRequirement(this);

        req.addAction(2, (s, args)-> {
            Player player = Bukkit.getPlayer((String) args[1].getParsedArg());
            if(player != null) {
                player.sendMessage(ChatColor.GOLD + "Req action worked !");
            }else {
                s.sendMessage(ChatColor.RED + args[1].getArgument() + " is Offline");
            }
        });

        return new SubCommandRequirement[] {
                req
        };
    }


    @Override
    public SubCommand[] getChildSubCommands() {
        return new SubCommand[] {
            new SecondSub()
        };
    }



}
