package dev.mqzn.lib.board;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.Team;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BoardEntry {


    /**
     * BoardEntry is a class that defines the team entry
     *
     */

    private final static List<ChatColor> COLORS;

    static {
        COLORS = Arrays.asList(ChatColor.values());
    }


    private final String uniqueId, text;
    private final int position;
    private final Board board;

    public BoardEntry(Board board, String text, int position) {
        this.board = board;
        this.text = text;
        this.position = position;
        this.uniqueId = genUniqueId();
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public int getPosition() {
        return position;
    }

    public String getText() {
        return text;
    }

    public Board getBoard() {
        return board;
    }

    protected String genUniqueId() {

        String c = randomColor() + ChatColor.WHITE.toString();

        while (board.getIdentifiers().contains(c)) {
            c += randomColor() + ChatColor.WHITE.toString();
        }

        if(c.length() > 16) return genUniqueId();
        board.getIdentifiers().add(c);

        return c;
    }

    private ChatColor randomColor() {
        return COLORS.get(ThreadLocalRandom.current().nextInt(COLORS.size()));
    }


    public Team toTeam() {
        return null;
    }

}
