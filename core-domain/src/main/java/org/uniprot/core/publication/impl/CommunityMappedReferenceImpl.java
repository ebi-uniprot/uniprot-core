package org.uniprot.core.publication.impl;

import static java.util.Collections.emptySet;

import java.util.Objects;
import java.util.Set;

import org.uniprot.core.publication.CommunityAnnotation;
import org.uniprot.core.publication.CommunityMappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.uniprotkb.UniProtKBAccession;

/**
 * Created 02/12/2020
 *
 * @author Edd
 */
public class CommunityMappedReferenceImpl extends AbstractMappedReference
        implements CommunityMappedReference {
    private static final long serialVersionUID = 3254591973693232714L;

    private final CommunityAnnotation communityAnnotation;

    public CommunityMappedReferenceImpl() {
        this(null, null, null, emptySet(), null);
    }

    public CommunityMappedReferenceImpl(
            MappedSource source,
            String pubMedId,
            UniProtKBAccession uniProtKBAccession,
            Set<String> sourceCategories,
            CommunityAnnotation communityAnnotation) {
        super(source, pubMedId, uniProtKBAccession, sourceCategories);
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
        CommunityMappedReferenceImpl that = (CommunityMappedReferenceImpl) o;
        return Objects.equals(communityAnnotation, that.communityAnnotation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(communityAnnotation);
    }
}
