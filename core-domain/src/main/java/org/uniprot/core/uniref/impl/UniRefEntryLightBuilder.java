package org.uniprot.core.uniref.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.uniref.*;
import org.uniprot.core.util.Utils;

import javax.annotation.Nonnull;
import java.time.LocalDate;
import java.util.*;

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
    private String representativeSequence;
    private List<String> members = new ArrayList<>();
    private Set<Long> organismIds = new LinkedHashSet<>();
    private Set<String> organisms = new LinkedHashSet<>();
    private Set<UniRefMemberIdType> memberIdTypes = new HashSet<>();
    private int memberCount;

    @Override
    public @Nonnull UniRefEntryLight build() {
        return new UniRefEntryLightImpl(
                id,
                name,
                updated,
                entryType,
                commonTaxonId,
                commonTaxon,
                representativeSequence,
                members,
                organismIds,
                organisms,
                memberCount,
                memberIdTypes);
    }

    public static @Nonnull UniRefEntryLightBuilder from(@Nonnull UniRefEntryLight instance) {
        return new UniRefEntryLightBuilder()
                .id(instance.getId())
                .name(instance.getName())
                .updated(instance.getUpdated())
                .entryType(instance.getEntryType())
                .commonTaxonId(instance.getCommonTaxonId())
                .commonTaxon(instance.getCommonTaxon())
                .representativeSequence(instance.getRepresentativeSequence())
                .membersSet(instance.getMembers())
                .organismIdsSet(instance.getOrganismIds())
                .memberCount(instance.getMemberCount())
                .memberIdTypesSet(instance.getMemberIdTypes());
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

    public @Nonnull UniRefEntryLightBuilder representativeSequence(String representativeSequence) {
        this.representativeSequence = representativeSequence;
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

    public @Nonnull UniRefEntryLightBuilder organismIdsSet(Set<Long> ids) {
        this.organismIds = Utils.modifiableSet(ids);
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder organismIdsAdd(Long id) {
        Utils.addOrIgnoreNull(id, this.organismIds);
        return this;
    }

    public @Nonnull UniRefEntryLightBuilder organismsSet(Set<String> ids) {
        this.organisms = Utils.modifiableSet(ids);
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
}
