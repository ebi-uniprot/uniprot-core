package uk.ac.ebi.uniprot.domain.uniprot;

import java.util.List;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;

/**
 *
 * @author jieluo
 * @date 17 Jan 2017
 * @time 18:41:20
 *
 */
public interface UniProtEntry {

	UniProtAccession getPrimaryUniProtAccession();

	List<UniProtAccession> getSecondaryUniProtAccessions();

	UniProtId getUniProtId();

	List<OrganismName> getTaxonomyLineage();

	ProteinExistence getProteinExistence();

	UniProtEntryType getType();

	EntryAudit getEntryAudit();

	List<Organelle> getOrganelles();

	List<Keyword> getKeywords();

	ProteinDescription getProteinDescription();

	Comments getComments();

	List<Feature> getFeatures();
	List<Feature> getFeaturesByType(FeatureType type);
	
	UniProtReferences getReferences();

	List<Gene> getGenes();

	OrganismName getOrganism();

	List<Organism> getOrganismHosts();

	UniProtDBCrossReferences getDatabaseCrossReferences();

	Sequence getSequence();

	UniProtTaxonId getTaxonId();

	Boolean isFragment();
	
	InternalSection getInternalSection();
	//FlagType getFlag();
}
