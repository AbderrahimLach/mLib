package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.menus.exceptions.MenuPageOutOfBounds;
import dev.mqzn.lib.menus.items.MenuItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import java.util.Map;

public abstract class LinkedMenu extends Menu {

    private final Map<Integer, CachedLinkMenu> linkedSlots;

    public LinkedMenu() {
        linkedSlots = this.initializeLinkedSlots();
    }

    public LinkedMenu(Map<Integer, CachedLinkMenu> linkedSlots) {
        this.linkedSlots = linkedSlots;
    }


    public abstract Map<Integer, CachedLinkMenu> initializeLinkedSlots();


    public void addMenuLink(int slot, IMenu IMenu) {
        linkedSlots.put(slot, new CachedLinkMenu(slot, IMenu));
    }


    public CachedLinkMenu getLinkIn(int slot) {
        return linkedSlots.getOrDefault(slot, null);
    }


    public void linkTo(Player clicker, int slot) {

        CachedLinkMenu linkMenu = getLinkIn(slot);
        if(linkMenu == null || linkMenu.getSlot() != slot) return;

        IMenu target = linkMenu.getIMenu();
        if(target == null) return;

        clicker.closeInventory();

        if(target instanceof PaginatedMenu) {
            PaginatedMenu paginatedMenu = (PaginatedMenu)target;
            try {
                paginatedMenu.openPage(clicker, 1);
            } catch (MenuPageOutOfBounds ex) {
                ex.printStackTrace();
            }


        }
        else if(target instanceof Menu) {

            ((Menu)target).open(clicker);
        }

    }


    @Override
    public void parseOnClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();
        if(item == null || item.getType() == Material.AIR || !item.hasItemMeta()) {
            e.setCancelled(true);
            return;
        }

        int slotClicked = e.getSlot();

        MenuItem registeredItem = getContents().get(slotClicked);
        if(registeredItem == null) {
            e.setCancelled(true);
            return;
        }

        if(linkedSlots.containsKey(slotClicked)) {
            System.out.println("LINKING TO SLOT " + slotClicked);
            this.linkTo(player, slotClicked);
            e.setCancelled(true);
            return;
        }

        //item is registered !
        registeredItem.getItemActions().accept(player, item);
        e.setCancelled(true);

    }



    protected static final class CachedLinkMenu {

        private final int slot;
        private final IMenu IMenu;

        private CachedLinkMenu(int slot, IMenu IMenu) {
            this.slot = slot;
            this.IMenu = IMenu;
        }

        public int getSlot() {
            return slot;
        }

        public IMenu getIMenu() {
            return IMenu;
        }


        public static CachedLinkMenu of(int slot, IMenu IMenu) {
            return new CachedLinkMenu(slot, IMenu);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof CachedLinkMenu)) return false;
            CachedLinkMenu that = (CachedLinkMenu) o;
            return getSlot() == that.getSlot() &&
                    Objects.equal(getIMenu(), that.getIMenu());
        }


        @Override
        public int hashCode() {
            return Objects.hashCode(getSlot(), getIMenu());
        }


    }



}
