package dev.mqzn.lib.menus;

import dev.mqzn.lib.MLib;
import dev.mqzn.lib.menus.exceptions.MenuPageOutOfBounds;
import org.bukkit.entity.Player;
import java.util.Map;

public abstract class PaginatedMenu implements IMenu {

    /*
        Pages of the menu
     */

    private final Map<Integer, ? extends MenuPage<? extends PaginatedMenu>> pages;

    public PaginatedMenu() {
        pages = calculatePages();
    }

    public Map<Integer, ? extends MenuPage<? extends PaginatedMenu>> getCachedPages() {
        return pages;
    }

    /*
        Open a specific page
    */
    public void openPage(Player viewer, int pageIndex) throws MenuPageOutOfBounds
    {

        Map<Integer, ? extends MenuPage<? extends PaginatedMenu>> map = calculatePages();

        MenuPage<? extends PaginatedMenu> page = map.get(pageIndex);
        if(page == null) {
            throw new MenuPageOutOfBounds(calculateMaxPages(), pageIndex);
        }

        viewer.closeInventory();
        MLib.getInstance().getMenuManager().register(viewer.getUniqueId(), page);

        page.open(viewer);

    }


    public abstract Map<Integer, ? extends MenuPage<? extends PaginatedMenu>> calculatePages();


    /*
        get Number Of Pages
     */
    abstract int calculateMaxPages();


}
