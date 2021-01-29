package org.uniprot.core.publication.impl;

import org.uniprot.core.publication.CommunityMappedReference;
import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedPublications;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.util.Utils;

import java.util.List;
import java.util.Objects;

/**
 * @author sahmad
 * @created 18/12/2020
 */
public class MappedPublicationsImpl implements MappedPublications {
    private static final long serialVersionUID = -6303466445944690517L;
    private final UniProtKBMappedReference uniProtKBMappedReference;
    private final List<ComputationallyMappedReference> computationalMappedReferences;
    private final List<CommunityMappedReference> communityMappedReferences;

    public MappedPublicationsImpl() {
        this(null, null, null);
    }

    public MappedPublicationsImpl(
            UniProtKBMappedReference uniProtKBMappedReference,
            List<ComputationallyMappedReference> computationalMappedReferences,
            List<CommunityMappedReference> communityMappedReferences) {
        this.uniProtKBMappedReference = uniProtKBMappedReference;
        this.computationalMappedReferences = Utils.unmodifiableList(computationalMappedReferences);
        this.communityMappedReferences = Utils.unmodifiableList(communityMappedReferences);
    }

    @Override
    public UniProtKBMappedReference getUniProtKBMappedReference() {
        return this.uniProtKBMappedReference;
    }

    @Override
    public List<ComputationallyMappedReference> getComputationalMappedReferences() {
        return this.computationalMappedReferences;
    }

    @Override
    public List<CommunityMappedReference> getCommunityMappedReferences() {
        return this.communityMappedReferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MappedPublicationsImpl that = (MappedPublicationsImpl) o;
        return Objects.equals(uniProtKBMappedReference, that.uniProtKBMappedReference)
                && Objects.equals(computationalMappedReferences, that.computationalMappedReferences)
                && Objects.equals(communityMappedReferences, that.communityMappedReferences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                uniProtKBMappedReference, computationalMappedReferences, communityMappedReferences);
    }
}
