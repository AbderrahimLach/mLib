package dev.mqzn.lib.menus;

import com.google.common.base.Objects;
import org.bukkit.plugin.Plugin;

public class StretchableMenuPage<M extends PaginatedMenu> extends MenuPage<M> implements IStretchableMenu {

    private final int rows;
    public StretchableMenuPage(Plugin plugin, int index, M menu) {
        super( plugin, index, menu);
        rows = ((IStretchableMenu)this).getRows();
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
