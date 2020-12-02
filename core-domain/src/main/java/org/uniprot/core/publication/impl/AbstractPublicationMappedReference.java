package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.PublicationMappedReference;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.util.List;
import java.util.Objects;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class AbstractPublicationMappedReference implements PublicationMappedReference {
    protected final String source;
    protected final String sourceId;
    protected final String pubMedId;
    protected final UniProtKBAccession uniProtKBAccession;
    protected final List<String> sourceCategories;

    public AbstractPublicationMappedReference(String source, String sourceId, String pubMedId, UniProtKBAccession uniProtKBAccession, List<String> sourceCategories) {
        this.source = source;
        this.sourceId = sourceId;
        this.pubMedId = pubMedId;
        this.uniProtKBAccession = uniProtKBAccession;
        this.sourceCategories = sourceCategories;
    }

    @Override
    public UniProtKBAccession getUniProtKBAccession() {
        return uniProtKBAccession;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getSourceId() {
        return sourceId;
    }

    @Override
    public String getPubMedId() {
        return pubMedId;
    }

    @Override
    public List<String> getSourceCategories() {
        return sourceCategories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPublicationMappedReference that = (AbstractPublicationMappedReference) o;
        return Objects.equals(source, that.source) &&
                Objects.equals(sourceId, that.sourceId) &&
                Objects.equals(pubMedId, that.pubMedId) &&
                Objects.equals(uniProtKBAccession, that.uniProtKBAccession) &&
                Objects.equals(sourceCategories, that.sourceCategories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(source, sourceId, pubMedId, uniProtKBAccession, sourceCategories);
    }
}
