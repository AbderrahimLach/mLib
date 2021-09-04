// 
// Decompiled by Procyon v0.5.36
// 

package dev.mqzn.lib.holograms.packets;


import dev.mqzn.lib.holograms.HologramLine;
import org.bukkit.Location;

public interface HologramPacketProvider {


    HologramPacket getPacketsFor(final Location position, final HologramLine line);

}
