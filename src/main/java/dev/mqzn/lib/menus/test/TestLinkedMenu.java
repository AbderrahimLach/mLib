package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.LinkedMenu;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class TestLinkedMenu extends LinkedMenu {

    public TestLinkedMenu(Plugin plugin, UUID viewer) {
        super(plugin, viewer);
    }

    @Override
    public void setLinkedSlots(Player player) {
        this.addMenuLink(4, new TestMenu(plugin, player.getUniqueId()));
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
    public void setContents() {
        this.setItem(new ItemBuilder(Material.BEDROCK, 1)
                .setDisplay("&6TEST LINKED MENU").build(),
                4,
                //action
                (p, item, clickType) -> {

            String msg;
            if(p.isOp()) {
                msg = "&aYou're OP";
            }else {
                msg = "&cYou're NOT OP";
            }

            p.closeInventory();
            p.sendMessage(Translator.color(msg));
        });
    }

}
