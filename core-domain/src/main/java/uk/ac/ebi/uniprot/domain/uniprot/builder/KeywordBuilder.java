package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.AbstractEvidencedValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.impl.KeywordImpl;

import java.util.List;

/**
 * Created 23/01/19
 *
 * @author Edd
 */
public class KeywordBuilder extends AbstractEvidencedValueBuilder<KeywordBuilder, Keyword> {
    private String id;

    public KeywordBuilder(){

    }

    public KeywordBuilder(String id, String value, List<Evidence> evidences) {
        this.id = id;
        this.value = value;
        this.evidences = evidences;
    }

    @Override
    public Keyword build() {
        return new KeywordImpl(id, value, evidences);
    }

    @Override
    protected KeywordBuilder getThis() {
        return this;
    }

    @Override
    public KeywordBuilder from(Keyword instance) {
        return super.from(instance)
                .id(instance.getId());
    }

    public KeywordBuilder id(String id) {
        this.id = id;
        return this;
    }
}
