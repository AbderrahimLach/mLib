package dev.mqzn.lib;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public class PluginHandler {

    private final static Set<MPlugin> plugins;
    //IGNORE THIS COMMENT
    static {
        plugins = new HashSet<>();
    }

    public static <P extends JavaPlugin> JavaPlugin getPluginInstance(Class<P> clazz) {
        return JavaPlugin.getPlugin(clazz);
    }

    public static MPlugin getRegisteredPlugin(String pluginName) {
        return plugins.stream().filter(p -> p.getName().equals(pluginName))
                .findFirst().orElse(null);
    }


    public static void registerPlugin(MPlugin plugin) {
        plugins.add(plugin);
    }

    public static Set<MPlugin> getPlugins() {
        return plugins;
    }

}
