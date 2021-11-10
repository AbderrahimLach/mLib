package dev.mqzn.lib.menus;

import dev.mqzn.lib.menus.items.MenuItem;
import org.bukkit.plugin.Plugin;

public class PageBuilder<M extends PaginatedMenu> {

    private final MenuPage<M> menuPage;

    public PageBuilder(Plugin plugin, int index, M menu) {
        menuPage = new MenuPage<>(plugin, index, menu);
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

    public void buildPage(M menu) {
        menu.addDistinctPage(menuPage);
    }


}
