package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.Menu;
import dev.mqzn.lib.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class TestMenu extends Menu {


    public TestMenu(Plugin plugin, UUID viewerId) {
        super(plugin, viewerId);
    }

    @Override
    public String getUniqueName() {
        return "test";
    }

    @Override
    public String getTitle() {
        return "TestMenu";
    }

    // 1 Row = 9 slots
    // 3 Rows means an inventory of size `27`
    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public void setContents() {
        ItemStack item = new ItemBuilder(Material.BEDROCK).setDisplay("&amLib Test Item").build();
        this.setItem(item, (clicker, itemStack, clickType) -> clicker.sendMessage("Hello"));

    }

}
