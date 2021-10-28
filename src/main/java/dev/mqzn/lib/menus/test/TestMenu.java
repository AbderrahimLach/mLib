package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.Menu;
import dev.mqzn.lib.menus.items.MenuItem;
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

    @Override
    public int getRows() {
        return 3;
    }

    @Override
    public Map<Integer, MenuItem> getContents(Player player) {

        return new HashMap<>();
    }

}
