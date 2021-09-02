package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.CommandArg;
import dev.mqzn.lib.commands.api.MCommand;
import dev.mqzn.lib.menus.test.TestLinkedMenu;
import dev.mqzn.lib.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class OpenMenuCommand extends MCommand {


    public OpenMenuCommand() {
        super("open", "cool cmd", "&c/Open <player>" , new String[] {"gui", "gui-open"});
    }


    @Override
    public Map<Integer, Class<?>> getArgParses() {
        return new HashMap<>();
    }

    @Override
    public void execute(CommandSender sender, CommandArg... args){

        switch (args.length) {

            case 0:
                new TestLinkedMenu().open((Player)sender);
                break;
            case 1:
                Player player = Bukkit.getPlayer(args[0].getArgument());
                if(player == null || !player.isOnline()) {
                    sender.sendMessage(FormatUtils.color("&cPlayer is offline"));
                }else {
                    new TestLinkedMenu().open(player);
                    sender.sendMessage(FormatUtils.color("&7Opened Menu for &a" + player.getName()));
                }
                break;
            default: {
                sender.sendMessage(FormatUtils.color(getUsage()));
            }
        }

    }

    @Override
    public boolean allowConsole() {
        return false;
    }


}
