package dev.mqzn.lib.menus;

import dev.mqzn.lib.menus.items.MenuItem;

import java.util.Collection;

public interface IStretchableMenu {

    /**
     * Items per row
     */
    int MAX_ITEMS_PER_ROW = 9;

    /**
     * will be used to retrieve the number of items
     * @return contents of the menu
     */

    Collection<? extends MenuItem> getContents();

    /**
     *
     * Algorithm Calculates the rows based on the items provided
     * @return stretched rows
     */
    default int getRows() {

        int capacity = this.getContents().size();

        if(capacity <= MAX_ITEMS_PER_ROW) return 1;
        if(capacity > 54) capacity = 54;

        /*
        int computedRows = 0;

        while (capacity >= MAX_ITEMS_PER_ROW) {
            computedRows++;
            capacity -= MAX_ITEMS_PER_ROW;
        }


        return computedRows;
       */

        return (int) Math.round( (double)capacity/MAX_ITEMS_PER_ROW);
    }

    //Alternative solution, but i love  the while loop XD



}
