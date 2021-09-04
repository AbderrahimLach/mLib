package dev.mqzn.lib.holograms;

import dev.mqzn.lib.utils.EntityUtils;
import dev.mqzn.lib.utils.FormatUtils;

public class HologramLine {

    private final int skullId, horseId;
    private String text;

    public HologramLine(final String text) {
        this.skullId = EntityUtils.getFakeEntityId();
        this.horseId = EntityUtils.getFakeEntityId();
        this.text = FormatUtils.color(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getHorseId() {
        return horseId;
    }

    public int getSkullId() {
        return skullId;
    }


}
