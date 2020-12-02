package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.CommunityAnnotation;
import org.uniprot.core.publication.CommunityMappedReference;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

import java.util.List;
import java.util.Objects;

/**
 * Created 02/12/2020
 * @author Edd
 */
public class CommunityMappedReferenceImpl extends AbstractMappedReference implements CommunityMappedReference {
    private final CommunityAnnotation communityAnnotation;

    public CommunityMappedReferenceImpl(String source, String sourceId, String pubMedId, UniProtKBAccession uniProtKBAccession, List<String> sourceCategories, CommunityAnnotation communityAnnotation) {
        super(source, sourceId, pubMedId, uniProtKBAccession, sourceCategories);
        this.communityAnnotation = communityAnnotation;
    }

    @Override
    public CommunityAnnotation getCommunityAnnotation() {
        return communityAnnotation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CommunityMappedReferenceImpl that = (CommunityMappedReferenceImpl) o;
        return Objects.equals(communityAnnotation, that.communityAnnotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), communityAnnotation);
    }
}
