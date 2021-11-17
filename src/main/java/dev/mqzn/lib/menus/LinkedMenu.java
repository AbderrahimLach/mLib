package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.menus.exceptions.MenuPageOutOfBounds;
import dev.mqzn.lib.menus.items.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class LinkedMenu extends Menu {

    private final Map<Integer, MenuLink> linkedSlots;

    public LinkedMenu(Plugin plugin, UUID viewer) {
        super(plugin, viewer);
        linkedSlots = new ConcurrentHashMap<>();
        this.setLinkedSlots(Bukkit.getPlayer(viewer));
    }


    /**
     * @author Mqzn
     * @see MenuLink
     * @param player set the linked slots(keys) for the player
     */
    public abstract void setLinkedSlots(Player player);


    /**
     * @author Mqzn
     * Adds a link to a menu
     * @see MenuLink
     *
     * @param slot the key of the link
     * @param IMenu the menu to which the key leads
     */
    public void addMenuLink(int slot, IMenu IMenu) {
        linkedSlots.put(slot, MenuLink.of(slot, IMenu));
    }


    /**
     * @author Mqzn
     *
     * @param slot the key containing the link
     * @return returns the link at the specific slot/key
     */
    public MenuLink getLinkIn(int slot) {
        return linkedSlots.getOrDefault(slot, null);
    }

    /**
     * A method to process the opening of a linked menu for the player
     * @param clicker the player to be open the linked menu for
     * @param slot the slot/key at which the link is
     */

    public void linkTo(Player clicker, int slot) {

        MenuLink linkMenu = getLinkIn(slot);
        if(linkMenu == null || linkMenu.getSlot() != slot) return;

        IMenu target = linkMenu.getLinkedMenu();
        if(target == null) return;

        clicker.closeInventory();

        if(target instanceof PaginatedMenu) {
            PaginatedMenu paginatedMenu = (PaginatedMenu)target;
            try {
                paginatedMenu.openPage( 1);
            } catch (MenuPageOutOfBounds ex) {
                ex.printStackTrace();
            }


        }
        else if(target instanceof Menu) {

            ((Menu)target).open();
        }

    }


    /**
     * @author Mqzn
     * @see Menu
     * @param e click event to register the links
     */

    @Override
    public void parseOnClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        ItemStack item = e.getCurrentItem();
        if(item == null || item.getType() == Material.AIR || !item.hasItemMeta()) {
            e.setCancelled(true);
            return;
        }

        int slotClicked = e.getSlot();

        MenuItem registeredItem = this.getItemAt(slotClicked);
        if(registeredItem == null) {
            e.setCancelled(true);
            return;
        }

        if(linkedSlots.containsKey(slotClicked)) {
            this.linkTo(player, slotClicked);
            e.setCancelled(true);
            return;
        }

        //item is registered !
        registeredItem.getItemActions().accept(player, item, e.getClick());
        e.setCancelled(true);

    }



    private static final class MenuLink {

        private final int slot;
        private final IMenu menu;

        private MenuLink(int slot, IMenu menu) {
            this.slot = slot;
            this.menu = menu;
        }

        public int getSlot() {
            return slot;
        }

        public IMenu getLinkedMenu() {
            return menu;
        }


        public static MenuLink of(int slot, IMenu IMenu) {
            return new MenuLink(slot, IMenu);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MenuLink)) return false;
            MenuLink that = (MenuLink) o;
            return getSlot() == that.getSlot() &&
                    Objects.equal(getLinkedMenu(), that.getLinkedMenu());
        }


        @Override
        public int hashCode() {
            return Objects.hashCode(getSlot(), getLinkedMenu());
        }


    }



}
