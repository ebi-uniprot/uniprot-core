package org.uniprot.core.builder;

import org.uniprot.core.Builder;
import org.uniprot.core.Value;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public abstract class AbstractValueBuilder<B extends AbstractValueBuilder<B, T>, T extends Value>
        implements Builder<B, T> {
    protected String value;

    public AbstractValueBuilder(String value) {
        this.value = value;
    }

    @Override
    public B from(T instance) {
        this.value = instance.getValue();
        return getThis();
    }

    protected abstract B getThis();
}
