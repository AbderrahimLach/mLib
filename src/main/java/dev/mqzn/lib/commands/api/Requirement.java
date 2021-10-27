package dev.mqzn.lib.commands.api;

import com.google.common.base.Objects;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Requirement {

    private BiConsumer<CommandSender, CommandArg[]> actions;
    private final Predicate<CommandArg[]> argsCondition;
    private final Map<Integer, Argument> argParses;
    private final MCommand command;

    public Requirement(MCommand command, Predicate<CommandArg[]> argsCondition, BiConsumer<CommandSender, CommandArg[]> actions) {
        this.command = command;
        this.argsCondition = argsCondition;
        this.actions = actions;
        argParses = new HashMap<>();
    }

    public Requirement(MCommand command, Predicate<CommandArg[]> argsCondition) {
        this.command = command;
        this.argsCondition = argsCondition;
        argParses = new HashMap<>();
    }

    public void setActions(BiConsumer<CommandSender, CommandArg[]> actions) {
        this.actions = actions;
    }

    public boolean hasActions() {
        return actions != null;
    }

    public Predicate<CommandArg[]> getArgsCondition() {
        return argsCondition;
    }

    public MCommand getCommand() {
        return command;
    }

    public BiConsumer<CommandSender, CommandArg[]> getActions() {
        return actions;
    }

    public void setArg(String argName, int argPosition, Class<?> argClassType, Argument.ArgumentType type) {
        argParses.put(argPosition, new Argument(argName, argPosition, argClassType, type));
    }

    public String getUsage() {
        String rest = argParses.values().stream().map(Argument::toString).collect(Collectors.joining());
        return Translator.color("&c/" + command.getLabel() + " " + rest);
    }

    public Map<Integer, Argument> getArgParses() {
        return argParses;
    }

    public void execute(CommandSender sender, CommandArg[] args) {
        if(argsCondition.test(args)) {
            this.getActions().accept(sender, args);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof dev.mqzn.lib.commands.api.Requirement)) return false;
        dev.mqzn.lib.commands.api.Requirement that = (dev.mqzn.lib.commands.api.Requirement) o;
        return  command.equals(that.getCommand()) &&
                getArgsCondition() == that.getArgsCondition() &&
                Objects.equal(getActions(), that.getActions()) &&
                Objects.equal(argParses, that.argParses);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getActions(), getArgsCondition(), argParses);
    }


}
