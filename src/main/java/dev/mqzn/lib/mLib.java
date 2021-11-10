package dev.mqzn.lib;

import dev.mqzn.lib.commands.test.TestCommand;
import dev.mqzn.lib.managers.CommandManager;
import dev.mqzn.lib.managers.MenuManager;
import dev.mqzn.lib.menus.listeners.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class mLib extends JavaPlugin {

    private static mLib instance;

    private MenuManager menuManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {

        // Plugin startup logic
        instance = this;

        menuManager = new MenuManager();
        commandManager = new CommandManager();
        commandManager.registerCommand(new TestCommand());

        //mLib menu listener
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.reloadConfig();
    }

    public static mLib getInstance() {
        return instance;
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }


}
