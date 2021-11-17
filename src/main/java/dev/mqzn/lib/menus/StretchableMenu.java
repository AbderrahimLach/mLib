package dev.mqzn.lib.menus;

import org.bukkit.plugin.Plugin;

import java.util.UUID;

public abstract class StretchableMenu extends Menu implements IStretchableMenu{

    private final int rows = ((IStretchableMenu)this).getRows();
    public StretchableMenu(Plugin plugin, UUID viewer) {
        super(plugin, viewer);
    }

    @Override
    public int getRows() {
        return rows;
    }
}
