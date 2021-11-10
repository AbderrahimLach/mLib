package dev.mqzn.lib.menus;

import dev.mqzn.lib.menus.items.MenuItem;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class MultiContentsMenu extends PaginatedMenu {

    private final List<MenuItem> totalItems ;

    public MultiContentsMenu(Plugin plugin, UUID id) {
        super(plugin, id);
        totalItems = new ArrayList<>();
        setTotalItems();
    }

    public void addItem(MenuItem menuItem) {
        totalItems.add(menuItem);
    }


    public abstract void setTotalItems();

    /*
        Items per one page !
     */
    public abstract int getPageCapacity();


    /*
        It may be a little heavy but it's the best option here
     */
    @Override
    public void setPages(UUID id) {

        //more efficient as we are caching the max pages for every iteration
        for (int i = 1, capacity = calculateMaxPages(); i < capacity+1; i++) {
            this.addDistinctPage(new BigMenuPage<>(plugin, i, this));
        }

    }


    @Override
    public int calculateMaxPages() {
        return (int) Math.ceil((double)this.totalItems.size()/
                (this.getPageCapacity()-2) );
    }


    public List<MenuItem> getTotalItems() {
        return totalItems;
    }
}
