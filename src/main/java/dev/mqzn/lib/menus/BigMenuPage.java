package dev.mqzn.lib.menus;

import dev.mqzn.lib.menus.items.MenuItem;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class BigMenuPage<M extends MultiContentsMenu> extends MenuPage<M> {

    public BigMenuPage(Plugin plugin, int index, M menu) {
        super(plugin, index, menu, 6);
        this.calcItems(menu);
    }

    public void calcItems(M menu) {

        int spaces = this.getSpaces();
        int max = this.getIndex() * spaces;
        int min = max-spaces;

        List<MenuItem> total = menu.getTotalItems();

        int c = Math.min(max, total.size());
        for(int i = min; i < c; i++) {
            MenuItem item = total.get(i);
            item.setSlot(this.nextEmptySlot());
            this.setItem(item);
        }

    }


}
