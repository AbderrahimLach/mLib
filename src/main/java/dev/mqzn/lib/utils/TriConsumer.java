package dev.mqzn.lib.utils;

import java.util.Objects;
import java.util.function.Consumer;

/**
 * Represents an operation that accepts three input arguments and returns no
 * result (like a void function that takes 3 parameters).
 * This is the three-arity specialization of {@link Consumer}.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <M> the type of the third argument to the operation
 *
 * @see Consumer
 * @see java.util.function.BiConsumer
 */

@FunctionalInterface
public interface TriConsumer<T, U, M> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     */
    void accept(T t, U u, M m);


    default TriConsumer<T, U, M> andThen(TriConsumer<? super T, ? super U, ? super M> after) {
        Objects.requireNonNull(after);

        return (l, r, m) -> {
            accept(l, r, m);
            after.accept(l, r, m);
        };
    }

}

