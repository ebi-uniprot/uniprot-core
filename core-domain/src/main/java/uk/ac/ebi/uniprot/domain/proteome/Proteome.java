package uk.ac.ebi.uniprot.domain.proteome;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxonomy;

public interface Proteome extends Serializable {
	ProteomeId getId();
	Taxonomy getTaxonomy();
	String getDescription();	
	LocalDate getModified();
	ProteomeType getProteomeType();
	ProteomeId getRedundantTo();
	String getStrain();
	String getIsolate();
	List<DBCrossReference<ProteomeXReferenceType>> getDbXReferences();	  
	List<Component> getComponents();
	List< Citation> getReferences();
	List<RedundantProteome> getRedudantProteomes();
	ProteomeId getPanproteome();
	int getAnnotationScore();
	Superkingdom getSuperkingdom();
	long getProteinCount();
	long getGeneCount();
	List<String> getTaxonLineage();
	
}
