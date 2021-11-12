package dev.mqzn.lib.commands.api;

import com.google.common.base.Objects;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Requirement {

    private final Executor actions;
    private final Criteria criteria;
    private final Map<Integer, UsageArg> argParses;

    protected Requirement(Criteria criteria, Executor actions) {
        this.criteria = criteria;
        this.actions = actions;
        argParses = new HashMap<>();
    }

    public static Requirement of(Criteria criteria, Executor executor) {
        return new Requirement(criteria, executor);
    }

    public Criteria getCriteria() {
        return criteria;
    }


    public Executor getExecutor() {
        return actions;
    }

    public void setArg(String argName, int argPosition, Class<?> argClassType, UsageArg.ArgumentType type) {
        argParses.put(argPosition, new UsageArg(argName, argPosition, argClassType, type));
    }

    public void setArg(UsageArg arg) {
        argParses.put(arg.getPosition(), arg);
    }

    public String getUsage(MCommand command) {
        String rest = argParses.values().stream().map(UsageArg::fullName).collect(Collectors.joining());
        return Translator.color("&c/" + command.getLabel() + " " + rest);
    }


    public Map<Integer, UsageArg> getArgParses() {
        return argParses;
    }

    public void execute(CommandSender sender, List<CommandArg> args) {
        if(criteria.test(args)) {
            this.getExecutor().execute(sender, args);
        }
    }

    @FunctionalInterface
    public interface Executor {

         void execute(CommandSender sender, List<CommandArg> args);

    }

    @FunctionalInterface
    public interface Criteria extends Predicate<List<CommandArg>> {


    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof dev.mqzn.lib.commands.api.Requirement)) return false;
        dev.mqzn.lib.commands.api.Requirement that = (dev.mqzn.lib.commands.api.Requirement) o;
        return getCriteria() == that.getCriteria() &&
                Objects.equal(getExecutor(), that.getExecutor()) &&
                Objects.equal(argParses, that.argParses);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getExecutor(), getCriteria(), argParses);
    }


}
