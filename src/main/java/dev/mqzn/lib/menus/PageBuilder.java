package dev.mqzn.lib.menus;

import dev.mqzn.lib.menus.items.MenuItem;
import java.util.Map;

public class PageBuilder<M extends PreCreatedPaginatedMenu> {

    private final MenuPage<M> menuPage;

    public PageBuilder(int index, M menu) {
        menuPage = new MenuPage<>(index, menu);
    }

    public PageBuilder<M> setRows(int rows) {
        menuPage.setRows(rows);
        return this;
    }

    public PageBuilder<M> setItems(MenuItem... items) {
        for (MenuItem menuItem : items) {
            menuPage.setItem(menuItem);
        }
        return this;
    }

    public void buildPage(Map<Integer, MenuPage<M>> pages) {
        pages.put(menuPage.getIndex(), menuPage);
    }


}
