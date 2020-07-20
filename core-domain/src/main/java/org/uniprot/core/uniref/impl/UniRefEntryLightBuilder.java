package org.uniprot.core.uniref.impl;

import java.time.LocalDate;
import java.util.*;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.uniref.*;
import org.uniprot.core.util.Utils;

/**
 * Builder for {@link UniRefEntryLight} objects.
 *
 * <p>Created 29/06/2020
 *
 * @author Edd
 */
public class UniRefEntryLightBuilder implements Builder<UniRefEntryLight> {
    private UniRefEntryId id;
    private String name;
    private LocalDate updated;
    private UniRefType entryType;
    private long commonTaxonId;
    private String commonTaxon;
    private String representativeId;
    private String sequence;
    private List<String> members = new ArrayList<>();
    private LinkedHashSet<Long> organismIds = new LinkedHashSet<>();
    private LinkedHashSet<String> organisms = new LinkedHashSet<>();
    private Set<UniRefMemberIdType> memberIdTypes = new HashSet<>();
    private List<GeneOntologyEntry> goTerms = new ArrayList<>();
    private String seedId;
    private int memberCount;
    private int organismCount;

    @Override
    public @Nonnull UniRefEntryLight build() {
        return new UniRefEntryLightImpl(
                id,
                name,
                updated,
                entryType,
                commonTaxonId,
                commonTaxon,
                representativeId,
                sequence,
                members,
                organismIds,
                organisms,
                memberCount,
                organismCount,
                memberIdTypes,
                goTerms,
                seedId);
    }

    public static @Nonnull UniRefEntryLightBuilder from(@Nonnull UniRefEntryLight instance) {
        return new UniRefEntryLightBuilder()
                .id(instance.getId())
                .name(instance.getName())
                .updated(instance.getUpdated())
                .entryType(instance.getEntryType())
                .commonTaxonId(instance.getCommonTaxonId())
                .commonTaxon(instance.getCommonTaxon())
                .representativeId(instance.getRepresentativeId())
                .sequence(instance.getSequence())
                .membersSet(instance.getMembers())
                .organismIdsSet(instance.getOrganismIds())
                .organismsSet(instance.getOrganisms())
                .memberCount(instance.getMemberCount())
                .organismCount(instance.getOrganismCount())
                .memberIdTypesSet(instance.getMemberIdTypes())
                .goTermsSet(instance.getGoTerms())
                .seedId(instance.getSeedId());
    }

    public @Nonnull UniRefEntryLightBuilder id(UniRefEntryId id) {
        this.id = id;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder id(String id) {
        this.id = new UniRefEntryIdBuilder(id).build();
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder updated(LocalDate updated) {
        this.updated = updated;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder entryType(UniRefType entryType) {
        this.entryType = entryType;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder commonTaxonId(long commonTaxonId) {
        this.commonTaxonId = commonTaxonId;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder commonTaxon(String commonTaxon) {
        this.commonTaxon = commonTaxon;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder representativeId(String id) {
        this.representativeId = id;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder sequence(String sequence) {
        this.sequence = sequence;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder membersSet(List<String> unirefMembers) {
        this.members = Utils.modifiableList(unirefMembers);
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder membersAdd(String unirefMember) {
        Utils.addOrIgnoreNull(unirefMember, this.members);
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder organismIdsSet(LinkedHashSet<Long> ids) {
        this.organismIds = ids;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder organismIdsAdd(Long id) {
        Utils.addOrIgnoreNull(id, this.organismIds);
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder organismsSet(LinkedHashSet<String> ids) {
        this.organisms = ids;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder organismsAdd(String organism) {
        if (Utils.notNullNotEmpty(organism)) {
            UniRefUtils.addOrganism(organism, this.organisms);
        }
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder memberIdTypesSet(Set<UniRefMemberIdType> types) {
        this.memberIdTypes = Utils.modifiableSet(types);
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder memberIdTypesAdd(UniRefMemberIdType type) {
        Utils.addOrIgnoreNull(type, this.memberIdTypes);
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder memberCount(int memberCount) {
        this.memberCount = memberCount;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder organismCount(int organismCount) {
        this.organismCount = organismCount;
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder goTermsSet(List<GeneOntologyEntry> goTerms) {
        this.goTerms = Utils.modifiableList(goTerms);
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder goTermsAdd(GeneOntologyEntry goTerms) {
        Utils.addOrIgnoreNull(goTerms, this.goTerms);
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder seedId(String seedId) {
        this.seedId = seedId;
        return this;
    }
}
