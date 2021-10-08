package dev.mqzn.lib.menus.events;

import dev.mqzn.lib.MLib;
import dev.mqzn.lib.managers.MenuManager;
import dev.mqzn.lib.menus.Menu;
import dev.mqzn.lib.menus.items.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class MenuContentChangeEvent extends Event {


    private final static HandlerList handlerList;
    static {
        handlerList = new HandlerList();
    }

    private Map<Integer, MenuItem> oldContents, newContents;
    private final Menu updatedMenu;
    public MenuContentChangeEvent(Menu menu, Map<Integer, MenuItem> oldContents, Map<Integer, MenuItem> newContents) {
        this.oldContents = oldContents;
        this.newContents = newContents;
        this.updatedMenu = menu;
    }

    public Menu getUpdatedMenu() {
        return updatedMenu;
    }

    public void setNewContents(Map<Integer, MenuItem> newContents) {
        this.newContents = newContents;
    }

    public void setOldContents(Map<Integer, MenuItem> oldContents) {
        this.oldContents = oldContents;
    }

    public Map<Integer, MenuItem> getNewContents() {
        return newContents;
    }

    public Map<Integer, MenuItem> getOldContents() {
        return oldContents;
    }


    public boolean contentUpdatedFor(UUID id) {
        Menu menu = MLib.getInstance().getMenuManager().getOpenMenu(id);
        return Objects.equals(oldContents, newContents) && Objects.equals(menu.getCachedItems(), newContents);
    }

    public void commitUpdates() {

        MenuManager menuManager = MLib.getInstance().getMenuManager();

        for(Player player : Bukkit.getOnlinePlayers()) {

            UUID id = player.getUniqueId();
            if(contentUpdatedFor(id)) continue;

            //mutable object, caching old menu then, registering change manually
            Menu openMenu = menuManager.getOpenMenu(id);
            openMenu.setContents(newContents);

            //registering player with new menu
            menuManager.unregister(id);
            player.closeInventory();
            menuManager.register(id, openMenu);
            openMenu.open(player);
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
