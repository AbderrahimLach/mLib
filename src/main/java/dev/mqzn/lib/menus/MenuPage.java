package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import dev.mqzn.lib.menus.exceptions.MenuPageOutOfBounds;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.FormatUtils;
import dev.mqzn.lib.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class MenuPage<M extends PaginatedMenu> extends Menu {

    private final String title;
    private final int index;
    private int rows;
    private final Map<Integer, MenuItem> contents;

    M menu;

    public MenuPage(int index, M menu) {
        this.index = index;
        this.menu = menu;
        this.title = menu.getTitle() + "#"  + index;
        contents = new HashMap<>();
        setPageItems(menu);
    }


    public MenuPage(int index, M menu, int rows) {
        this.index = index;
        this.title = menu.getTitle() + " Page #" + index;
        this.rows = rows;
        this.contents = new HashMap<>();
        setPageItems(menu);
    }

    public void setRows(int rows) {
        this.rows = rows;
    }



    private void setPageItems(M menu) {
        String display = "&aNext Page >>";

        MenuItem NEXT_PAGE = new MenuItem(new ItemBuilder(Material.ARROW, 1)
                .setDisplay(display).build(), this.getSize()-1, ((player, itemStack) -> {

            try {
                menu.openPage(player, this.getIndex() + 1);
            } catch (MenuPageOutOfBounds ex) {
                ex.printStackTrace();
                player.closeInventory();
                player.sendMessage(FormatUtils.color("&cThis is the last page, no next pages !"));
            }
        }));

        display = "&e<< Previous Page";

        MenuItem PREVIOUS_PAGE = new MenuItem(new ItemBuilder(Material.ARROW, 1)
                .setDisplay(display).build(), this.getSize()-9, ((player, itemStack) -> {

            try {
                menu.openPage(player, this.getIndex() - 1);
            } catch (MenuPageOutOfBounds ex) {
                ex.printStackTrace();
                player.closeInventory();
                player.sendMessage(FormatUtils.color("&cThis is the first page, no previous pages !"));
            }

        }));

        this.setItem(NEXT_PAGE);
        this.setItem(PREVIOUS_PAGE);
    }

    public int getSpaces() {
        return this.getSize()-2;
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
    public Map<Integer, MenuItem> getContents() {
        return contents;
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
