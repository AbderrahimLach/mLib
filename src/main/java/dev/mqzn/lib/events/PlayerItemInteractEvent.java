package dev.mqzn.lib.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;

public class PlayerItemInteractEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();

    private final Player player;
    private final ItemStack item;
    private final Action action;

    private boolean cancelled = false;

    public PlayerItemInteractEvent(Player player, ItemStack item, Action action) {
        this.player = player;
        this.item = item;
        this.action = action;
    }


    public ItemStack getItem() {
        return item;
    }

    public Player getPlayer() {
        return player;
    }

    public Action getAction() {
        return action;
    }


    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled =b;
    }
}
