package dev.mqzn.lib.managers;

import dev.mqzn.lib.commands.api.ArgumentParser;
import dev.mqzn.lib.commands.api.MCommand;
import dev.mqzn.lib.commands.api.identifiers.BooleanParser;
import dev.mqzn.lib.commands.api.identifiers.DoubleParser;
import dev.mqzn.lib.commands.api.identifiers.IntegerParser;
import dev.mqzn.lib.commands.api.identifiers.MaterialParser;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandMap;
import java.lang.reflect.Field;
import java.util.*;

public class CommandManager {

    private final Map<String, MCommand> commands;
    private CommandMap commandMap;
    private final Map<Class<?>, ArgumentParser<?>> parsers;

    public CommandManager() {

        //initialization

        this.commands = new HashMap<>();
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            commandMap = (CommandMap) field.get(Bukkit.getServer());

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        parsers = new HashMap<>();
        parsers.put(String.class, (ArgumentParser<String>) arg -> arg);
        parsers.put(Material.class, new MaterialParser());

        DoubleParser doubleParser = new DoubleParser();
        parsers.put(double.class, doubleParser);
        parsers.put(Double.class, doubleParser);

        IntegerParser integerParser = new IntegerParser();
        parsers.put(int.class, integerParser);
        parsers.put(Integer.class, integerParser);

        BooleanParser booleanParser = new BooleanParser();
        parsers.put(boolean.class, booleanParser);
        parsers.put(Boolean.class, booleanParser);

    }

    public void registerCommand(MCommand mCommand) {
        commands.put(mCommand.getName(), mCommand);
    }

    public void unregisterCommand(MCommand mCommand) {
        commands.remove(mCommand.getName());
    }

    public void unregisterCommand(String command) {
        commands.remove(command);
    }

    public boolean hasArgumentParser (Class<?> type){
        return parsers.containsKey(type);
    }

    public ArgumentParser<?> getArgumentParser (Class<?> type){
        if(!hasArgumentParser(type)) throw new IllegalArgumentException("Argument Parser for type " + type.getName() + " isn't registered!");

        return parsers.get(type);
    }

    public  Optional<MCommand> getCommand(String name) {

        MCommand cmd = null;
        for(MCommand mc : commands.values()) {
            if(name.equals(mc.getName())) {
                cmd = mc;
                break;
            }
        }

        return Optional.ofNullable(cmd);
    }


    public void handleRegistries() {
        commands.forEach((name, cmd) -> commandMap.register(name, cmd));
    }


}
