package dev.mqzn.lib.commands.api.identifiers;

import dev.mqzn.lib.commands.api.ArgumentParser;

public class IntegerParser implements ArgumentParser<Integer> {



    public boolean matches(String arg) {

        try {
            Integer.parseInt(arg);
            return true;
        }catch (NumberFormatException ex) {
            return false;
        }

    }

    @Override
    public Integer parse(String arg) {
        return matches(arg) ? Integer.parseInt(arg) : -1;
    }


}
