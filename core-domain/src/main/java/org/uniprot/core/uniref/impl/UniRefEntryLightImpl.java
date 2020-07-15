package org.uniprot.core.uniref.impl;

import java.time.LocalDate;
import java.util.*;

import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.util.Utils;

/**
 * Implementation of {@link UniRefEntryLight} objects.
 *
 * <p>Created 29/06/2020
 *
 * @author Edd
 */
public class UniRefEntryLightImpl implements UniRefEntryLight {
    static final String NAME_PREFIX = "Cluster: ";
    private static final long serialVersionUID = 6240419671874348249L;
    private final List<String> members;
    private final LinkedHashSet<Long> organismIds;
    private final LinkedHashSet<String> organisms;
    private final Set<UniRefMemberIdType> memberIdTypes;
    private UniRefEntryId id;
    private String name;
    private LocalDate updated;
    private UniRefType entryType;
    private long commonTaxonId;
    private String commonTaxon;
    private String sequence;
    private int sequenceLength;
    private int memberCount;
    private int organismCount;
    private String representativeId;

    // no arg constructor for JSON deserialization
    UniRefEntryLightImpl() {
        members = Collections.emptyList();
        organismIds = new LinkedHashSet<>();
        organisms = new LinkedHashSet<>();
        memberIdTypes = Collections.emptySet();
    }

    UniRefEntryLightImpl(
            UniRefEntryId id,
            String name,
            LocalDate updated,
            UniRefType entryType,
            long commonTaxonId,
            String commonTaxon,
            String representativeId,
            String representativeSequence,
            List<String> members,
            LinkedHashSet<Long> organismIds,
            LinkedHashSet<String> organisms,
            int memberCount,
            int organismCount,
            Set<UniRefMemberIdType> memberIdTypes) {
        this.id = id;
        this.name = name;
        this.updated = updated;
        this.entryType = entryType;
        this.commonTaxonId = commonTaxonId;
        this.commonTaxon = commonTaxon;
        this.representativeId = representativeId;
        this.sequence = representativeSequence;
        if (Utils.notNullNotEmpty(this.sequence)) {
            this.sequenceLength = representativeSequence.length();
        }
        this.members = Utils.unmodifiableList(members);
        this.organismIds = organismIds;
        this.organisms = organisms;
        this.memberIdTypes = memberIdTypes;
        if (memberCount == 0) {
            this.memberCount = this.members.size() + 1;
        } else {
            this.memberCount = memberCount;
        }
        if (organismCount == 0) {
            this.organismCount = organismIds.size();
        } else {
            this.organismCount = organismCount;
        }
    }

    @Override
    public UniRefEntryId getId() {
        return id;
    }

    @Override
    public String getRepresentativeProteinName() {
        return name.substring(NAME_PREFIX.length());
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
    public int getMemberCount() {
        return memberCount;
    }

    @Override
    public LinkedHashSet<String> getOrganisms() {
        return organisms;
    }

    @Override
    public LinkedHashSet<Long> getOrganismIds() {
        return organismIds;
    }

    @Override
    public int getOrganismCount() {
        return organismCount;
    }

    @Override
    public String getSequence() {
        return sequence;
    }

    @Override
    public int getSequenceLength() {
        return sequenceLength;
    }

    @Override
    public String getRepresentativeId() {
        return representativeId;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Set<UniRefMemberIdType> getMemberIdTypes() {
        return memberIdTypes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniRefEntryLightImpl that = (UniRefEntryLightImpl) o;
        return commonTaxonId == that.commonTaxonId
                && sequenceLength == that.sequenceLength
                && memberCount == that.memberCount
                && Objects.equals(id, that.id)
                && Objects.equals(updated, that.updated)
                && entryType == that.entryType
                && Objects.equals(commonTaxon, that.commonTaxon)
                && Objects.equals(sequence, that.sequence)
                && Objects.equals(members, that.members)
                && Objects.equals(organismIds, that.organismIds)
                && Objects.equals(organisms, that.organisms)
                && Objects.equals(memberIdTypes, that.memberIdTypes)
                && Objects.equals(name, that.name)
                && Objects.equals(representativeId, that.representativeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                updated,
                entryType,
                commonTaxonId,
                commonTaxon,
                sequence,
                sequenceLength,
                members,
                organismIds,
                memberCount,
                organisms,
                memberIdTypes,
                name,
                representativeId);
    }
}
