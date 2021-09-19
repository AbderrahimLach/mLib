package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.LinkedMenu;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.FormatUtils;
import dev.mqzn.lib.utils.ItemBuilder;
import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class TestLinkedMenu extends LinkedMenu {

    @Override
    public Map<Integer, MenuLink> initializeLinkedSlots() {

        Map<Integer, MenuLink> map = new HashMap<>();
        map.put(4, MenuLink.of(4, new TestMenu()));

        return map;
    }

    @Override
    public String getUniqueName() {
        return "testLinkedMenu";
    }

    @Override
    public String getTitle() {
        return "TestLinkedMenu";
    }

    @Override
    public int getRows() {
        return 4;
    }

    @Override
    public Map<Integer, MenuItem> getContents() {

        Map<Integer, MenuItem> map = new HashMap<>();

        map.put(4, new MenuItem(ItemBuilder.construct()
                .create(Material.BEDROCK, 1)
                .setDisplay("&6TEST LINKED MENU").build(), 4, (p, item) -> {

            String msg;
            if(p.isOp()) {
                msg = "&aYou're OP";
            }else {
                msg = "&cYou're NOT OP";
            }

            p.closeInventory();
            p.sendMessage(FormatUtils.color(msg));
        }));

        return map;
    }


}
