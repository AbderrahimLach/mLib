package dev.mqzn.lib.commands.test;

import dev.mqzn.lib.commands.api.MCommand;
import dev.mqzn.lib.commands.api.Requirement;
import dev.mqzn.lib.menus.test.TestLinkedMenu;
import dev.mqzn.lib.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;


public class OpenMenuCommand extends MCommand {


    public OpenMenuCommand() {
        super("open", "cool cmd",
                "&c/Open <player>" , new String[] {"gui", "gui-open"});
    }



    @Override
    public Requirement[] setRequirements() {

        return new Requirement[] {
                new Requirement(this, args -> args.length == 1, ((sender, args) -> {

                    Player player = Bukkit.getPlayer(args[0].getArgument());
                    if (player == null || !player.isOnline()) {
                        sender.sendMessage(FormatUtils.color("&cPlayer is offline"));
                    } else {
                        new TestLinkedMenu().open(player);
                        sender.sendMessage(FormatUtils.color("&7Opened Menu for &a" + player.getName()));
                    }
                }))
                , //requirement separator
                new Requirement(this, args -> args.length == 0,
                        ((sender, args) -> new TestLinkedMenu().open((Player)sender)))

                , new SubCommandTest(this, args -> args.length == 1)
        };
    }



    @Override
    public boolean allowConsole() {
        return false;
    }


}
