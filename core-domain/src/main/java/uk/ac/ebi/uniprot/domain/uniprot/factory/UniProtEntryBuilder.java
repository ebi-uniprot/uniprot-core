package uk.ac.ebi.uniprot.domain.uniprot.factory;

import java.util.List;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.common.Builder;
import uk.ac.ebi.uniprot.domain.common.Sequence;
import uk.ac.ebi.uniprot.domain.feature.Feature;
import uk.ac.ebi.uniprot.domain.feature.Features;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.taxonomy.Taxon;
import uk.ac.ebi.uniprot.domain.taxonomy.TaxonName;
import uk.ac.ebi.uniprot.domain.uniprot.EntryAudit;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;
import uk.ac.ebi.uniprot.domain.uniprot.Organelle;
import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtDBCrossReferences;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeature;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtFeatures;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtId;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReferences;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtEntryImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;

public final class UniProtEntryBuilder implements Builder<UniProtEntry> {
    public static UniProtEntryBuilder newInstance(){
        return new UniProtEntryBuilder();
    }
    private  UniProtEntryType entryType =UniProtEntryType.UNKNOWN;
    private  UniProtAccession accession;
    private  List<UniProtAccession> secondaryAccessions;
    private  UniProtId uniprotId;
    private  List<TaxonName> lineage;
    private  ProteinExistence proteinExistance =ProteinExistence.UNKNOWN;
    private  EntryAudit entryAudit;
    private  List<Organelle> organelles;
    private  List<Keyword> keywords;
    private  ProteinDescription description;
    private  Comments comments;
    private  UniProtReferences references;
    private  List<Gene> genes;
    private  Organism organism;
    private  List<OrganismHost> organismHosts;
    private  UniProtDBCrossReferences xrefs;
    private  Sequence sequence;
    private  UniProtTaxonId taxonId;
    private  InternalSection internalSection;
    private  UniProtFeatures features;
    @Override
    public UniProtEntry build() {
        return new UniProtEntryImpl(
                 entryType,
                 accession,
                secondaryAccessions,
                 uniprotId,
                 lineage,
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

    public UniProtEntryBuilder entryType(UniProtEntryType entryType){
        this.entryType = entryType;
        return this;
    }
    
    public UniProtEntryBuilder accession(UniProtAccession accession){
        this.accession = accession;
        return this;
    }
    
    public UniProtEntryBuilder accession(String accession){
        this.accession = UniProtFactory.INSTANCE.createUniProtAccession(accession);
        return this;
    }
    
    public UniProtEntryBuilder secondaryAccessions(List<UniProtAccession> secondaryAccessions){
        this.secondaryAccessions = secondaryAccessions;
        return this;
    }
    
    public UniProtEntryBuilder uniProtId(UniProtId uniprotId){
        this.uniprotId = uniprotId;
        return this;
    }
    
    public UniProtEntryBuilder uniProtId(String uniprotId){
        this.uniprotId =  UniProtFactory.INSTANCE.createUniProtId(uniprotId);
        return this;
    }
    
    public UniProtEntryBuilder taxonomyLineage(List<TaxonName> lineage){
        this.lineage = lineage;
        return this;
    }
    public UniProtEntryBuilder proteinExistence(ProteinExistence proteinExistance){
        this.proteinExistance = proteinExistance;
        return this;
    }
    
    public UniProtEntryBuilder entryAudit(EntryAudit entryAudit){
        this.entryAudit = entryAudit;
        return this;
    }
    public UniProtEntryBuilder organelles(List<Organelle> organelles){
        this.organelles = organelles;
        return this;
    }
    public UniProtEntryBuilder keywords(List<Keyword> keywords){
        this.keywords = keywords;
        return this;
    }
    public UniProtEntryBuilder proteinDescription(ProteinDescription description){
        this.description = description;
        return this;
    }
    public UniProtEntryBuilder comments(Comments comments){
        this.comments = comments;
        return this;
    }
    public UniProtEntryBuilder comments(List<Comment> comments){
        this.comments = CommentFactory.INSTANCE.createComments(comments);
        return this;
    }
    public UniProtEntryBuilder uniProtReferences(UniProtReferences references){
        this.references = references;
        return this;
    }
    public UniProtEntryBuilder uniProtReferences(List<UniProtReference<? extends Citation>> references){
        this.references = UniProtReferenceFactory.INSTANCE.createUniProtReferences(references);
        return this;
    }
    public UniProtEntryBuilder genes(List<Gene> genes){
        this.genes = genes;
        return this;
    }
    public UniProtEntryBuilder organism(Organism organism){
        this.organism = organism;
        return this;
    }
    public UniProtEntryBuilder organismHosts(List<OrganismHost> organismHosts){
        this.organismHosts = organismHosts;
        return this;
    }
    public UniProtEntryBuilder uniProtDBCrossReferences(UniProtDBCrossReferences xrefs){
        this.xrefs = xrefs;
        return this;
    }
    public UniProtEntryBuilder uniProtDBCrossReferences(List<UniProtDBCrossReference>  xrefs){
        this.xrefs = UniProtDBCrossReferenceFactory.INSTANCE.createUniProtDBCrossReferences(xrefs);
        return this;
    }
    public UniProtEntryBuilder sequence(Sequence sequence){
        this.sequence = sequence;
        return this;
    }
    public UniProtEntryBuilder sequence(String sequence){
        this.sequence = UniProtFactory.INSTANCE.createSequence(sequence);
        return this;
    }
    public UniProtEntryBuilder uniProtTaxonId(UniProtTaxonId taxonId){
        this.taxonId = taxonId;
        return this;
    }
    public UniProtEntryBuilder internalSection(InternalSection internalSection){
        this.internalSection = internalSection;
        return this;
    }
    public UniProtEntryBuilder features(UniProtFeatures features){
        this.features = features;
        return this;
    }
    public UniProtEntryBuilder features(List<UniProtFeature<? extends Feature>> features){
        this.features = FeatureFactory.INSTANCE.createUniProtFeatures(features);
        return this;
    }
}
