package dev.mqzn.lib.backend;

import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.io.ByteStreams;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginAwareness;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.logging.Level;

public class SpigotConfigFile extends SpigotFile {

    private FileConfiguration config;

    public SpigotConfigFile(Plugin plugin, String fileName) {
        super(plugin, fileName, true);
        config = YamlConfiguration.loadConfiguration(file);
    }


    public FileConfiguration getConfig() {
        return config;
    }

    public void saveConfig() {
        try {
            config.save(getFile());
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private boolean isStrictlyUTF8() {
        return plugin.getDescription().getAwareness().contains(PluginAwareness.Flags.UTF8);
    }


    public void reloadConfig() {

        this.config = YamlConfiguration.loadConfiguration(file);
        InputStream defConfigStream = plugin.getResource(getFileName());

        if (defConfigStream != null) {
            YamlConfiguration defConfig;

            if (!this.isStrictlyUTF8() && !FileConfiguration.UTF8_OVERRIDE) {
                defConfig = new YamlConfiguration();

                byte[] contents;
                try {
                    contents = ByteStreams.toByteArray(defConfigStream);
                } catch (IOException ex) {
                    plugin.getLogger().log(Level.SEVERE, "Unexpected failure reading config.yml", ex);
                    return;
                }

                String text = new String(contents, Charset.defaultCharset());
                if (!text.equals(new String(contents, Charsets.UTF_8))) {
                    plugin.getLogger().warning("Default system encoding may have misread config.yml from plugin jar");
                }

                try {
                    defConfig.loadFromString(text);
                } catch (InvalidConfigurationException ex) {
                    plugin.getLogger().log(Level.SEVERE, "Cannot load configuration from jar", ex);
                }

            } else {
                defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8));
            }

            this.config.setDefaults(defConfig);
        }

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpigotConfigFile)) return false;
        if (!super.equals(o)) return false;
        SpigotConfigFile that = (SpigotConfigFile) o;
        return super.equals(o) && Objects.equal(getConfig(), that.getConfig());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getConfig());
    }



}
