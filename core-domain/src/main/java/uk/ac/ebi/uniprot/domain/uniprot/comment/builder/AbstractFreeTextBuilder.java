package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeText;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

/**
 * Created 16/01/19
 *
 * @author Edd
 */
public abstract class AbstractFreeTextBuilder<B extends AbstractFreeTextBuilder<B, F>, F extends FreeText> implements Builder2<AbstractFreeTextBuilder, F> {
    protected List<EvidencedValue> evidencedValues = new ArrayList<>();

    @Override
    public F build() {
        return createConcreteInstance();
    }

    @Override
    public B from(F instance) {
        this.evidencedValues.clear();
        nonNullAddAll(instance.getTexts(), this.evidencedValues);
        return getThis();
    }

    protected abstract B getThis();
    protected abstract F createConcreteInstance();
}
