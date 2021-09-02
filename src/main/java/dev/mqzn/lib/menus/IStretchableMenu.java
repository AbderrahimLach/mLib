package dev.mqzn.lib.menus;

public interface IStretchableMenu extends IMenu {


    int getItemsSize();

    default int stretchedRows() {

        int capacity = getItemsSize();
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






}
