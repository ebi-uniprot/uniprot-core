package org.uniprot.core.uniref.impl;

import java.util.Objects;

import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniref.OverlapRegion;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMember;
import org.uniprot.core.uniref.UniRefMemberIdType;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
 */

public class UniRefMemberImpl implements UniRefMember {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1490306000699324397L;
	private UniRefMemberIdType memberIdType;
	private String memberId;
	private Taxonomy taxonomy;
	private int sequenceLength;
	private String proteinName;
	private UniProtAccession accession;
	private UniRefEntryId uniref50Id;
	private UniRefEntryId uniref90Id;
	private UniRefEntryId uniref100Id;
	private UniParcId uniparcId;
	private OverlapRegion overlapRegion;
	private Boolean seed;

	protected UniRefMemberImpl() {

	}

	public UniRefMemberImpl(UniRefMemberIdType memberIdType, String memberId, 
			Taxonomy taxonomy, int sequenceLength,
			String proteinName, UniProtAccession accession, UniRefEntryId uniref50Id, UniRefEntryId uniref90Id,
			UniRefEntryId uniref100Id, UniParcId uniparcId, OverlapRegion overlapRegion, Boolean seed) {
		this.memberIdType =memberIdType;
		this.memberId =memberId;
		this.taxonomy =taxonomy;
		this.sequenceLength = sequenceLength;
		this.proteinName =proteinName;
		this.accession = accession;
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
	public Taxonomy getTaxonomy() {
		return taxonomy;
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
	public UniProtAccession getUniProtAccession() {
		return accession;
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
		return Objects.hash( memberIdType,  memberId, 
				 taxonomy,  sequenceLength,
				 proteinName,  accession, uniref50Id ,  uniref90Id,
				 uniref100Id,  uniparcId,  overlapRegion,  seed);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UniRefMemberImpl other = (UniRefMemberImpl) obj;
		return Objects.equals(memberIdType, other.memberIdType)
				&& Objects.equals(memberId, other.memberId)
				&& Objects.equals(taxonomy, other.taxonomy)
				&& (sequenceLength==other.sequenceLength)
				&& Objects.equals(proteinName, other.proteinName)
				&& Objects.equals(accession, other.accession)
				&& Objects.equals(uniref50Id, other.uniref50Id)
				&& Objects.equals(uniref90Id, other.uniref90Id)
				&& Objects.equals(uniref100Id, other.uniref100Id)
				&& Objects.equals(uniparcId, other.uniparcId)
				&& Objects.equals(overlapRegion, other.overlapRegion)
				&& (this.seed == other.seed)
				;

	}
}
