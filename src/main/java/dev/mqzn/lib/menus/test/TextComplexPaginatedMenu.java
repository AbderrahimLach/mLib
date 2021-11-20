package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.ComplexPaginatedMenu;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class TextComplexPaginatedMenu extends ComplexPaginatedMenu {

    public TextComplexPaginatedMenu(Plugin plugin, UUID id) {
        super(plugin, id);
    }

    @Override
    public void setTotalItems() {

        ItemStack it = new ItemBuilder(Material.WOOD_AXE)
                .setDisplay("&cTest Axe").build();

        MenuItem.ItemAction action = (p, item, clickType) -> p.sendMessage(Translator
                .color("&7You CLICKED ON "
                        + item.getItemMeta().getDisplayName()));

        for (int i = 0; i < 5000; i++) {
            this.addItem(it, action);
        }
    }

    //number of items per page
    @Override
    public int getPageCapacity() {
        return 12;
    }

    //number of rows of each page
    @Override
    public int getPageRows() {
        return 3;
    }


    //The slot to start with when adding items, per page
    @Override
    public int getPageStartSlot() {
        return 2;
    }

    //The slot to end adding items, per page
    @Override
    public int getPageEndSlot() {
        return super.getPageEndSlot();
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
