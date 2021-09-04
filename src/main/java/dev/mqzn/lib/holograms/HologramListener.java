package dev.mqzn.lib.holograms;

import dev.mqzn.lib.MLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class HologramListener implements Listener {

    @EventHandler
    public void onPlayerChangedWorld(final PlayerChangedWorldEvent e) {

        Player player = e.getPlayer();

        Bukkit.getScheduler().runTaskLater(MLib.getInstance(), () -> {
            for (Hologram holo : MLib.getInstance().getHologramManager().getHolograms()) {

                if ((holo.getViewers() == null || holo.getViewers().contains(player.getUniqueId()))
                        && holo.getPosition().getWorld().equals(player.getWorld())
                        && holo.getPosition().distanceSquared(player.getLocation()) <= 1600.0) {

                    holo.show(e.getPlayer());
                }
            }
        }, 20L);
    }

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent event) {
        final Player player = event.getPlayer();
        final Location to = event.getTo();
        final Location from = event.getFrom();
        if (to.getBlockX() == from.getBlockX() && to.getBlockZ() == from.getBlockZ()) {
            return;
        }
        for (final Hologram hologram : MLib.getInstance().getHologramManager().getHolograms()) {
            if ((hologram.getViewers() == null || hologram.getViewers().contains(event.getPlayer().getUniqueId())) && hologram.getPosition().getWorld().equals(event.getPlayer().getWorld())) {

                if (!hologram.getViewers().contains(player.getUniqueId())
                        && hologram.getPosition().distanceSquared(player.getLocation()) <= 1600.0) {
                    hologram.show(player);
                }
                else {
                    if (!hologram.getViewers().contains(player.getUniqueId()) || hologram.getPosition().distanceSquared(player.getLocation()) <= 1600.0) {
                        continue;
                    }
                    hologram.processDestroy(player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        for (final Hologram hologram : MLib.getInstance().getHologramManager().getHolograms()) {
            if ((hologram.getViewers() == null || hologram.getViewers().contains(event.getPlayer().getUniqueId()))
                    && hologram.getPosition().getWorld().equals(event.getPlayer().getWorld())) {
                hologram.show(event.getPlayer());
            }
        }
    }

    @EventHandler
    public void onRespawn(final PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        new BukkitRunnable() {
            public void run() {
                for (final Hologram h : MLib.getInstance().getHologramManager().getHolograms()) {
                    h.processDestroy(player);
                    if ((h.getViewers() == null || h.getViewers().contains(player.getUniqueId()))
                            && h.getPosition().getWorld().equals(player.getWorld())) {
                        h.show(event.getPlayer());
                    }
                }
            }
        }.runTaskLater(MLib.getInstance(), 10L);

    }


}
