package org.uniprot.core.uniref;

import java.io.Serializable;

import org.uniprot.core.uniparc.UniParcId;
import org.uniprot.core.uniprot.UniProtAccession;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;

/**
 *
 * @author jluo
 * @date: 12 Aug 2019
 *
*/

public interface UniRefMember extends Serializable {
	UniRefMemberIdType getMemberIdType();
	String getMemberId();
	Taxonomy getTaxonomy();
	int getSequenceLength();
	String getProteinName();
	UniProtAccession getUniProtAccession();
	UniRefEntryId getUniRef50Id();
	UniRefEntryId getUniRef90Id();
	UniRefEntryId getUniRef100Id();
	UniParcId getUniParcId();
	OverlapRegion getOverlapRegion();
	boolean isSeed();
	
	
}

