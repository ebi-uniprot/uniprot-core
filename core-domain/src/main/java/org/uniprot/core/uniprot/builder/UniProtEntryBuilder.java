package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.nonNullAdd;
import static org.uniprot.core.util.Utils.nonNullList;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprot.*;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.description.ProteinDescription;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.impl.UniProtEntryImpl;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

/**
 * A staged builder that guides a user when creating a {@link UniProtEntry} instance. For example, the accession
 * must be supplied first, after which the ID must be added. After this, one chooses the entry type. Many fields
 * can be set for the usual Swiss-Prot/TrEMBL entry types. However, inactive entries have only a single field
 * available for setting; the inactive reason.
 *
 * @author lgonzales
 */
public class UniProtEntryBuilder {

    public EntryBuilder from(UniProtEntry instance) {
        return new EntryBuilder(instance.getPrimaryAccession()).from(instance);
    }

    public UniProtIdBuilder primaryAccession(UniProtAccession primaryAccession) {
        return new EntryBuilder(primaryAccession);
    }

    public interface UniProtIdBuilder {
        Status uniProtId(UniProtId uniProtId);
    }

    public interface Status {
        ActiveEntryBuilder active();

        InactiveEntryBuilder inactive();
    }

    public interface ActiveEntryBuilder extends Builder<EntryBuilder, UniProtEntry> {
        ActiveEntryBuilder entryType(UniProtEntryType entryType);

        ActiveEntryBuilder addSecondaryAccession(UniProtAccession secondaryAccession);

        ActiveEntryBuilder secondaryAccessions(List<UniProtAccession> secondaryAccessions);

        ActiveEntryBuilder entryAudit(EntryAudit entryAudit);

        ActiveEntryBuilder annotationScore(double annotationScore);

        ActiveEntryBuilder organism(Organism organism);

        ActiveEntryBuilder addOrganismHost(OrganismHost organismHost);

        ActiveEntryBuilder organismHosts(List<OrganismHost> organismHosts);

        ActiveEntryBuilder proteinExistence(ProteinExistence proteinExistence);

        ActiveEntryBuilder proteinDescription(ProteinDescription proteinDescription);

        ActiveEntryBuilder addGene(Gene gene);

        ActiveEntryBuilder genes(List<Gene> genes);

        ActiveEntryBuilder addComment(Comment comment);

        ActiveEntryBuilder comments(List<Comment> comments);

        ActiveEntryBuilder addFeature(Feature feature);

        ActiveEntryBuilder features(List<Feature> features);

        ActiveEntryBuilder addGeneLocation(GeneLocation geneLocation);

        ActiveEntryBuilder geneLocations(List<GeneLocation> geneLocations);

        ActiveEntryBuilder addKeyword(Keyword keyword);

        ActiveEntryBuilder keywords(List<Keyword> keywords);

        ActiveEntryBuilder addReference(UniProtReference reference);

        ActiveEntryBuilder references(List<UniProtReference> references);

        ActiveEntryBuilder addDatabaseCrossReference(UniProtDBCrossReference databaseCrossReference);

        ActiveEntryBuilder databaseCrossReferences(List<UniProtDBCrossReference> databaseCrossReferences);

        ActiveEntryBuilder sequence(Sequence sequence);

        ActiveEntryBuilder internalSection(InternalSection internalSection);
    }

    public interface InactiveEntryBuilder extends Builder<EntryBuilder, UniProtEntry> {
        InactiveEntryBuilder inactiveReason(EntryInactiveReason inactiveReason);
    }

    private class EntryBuilder extends UniProtEntryBuilder implements UniProtIdBuilder, ActiveEntryBuilder, InactiveEntryBuilder, Status {
        private UniProtAccession primaryAccession;
        private UniProtEntryType entryType = null;
        private List<UniProtAccession> secondaryAccessions = new ArrayList<>();
        private UniProtId uniProtId = null;
        private EntryAudit entryAudit = null;
        private double annotationScore;
        private Organism organism = null;
        private List<OrganismHost> organismHosts = new ArrayList<>();
        private ProteinExistence proteinExistence = null;
        private ProteinDescription proteinDescription = null;
        private List<Gene> genes = new ArrayList<>();
        private List<Comment> comments = new ArrayList<>();
        private List<Feature> features = new ArrayList<>();
        private List<GeneLocation> geneLocations = new ArrayList<>();
        private List<Keyword> keywords = new ArrayList<>();
        private List<UniProtReference> references = new ArrayList<>();
        private List<UniProtDBCrossReference> databaseCrossReferences = new ArrayList<>();
        private Sequence sequence = null;
        private InternalSection internalSection = null;
        private EntryInactiveReason inactiveReason = null;
        private boolean active = false;

        private EntryBuilder(UniProtAccession primaryAccession) {
            this.primaryAccession = primaryAccession;
        }

        @Override
        public Status uniProtId(UniProtId uniProtId) {
            this.uniProtId = uniProtId;
            return this;
        }

        @Override
        public ActiveEntryBuilder active() {
            this.active = true;
            return this;
        }

        @Override
        public InactiveEntryBuilder inactive() {
            this.active = false;
            this.entryType = UniProtEntryType.INACTIVE;
            return this;
        }

        @Override
        public InactiveEntryBuilder inactiveReason(EntryInactiveReason inactiveReason) {
            this.inactiveReason = inactiveReason;
            return this;
        }

        @Override
        public ActiveEntryBuilder entryType(UniProtEntryType entryType) {
            if (active && entryType == UniProtEntryType.INACTIVE) {
                throw new IllegalArgumentException("Cannot set an active entry to be inactive.");
            }
            this.entryType = entryType;
            return this;
        }

