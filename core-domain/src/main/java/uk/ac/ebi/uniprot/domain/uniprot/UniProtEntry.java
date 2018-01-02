package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.common.Sequence;
import uk.ac.ebi.uniprot.domain.feature.Features;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;

import java.util.List;

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
    List<Taxon> getTaxonomy();
    ProteinExistence getProteinExistence();
    UniProtEntryType getType();
    EntryAudit getEntryAudit();
    List<Organelle> getOrganelles();
    List<Keyword> getKeywords();
    ProteinDescription getProteinDescription();
     Comments getComments();
     Features getFeatures();
    UniProtReferences getReferences();
     List<Gene> getGenes();
     Organism getOrganism();
     List<Organism> getOrganismHosts();
      UniProtDatabaseCrossReferences getDatabaseCrossReferences();
     Sequence getSequence();
     UniProtTaxonId getTaxonId();
     Boolean isFragment();
    InternalSection getInternalSection();
}
