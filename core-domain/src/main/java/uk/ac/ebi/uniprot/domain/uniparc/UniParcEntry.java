package uk.ac.ebi.uniprot.domain.uniparc;

import java.io.Serializable;
import java.util.List;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.uniprot.taxonomy.Taxonomy;

/**
 *
 * @author jluo
 * @date: 21 May 2019
 *
*/

public interface UniParcEntry extends Serializable {
	UniParcId getUniParcId();
	List<UniParcDBCrossReference> getDbXReferences();	 
	Sequence getSequence();
	String getUniProtExclusionReason();
	List<SequenceFeature> getSequenceFeatures();
	List<Taxonomy> getTaxonomies();
}

