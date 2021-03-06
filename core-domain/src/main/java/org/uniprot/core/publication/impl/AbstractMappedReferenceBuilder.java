package org.uniprot.core.publication.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableSet;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.publication.MappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public abstract class AbstractMappedReferenceBuilder<
                B extends AbstractMappedReferenceBuilder<B, T>, T extends MappedReference>
        implements Builder<T> {
    protected UniProtKBAccession uniProtKBAccession;
    protected String citationId;
    protected MappedSource source;
    protected Set<String> sourceCategories = new HashSet<>();

    public B uniProtKBAccession(String accession) {
        this.uniProtKBAccession = new UniProtKBAccessionBuilder(accession).build();
        return getThis();
    }

    public B uniProtKBAccession(UniProtKBAccession accession) {
        this.uniProtKBAccession = accession;
        return getThis();
    }

    public B source(MappedSource source) {
        this.source = source;
        return getThis();
    }

    public B citationId(String citationId) {
        this.citationId = citationId;
        return getThis();
    }

    public B sourceCategoriesAdd(String sourceCategory) {
        addOrIgnoreNull(sourceCategory, this.sourceCategories);
        return getThis();
    }

    public B sourceCategoriesSet(Set<String> sourceCategories) {
        this.sourceCategories = modifiableSet(sourceCategories);
        return getThis();
    }

    protected abstract @Nonnull B getThis();

    protected static <B extends AbstractMappedReferenceBuilder<B, T>, T extends MappedReference>
            B from(@Nonnull B builder, @Nonnull T instance) {
        return builder.source(instance.getSource())
                .citationId(instance.getCitationId())
                .uniProtKBAccession(instance.getUniProtKBAccession())
                .sourceCategoriesSet(instance.getSourceCategories());
    }
}
