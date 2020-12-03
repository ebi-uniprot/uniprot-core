package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.MappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class AbstractMappedReference implements MappedReference {
    protected final Set<MappedSource> sources;
    protected final String pubMedId;
    protected final UniProtKBAccession uniProtKBAccession;
    protected final Set<String> sourceCategories;

    public AbstractMappedReference(
            Set<MappedSource> sources,
            String pubMedId,
            UniProtKBAccession uniProtKBAccession,
            Set<String> sourceCategories) {
        this.sources = sources;
        this.pubMedId = pubMedId;
        this.uniProtKBAccession = uniProtKBAccession;
        this.sourceCategories = sourceCategories;
    }

    @Override
    public UniProtKBAccession getUniProtKBAccession() {
        return uniProtKBAccession;
    }

    @Override
    public Set<MappedSource> getSources() {
        return sources;
    }

    @Override
    public String getPubMedId() {
        return pubMedId;
    }

    @Override
    public Set<String> getSourceCategories() {
        return sourceCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractMappedReference that = (AbstractMappedReference) o;
        return Objects.equals(sources, that.sources) &&
                Objects.equals(pubMedId, that.pubMedId) &&
                Objects.equals(uniProtKBAccession, that.uniProtKBAccession) &&
                Objects.equals(sourceCategories, that.sourceCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sources, pubMedId, uniProtKBAccession, sourceCategories);
    }
}
