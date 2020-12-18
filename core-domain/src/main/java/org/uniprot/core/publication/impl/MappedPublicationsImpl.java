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
    private UniProtKBMappedReference unreviewedMappedReference;
    private UniProtKBMappedReference reviewedMappedReference;
    private List<ComputationallyMappedReference> computationalMappedReferences;
    private List<CommunityMappedReference> communityMappedReferences;

    public MappedPublicationsImpl(){
        this(null, null, null, null);
    }
    public MappedPublicationsImpl(UniProtKBMappedReference unreviewedMappedReference,
                                  UniProtKBMappedReference reviewedMappedReference,
                                  List<ComputationallyMappedReference> computationalMappedReferences,
                                  List<CommunityMappedReference> communityMappedReferences) {
        this.unreviewedMappedReference = unreviewedMappedReference;
        this.reviewedMappedReference = reviewedMappedReference;
        this.computationalMappedReferences = Utils.unmodifiableList(computationalMappedReferences);
        this.communityMappedReferences = Utils.unmodifiableList(communityMappedReferences);
    }

    @Override
    public UniProtKBMappedReference getReviewedMappedReference() {
        return this.reviewedMappedReference;
    }

    @Override
    public UniProtKBMappedReference getUnreviewedMappedReference() {
        return this.unreviewedMappedReference;
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
        return Objects.equals(unreviewedMappedReference, that.unreviewedMappedReference) &&
                Objects.equals(reviewedMappedReference, that.reviewedMappedReference) &&
                Objects.equals(computationalMappedReferences, that.computationalMappedReferences) &&
                Objects.equals(communityMappedReferences, that.communityMappedReferences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(unreviewedMappedReference, reviewedMappedReference,
                computationalMappedReferences, communityMappedReferences);
    }
}
