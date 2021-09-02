package dev.mqzn.lib.menus;


public abstract class PreCreatedPaginatedMenu extends PaginatedMenu {

    @Override
    public int calculateMaxPages() {
        return getCachedPages().size();
    }

}
