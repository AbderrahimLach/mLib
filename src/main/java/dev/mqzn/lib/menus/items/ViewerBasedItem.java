package dev.mqzn.lib.menus.items;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.function.BiConsumer;

public class ViewerBasedItem extends MenuItem {

    private final BiConsumer<Player, ItemStack> itemBuild;

    public ViewerBasedItem(Player player, ItemStack item, BiConsumer<Player, ItemStack> itemBuild,
                           int slot, BiConsumer<Player, ItemStack> itemActions) {

        super(buildItem(player, item, itemBuild), slot, itemActions);
        this.itemBuild = itemBuild;
    }

    private static ItemStack buildItem(Player player, ItemStack itemStack,
                                       BiConsumer<Player, ItemStack> itemBuild) {

        itemBuild.accept(player, itemStack);
        return itemStack;
    }

    public BiConsumer<Player, ItemStack> getItemBuild() {
        return itemBuild;
    }


}
