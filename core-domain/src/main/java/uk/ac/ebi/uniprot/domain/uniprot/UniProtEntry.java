package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReferences;

import java.util.List;

/**
 *
 * @author jieluo
 * @date 17 Jan 2017
 * @time 18:41:20
 *
 */
public interface UniProtEntry {
	UniProtEntryType getEntryType();

	UniProtAccession getPrimaryAccession();

	List<UniProtAccession> getSecondaryAccessions();

	UniProtId getUniProtId();

	EntryAudit getEntryAudit();

	UniProtTaxonId getTaxonId();

	OrganismName getOrganism();

	List<Organism> getOrganismHosts();

	List<OrganismName> getTaxonomyLineage();

	ProteinExistence getProteinExistence();

	ProteinDescription getProteinDescription();

	List<Gene> getGenes();

	Comments getComments();

	List<Feature> getFeatures();

	List<Feature> getFeaturesByType(FeatureType type);

	List<Organelle> getOrganelles();

	List<Keyword> getKeywords();

	UniProtReferences getReferences();

	UniProtDBCrossReferences getDatabaseCrossReferences();

	Sequence getSequence();

	InternalSection getInternalSection();

	Boolean isFragment();

	EntryInactiveReason getInactiveReason();
	boolean isActive();
}
