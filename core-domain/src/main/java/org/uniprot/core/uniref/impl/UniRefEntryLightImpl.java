package org.uniprot.core.uniref.impl;

import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.util.Utils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Implementation of {@link UniRefEntryLight} objects.
 *
 * <p>Created 29/06/2020
 *
 * @author Edd
 */
public class UniRefEntryLightImpl implements UniRefEntryLight {
    private static final long serialVersionUID = 6240419671874348249L;
    private UniRefEntryId id;
    private String name;
    private LocalDate updated;
    private UniRefType entryType;
    private long commonTaxonId;
    private String commonTaxon;
    private String representativeSequence;
    private boolean hasMemberUniParcIds;
    private final List<String> members;
    private final Set<Long> organismIds;
    private int memberCount;
    private Set<String> organisms;

    // no arg constructor for JSON deserialization
    UniRefEntryLightImpl() {
        members = Collections.emptyList();
        organismIds = Collections.emptySet();
        organisms = Collections.emptySet();
    }

    UniRefEntryLightImpl(
            UniRefEntryId id,
            String name,
            LocalDate updated,
            UniRefType entryType,
            long commonTaxonId,
            String commonTaxon,
            String representativeSequence,
            List<String> members,
            boolean hasMemberUniParcIds,
            Set<Long> organismIds,
            Set<String> organisms,
            int memberCount) {
        this.id = id;
        this.name = name;
        this.updated = updated;
        this.entryType = entryType;
        this.commonTaxonId = commonTaxonId;
        this.commonTaxon = commonTaxon;
        this.representativeSequence = representativeSequence;
        this.members = Utils.unmodifiableList(members);
        this.hasMemberUniParcIds = hasMemberUniParcIds;
        this.organismIds = Utils.unmodifiableSet(organismIds);
        this.organisms = organisms;
        if (memberCount == 0) {
            this.memberCount = this.members.size() + 1;
        } else {
            this.memberCount = memberCount;
        }
    }

    @Override
    public UniRefEntryId getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public LocalDate getUpdated() {
        return updated;
    }

    @Override
    public UniRefType getEntryType() {
        return entryType;
    }

    @Override
    public long getCommonTaxonId() {
        return commonTaxonId;
    }

    @Override
    public String getCommonTaxon() {
        return commonTaxon;
    }

    @Override
    public List<String> getMembers() {
        return members;
    }

    @Override
    public boolean hasMemberUniParcIDs() {
        return hasMemberUniParcIds;
    }

    @Override
    public int getMemberCount() {
        return memberCount;
    }

    @Override
    public Set<String> getOrganisms() {
        return organisms;
    }

    @Override
    public Set<Long> getOrganismIds() {
        return organismIds;
    }

    @Override
    public String getRepresentativeSequence() {
        return representativeSequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniRefEntryLightImpl that = (UniRefEntryLightImpl) o;
        return commonTaxonId == that.commonTaxonId &&
                hasMemberUniParcIds == that.hasMemberUniParcIds &&
                memberCount == that.memberCount &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(updated, that.updated) &&
                entryType == that.entryType &&
                Objects.equals(commonTaxon, that.commonTaxon) &&
                Objects.equals(representativeSequence, that.representativeSequence) &&
                Objects.equals(members, that.members) &&
                Objects.equals(organismIds, that.organismIds) &&
                Objects.equals(organisms, that.organisms);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, name, updated, entryType, commonTaxonId, commonTaxon, representativeSequence, hasMemberUniParcIds, members, organismIds, memberCount, organisms);
    }
}
