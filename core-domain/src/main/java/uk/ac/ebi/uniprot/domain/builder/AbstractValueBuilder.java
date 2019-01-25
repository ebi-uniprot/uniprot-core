package uk.ac.ebi.uniprot.domain.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.Value;

/**
 * Created 24/01/19
 *
 * @author Edd
 */
public abstract class AbstractValueBuilder<B extends AbstractValueBuilder<B, T>, T extends Value> implements Builder2<B, T> {
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
