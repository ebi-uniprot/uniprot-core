package org.uniprot.core.builder;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Value;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public abstract class AbstractValueBuilder<T extends Value> implements Builder<T> {
    protected String value;

    public AbstractValueBuilder(String value) {
        this.value = value;
    }
}
