package dev.mqzn.lib.menus;

public abstract class StretchableMenu extends Menu implements IStretchableMenu{

    public StretchableMenu() {
        super();
    }

    @Override
    public int getItemsSize() {
        return getCachedItems().size();
    }

    @Override
    public int getRows() {
        return stretchedRows();
    }


}
