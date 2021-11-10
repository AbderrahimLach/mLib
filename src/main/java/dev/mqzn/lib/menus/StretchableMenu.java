package dev.mqzn.lib.menus;

import org.bukkit.plugin.Plugin;

import java.util.UUID;

public abstract class StretchableMenu extends Menu{

    private final int rows;
    public StretchableMenu(Plugin plugin, UUID viewer) {
        super(plugin, viewer);
        rows = buildRows();
    }

    private int buildRows() {
        int capacity = this.getContents().size();
        final int MAX_ITEMS_PER_ROW = 9;

        if(capacity <= MAX_ITEMS_PER_ROW) return 1;
        if(capacity > 54) capacity = 54;

        int computedRows = 0;

        while (capacity >= MAX_ITEMS_PER_ROW) {
            computedRows++;
            capacity -= MAX_ITEMS_PER_ROW;
        }

        //Alternative solution, but i love  the while loop XD
        // computedRows = Math.round( (double)capacity/MAX_ITEMS_PER_ROW);
        return computedRows;
    }

    @Override
    public int getRows() {
        return rows;
    }


}
