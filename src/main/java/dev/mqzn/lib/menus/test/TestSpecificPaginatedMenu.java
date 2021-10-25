package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.MenuPage;
import dev.mqzn.lib.menus.PageBuilder;
import dev.mqzn.lib.menus.PaginatedMenu;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.FormatUtils;
import dev.mqzn.lib.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TestSpecificPaginatedMenu extends PaginatedMenu {

    @Override
    public Map<Integer, ? extends MenuPage<? extends PaginatedMenu>> setPages() {

        Map<Integer, MenuPage<TestSpecificPaginatedMenu>> map = new HashMap<>();

        ItemStack it = new ItemBuilder(Material.BEACON, 1)
                .setDisplay("&9HELLO").build();

        ItemStack it2 = new ItemBuilder(Material.DIAMOND, 1)
                .setDisplay("&aWelcome").build();

        new PageBuilder<>(1, this).setRows(3)

                .setItems(
                        new MenuItem(it, 13, (p, item) -> {
                    p.closeInventory();
                    p.sendMessage(FormatUtils.color("&9HELLO &7" + p.getName()));
                }),
                        new MenuItem(it2, 15, (p, item) -> {
                    p.closeInventory();
                    p.sendMessage(FormatUtils.color("&aWelcome &7" + p.getName()));

                })).buildPage(map);


        return map;
    }

    @Override
    public String getUniqueName() {
        return "testspecific";
    }

    @Override
    public String getTitle() {
        return "SpecificTest";
    }


}
