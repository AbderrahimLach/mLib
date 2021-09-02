package dev.mqzn.lib;

import dev.mqzn.lib.commands.test.OpenMenuCommand;
import dev.mqzn.lib.commands.test.SubsCommand;
import dev.mqzn.lib.managers.CommandManager;
import dev.mqzn.lib.managers.MenuManager;
import dev.mqzn.lib.menus.listeners.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public final class MLib extends JavaPlugin {

    private static MLib instance;

    private MenuManager menuManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {

        // Plugin startup logic
        instance = this;

        menuManager = new MenuManager();
        commandManager = new CommandManager();

        PluginHandler.getPlugins().forEach(p -> {
            if(p != null) {
                p.registerCommands();
                for(Permission permission : p.getPermissions()) {
                    Bukkit.getPluginManager().addPermission(permission);
                }
            }
        });

        commandManager.registerCommand(new OpenMenuCommand());
        commandManager.registerCommand(new SubsCommand());
        commandManager.handleRegistries();

        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.reloadConfig();
    }

    public static MLib getInstance() {
        return instance;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

}
