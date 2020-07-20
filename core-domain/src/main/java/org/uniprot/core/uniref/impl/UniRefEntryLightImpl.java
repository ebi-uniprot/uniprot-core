package org.uniprot.core.uniref.impl;

import java.time.LocalDate;
import java.util.*;

import org.uniprot.core.cv.go.GeneOntologyEntry;
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
    private final UniRefEntryId id;
    private final String name;
    private final LocalDate updated;
    private final UniRefType entryType;
    private final long commonTaxonId;
    private final String commonTaxon;
    private final String sequence;
    private int sequenceLength;
    private final int memberCount;
    private final int organismCount;
    private final String representativeId;
    private final String seedId;
    private final Set<UniRefMemberIdType> memberIdTypes;
    private final List<String> members;
    private final LinkedHashSet<Long> organismIds;
    private final LinkedHashSet<String> organisms;
    private final List<GeneOntologyEntry> goTerms;

    // no arg constructor for JSON deserialization
    UniRefEntryLightImpl() {
        this(
                null, null, null, null, 0L, null, null, null, null, null, null, 0, 0, null, null,
                null);
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
            Set<UniRefMemberIdType> memberIdTypes,
            List<GeneOntologyEntry> goTerms,
            String seedId) {
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
        this.organismIds = Utils.modifiableLinkedHashSet(organismIds);
        this.organisms = Utils.modifiableLinkedHashSet(organisms);
        this.memberIdTypes = Utils.unmodifiableSet(memberIdTypes);
        if (memberCount == 0) {
            this.memberCount = this.members.size() + 1;
        } else {
            this.memberCount = memberCount;
        }
        if (organismCount == 0) {
            this.organismCount = this.organismIds.size();
        } else {
            this.organismCount = organismCount;
        }
        this.goTerms = Utils.unmodifiableList(goTerms);
        this.seedId = seedId;
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
    public List<GeneOntologyEntry> getGoTerms() {
        return goTerms;
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
    public String getSeedId() {
        return seedId;
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
        return getCommonTaxonId() == that.getCommonTaxonId()
                && getSequenceLength() == that.getSequenceLength()
                && getMemberCount() == that.getMemberCount()
                && getOrganismCount() == that.getOrganismCount()
                && Objects.equals(getMembers(), that.getMembers())
                && Objects.equals(getOrganismIds(), that.getOrganismIds())
                && Objects.equals(getOrganisms(), that.getOrganisms())
                && Objects.equals(getMemberIdTypes(), that.getMemberIdTypes())
                && Objects.equals(getGoTerms(), that.getGoTerms())
                && Objects.equals(getId(), that.getId())
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getUpdated(), that.getUpdated())
                && getEntryType() == that.getEntryType()
                && Objects.equals(getCommonTaxon(), that.getCommonTaxon())
                && Objects.equals(getSequence(), that.getSequence())
                && Objects.equals(getRepresentativeId(), that.getRepresentativeId())
                && Objects.equals(getSeedId(), that.getSeedId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getMembers(),
                getOrganismIds(),
                getOrganisms(),
                getMemberIdTypes(),
                getGoTerms(),
                getId(),
                getName(),
                getUpdated(),
                getEntryType(),
                getCommonTaxonId(),
                getCommonTaxon(),
                getSequence(),
                getSequenceLength(),
                getMemberCount(),
                getOrganismCount(),
                getRepresentativeId(),
                getSeedId());
    }
}
