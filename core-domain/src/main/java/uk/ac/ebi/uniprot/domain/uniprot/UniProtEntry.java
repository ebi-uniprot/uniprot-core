package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.common.Sequence;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.uniprot.api.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.internalsection.InternalSection;

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
    public Comments getComments();

    UniProtReferences getReferences();
    public List<Gene> getGenes();
    public Organism getOrganism();
    public List<Organism> getOrganismHosts();
    public  UniProtDatabaseCrossReferences getDatabaseCrossReferences();
    public Sequence getSequence();
    public UniProtTaxonId getTaxonId();
    public Boolean isFragment();
    InternalSection getInternalSection();
}
