package org.uniprot.core.uniref.impl;

import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.util.Utils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    private final List<Long> organismIds;
    private int memberCount;

    // no arg constructor for JSON deserialization
    UniRefEntryLightImpl() {
        members = Collections.emptyList();
        organismIds = Collections.emptyList();
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
            List<Long> organismIds,
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
        this.organismIds = Utils.unmodifiableList(organismIds);
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
    public List<Long> getOrganismIds() {
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
                Objects.equals(organismIds, that.organismIds);
    }

    @Override
    public int hashCode() {
        return Objects
                .hash(id, name, updated, entryType, commonTaxonId, commonTaxon, representativeSequence, hasMemberUniParcIds, members, organismIds, memberCount);
    }
}
