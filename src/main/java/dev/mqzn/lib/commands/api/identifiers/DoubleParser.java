package dev.mqzn.lib.commands.api.identifiers;

import dev.mqzn.lib.commands.api.ArgumentParser;

public class DoubleParser implements ArgumentParser<Double> {



    @Override
    public Double parse(String arg) {
        try{
            return Double.valueOf(arg);
        }catch (Exception ex) {
            return  -1.0D;
        }
    }

}
