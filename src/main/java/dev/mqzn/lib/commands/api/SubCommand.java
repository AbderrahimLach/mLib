package dev.mqzn.lib.commands.api;

import com.google.common.base.Objects;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class SubCommand extends Requirement {

    private final String name;
    private final int position;

    private final HashSet<Requirement> requirements;
    private final HashSet<SubCommand> children;

    public SubCommand(String name, int position) {
        super((args)-> position >= 0 && position <= args.size()-1
                && args.get(position) != null
                && args.get(position).getArgument().equalsIgnoreCase(name));

        this.name = name;
        this.position = position;

        requirements = new HashSet<>();
        this.setRequirements();

        children = this.collectChildren();
        this.separateRequirements();
    }
    private HashSet<SubCommand> collectChildren() {
        HashSet<SubCommand> kids = new HashSet<>();
        for (Requirement requirement : requirements) {
            if(requirement instanceof SubCommand) {
                kids.add((SubCommand)requirement);
            }
        }
        return kids;
    }
    private void separateRequirements() {
        this.requirements.removeIf(rq -> rq instanceof SubCommand);
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

    public void sendUsage(MCommand command, CommandSender sender) {
        for(Requirement reqs : this.getRequirements()) {
            sender.sendMessage(reqs.getUsage(command, this));
        }
        for(SubCommand child : this.getChildren()) {
            sender.sendMessage(child.getUsage(command, this));
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
        this.addRequirement(requirement);
    }

    protected void addChildren(SubCommand... children) {
        this.children.addAll(Arrays.asList(children));
    }

    protected void addRequirement(Requirement requirement) {
        if(requirement instanceof  SubCommand) {
            children.add((SubCommand)requirement);
        }else {
            requirements.add(requirement);
        }
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
