package org.uniprot.core.uniref.impl;

import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniref.OverlapRegion;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author jluo
 * @date: 12 Aug 2019
 */
public class UniRefMemberImpl implements UniRefMember {
    private static final long serialVersionUID = -1490306000699324397L;
    private UniRefMemberIdType memberIdType;
    private String memberId;
    private String organismName;
    private long organismTaxId;
    private int sequenceLength;
    private String proteinName;
    private List<UniProtKBAccession> accessions;
    private UniRefEntryId uniref50Id;
    private UniRefEntryId uniref90Id;
    private UniRefEntryId uniref100Id;
    private UniParcId uniparcId;
    private OverlapRegion overlapRegion;
    private Boolean seed;

    // no arg constructor for JSON deserialization
    UniRefMemberImpl() {
        accessions = Collections.emptyList();
    }

    UniRefMemberImpl(
            UniRefMemberIdType memberIdType,
            String memberId,
            String organismName,
            long organismTaxId,
            int sequenceLength,
            String proteinName,
            List<UniProtKBAccession> accessions,
            UniRefEntryId uniref50Id,
            UniRefEntryId uniref90Id,
            UniRefEntryId uniref100Id,
            UniParcId uniparcId,
            OverlapRegion overlapRegion,
            Boolean seed) {
        this.memberIdType = memberIdType;
        this.memberId = memberId;
        this.organismName = organismName;
        this.organismTaxId = organismTaxId;
        this.sequenceLength = sequenceLength;
        this.proteinName = proteinName;
        this.accessions = Utils.unmodifiableList(accessions);
        this.uniref50Id = uniref50Id;
        this.uniref90Id = uniref90Id;
        this.uniref100Id = uniref100Id;
        this.uniparcId = uniparcId;
        this.overlapRegion = overlapRegion;
        this.seed = seed;
    }

    @Override
    public UniRefMemberIdType getMemberIdType() {
        return memberIdType;
    }

    @Override
    public String getMemberId() {
        return memberId;
    }

    @Override
    public String getOrganismName() {
        return organismName;
    }

    @Override
    public long getOrganismTaxId() {
        return organismTaxId;
    }

    @Override
    public int getSequenceLength() {
        return sequenceLength;
    }

    @Override
    public String getProteinName() {
        return proteinName;
    }

    @Override
    public List<UniProtKBAccession> getUniProtAccessions() {
        return accessions;
    }

    @Override
    public UniRefEntryId getUniRef50Id() {
        return uniref50Id;
    }

    @Override
    public UniRefEntryId getUniRef90Id() {
        return uniref90Id;
    }

    @Override
    public UniRefEntryId getUniRef100Id() {
        return uniref100Id;
    }

    @Override
    public UniParcId getUniParcId() {
        return uniparcId;
    }

    @Override
    public OverlapRegion getOverlapRegion() {
        return overlapRegion;
    }

    @Override
    public Boolean isSeed() {
        return seed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                memberIdType,
                memberId,
                organismName,
                organismTaxId,
                sequenceLength,
                proteinName,
                accessions,
                uniref50Id,
                uniref90Id,
                uniref100Id,
                uniparcId,
                overlapRegion,
                seed);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UniRefMemberImpl other = (UniRefMemberImpl) obj;
        return Objects.equals(memberIdType, other.memberIdType)
                && Objects.equals(memberId, other.memberId)
                && Objects.equals(organismName, other.organismName)
                && (organismTaxId == other.organismTaxId)
                && (sequenceLength == other.sequenceLength)
                && Objects.equals(proteinName, other.proteinName)
                && Objects.equals(accessions, other.accessions)
                && Objects.equals(uniref50Id, other.uniref50Id)
                && Objects.equals(uniref90Id, other.uniref90Id)
                && Objects.equals(uniref100Id, other.uniref100Id)
                && Objects.equals(uniparcId, other.uniparcId)
                && Objects.equals(overlapRegion, other.overlapRegion)
                && Objects.equals(seed, other.seed);
    }
}
