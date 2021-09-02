package dev.mqzn.lib.commands.api.identifiers;

import dev.mqzn.lib.commands.api.ArgumentParser;

public class LongParser implements ArgumentParser<Long> {



    public boolean matches(String arg) {

        try {
            Long.parseLong(arg);
            return true;
        }catch (NumberFormatException ex) {
            return false;
        }

    }

    @Override
    public Long parse(String arg) {
        return matches(arg) ? Long.parseLong(arg) : -1L;
    }


}
