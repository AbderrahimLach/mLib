package dev.mqzn.lib.holograms;

import dev.mqzn.lib.MLib;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class AnimatedHologram extends Hologram {

    private final long interval;
    private final Consumer<Hologram> update;
    private boolean displayed;

    public AnimatedHologram(Location position, Collection<UUID> viewers, long interval,
                            Consumer<Hologram> update, String... lines) {

        super(position, viewers, lines);

        this.interval = interval;
        this.update = update;
        this.displayed = false;

    }

    @Override
    public void send() {
        if (this.displayed) {
            this.update();
            return;
        }
        super.send();
        this.displayed = true;
        new BukkitRunnable() {
            public void run() {
                if (!AnimatedHologram.this.displayed) {
                    this.cancel();
                }
                else {
                    AnimatedHologram.this.update();
                }
            }
        }.runTaskTimerAsynchronously(MLib.getInstance(), 0L, this.interval * 20L);
    }

    @Override
    public void setLine(final int index, final String line) {
        if (index > this.getLines().size() - 1) {
            this.getRawLines().add(new HologramLine(line));
        }
        else if (this.getRawLines().get(index) != null) {
            this.getRawLines().get(index).setText(line);
        }
        else {
            this.getRawLines().set(index, new HologramLine(line));
        }
    }

    @Override
    public void setLines(final List<String> lines) {

        for (final UUID uuid : this.getViewers()) {
            final Player player = Bukkit.getPlayer(uuid);

            if (player != null && player.isOnline()) {
                this.processDestroy(player);
            }
        }

        this.getRawLines().clear();
        for (final String line : lines) {
            this.getRawLines().add(new HologramLine(line));
        }

    }

    @Override
    public void destroy() {
        super.destroy();
        this.displayed = false;
    }



    @Override
    public void update() {
        this.update.accept(this);
        if (!this.displayed) {
            return;
        }

        for (final UUID uuid : getViewers()) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                this.update(player);
            }
        }
        this.lastLines = this.getRawLines();
    }



}
