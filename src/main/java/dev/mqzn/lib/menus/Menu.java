package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.mLib;
import dev.mqzn.lib.menus.items.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Menu implements IMenu {

    protected final UUID viewerId;
    private final Map<Integer, MenuItem> contents;

    protected final Plugin plugin;

    public Menu(Plugin plugin, UUID viewerId) {
        this.viewerId = viewerId;
        contents = new ConcurrentHashMap<>();
        this.setContents(Bukkit.getPlayer(viewerId));
        this.plugin = plugin;
    }

    /**
     * @author Mqzn
     * defines each row of the child class menu
     * @return rows of the  menu
     */
    public abstract int getRows();

    /**
     * @author Mqzn
     * @see MenuItem
     * @param item the menu item to set
     */
    public void setItem(MenuItem item) {
        contents.put(item.getSlot(), item);
    }

    /**
     * @author Mqzn
     * @see Collection
     * @see MenuItem
     * @return cached menu items
     */
    public Collection<? extends MenuItem> getContents() {
        return contents.values();
    }


    /**
     * @author Mqzn
     * @see MenuItem
     *
     * @date 17/11/2021
     *
     * @param slot the slot at which the item is
     * @return gets the item at the specific slot
     */
    public MenuItem getItemAt(int slot) {
        return contents.get(slot);
    }

    public void addItem(MenuItem menuItem)
    {

        int slot = nextEmptySlot();
        if(slot == -1) return;

        menuItem.setSlot(slot);
        this.setItem(menuItem);

    }

    public abstract void setContents(Player viewer);

    public Inventory createInventory() {

        Inventory inv = Bukkit.createInventory(null,
                getRows()*9, getTitle());

        contents.forEach((slot, mi)->
                inv.setItem(slot, mi.getItem()));

        return inv;
    }

    public void open() {
        Player p = Bukkit.getPlayer(viewerId);
        mLib.getInstance().getMenuManager().unregister(viewerId);
        p.closeInventory();

        mLib.getInstance().getMenuManager().register(viewerId, this);
        p.openInventory(createInventory());
    }


    public void parseOnClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();
        if(item == null || item.getType() == Material.AIR || !item.hasItemMeta()) {
            e.setCancelled(true);
            return;
        }

        MenuItem registeredItem = contents.get(e.getSlot());
        if(registeredItem == null) {
            e.setCancelled(true);
            return;
        }

        //item is registered !
        registeredItem.getItemActions().accept(player, item);
        e.setCancelled(true);

    }

    public int getSize() {
        return this.getRows()*9;
    }

    public int nextEmptySlot() {
        for (int i = 0, c = this.getSize(); i < c; i++) {
            if (contents.get(i) == null) return i;
        }

        return -1; // full
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Menu)) return false;
        Menu menu = (Menu) o;
        return Objects.equal(getUniqueName(), menu.getUniqueName()) &&
                Objects.equal(contents, menu.contents);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUniqueName(), contents);
    }


}
