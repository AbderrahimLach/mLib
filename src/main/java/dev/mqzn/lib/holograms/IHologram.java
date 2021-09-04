package dev.mqzn.lib.holograms;

import org.bukkit.Location;

import java.util.List;

public interface IHologram {

    /**
     * {@link IHologram} interface stands for how
     * every hologram should handle it's holograms
     * and that's it :)
     *
     * @Author Mqzn
     * @Discord Mqzn#8141
     *
     * @return the bukkit location of the hologram
     */

    Location getPosition();


    /**
     * {@link IHologram} interface stands for how
     * every hologram should handle it's holograms
     * and that's it :)
     *
     * This method allows the base
     * holograms to handle sending the holograms
     * using a variety of ways
     *
     * @Author Mqzn
     * @Discord Mqzn#8141
     */

    void send();

    void destroy();

    void addLines(String... lines);

    void setLine(int index, String line);

    void setLines(List<String> lines);

    List<String> getLines();



}
