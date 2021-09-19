package dev.mqzn.lib.managers;

import dev.mqzn.lib.MLib;
import dev.mqzn.lib.commands.api.ArgumentParser;
import dev.mqzn.lib.commands.api.MCommand;
import dev.mqzn.lib.commands.api.args.IP;
import dev.mqzn.lib.commands.api.identifiers.BooleanParser;
import dev.mqzn.lib.commands.api.identifiers.DoubleParser;
import dev.mqzn.lib.commands.api.identifiers.IPParser;
import dev.mqzn.lib.commands.api.identifiers.IntegerParser;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

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

        parsers = new LinkedHashMap<>();

        DoubleParser doubleParser = new DoubleParser();
        parsers.put(double.class, doubleParser);
        parsers.put(Double.class, doubleParser);

        IntegerParser integerParser = new IntegerParser();
        parsers.put(int.class, integerParser);
        parsers.put(Integer.class, integerParser);

        BooleanParser booleanParser = new BooleanParser();
        parsers.put(boolean.class, booleanParser);
        parsers.put(Boolean.class, booleanParser);

        parsers.put(IP.class, new IPParser());
        parsers.put(String.class, new ArgumentParser<String>() {

            @Override
            public boolean matches(String arg) {
                return true;
            }

            @Override
            public String parse(String arg) {
                return arg;
            }
        });

    }

    public Map<Class<?>, ArgumentParser<?>> getParsers() {
        return parsers;
    }

    public void registerCommand(MCommand mCommand) {
        commands.put(mCommand.getName(), mCommand);
        commandMap.register(mCommand.getName(), mCommand);
    }


    public boolean hasArgumentParser (Class<?> type){
        return parsers.containsKey(type);
    }

    public ArgumentParser<?> getArgumentParser (Class<?> type){
        if(!hasArgumentParser(type)) throw new IllegalArgumentException("Argument Parser for type " + type.getName() + " isn't registered!");

        return parsers.get(type);
    }

    public Optional<MCommand> getCommand(String name) {

        MCommand cmd = null;
        for(MCommand mc : commands.values()) {
            if(name.equals(mc.getName())) {
                cmd = mc;
                break;
            }
        }

        return Optional.ofNullable(cmd);
    }

    public static Class<?> getClazzType(String arg) {

        for(ArgumentParser<?> parser : MLib.getInstance().getCommandManager().getParsers().values()) {
            if(parser.matches(arg)) {
                return parser.parse(arg).getClass();
            }
        }

        return String.class;
    }


}
