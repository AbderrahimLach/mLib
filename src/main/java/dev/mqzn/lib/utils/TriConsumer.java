package dev.mqzn.lib.utils;

import javax.annotation.Nonnull;
import java.util.Objects;

@FunctionalInterface
public interface TriConsumer<T, U, M>  {

    void accept(T t, U u, M m);


    @Nonnull
    default TriConsumer<T, U, M> andThen(TriConsumer<? super T, ? super U, ? super M> after) {
        Objects.requireNonNull(after);

        return (l, r, m) -> {
            accept(l, r, m);
            after.accept(l, r, m);
        };

    }

}