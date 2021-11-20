package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.StretchableMenu;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public class TestStretchableMenu extends StretchableMenu {

    public TestStretchableMenu(Plugin plugin, UUID viewer) {
        super(plugin, viewer);
    }

    @Override
    public void setContents() {
        ItemStack testItem = new ItemBuilder(Material.DIAMOND)
                .setDisplay("&aThis is a test").build();

        for (int i = 0; i < 20 ; i++) {
            this.setItem(testItem, i, (p, item, click)-> p.sendMessage(Translator.color("&6Works like fire !")));
        }
    }

    @Override
    public String getUniqueName() {
        return "stretchable";
    }

    @Override
    public String getTitle() {
        return "Elastic Menu";
    }

}
