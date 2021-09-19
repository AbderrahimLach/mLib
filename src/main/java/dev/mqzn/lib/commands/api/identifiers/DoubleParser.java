package dev.mqzn.lib.commands.api.identifiers;

import dev.mqzn.lib.commands.api.ArgumentParser;

public class DoubleParser implements ArgumentParser<Double> {


    @Override
    public boolean matches(String arg) {
        try {
            Double.valueOf(arg);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    @Override
    public Double parse(String arg) {
        return Double.valueOf(arg);
    }

}
