package dev.mqzn.lib.menus.exceptions;

import dev.mqzn.lib.utils.FormatUtils;
import org.bukkit.Bukkit;

public class MenuPageOutOfBounds extends Exception {

    public MenuPageOutOfBounds(int maxPages, int index) {
        Bukkit.getConsoleSender().sendMessage(FormatUtils.color("&cMaxPages: "
                + maxPages + ", Index Provided: " + index));
    }


}
