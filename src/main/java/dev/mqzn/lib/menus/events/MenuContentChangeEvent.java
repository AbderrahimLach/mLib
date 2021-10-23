package dev.mqzn.lib.menus.events;

import dev.mqzn.lib.MLib;
import dev.mqzn.lib.managers.MenuManager;
import dev.mqzn.lib.menus.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class MenuContentChangeEvent extends Event {


    private final static HandlerList handlerList;
    static {
        handlerList = new HandlerList();
    }

    private final Menu updatedMenu;
    public MenuContentChangeEvent(Menu menu) {
        this.updatedMenu = menu;
    }

    public Menu getUpdatedMenu() {
        return updatedMenu;
    }


    public void commitUpdates() {

        MenuManager menuManager = MLib.getInstance().getMenuManager();

        for(Player player : Bukkit.getOnlinePlayers()) {

            UUID id = player.getUniqueId();
            if(menuManager.getOpenMenu(id) == null) continue;

            //registering player with new menu
            menuManager.unregister(id);
            player.closeInventory();

            menuManager.register(id, updatedMenu);
            updatedMenu.open(player);
        }
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

}
