package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.MultiContentsMenu;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class TestMultiPaginatedMenu extends MultiContentsMenu {

    public TestMultiPaginatedMenu(Plugin plugin, UUID id) {
        super(plugin, id);
    }

    @Override
    public void setTotalItems() {

        ItemStack it = new ItemBuilder(Material.WOOD_AXE)
                .setDisplay("&cTest Axe").build();

        for (int i = 0; i < 5000; i++) {
            this.addItem(new MenuItem(it, (p, item) ->
                    p.sendMessage(Translator.color("&7You CLICKED ON " + item.getItemMeta().getDisplayName()))));
        }
    }


    @Override
    public int getPageCapacity() {
        return 12;
    }

    @Override
    public int getPageRows() {
        return 3;
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
