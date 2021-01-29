package org.uniprot.core.publication.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.publication.CommunityMappedReference;
import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedPublications;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.util.Utils;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * @author sahmad
 * @created 18/12/2020
 */
public class MappedPublicationsBuilder implements Builder<MappedPublications> {
    private UniProtKBMappedReference uniProtKBMappedReference;
    private List<ComputationallyMappedReference> computationalMappedReferences = new ArrayList<>();
    private List<CommunityMappedReference> communityMappedReferences = new ArrayList<>();

    public @Nonnull MappedPublicationsBuilder uniProtKBMappedReference(
            UniProtKBMappedReference uniProtKBMappedReference) {
        this.uniProtKBMappedReference = uniProtKBMappedReference;
        return this;
    }

    public @Nonnull MappedPublicationsBuilder computationalMappedReferencesSet(
            List<ComputationallyMappedReference> computationalMappedReferences) {
        this.computationalMappedReferences = Utils.modifiableList(computationalMappedReferences);
        return this;
    }

    public @Nonnull MappedPublicationsBuilder computationalMappedReferencesAdd(
            ComputationallyMappedReference computationalMappedReference) {
        Utils.addOrIgnoreNull(computationalMappedReference, this.computationalMappedReferences);
        return this;
    }

    public @Nonnull MappedPublicationsBuilder communityMappedReferencesSet(
            List<CommunityMappedReference> communityMappedReferences) {
        this.communityMappedReferences = Utils.modifiableList(communityMappedReferences);
        return this;
    }

    public @Nonnull MappedPublicationsBuilder communityMappedReferencesAdd(
            CommunityMappedReference communityMappedReference) {
        Utils.addOrIgnoreNull(communityMappedReference, this.communityMappedReferences);
        return this;
    }

    @Nonnull
    @Override
    public MappedPublications build() {
        return new MappedPublicationsImpl(
                uniProtKBMappedReference, computationalMappedReferences, communityMappedReferences);
    }

    public static @Nonnull MappedPublicationsBuilder from(@Nonnull MappedPublications instance) {
        return new MappedPublicationsBuilder()
                .uniProtKBMappedReference(instance.getUniProtKBMappedReference())
                .communityMappedReferencesSet(instance.getCommunityMappedReferences())
                .computationalMappedReferencesSet(instance.getComputationalMappedReferences());
    }
}
