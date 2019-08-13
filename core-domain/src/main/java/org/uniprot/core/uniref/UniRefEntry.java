package org.uniprot.core.uniref;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author jluo
 * @date: 9 Aug 2019
 *
*/

public interface UniRefEntry extends Serializable {
	UniRefEntryId getId();
	String getName();
	LocalDate getUpdated();
	UniRefDatabase getDatabase();
	long getCommonTaxonId();
	String getCommonTaxonName();
	List<GoTerm> getGoTerms();
	RepresentativeMember getRepresentativeMember();
	List<UniRefMember> getUniRefMembers();	
}

