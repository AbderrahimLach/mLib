package dev.mqzn.lib.commands.api;

import com.google.common.base.Objects;

public class Argument {

    private final String name;
    private final int position;
    private final Class<?> typeClass;

    public Argument(String name, int position, Class<?> typeClass) {
        this.name = name;
        this.position = position;
        this.typeClass = typeClass;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public Class<?> getTypeClass() {
        return typeClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Argument)) return false;
        Argument argument = (Argument) o;
        return getPosition() == argument.getPosition() &&
                Objects.equal(getName(), argument.getName()) &&
                Objects.equal(getTypeClass(), argument.getTypeClass());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getName(), getPosition(), getTypeClass());
    }

    @Override
    public String toString() {
        return "<" + this.getName().toUpperCase() + "> ";
    }

}
