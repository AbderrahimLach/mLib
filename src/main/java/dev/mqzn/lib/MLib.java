package dev.mqzn.lib;

import dev.mqzn.lib.commands.test.OpenMenuCommand;
import dev.mqzn.lib.managers.CommandManager;
import dev.mqzn.lib.managers.HologramManager;
import dev.mqzn.lib.managers.MenuManager;
import dev.mqzn.lib.menus.listeners.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class MLib extends JavaPlugin {

    private static MLib instance;

    private MenuManager menuManager;
    private CommandManager commandManager;
    private HologramManager hologramManager;

    @Override
    public void onEnable() {

        // Plugin startup logic
        instance = this;

        menuManager = new MenuManager();
        commandManager = new CommandManager();
        hologramManager = new HologramManager();

        //mLib test commands
        commandManager.registerCommand(new OpenMenuCommand());

        //mLib menu listener
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

    public HologramManager getHologramManager() {
        return hologramManager;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }


}
