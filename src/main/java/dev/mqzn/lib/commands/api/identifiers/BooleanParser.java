package dev.mqzn.lib.commands.api.identifiers;

import dev.mqzn.lib.commands.api.ArgumentParser;

public class BooleanParser implements ArgumentParser<Boolean> {


    @Override
    public Boolean parse(String arg) {
        return Boolean.parseBoolean(arg);
    }


}
