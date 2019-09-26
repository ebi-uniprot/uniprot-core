package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.modifiableList;

import java.util.List;

import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.uniprot.Keyword;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.builder.AbstractEvidencedValueBuilder;
import org.uniprot.core.uniprot.impl.KeywordImpl;

/**
 * Created 23/01/19
 *
 * @author Edd
 */
public class KeywordBuilder extends AbstractEvidencedValueBuilder<KeywordBuilder, Keyword> {
    private String id;
    private KeywordCategory category;
    public KeywordBuilder(){

    }

    public KeywordBuilder(String id, String value, KeywordCategory category, List<Evidence> evidences) {
        this.id = id;
        this.value = value;
        this.category = category;
        this.evidences = modifiableList(evidences);
    }

    @Override
    public Keyword build() {
        return new KeywordImpl(id, value, category, evidences);
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
    public KeywordBuilder category(KeywordCategory category) {
        this.category = category;
        return this;
    }
}
