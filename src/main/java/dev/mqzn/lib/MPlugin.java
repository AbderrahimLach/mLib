package dev.mqzn.lib;

import dev.mqzn.lib.commands.api.MCommand;
import dev.mqzn.lib.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class MPlugin extends JavaPlugin {

    private static MPlugin instance;

    public abstract void onPluginEnable();
    public abstract void onPluginDisable();

    @Override
    public void onEnable() {

        instance = this;
        onPluginEnable();
        confirmEnable();

    }

    @Override
    public void onDisable() {
        onPluginDisable();
        confirmDisable();
    }

    public static MPlugin getInstance() {
        return instance;
    }

    public abstract String[] enabledMessage();

    public abstract String[] disableMessage();

    public abstract MCommand[] getCommands();

    public Permission[] getPermissions() {
        return new Permission[0];
    }

    protected void confirmEnable() {
        for (String str : enabledMessage()) {
            Bukkit.getConsoleSender().sendMessage(FormatUtils.color(str));
        }
    }

    protected void confirmDisable() {
        for (String str : disableMessage()) {
            Bukkit.getConsoleSender().sendMessage(FormatUtils.color(str));
        }
    }

    public void registerListeners(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }


    public void registerCommands() {
        for(MCommand cmd : getCommands()) {
            MLib.getInstance().getCommandManager().registerCommand(cmd);
        }

    }

    public MCommand getRegisteredCommand(String name) {
        return MLib.getInstance().getCommandManager()
                .getCommand(name).orElse(null);
    }


}
