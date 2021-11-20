package dev.mqzn.lib.menus.test;

import dev.mqzn.lib.menus.Menu;
import dev.mqzn.lib.utils.ItemBuilder;
import dev.mqzn.lib.utils.ItemEnchant;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
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
        ItemStack item = new ItemBuilder(Material.DIAMOND_SWORD)
                .setDisplay("&amLib Test Item")
                .setLore("&7&l&m-------------",
                        "&2Test Lore",
                        "&7&l&m-------------")
                .setUnbreakable(true)
                .addEnchants(ItemEnchant.of(Enchantment.DAMAGE_ALL, 1),
                        ItemEnchant.of(Enchantment.DURABILITY, 2))
                .addFlags(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_UNBREAKABLE)
                .build();

        this.setItem(item, (clicker, itemStack, clickType) -> clicker.sendMessage("Hello"));

    }

}
