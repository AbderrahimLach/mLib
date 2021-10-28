package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.mLib;
import dev.mqzn.lib.menus.exceptions.MenuPageOutOfBounds;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public abstract class PaginatedMenu implements IMenu {

    private final Map<Integer, ? extends MenuPage<? extends PaginatedMenu>> pages; //caching calculated pages from the abstract method
    private int currentPage; //current page opened for whoever viewer

    private final UUID viewer;

    public PaginatedMenu(UUID viewer) { //simple constructor
        pages = setPages(viewer);
        this.viewer = viewer;
    }

    public UUID getViewer() {
        return viewer;
    }

    public Map<Integer, ? extends MenuPage<? extends PaginatedMenu>> getPages() {
        return pages;
    }

    /**
     * @Author Mqzn
     * @Discord Mqzn#8141
     *
     * @param pageIndex the index of the page menu to open !
     *
     * @throws MenuPageOutOfBounds if the index of the page is greater than
     * the max number of pages or less than 1
     */
    public void openPage(int pageIndex) throws MenuPageOutOfBounds {

        MenuPage<? extends PaginatedMenu> page = getPages().get(pageIndex);
        if(page == null) {
            throw new MenuPageOutOfBounds(calculateMaxPages(), pageIndex);
        }

        Player player  = Bukkit.getPlayer(viewer);

        player.closeInventory();
        mLib.getInstance().getMenuManager().register(viewer, page);

        currentPage = pageIndex;
        page.open();
    }

    /**
     * defines how each sub class menu can define it's own pages
     * @return A map storing each page with it's index
     */
    public abstract Map<Integer, ? extends MenuPage<? extends PaginatedMenu>> setPages(UUID viewer);


    /**
     * To define how the max pages will be calculated !
     * This method will be overrided to
     * allow a different way of calculating the number of pages
     * @return the max pages calculated
     */

    public int calculateMaxPages() {
        return pages.size();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaginatedMenu)) return false;
        PaginatedMenu that = (PaginatedMenu) o;
        return getCurrentPage() == that.getCurrentPage() &&
                Objects.equal(getPages(), that.getPages());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPages(), getCurrentPage());
    }

}
