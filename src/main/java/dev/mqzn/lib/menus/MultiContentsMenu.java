package dev.mqzn.lib.menus;

import dev.mqzn.lib.menus.items.MenuItem;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class MultiContentsMenu extends PaginatedMenu {

    private final List<MenuItem> totalItems ;
    private final int pageEndSlot, pageStartSlot = 0;

    /**
     *
     * @param plugin , the plugin that is opening the gui
     * @param id, UUID of the viewer
     */
    public MultiContentsMenu(Plugin plugin, UUID id) {
        super(plugin, id);
        totalItems = new ArrayList<>();
        setTotalItems();
        pageEndSlot = this.getPageRows()*9-1;
    }

    /**
     * @author Mqzn
     * @date 17/11/2021
     *
     * @param menuItem the item that will be added !
     * That parameter includes the item stack, slot, and it's action
     */
    public void addItem(MenuItem menuItem) {
        totalItems.add(menuItem);
    }


    /**
     * @author Mqzn
     * @date 17/11/2021
     *
     * Sets the ALL items that will be distributed on the pages
     * @see PaginatedMenu
     */
    public abstract void setTotalItems();


    /**
     * @author Mqzn
     * @date 17/11/2021
     * Note: Please when overriding this to set your own start slot of the items
     * MAKE SURE YOU SET IT TO A VALID SLOT !!!
     * @return the slot at which the items will be set from
     */
    public int getPageStartSlot() {
        return pageStartSlot;
    }


    /**
     * @author Mqzn
     * @date 17/11/2021
     * Note: Please when overriding this to set your own end slot of the items
     * MAKE SURE YOU SET IT TO A VALID SLOT !!!
     * @return the slot at which the items will be set from
     */
    public int getPageEndSlot() {
        return pageEndSlot;
    }

    /**
     * @author Mqzn
     * @date 17/11/2021
     * @return the amount of items allowed to be in one single page
     */
    public abstract int getPageCapacity();

    /**
     * @author Mqzn
     * @date 17/11/2021
     * Note: All pages will have the same number of rows !
     * @see MultiContentsMenu
     * @return the number of rows of a one single page
     */
    public abstract int getPageRows();


    /**
     * @see PaginatedMenu
     * @param id uuid of the viewer
     */
    @Override
    public void setPages(UUID id) {

        //More efficient as we are caching the max pages for every iteration
        for (int i = 1, capacity = calculateMaxPages(); i < capacity+1; i++) {
            this.addDistinctPage(new MenuPage<>(plugin, i, this));
        }

    }

    /**
     *
     * @return number of max pages calculated
     * each type of paginated menu has it's own way
     * to calculate it
     */
    @Override
    public int calculateMaxPages() {
        return (int) Math.ceil((double)this.totalItems.size()/
                (this.getPageCapacity()-2) );
    }

    /**
     * @return total items cached to be set !
     */

    protected List<MenuItem> getTotalItems() {
        return totalItems;
    }


}
