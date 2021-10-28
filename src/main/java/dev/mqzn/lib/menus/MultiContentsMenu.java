package dev.mqzn.lib.menus;

import dev.mqzn.lib.menus.items.MenuItem;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class MultiContentsMenu extends PaginatedMenu {

    private final Map<Integer, BigMenuPage<? extends MultiContentsMenu>> cachedPages;
    private final List<MenuItem> cachedItems;

    public MultiContentsMenu(UUID id) {
        super(id);
        cachedItems = this.getTotalItems();
        cachedPages = this.setPages(id);
    }

    public List<MenuItem> getCachedItems() {
        return cachedItems;
    }

    public Map<Integer, BigMenuPage<? extends MultiContentsMenu>> getCachedPages() {
        return cachedPages;
    }

    /*
      All items that will be divided upon all pages
     */
    public abstract List<MenuItem> getTotalItems();

    /*
        Items per one page !
     */
    public abstract int getPageCapacity();


    /*
        It may be a little heavy but it's the best option here
     */
    @Override
    public Map<Integer, BigMenuPage<? extends MultiContentsMenu>> setPages(UUID id) {

        Map<Integer, BigMenuPage<? extends MultiContentsMenu>> pages = new ConcurrentHashMap<>();

        //more efficient as we are caching the max pages for every iteration
        for (int i = 1, capacity = calculateMaxPages(); i < capacity+1; i++) {
            pages.put(i, new BigMenuPage<>(i, this));
        }

        return pages;
    }


    @Override
    public int calculateMaxPages() {
        return (int) Math.ceil((double)this.getCachedItems().size()/
                (this.getPageCapacity()-2) );
    }


}
