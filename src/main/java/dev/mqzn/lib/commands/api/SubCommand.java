package dev.mqzn.lib.commands.api;

import com.google.common.base.Objects;
import dev.mqzn.lib.utils.Translator;
import org.bukkit.command.CommandSender;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class SubCommand extends Requirement {

    private final String name;
    private final int position;

    private final Set<Requirement> requirements;
    private final Set<SubCommand> children;

    public SubCommand(String name, int position, Executor executor) {
        super( (args) -> !args.isEmpty()
                && args.get(position).getArgument()
                        .equalsIgnoreCase(name)
                , executor);

        this.name = name;
        this.position = position;

        requirements = new LinkedHashSet<>();
        this.setRequirements();
        children = requirements.stream()
                .filter(req -> req instanceof SubCommand).map(req -> (SubCommand)req)
                .collect(Collectors.toSet());
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

    public void sendUsage(MCommand command, CommandSender sender, List<CommandArg> args) {
        sender.sendMessage(Translator.color("&9/" + this.getName() + " &eUsages: "));
        for(Requirement reqs : this.getRequirements()) {
            if(reqs.getCriteria().test(args)) {
                sender.sendMessage(Translator.color("&7&l- " + reqs.getUsage(command)));
            }
        }
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public abstract String getPermission();

    public abstract String getDescription();

    public abstract void setRequirements();

    protected void addRequirement(Criteria criteria, Executor executor, UsageArg... args) {
        Requirement requirement = Requirement.of(criteria, executor);
        for(UsageArg arg : args) {
            requirement.setArg(arg);
        }
        requirements.add(requirement);
    }

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
