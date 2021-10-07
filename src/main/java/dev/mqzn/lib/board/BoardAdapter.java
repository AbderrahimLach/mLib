package dev.mqzn.lib.board;

import org.bukkit.entity.Player;

import java.util.List;

public interface BoardAdapter {


    String getTitle(Player player);

    List<String> getLines(Player player);


}
