package dev.mqzn.lib.commands.api;

import com.google.common.base.Objects;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class SubCommand extends Requirement {

    private final Set<Requirement> requirements;
    private final Set<SubCommand> children;

    public SubCommand(MCommand command, Predicate<CommandArg[]> argLength) {
        super(command, argLength);
        requirements = new LinkedHashSet<>(Arrays.asList(setRequirements()));
        children = requirements.stream()
                .filter(req -> req instanceof SubCommand).map(req -> (SubCommand)req)
                .collect(Collectors.toSet());
    }

    @Override
    public MCommand getCommand() {
        return super.getCommand();
    }

    public Set<Requirement> getRequirements() {
        return requirements;
    }

    public Set<SubCommand> getChildren() {
        return children;
    }

    public boolean isParent() {
        return !children.isEmpty();
    }

    public void sendUsage(CommandSender sender, CommandArg[] args) {
        sender.sendMessage(Translator.color("&9/" + this.getName() + " &eUsages: "));
        for(Requirement reqs : this.getRequirements()) {
            if(reqs.getArgsCondition().test(args)) {
                sender.sendMessage(Translator.color("&7&l- " + reqs.getUsage()));
            }
        }
    }

    public abstract String getName();

    public abstract String getPermission();

    public abstract String getDescription();

    public abstract int getPosition();

    public abstract Requirement[] setRequirements();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubCommand)) return false;
        if (!super.equals(o)) return false;
        SubCommand that = (SubCommand) o;
        return getName().equals(that.getName()) && getPosition() == that.getPosition() &&
                Objects.equal(getRequirements(), that.getRequirements()) &&
                Objects.equal(getChildren(), that.getChildren());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(super.hashCode(), getName(),
                getPosition(), getRequirements(), getChildren());
    }


}
