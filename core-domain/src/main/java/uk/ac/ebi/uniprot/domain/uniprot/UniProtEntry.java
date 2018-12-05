package uk.ac.ebi.uniprot.domain.uniprot;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id.NAME;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReferences;

/**
 *
 * @author jieluo
 * @date 17 Jan 2017
 * @time 18:41:20
 *
 */
@JsonTypeInfo(use = NAME, include = PROPERTY, property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtEntryImpl.class, name = "UniProtEntry") })
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
