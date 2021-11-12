package dev.mqzn.lib.menus;


import dev.mqzn.lib.mLib;
import org.bukkit.entity.Player;

public interface IMenu {

    String getUniqueName();


    String getTitle();

    static void close(Player player) {
        mLib.getInstance().getMenuManager().close(player);
    }
}
