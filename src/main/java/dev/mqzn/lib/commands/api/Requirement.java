package dev.mqzn.lib.commands.api;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.command.CommandSender;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

public class Requirement {

    private final Executor actions;
    private final Criteria criteria;
    private final HashMap<Integer, UsageArg> argParses = Maps.newHashMap();

    protected Requirement(Criteria criteria, Executor actions) {
        this.criteria = criteria;
        this.actions = actions;

    }

    protected Requirement(Criteria criteria) {
        this.criteria = criteria;
        this.actions = (sender, args) -> {};
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
        return this.getUsage(command, null);
    }

    public String getUsage(MCommand command, SubCommand subCommand) {
        StringBuilder rest = new StringBuilder();
        argParses.values().stream().sorted().map(UsageArg::fullName)
                .forEachOrdered(str -> rest.append(str).append(" "));

        return Translator.color("&8&l[&9+&8&l] &a/"
                + command.getLabel() + " " +
                (subCommand != null ? (subCommand.getName() + " ") : "" )
                + rest.toString());
    }

    public UsageArg getArgParse(int position) {
        return argParses.get(position);
    }
    public Collection<? extends UsageArg> getArgParses() {
        return argParses.values();
    }

    public void execute(CommandSender sender, List<CommandArg> args) {
        if(criteria == null || this.actions == null) return;

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
