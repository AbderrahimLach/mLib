package dev.mqzn.lib.menus.exceptions;

import dev.mqzn.lib.utils.Translator;
import org.bukkit.Bukkit;

public class MenuPageOutOfBounds extends Exception {

    public MenuPageOutOfBounds(int maxPages, int index) {
        Bukkit.getConsoleSender().sendMessage(Translator.color("&cMaxPages: "
                + maxPages + ", Index Provided: " + index));
    }


}
