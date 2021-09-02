package dev.mqzn.lib.menus.items;

import com.google.common.base.Objects;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.function.BiConsumer;

public class MenuItem implements Comparable<MenuItem> {

    private final ItemStack item;
    private final BiConsumer<Player, ItemStack> itemActions;
    private int slot;

    public MenuItem(ItemStack item,int slot, BiConsumer<Player, ItemStack> itemActions) {
        this.item = item;
        this.slot = slot;
        this.itemActions = itemActions;
    }


    public MenuItem(ItemStack item, BiConsumer<Player, ItemStack> itemActions) {
        this.item = item;
        this.itemActions = itemActions;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public ItemStack getItem() {
        return item;
    }

    public int getSlot() {
        return slot;
    }

    public BiConsumer<Player, ItemStack> getItemActions() {
        return itemActions;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItem)) return false;
        MenuItem menuItem = (MenuItem) o;
        return getSlot() == menuItem.getSlot() &&
                Objects.equal(getItemActions(), menuItem.getItemActions());


    }


    @Override
    public int hashCode() {
        return Objects.hashCode(getItemActions(), getSlot());
    }

    @Override
    public int compareTo(MenuItem o) {
        return this.getSlot()-o.getSlot();
    }


}
