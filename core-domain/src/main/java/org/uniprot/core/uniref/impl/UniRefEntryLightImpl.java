package org.uniprot.core.uniref.impl;

import java.time.LocalDate;
import java.util.*;

import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniref.*;
import org.uniprot.core.util.Utils;

/**
 * Implementation of {@link UniRefEntryLight} objects.
 *
 * <p>Created 29/06/2020
 *
 * @author Edd
 */
public class UniRefEntryLightImpl implements UniRefEntryLight {
    private static final long serialVersionUID = 6240419671874348249L;
    private final UniRefEntryId id;
    private final String name;
    private final LocalDate updated;
    private final UniRefType entryType;
    private final Organism commonTaxon;
    private final int memberCount;
    private final int organismCount;
    private final RepresentativeMember representativeMember;
    private final String seedId;
    private final Set<UniRefMemberIdType> memberIdTypes;
    private final List<String> members;
    private final LinkedHashSet<Organism> organisms;
    private final List<GeneOntologyEntry> goTerms;

    // no arg constructor for JSON deserialization
    UniRefEntryLightImpl() {
        this(null, null, null, null, null, null, null, null, 0, 0, null, null, null);
    }

    UniRefEntryLightImpl(
            UniRefEntryId id,
            String name,
            LocalDate updated,
            UniRefType entryType,
            Organism commonTaxon,
            RepresentativeMember representativeMember,
            List<String> members,
            LinkedHashSet<Organism> organisms,
            int memberCount,
            int organismCount,
            Set<UniRefMemberIdType> memberIdTypes,
            List<GeneOntologyEntry> goTerms,
            String seedId) {
        this.id = id;
        this.name = name;
        this.updated = updated;
        this.entryType = entryType;
        this.commonTaxon = commonTaxon;
        this.representativeMember = representativeMember;
        this.members = Utils.unmodifiableList(members);
        this.organisms = Utils.modifiableLinkedHashSet(organisms);
        this.memberIdTypes = Utils.unmodifiableSet(memberIdTypes);
        if (memberCount == 0) {
            this.memberCount = this.members.size();
        } else {
            this.memberCount = memberCount;
        }
        if (organismCount == 0) {
            this.organismCount = this.organisms.size();
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
    public LocalDate getUpdated() {
        return updated;
    }

    @Override
    public UniRefType getEntryType() {
        return entryType;
    }

    @Override
    public Organism getCommonTaxon() {
        return commonTaxon;
    }

    @Override
    public List<GeneOntologyEntry> getGoTerms() {
        return goTerms;
    }

    @Override
    public RepresentativeMember getRepresentativeMember() {
        return representativeMember;
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
    public LinkedHashSet<Organism> getOrganisms() {
        return organisms;
    }

    @Override
    public int getOrganismCount() {
        return organismCount;
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
        return getMemberCount() == that.getMemberCount()
                && getOrganismCount() == that.getOrganismCount()
                && Objects.equals(getMembers(), that.getMembers())
                && Objects.equals(getOrganisms(), that.getOrganisms())
                && Objects.equals(getMemberIdTypes(), that.getMemberIdTypes())
                && Objects.equals(getGoTerms(), that.getGoTerms())
                && Objects.equals(getId(), that.getId())
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getUpdated(), that.getUpdated())
                && getEntryType() == that.getEntryType()
                && Objects.equals(getCommonTaxon(), that.getCommonTaxon())
                && Objects.equals(getRepresentativeMember(), that.getRepresentativeMember())
                && Objects.equals(getSeedId(), that.getSeedId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getMembers(),
                getOrganisms(),
                getMemberIdTypes(),
                getGoTerms(),
                getId(),
                getName(),
                getUpdated(),
                getEntryType(),
                getCommonTaxon(),
                getMemberCount(),
                getOrganismCount(),
                getRepresentativeMember(),
                getSeedId());
    }
}
