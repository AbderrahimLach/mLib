package dev.mqzn.lib.events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class InteractListener implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        ItemStack item = e.getItem();
        if(item == null || item.getType() == Material.AIR) return;

        PlayerItemInteractEvent itemInteract = new PlayerItemInteractEvent(e.getPlayer(), item, e.getAction());
        itemInteract.setCancelled(e.isCancelled());

        if(!itemInteract.isCancelled()) {
            Bukkit.getPluginManager().callEvent(itemInteract);
        }
    }
}
