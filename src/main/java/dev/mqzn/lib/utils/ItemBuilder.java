package dev.mqzn.lib.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemBuilder {

    private ItemStack itemStack;


    public ItemBuilder create(Material material, int amount) {
        itemStack = new ItemStack(material, amount);
        return this;
    }

    public ItemBuilder create(Material material, int amount, short damage) {
        itemStack = new ItemStack(material, amount, damage);
        return this;
    }

    public ItemBuilder setDisplay(String name) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(FormatUtils.color(name));
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setLore(String... lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(FormatUtils.colorList(Arrays.asList(lore)));
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(FormatUtils.colorList(lore));
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder addFlags(ItemFlag... flags) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(flags);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        ItemMeta meta = itemStack.getItemMeta();
        meta.spigot().setUnbreakable(unbreakable);
        itemStack.setItemMeta(meta);

        return this;
    }

    public ItemBuilder addEnchants(ItemEnchant... enchants) {
        ItemMeta meta = itemStack.getItemMeta();
        for (ItemEnchant itemEnchant : enchants) {
            meta.addEnchant(itemEnchant.getEnchantment(), itemEnchant.getLevel(), true);
        }
        itemStack.setItemMeta(meta);

        return this;
    }



}

