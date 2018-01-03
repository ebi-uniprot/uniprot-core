package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.common.Sequence;
import uk.ac.ebi.uniprot.domain.feature.Features;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtDatabaseCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReferences;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtEntryImpl;

import java.util.List;

public final class UniProtEntryBuilder implements Builder<UniProtEntry> {
    public static UniProtEntryBuilder newInstance(){
        return new UniProtEntryBuilder();
    }
    private  UniProtEntryType entryType;
    private  UniProtAccession accession;
    private  List<UniProtAccession> secondaryAccessions;
    private  UniProtId uniprotId;
    private  List<Taxon> taxonomies;
    private  ProteinExistence proteinExistance;
    private  EntryAudit entryAudit;
    private  List<Organelle> organelles;
    private  List<Keyword> keywords;
    private  ProteinDescription description;
    private  Comments comments;
    private  UniProtReferences references;
    private  List<Gene> genes;
    private  Organism organism;
    private  List<Organism> organismHosts;
    private  UniProtDatabaseCrossReferences xrefs;
    private  Sequence sequence;
    private  UniProtTaxonId taxonId;
    private  InternalSection internalSection;
    private  Features features;
    @Override
    public UniProtEntry build() {
        return new UniProtEntryImpl(
                 entryType,
                 accession,
                secondaryAccessions,
                 uniprotId,
                 taxonomies,
                 proteinExistance,
                 entryAudit,
                 organelles,
                 keywords,
                 description,
                 comments,
                 references,
                 genes,
                 features,
                 organism,
                 organismHosts,
                 xrefs,
                 sequence,
                 taxonId,
                 internalSection
                );

    }

    public UniProtEntryBuilder setEntryType(UniProtEntryType entryType){
        this.entryType = entryType;
        return this;
    }
    
    public UniProtEntryBuilder setAccession(UniProtAccession accession){
        this.accession = accession;
        return this;
    }
    
    public UniProtEntryBuilder setSecondaryAccessions(List<UniProtAccession> secondaryAccessions){
        this.secondaryAccessions = secondaryAccessions;
        return this;
    }
    
    public UniProtEntryBuilder setUniProtId(UniProtId uniprotId){
        this.uniprotId = uniprotId;
        return this;
    }
    
    public UniProtEntryBuilder setTaxons(List<Taxon> taxonomies){
        this.taxonomies = taxonomies;
        return this;
    }
    public UniProtEntryBuilder setProteinExistence(ProteinExistence proteinExistance){
        this.proteinExistance = proteinExistance;
        return this;
    }
    
    public UniProtEntryBuilder setEntryAudit(EntryAudit entryAudit){
        this.entryAudit = entryAudit;
        return this;
    }
    public UniProtEntryBuilder setOrganelles(List<Organelle> organelles){
        this.organelles = organelles;
        return this;
    }
    public UniProtEntryBuilder setKeywords(List<Keyword> keywords){
        this.keywords = keywords;
        return this;
    }
    public UniProtEntryBuilder setProteinDescription(ProteinDescription description){
        this.description = description;
        return this;
    }
    public UniProtEntryBuilder setComments(Comments comments){
        this.comments = comments;
        return this;
    }
    public UniProtEntryBuilder setUniProtReferences(UniProtReferences references){
        this.references = references;
        return this;
    }
    public UniProtEntryBuilder setGenes(List<Gene> genes){
        this.genes = genes;
        return this;
    }
    public UniProtEntryBuilder setOrganism(Organism organism){
        this.organism = organism;
        return this;
    }
    public UniProtEntryBuilder setOrganismHosts(List<Organism> organismHosts){
        this.organismHosts = organismHosts;
        return this;
    }
    public UniProtEntryBuilder setUniProtDatabaseCrossReferences(UniProtDatabaseCrossReferences xrefs){
        this.xrefs = xrefs;
        return this;
    }
    public UniProtEntryBuilder setSequence(Sequence sequence){
        this.sequence = sequence;
        return this;
    }
    public UniProtEntryBuilder setUniProtTaxonId(UniProtTaxonId taxonId){
        this.taxonId = taxonId;
        return this;
    }
    public UniProtEntryBuilder setInternalSection(InternalSection internalSection){
        this.internalSection = internalSection;
        return this;
    }
    public UniProtEntryBuilder setFeatures(Features features){
        this.features = features;
        return this;
    }
}