        @Override
        public ActiveEntryBuilder addSecondaryAccession(UniProtAccession secondaryAccession) {
            nonNullAdd(secondaryAccession, this.secondaryAccessions);
            return this;
        }

        @Override
        public ActiveEntryBuilder secondaryAccessions(List<UniProtAccession> secondaryAccessions) {
            this.secondaryAccessions = nonNullList(secondaryAccessions);
            return this;
        }

        @Override
        public ActiveEntryBuilder entryAudit(EntryAudit entryAudit) {
            this.entryAudit = entryAudit;
            return this;
        }

        @Override
        public ActiveEntryBuilder annotationScore(double annotationScore) {
            this.annotationScore = annotationScore;
            return this;
        }

        @Override
        public ActiveEntryBuilder organism(Organism organism) {
            this.organism = organism;
            return this;
        }

        @Override
        public ActiveEntryBuilder addOrganismHost(OrganismHost organismHost) {
            nonNullAdd(organismHost, this.organismHosts);
            return this;
        }

        @Override
        public ActiveEntryBuilder organismHosts(List<OrganismHost> organismHosts) {
            this.organismHosts = nonNullList(organismHosts);
            return this;
        }

        @Override
        public ActiveEntryBuilder proteinExistence(ProteinExistence proteinExistence) {
            this.proteinExistence = proteinExistence;
            return this;
        }

        @Override
        public ActiveEntryBuilder proteinDescription(ProteinDescription proteinDescription) {
            this.proteinDescription = proteinDescription;
            return this;
        }

        @Override
        public ActiveEntryBuilder addGene(Gene gene) {
            nonNullAdd(gene, this.genes);
            return this;
        }

        @Override
        public ActiveEntryBuilder genes(List<Gene> genes) {
            this.genes = nonNullList(genes);
            return this;
        }

        @Override
        public ActiveEntryBuilder addComment(Comment comment) {
            nonNullAdd(comment, this.comments);
            return this;
        }

        @Override
        public ActiveEntryBuilder comments(List<Comment> comments) {
            this.comments = nonNullList(comments);
            return this;
        }

        @Override
        public ActiveEntryBuilder addFeature(Feature feature) {
            nonNullAdd(feature, this.features);
            return this;
        }

        @Override
        public ActiveEntryBuilder features(List<Feature> features) {
            this.features = nonNullList(features);
            return this;
        }

        @Override
        public ActiveEntryBuilder addGeneLocation(GeneLocation geneLocation) {
            nonNullAdd(geneLocation, this.geneLocations);
            return this;
        }

        @Override
        public ActiveEntryBuilder geneLocations(List<GeneLocation> geneLocations) {
            this.geneLocations = nonNullList(geneLocations);
            return this;
        }

        @Override
        public ActiveEntryBuilder addKeyword(Keyword keyword) {
            nonNullAdd(keyword, this.keywords);
            return this;
        }

        @Override
        public ActiveEntryBuilder keywords(List<Keyword> keywords) {
            this.keywords = nonNullList(keywords);
            return this;
        }

        @Override
        public ActiveEntryBuilder addReference(UniProtReference reference) {
            nonNullAdd(reference, this.references);
            return this;
        }

        @Override
        public ActiveEntryBuilder references(List<UniProtReference> references) {
            this.references = nonNullList(references);
            return this;
        }

        @Override
        public ActiveEntryBuilder addDatabaseCrossReference(UniProtDBCrossReference databaseCrossReference) {
            nonNullAdd(databaseCrossReference, this.databaseCrossReferences);
            return this;
        }

        @Override
        public ActiveEntryBuilder databaseCrossReferences(List<UniProtDBCrossReference> databaseCrossReferences) {
            this.databaseCrossReferences = nonNullList(databaseCrossReferences);
            return this;
        }

        @Override
        public ActiveEntryBuilder sequence(Sequence sequence) {
            this.sequence = sequence;
            return this;
        }

        @Override
        public ActiveEntryBuilder internalSection(InternalSection internalSection) {
            this.internalSection = internalSection;
            return this;
        }

        @Override
        public UniProtEntry build() {
            return new UniProtEntryImpl(entryType, primaryAccession, secondaryAccessions, uniProtId, entryAudit, annotationScore, organism,
                                        organismHosts, proteinExistence, proteinDescription, genes, comments, features, geneLocations, keywords,
                                        references, databaseCrossReferences, sequence, internalSection, inactiveReason);
        }

        @Override
        public EntryBuilder from(UniProtEntry instance) {
            this.entryType = instance.getEntryType();
            this.primaryAccession = instance.getPrimaryAccession();
            this.secondaryAccessions = instance.getSecondaryAccessions();
            this.uniProtId = instance.getUniProtId();
            this.entryAudit = instance.getEntryAudit();
            this.organism = instance.getOrganism();
            this.organismHosts = instance.getOrganismHosts();
            this.proteinExistence = instance.getProteinExistence();
            this.proteinDescription = instance.getProteinDescription();
            this.genes = instance.getGenes();
            this.comments = instance.getComments();
            this.features = instance.getFeatures();
            this.geneLocations = instance.getGeneLocations();
            this.keywords = instance.getKeywords();
            this.references = instance.getReferences();
            this.databaseCrossReferences = instance.getDatabaseCrossReferences();
            this.sequence = instance.getSequence();
            this.internalSection = instance.getInternalSection();
            this.inactiveReason = instance.getInactiveReason();
            this.annotationScore = instance.getAnnotationScore();
            return this;
        }
    }
}