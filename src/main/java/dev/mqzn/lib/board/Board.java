package dev.mqzn.lib.board;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Board {

    private final List<String> identifiers;
    private final String title;
    private final List<BoardEntry> entries;
    private final UUID id;

    private final Scoreboard scoreboard;

    public Board(Player player, BoardAdapter adapter) {

        this.identifiers = new ArrayList<>();
        this.title = adapter.getTitle(player);
        this.id = player.getUniqueId();

        //initializing identifiers
        {
           List<String> lines = adapter.getLines(player);
           entries = lines.stream()
                   .map(l -> new BoardEntry(this, l, lines.indexOf(l)))
                   .collect(Collectors.toList());
        }

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    }

    public List<String> getIdentifiers() {
        return identifiers;
    }

    public String getTitle() {
        return title;
    }

    public List<BoardEntry> getEntries() {
        return entries;
    }


    public UUID getViewerId() {
        return id;
    }

    public Objective getObjective() {

        Objective obj;
        if(scoreboard.getObjective("mBoard") != null) {
            return scoreboard.getObjective("mBoard");
        }

        return sc
    }


}
