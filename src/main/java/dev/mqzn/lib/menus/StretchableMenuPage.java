package dev.mqzn.lib.menus;

import com.google.common.base.Objects;

public class StretchableMenuPage<M extends PaginatedMenu> extends MenuPage<M> {

    private final int rows;
    public StretchableMenuPage(int index, M menu) {
        super(index, menu);
        rows = this.buildRows();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StretchableMenuPage)) return false;
        if (!super.equals(o)) return false;
        StretchableMenuPage<?> that = (StretchableMenuPage<?>) o;
        return getRows() == that.getRows();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getRows());
    }
}
