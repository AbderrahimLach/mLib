package dev.mqzn.lib.commands.api;

public interface ArgumentParser<T> {


    /**
     *
     * parse the object
     *
     * @param arg the argument in it's string form
     * @return the arg object
     */


    T parse(String arg);

}
