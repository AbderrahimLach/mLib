package dev.mqzn.lib.holograms;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import dev.mqzn.lib.MLib;
import dev.mqzn.lib.holograms.packets.HologramPacket;
import dev.mqzn.lib.holograms.packets.HologramPacketProvider;
import dev.mqzn.lib.holograms.v1_8.Minecraft18HologramPacketProvider;
import javafx.util.Pair;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import java.util.*;
import java.util.stream.Collectors;

public class Hologram implements IHologram {

    private final Collection<UUID> viewers; //collection so that we can use it in Bukkit#getOnlinePlayers()
    private final Location position;
    private final List<HologramLine> lines;
    protected List<HologramLine> lastLines;

    //private static final double distance = 0.23;

    public Hologram(Location position, Collection<UUID> viewers, String... lines) {

        if(position == null) {
            throw new IllegalArgumentException("Location parameter cannot be null !");
        }

        this.position = position;

        if(viewers == null) {
            viewers = ImmutableList.copyOf(Bukkit.getOnlinePlayers()).stream()
                    .map(Entity::getUniqueId).collect(Collectors.toSet());
        }
        this.viewers = viewers;

        this.lines = new ArrayList<>();
        this.lastLines = new ArrayList<>();

        for(String l : lines) {
            this.lines.add(new HologramLine(l));
        }

    }


    @Override
    public Location getPosition() {
        return position;
    }



    @Override
    public void send() {
        for (final UUID uuid : viewers) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                this.show(player);
            }
        }
        MLib.getInstance().getHologramManager().addHologram(this);
    }

    @Override
    public void destroy() {
        for (final UUID uuid : viewers) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                this.processDestroy(player);
            }
        }
        this.viewers.clear();
        MLib.getInstance().getHologramManager().removeHologram(this);
    }

    @Override
    public void addLines(String... lines) {
        for (final String line : lines) {
            this.lines.add(new HologramLine(line));
        }
        this.update();
    }

    @Override
    public void setLine(int index, String line) {
        if (index > this.lines.size() - 1) {
            this.lines.add(new HologramLine(line));
        }
        else if (this.lines.get(index) != null) {
            this.lines.get(index).setText(line);
        }
        else {
            this.lines.set(index, new HologramLine(line));
        }
        this.update();
    }

    @Override
    public void setLines(List<String> lines) {
        for (final UUID uuid : viewers) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                this.processDestroy(player);
            }
        }
        this.lines.clear();
        for (final String line : lines) {
            this.lines.add(new HologramLine(line));
        }
        this.update();
    }

    @Override
    public List<String> getLines() {
        final List<String> lines = new ArrayList<>();
        for (final HologramLine line : this.lines) {
            lines.add(line.getText());
        }
        return lines;
    }

    public List<HologramLine> getRawLines()
    {
        return this.lines;
    }



    protected void show(final Player player) {
        if (!player.getLocation().getWorld().equals(this.position.getWorld())) {
            return;
        }
        final Location first = this.position.clone().add(0.0, this.lines.size() * 0.23, 0.0);
        for (final HologramLine line : this.lines) {
            this.showLine(player, first.clone(), line);
            first.subtract(0.0, 0.23, 0.0);
        }
        this.viewers.add(player.getUniqueId());
    }

    @SuppressWarnings("unchecked")
    protected Pair<Integer, Integer> showLine(final Player player, final Location loc, final HologramLine line) {
        final HologramPacketProvider packetProvider = this.getPacketProvider();
        final HologramPacket hologramPacket = packetProvider.getPacketsFor(loc, line);
        if (hologramPacket != null) {
            hologramPacket.sendToPlayer(player);
            return new Pair<>(hologramPacket.getEntityIds().get(0), hologramPacket.getEntityIds().get(1));
        }
        return null;
    }

    protected void processDestroy(final Player player) {
        final List<Integer> ints = new ArrayList<>();
        for (final HologramLine line : this.lines) {
            if (line.getHorseId() == -1337) {
                ints.add(line.getSkullId());
            }
            else {
                ints.add(line.getSkullId());
                ints.add(line.getHorseId());
            }
        }
        final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(this.convertIntegers(ints));
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        this.viewers.remove(player.getUniqueId());
    }

    protected int[] convertIntegers(final List<Integer> integers) {
        final int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; ++i) {
            ret[i] = integers.get(i);
        }
        return ret;
    }

    public void update() {
        for (final UUID uuid : viewers) {
            final Player player = Bukkit.getPlayer(uuid);
            if (player != null && player.isOnline()) {
                this.update(player);
            }
        }
        this.lastLines.addAll(this.lines);
    }

    public void update(final Player player) {
        if (!player.getLocation().getWorld().equals(this.position.getWorld())) {
            return;
        }
        if (this.lastLines.size() != this.lines.size()) {
            this.processDestroy(player);
            this.show(player);
            return;
        }
        for (final HologramLine line : this.lines) {

            final String text = line.getText();

            try {

                final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
                container.getIntegers().write(0, line.getSkullId());
                final WrappedDataWatcher wrappedDataWatcher = new WrappedDataWatcher();
                wrappedDataWatcher.setObject(2, (Object) text);
                final List<WrappedWatchableObject> watchableObjects = Arrays.asList(
                        Iterators.toArray(wrappedDataWatcher.iterator(), WrappedWatchableObject.class));
                container.getWatchableCollectionModifier().write(0, watchableObjects);
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, container);
                } catch (Exception ignored) {
                }

            } catch (IndexOutOfBoundsException e) {
                this.processDestroy(player);
                this.show(player);
            }
        }
    }


    private HologramPacketProvider getPacketProvider() {
        return new Minecraft18HologramPacketProvider();
    }

    public Collection<UUID> getViewers() {
        return viewers;
    }
}
