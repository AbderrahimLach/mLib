package dev.mqzn.lib.menus.events;

import dev.mqzn.lib.menus.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MenuOpenEvent extends Event {

    private final static HandlerList handlerList = new HandlerList();

    private final Player player;
    private final Menu menu;

    public MenuOpenEvent(Player player, Menu menu) {
        this.player = player;
        this.menu = menu;
    }

    public Player getPlayer() {
        return player;
    }

    public Menu getMenu() {
        return menu;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }



}
