package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.MultiContentsMenu;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TestMultiPaginatedMenu extends MultiContentsMenu {

    public TestMultiPaginatedMenu(UUID id) {
        super(id);
    }

    @Override
    public List<MenuItem> getTotalItems() {
        ItemStack it = new ItemBuilder(Material.WOOD_AXE)
                .setDisplay("&cTest Axe").build();

        List<MenuItem> items = new ArrayList<>();
        for (int i = 0; i < 5000; i++) {
            items.add(new MenuItem(it, (p, item) ->
                    p.sendMessage(Translator.color("&7You CLICKED ON " + item.getItemMeta().getDisplayName()))));
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
