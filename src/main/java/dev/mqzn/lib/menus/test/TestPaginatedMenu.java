package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.MultiContentsMenu;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.FormatUtils;
import dev.mqzn.lib.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class TestPaginatedMenu extends MultiContentsMenu {

    @Override
    public List<MenuItem> getTotalItems() {
        ItemStack it = ItemBuilder.construct().create(Material.WOOD_AXE, 1)
                .setDisplay("&cTest Axe").build();

        List<MenuItem> items = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            items.add(new MenuItem(it, (p, item) ->
                    p.sendMessage(FormatUtils.color("&7You CLICKED ON " + item.getItemMeta().getDisplayName()))));
        }

        return items;
    }

    @Override
    public int getPageCapacity() {
        return 52;
    }


    @Override
    public String getUniqueName() {
        return "testmenu";
    }

    @Override
    public String getTitle() {
        return "TestPaginatedMenu";
    }
}
