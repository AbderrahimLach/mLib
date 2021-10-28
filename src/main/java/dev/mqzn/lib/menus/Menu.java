package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.mLib;
import dev.mqzn.lib.managers.MenuManager;
import dev.mqzn.lib.menus.items.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.UUID;

public abstract class Menu implements IMenu {

    private final UUID viewerId;
    private final Map<Integer, MenuItem> contents;


    public Menu(UUID viewerId) {
        contents = this.getContents(Bukkit.getPlayer(viewerId));
        this.viewerId = viewerId;
    }

    public UUID getViewerId() {
        return viewerId;
    }


    public abstract int getRows();

    public void setItem(MenuItem item) {
        contents.put(item.getSlot(), item);

        if(isOpenForAnyone()) {
            MenuManager menuManager = mLib.getInstance().getMenuManager();

            for(Player player : Bukkit.getOnlinePlayers()) {

                UUID id = player.getUniqueId();
                if(menuManager.getOpenMenu(id) == null || !menuManager.getOpenMenu(id).equals(this)) continue;

                //registering player with new menu
                menuManager.unregister(id);
                player.closeInventory();

                this.open();
                player.updateInventory();
            }
        }
    }

    private boolean isOpenForAnyone() {
        return mLib.getInstance().getMenuManager().getOpenMenus().containsValue(this);
    }

    public Map<Integer, MenuItem> getCachedItems() {
        return contents;
    }


    public void addItem(MenuItem menuItem)
    {

        int slot = nextEmptySlot();
        if(slot == -1) return;

        menuItem.setSlot(slot);
        this.setItem(menuItem);

    }

    public abstract Map<Integer, MenuItem> getContents(Player viewer);

    public Inventory buildInv() {

        Inventory inv = Bukkit.createInventory(null,
                getRows()*9, getTitle());

        contents.forEach((slot, mi)->
                inv.setItem(slot, mi.getItem()));

        return inv;
    }

    public void open() {
        mLib.getInstance().getMenuManager().register(viewerId, this);
        Bukkit.getPlayer(viewerId).openInventory(buildInv());
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
                Objects.equal(getCachedItems(), menu.getCachedItems());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getUniqueName(), getCachedItems());
    }


}
