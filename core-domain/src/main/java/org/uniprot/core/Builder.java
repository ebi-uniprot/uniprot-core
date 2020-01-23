package org.uniprot.core;

import javax.annotation.Nonnull;

/**
 * The contract for all builders to adhere to, and also implement a {@code from} method to create a
 * builder whose values are copied from a specified instance.
 *
 * <p>Created 10/01/19
 *
 * @author Edd
 */
public interface Builder<B extends Builder, T> {
    /**
     * Creates the instance of type, {@code T}.
     *
     * @return an instance of type, {@code T}.
     */
    @Nonnull
    T build();
}
