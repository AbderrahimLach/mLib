package dev.mqzn.lib.managers;

import dev.mqzn.lib.menus.Menu;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MenuManager {

    private final Map<UUID, Menu> openMenus;

    public MenuManager() {
        openMenus = new ConcurrentHashMap<>();
    }

    public Map<UUID, Menu> getOpenMenus() {
        return openMenus;
    }


    public Menu getOpenMenu(UUID viewerId) {
        return openMenus.get(viewerId);
    }


    public void register(UUID viewer, Menu menu) {
        openMenus.put(viewer, menu);
    }

    public void unregister(UUID viewer) {
        openMenus.remove(viewer);
    }


}
