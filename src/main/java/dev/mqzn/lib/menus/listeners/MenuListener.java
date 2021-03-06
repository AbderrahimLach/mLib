package dev.mqzn.lib.menus.listeners;

import dev.mqzn.lib.managers.MenuManager;
import dev.mqzn.lib.menus.Menu;
import dev.mqzn.lib.menus.events.MenuCloseEvent;
import dev.mqzn.lib.menus.events.MenuOpenEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void onClose(InventoryCloseEvent e) {

        Player player = (Player) e.getPlayer();
        Menu m = MenuManager.getInstance().getOpenMenus().get(player.getUniqueId());
        Bukkit.getPluginManager().callEvent(new MenuCloseEvent(player, m));

    }

    @EventHandler
    public void onOpen(InventoryOpenEvent e) {

        Player player = (Player) e.getPlayer();
        Menu m = MenuManager.getInstance().getOpenMenus().get(player.getUniqueId());
        Bukkit.getPluginManager().callEvent(new MenuOpenEvent(player, m));

    }

    @EventHandler
    public void onMenuClose(MenuCloseEvent e) {
        MenuManager.getInstance().unregister(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player player = (Player)e.getWhoClicked();

        Menu menu = MenuManager.getInstance().getOpenMenu(player.getUniqueId());
        if(menu != null) menu.parseOnClick(e);

    }



}
