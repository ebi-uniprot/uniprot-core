package org.uniprot.core.uniref.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntry;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.util.Utils;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public class UniRefEntryImpl implements UniRefEntry {

    private static final long serialVersionUID = -3900697447474386293L;
    private final UniRefEntryId id;
    private final String name;
    private final Integer memberCount;
    private final LocalDate updated;
    private final UniRefType entryType;
    private final Organism commonTaxon;
    private final String seedId;
    private final List<GeneOntologyEntry> goTerms;
    private final RepresentativeMember representativeMember;
    private final List<UniRefMember> members;

    // no arg constructor for JSON deserialization
    UniRefEntryImpl() {
        this(null, null, null, null, null, null, null, null, null, null);
    }

    UniRefEntryImpl(
            UniRefEntryId id,
            String name,
            Integer memberCount,
            LocalDate updated,
            UniRefType entryType,
            Organism commonTaxon,
            String seedId,
            List<GeneOntologyEntry> goTerms,
            RepresentativeMember representativeMember,
            List<UniRefMember> members) {
        this.id = id;
        this.name = name;
        this.updated = updated;
        this.entryType = entryType;
        this.commonTaxon = commonTaxon;
        this.seedId = seedId;
        this.goTerms = Utils.unmodifiableList(goTerms);
        this.representativeMember = representativeMember;
        this.members = Utils.unmodifiableList(members);
        this.memberCount = memberCount;
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
    public Organism getCommonTaxon() {
        return commonTaxon;
    }

    @Override
    public String getSeedId() {
        return seedId;
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
    public List<UniRefMember> getMembers() {
        return members;
    }

    @Override
    public Integer getMemberCount() {
        return memberCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id,
                name,
                updated,
                entryType,
                commonTaxon,
                seedId,
                goTerms,
                representativeMember,
                members,
                memberCount);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UniRefEntryImpl other = (UniRefEntryImpl) obj;
        return Objects.equals(id, other.id)
                && Objects.equals(name, other.name)
                && Objects.equals(memberCount, other.memberCount)
                && Objects.equals(updated, other.updated)
                && Objects.equals(entryType, other.entryType)
                && Objects.equals(commonTaxon, other.commonTaxon)
                && Objects.equals(seedId, other.seedId)
                && Objects.equals(goTerms, other.goTerms)
                && Objects.equals(representativeMember, other.representativeMember)
                && Objects.equals(members, other.members);
    }
}
