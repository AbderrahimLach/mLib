package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.menus.exceptions.MenuPageOutOfBounds;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class MenuPage<M extends PaginatedMenu> extends Menu {

    private final String title;
    private final int index;
    private int rows;

    private final M menu;

    public MenuPage(Plugin plugin, int index, M menu) {
        super(plugin, menu.getViewer());
        this.index = index;
        this.menu = menu;
        this.title = menu.getTitle() + "#"  + index;
        setPageItems(menu);
        this.loadMultipleItems();

    }


    public MenuPage(Plugin plugin, int index, M menu, int rows) {
        super(plugin, menu.getViewer());
        this.index = index;
        this.menu = menu;
        this.title = menu.getTitle() + " Page #" + index;
        this.rows = rows;
        setPageItems(menu);
        this.loadMultipleItems();
    }

    private void loadMultipleItems() {
        if(menu instanceof ComplexPaginatedMenu) {
            ComplexPaginatedMenu multiMenu = (ComplexPaginatedMenu)menu;
            int capacity = multiMenu.getPageCapacity();
            int max = this.getIndex() * capacity;
            int min = max-capacity;

            List<MenuItem> total = multiMenu.getTotalItems();

            int c = Math.min(max, total.size());
            for(int i = min; i < c; i++) {

                MenuItem item = total.get(i);
                int slot = this.nextEmptySlot();

                if(slot < multiMenu.getPageStartSlot() || slot > multiMenu.getPageEndSlot()) break;

                item.setSlot(slot);
                this.setItem(item);
            }
        }
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    private void setPageItems(M menu) {
        String display = "&aNext Page >>";

        MenuItem NEXT_PAGE = new MenuItem(new ItemBuilder(Material.ARROW, 1)
                .setDisplay(display).build(), this.getSize()-1, ((player, itemStack, clickType) -> {

            try {
                if(menu == null) return;
                menu.openPage(this.getIndex() + 1);
            } catch (MenuPageOutOfBounds ex) {
                ex.printStackTrace();
                player.closeInventory();
                player.sendMessage(Translator.color("&cThis is the last page, no next pages !"));
            }
        }));

        display = "&e<< Previous Page";

        MenuItem PREVIOUS_PAGE = new MenuItem(new ItemBuilder(Material.ARROW, 1)
                .setDisplay(display).build(), this.getSize()-9, ((player, itemStack, clickType) -> {

            try {
                menu.openPage(this.getIndex() - 1);
            } catch (MenuPageOutOfBounds ex) {
                ex.printStackTrace();
                player.closeInventory();
                player.sendMessage(Translator.color("&cThis is the first page, no previous pages !"));
            }

        }));

        this.setItem(NEXT_PAGE);
        this.setItem(PREVIOUS_PAGE);
    }


    public int getIndex() {
        return index;
    }

    @Override
    public String getUniqueName() {
        return "MenuPage={Index=" + index + "," + " Rows=" +
                this.getRows() + ", MainMenu=" + menu.getUniqueName() + "}";
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int getRows() {
        return rows;
    }


    @Override
    public void setContents() {

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuPage)) return false;
        MenuPage<?> menuPage = (MenuPage<?>) o;
        return getIndex() == menuPage.getIndex() &&
                getRows() == menuPage.getRows();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getIndex());
    }


}
