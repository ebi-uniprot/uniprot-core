package uk.ac.ebi.uniprot.domain.uniprot;

import java.util.List;

import uk.ac.ebi.uniprot.domain.common.Sequence;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;

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

	List<TaxonName> getTaxonomyLineage();

	ProteinExistence getProteinExistence();

	UniProtEntryType getType();

	EntryAudit getEntryAudit();

	List<Organelle> getOrganelles();

	List<Keyword> getKeywords();

	ProteinDescription getProteinDescription();

	Comments getComments();

	UniProtFeatures getFeatures();

	UniProtReferences getReferences();

	List<Gene> getGenes();

	Organism getOrganism();

	List<OrganismHost> getOrganismHosts();

	UniProtDBCrossReferences getDatabaseCrossReferences();

	Sequence getSequence();

	UniProtTaxonId getTaxonId();

	Boolean isFragment();
	
	InternalSection getInternalSection();
}
