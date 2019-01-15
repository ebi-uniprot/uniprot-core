package uk.ac.ebi.uniprot.domain;

// TODO: 11/01/19 this should be moved out into a higher level package structure
/**
 * The contract for all builders to adhere to, and also implement a
 * {@code from} method to create a builder whose values are copied from a specified instance.
 * <p>
 * Created 10/01/19
 *
 * @author Edd
 */
public interface Builder2<B extends Builder2, T> {
    /**
     * Creates the instance of type, {@code T}.
     * @return an instance of type, {@code T}.
     */
    T build();

    /**
     * Creates a builder of type {@code B} whose
     * contents is copied from the supplied instance
     * of type {@code T}.
     * @param instance the instance whose values are copied from.
     * @return a new builder of type {@code B} based on the values in {@code instance}.
     */
    B from(T instance);
}
