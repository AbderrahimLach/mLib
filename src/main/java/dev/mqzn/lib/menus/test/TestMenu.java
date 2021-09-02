package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.Menu;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.FormatUtils;
import dev.mqzn.lib.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TestMenu extends Menu {


    @Override
    public String getUniqueName() {
        return "test";
    }

    @Override
    public String getTitle() {
        return "TestMenu";
    }

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public Map<Integer, MenuItem> getContents() {

        Map<Integer, MenuItem> items = new HashMap<>();
        ItemStack it = ItemBuilder.construct().create(Material.BEACON, 1)
                .setDisplay("&9HELLO").build();


        items.put(13, new MenuItem(it, 13, (p, item) -> {
            p.closeInventory();
            p.sendMessage(FormatUtils.color("&9HELLO &7" + p.getName()));
        }));

        return items;
    }

}
