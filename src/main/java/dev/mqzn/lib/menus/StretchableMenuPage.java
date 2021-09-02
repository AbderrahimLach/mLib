package dev.mqzn.lib.menus;

public class StretchableMenuPage<M extends PaginatedMenu> extends MenuPage<M> implements IStretchableMenu {

    public StretchableMenuPage(int index, M menu) {
        super(index, menu);
    }

    @Override
    public int getItemsSize() {
        return this.getCachedItems().size();
    }

    @Override
    public int getRows() {
        return stretchedRows();
    }


}
