package org.uniprot.core.uniref.impl;

import java.util.Objects;

import org.uniprot.core.Sequence;
import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
import org.uniprot.core.uniref.OverlapRegion;
import org.uniprot.core.uniref.RepresentativeMember;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefMemberIdType;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public class RepresentativeMemberImpl extends UniRefMemberImpl implements RepresentativeMember {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Sequence sequence;
	protected RepresentativeMemberImpl() {
		super();
	}
	
	public RepresentativeMemberImpl(UniRefMemberIdType memberIdType, String memberId, 
			Taxonomy taxonomy, int sequenceLength,
			String proteinName, UniProtAccession accession, UniRefEntryId uniref50Id, UniRefEntryId uniref90Id,
			UniRefEntryId uniref100Id, UniParcId uniparcId, OverlapRegion overlapRegion, boolean seed, 
			Sequence sequence) {
		super( memberIdType,  memberId, 
			 taxonomy,  sequenceLength,
			 proteinName,  accession,  uniref50Id,  uniref90Id,
			 uniref100Id,  uniparcId,  overlapRegion,  seed);
		
		this.sequence =sequence;

	}

	@Override
	public Sequence getSequence() {
		return sequence;
	}
	@Override
	public int hashCode() {
		 return Objects.hash(super.hashCode(), sequence);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!super.equals(obj)) return false;
		RepresentativeMemberImpl other = (RepresentativeMemberImpl) obj;
		return Objects.equals(sequence, other.sequence)			
				;

	}
}

