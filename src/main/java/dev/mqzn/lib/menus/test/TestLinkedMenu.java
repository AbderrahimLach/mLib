package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.LinkedMenu;
import dev.mqzn.lib.menus.items.MenuItem;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TestLinkedMenu extends LinkedMenu {

    public TestLinkedMenu(UUID viewer) {
        super(viewer);
    }

    @Override
    public Map<Integer, MenuLink> initializeLinkedSlots(Player player) {

        Map<Integer, MenuLink> map = new HashMap<>();
        map.put(4, MenuLink.of(4, new TestMenu(player.getUniqueId())));

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
    public Map<Integer, MenuItem> getContents(Player player) {

        Map<Integer, MenuItem> map = new HashMap<>();

        map.put(4, new MenuItem(new ItemBuilder(Material.BEDROCK, 1)
                .setDisplay("&6TEST LINKED MENU").build(), 4, (p, item) -> {

            String msg;
            if(p.isOp()) {
                msg = "&aYou're OP";
            }else {
                msg = "&cYou're NOT OP";
            }

            p.closeInventory();
            p.sendMessage(Translator.color(msg));
        }));

        return map;
    }


}
