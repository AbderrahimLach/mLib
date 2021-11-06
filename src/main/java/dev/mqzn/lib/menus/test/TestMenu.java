package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.Menu;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestMenu extends Menu {


    public TestMenu(UUID viewerId) {
        super(viewerId);
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
    public Map<Integer, MenuItem> getContents(Player player) {
        Map<Integer, MenuItem> items = new HashMap<>();
        items.put(0, new MenuItem(new ItemBuilder(Material.BEDROCK).setDisplay("&amLib Test Item").build(),
                (clicker, item) -> clicker.sendMessage("Hello")));

        return items;
    }

}
