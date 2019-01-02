package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.Builder;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtEntryImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;

import java.util.List;

public final class UniProtEntryBuilder implements Builder<UniProtEntry> {
    private UniProtEntryType entryType = UniProtEntryType.UNKNOWN;
    private UniProtAccession primaryAccession;
    private List<UniProtAccession> secondaryAccessions;
    private UniProtId uniProtId;
    private EntryAudit entryAudit;
    private UniProtTaxonId taxonId;
    private OrganismName organism;
    private List<Organism> organismHosts;
    private List<OrganismName> taxonomyLineage;
    private ProteinExistence proteinExistence = ProteinExistence.UNKNOWN;
    private ProteinDescription proteinDescription;
    private List<Gene> genes;
    private List<Comment> comments;
    private List<Feature> features;
    private List<Organelle> organelles;
    private List<Keyword> keywords;
    private List<UniProtReference> references;
    private List<UniProtDBCrossReference> databaseCrossReferences;
    private Sequence sequence;
    private InternalSection internalSection;

    public static UniProtEntryBuilder newInstance() {
        return new UniProtEntryBuilder();
    }

    @Override
    public UniProtEntry build() {
        return new UniProtEntryImpl(
                entryType,
                primaryAccession,
                secondaryAccessions,
                uniProtId,
                entryAudit,
                taxonId,
                organism,
                organismHosts,
                taxonomyLineage,
                proteinExistence,
                proteinDescription,
                genes,
                comments,
                features,
                organelles,
                keywords,
                references,
                databaseCrossReferences,
                sequence,
                internalSection
        );

    }

    public UniProtEntryBuilder entryType(UniProtEntryType entryType) {
        this.entryType = entryType;
        return this;
    }

    public UniProtEntryBuilder primaryAccession(UniProtAccession accession) {
        this.primaryAccession = accession;
        return this;
    }

    public UniProtEntryBuilder primaryAccession(String accession) {
        this.primaryAccession = UniProtFactory.INSTANCE.createUniProtAccession(accession);
        return this;
    }

    public UniProtEntryBuilder secondaryAccessions(List<UniProtAccession> secondaryAccessions) {
        this.secondaryAccessions = secondaryAccessions;
        return this;
    }

    public UniProtEntryBuilder uniProtId(UniProtId uniProtId) {
        this.uniProtId = uniProtId;
        return this;
    }

    public UniProtEntryBuilder uniProtId(String uniProtId) {
        this.uniProtId = UniProtFactory.INSTANCE.createUniProtId(uniProtId);
        return this;
    }

    public UniProtEntryBuilder taxonomyLineage(List<OrganismName> taxonomyLineage) {
        this.taxonomyLineage = taxonomyLineage;
        return this;
    }

    public UniProtEntryBuilder proteinExistence(ProteinExistence proteinExistence) {
        this.proteinExistence = proteinExistence;
        return this;
    }

    public UniProtEntryBuilder entryAudit(EntryAudit entryAudit) {
        this.entryAudit = entryAudit;
        return this;
    }

    public UniProtEntryBuilder organelles(List<Organelle> organelles) {
        this.organelles = organelles;
        return this;
    }

    public UniProtEntryBuilder keywords(List<Keyword> keywords) {
        this.keywords = keywords;
        return this;
    }

    public UniProtEntryBuilder proteinDescription(ProteinDescription proteinDescription) {
        this.proteinDescription = proteinDescription;
        return this;
    }

    public UniProtEntryBuilder comments(List<Comment> comments) {
        this.comments = comments;
        return this;
    }

    public UniProtEntryBuilder references(List<UniProtReference> references) {
        this.references = references;
        return this;
    }

    public UniProtEntryBuilder genes(List<Gene> genes) {
        this.genes = genes;
        return this;
    }

    public UniProtEntryBuilder organism(OrganismName organism) {
        this.organism = organism;
        return this;
    }

    public UniProtEntryBuilder organismHosts(List<Organism> organismHosts) {
        this.organismHosts = organismHosts;
        return this;
    }

    public UniProtEntryBuilder uniProtDBCrossReferences(List<UniProtDBCrossReference> xrefs) {
        this.databaseCrossReferences = xrefs;
        return this;
    }

    public UniProtEntryBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public UniProtEntryBuilder sequence(String sequence) {
        this.sequence = UniProtFactory.INSTANCE.createSequence(sequence);
        return this;
    }

    public UniProtEntryBuilder uniProtTaxonId(UniProtTaxonId taxonId) {
        this.taxonId = taxonId;
        return this;
    }

    public UniProtEntryBuilder internalSection(InternalSection internalSection) {
        this.internalSection = internalSection;
        return this;
    }

    public UniProtEntryBuilder features(List<Feature> features) {
        this.features = features;
        return this;
    }


}
