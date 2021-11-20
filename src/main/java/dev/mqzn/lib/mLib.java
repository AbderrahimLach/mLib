package dev.mqzn.lib;

import dev.mqzn.lib.commands.test.TestCommand;
import dev.mqzn.lib.events.InteractListener;
import dev.mqzn.lib.managers.CommandManager;
import dev.mqzn.lib.menus.listeners.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class mLib extends JavaPlugin {

    private static mLib instance;

    @Override
    public void onEnable() {
        instance = this;

        CommandManager.getInstance().registerCommand(new TestCommand());

        //mLib menu listener
        Bukkit.getPluginManager().registerEvents(new MenuListener(), this);

        //mLib interact listener
        Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.reloadConfig();
    }

    public static mLib getInstance() {
        return instance;
    }
}
