package dev.mqzn.lib.menus;


import dev.mqzn.lib.mLib;
import org.bukkit.entity.Player;

public interface IMenu {

    /**
     * @author Mqzn
     * @date 17/11/2021
     * @return a unique identifier for each menu
     */

    String getUniqueName();


    /**
     * @author Mqzn
     * @return the title of each menu
     */
    String getTitle();


    /**
     * closes a menu for a player !
     * @param player the player for which the menu will be closed
     */
    static void close(Player player) {
        mLib.getInstance().getMenuManager().close(player);
    }


}
