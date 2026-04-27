package org.uniprot.core.publication.impl;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.publication.CommunityMappedReference;
import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedPublications;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.util.Utils;

/**
 * @author sahmad
 * @created 18/12/2020
 */
public class MappedPublicationsImpl implements MappedPublications {
    private static final long serialVersionUID = 1747477865356912233L;
    private final List<UniProtKBMappedReference> uniProtKBMappedReferences;
    private final List<ComputationallyMappedReference> computationallyMappedReferences;
    private final List<CommunityMappedReference> communityMappedReferences;

    public MappedPublicationsImpl() {
        this(null, null, null);
    }

    public MappedPublicationsImpl(
            List<UniProtKBMappedReference> uniProtKBMappedReferences,
            List<ComputationallyMappedReference> computationallyMappedReferences,
            List<CommunityMappedReference> communityMappedReferences) {
        this.uniProtKBMappedReferences = Utils.unmodifiableList(uniProtKBMappedReferences);
        this.computationallyMappedReferences =
                Utils.unmodifiableList(computationallyMappedReferences);
        this.communityMappedReferences = Utils.unmodifiableList(communityMappedReferences);
    }

    @Override
    public List<UniProtKBMappedReference> getUniProtKBMappedReferences() {
        return this.uniProtKBMappedReferences;
    }

    @Override
    public List<ComputationallyMappedReference> getComputationallyMappedReferences() {
        return this.computationallyMappedReferences;
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
        return Objects.equals(uniProtKBMappedReferences, that.uniProtKBMappedReferences)
                && Objects.equals(
                        computationallyMappedReferences, that.computationallyMappedReferences)
                && Objects.equals(communityMappedReferences, that.communityMappedReferences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                uniProtKBMappedReferences,
                computationallyMappedReferences,
                communityMappedReferences);
    }
}
