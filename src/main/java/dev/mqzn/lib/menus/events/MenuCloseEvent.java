package dev.mqzn.lib.menus.events;

import dev.mqzn.lib.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MenuCloseEvent extends Event {

    private final static HandlerList handlerList = new HandlerList();


    private final Player player;
    private final Menu menu;
    public MenuCloseEvent(Player player, Menu IMenu) {
        this.player = player;
        this.menu = IMenu;
    }

    public Menu getMenu() {
        return menu;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
