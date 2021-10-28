package dev.mqzn.lib.menus;

import java.util.UUID;

public abstract class StretchableMenu extends Menu{

    private final int rows;
    public StretchableMenu(UUID viewer) {
        super(viewer);
        rows = buildRows();
    }

    private int buildRows() {
        int capacity = this.getCachedItems().size();
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
