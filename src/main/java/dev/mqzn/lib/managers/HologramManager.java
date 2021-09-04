package dev.mqzn.lib.managers;

import dev.mqzn.lib.holograms.Hologram;
import java.util.LinkedHashSet;
import java.util.Set;

public class HologramManager {

    private final Set<Hologram> holograms;

    public HologramManager() {
        this.holograms = new LinkedHashSet<>();
    }

    public void addHologram(Hologram hologram) {
        this.holograms.add(hologram);
    }

    public void removeHologram(Hologram hologram) {
        this.holograms.remove(hologram);
    }

    public Set<Hologram> getHolograms() {
        return holograms;
    }

}
