package uk.ac.ebi.uniprot.domain.uniprot.builder;

import uk.ac.ebi.uniprot.domain.Builder2;
import uk.ac.ebi.uniprot.domain.Sequence;
import uk.ac.ebi.uniprot.domain.gene.Gene;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismHost;
import uk.ac.ebi.uniprot.domain.uniprot.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinDescription;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtEntryImpl;
import uk.ac.ebi.uniprot.domain.uniprot.xdb.UniProtDBCrossReference;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.util.Utils.nonNullAddAll;

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
        EntryTypeBuilder uniProtId(UniProtId uniProtId);
    }

    public interface EntryTypeBuilder {
        ActiveEntryBuilder trembl();

        ActiveEntryBuilder swissProt();

        InactiveEntryBuilder inactive();
    }

    public interface ActiveEntryBuilder extends Builder2<EntryBuilder, UniProtEntry> {
        ActiveEntryBuilder addSecondaryAccession(UniProtAccession secondaryAccession);

        ActiveEntryBuilder secondaryAccessions(List<UniProtAccession> secondaryAccessions);

        ActiveEntryBuilder entryAudit(EntryAudit entryAudit);

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

        ActiveEntryBuilder addOrganelle(Organelle organelle);

        ActiveEntryBuilder organelles(List<Organelle> organelles);

        ActiveEntryBuilder addKeyword(Keyword keyword);

        ActiveEntryBuilder keywords(List<Keyword> keywords);

        ActiveEntryBuilder addReference(UniProtReference reference);

        ActiveEntryBuilder references(List<UniProtReference> references);

        ActiveEntryBuilder addDatabaseCrossReference(UniProtDBCrossReference databaseCrossReference);

        ActiveEntryBuilder databaseCrossReferences(List<UniProtDBCrossReference> databaseCrossReferences);

        ActiveEntryBuilder sequence(Sequence sequence);

        ActiveEntryBuilder internalSection(InternalSection internalSection);
    }

    public interface InactiveEntryBuilder extends Builder2<EntryBuilder, UniProtEntry> {
        InactiveEntryBuilder inactiveReason(EntryInactiveReason inactiveReason);
    }

    private class EntryBuilder extends UniProtEntryBuilder implements UniProtIdBuilder, ActiveEntryBuilder, InactiveEntryBuilder, EntryTypeBuilder {
        private UniProtAccession primaryAccession;
        private UniProtEntryType entryType = null;
        private List<UniProtAccession> secondaryAccessions = new ArrayList<>();
        private UniProtId uniProtId = null;
        private EntryAudit entryAudit = null;
        private Organism organism = null;
        private List<OrganismHost> organismHosts = new ArrayList<>();
        private ProteinExistence proteinExistence = null;
        private ProteinDescription proteinDescription = null;
        private List<Gene> genes = new ArrayList<>();
        private List<Comment> comments = new ArrayList<>();
        private List<Feature> features = new ArrayList<>();
        private List<Organelle> organelles = new ArrayList<>();
        private List<Keyword> keywords = new ArrayList<>();
        private List<UniProtReference> references = new ArrayList<>();
        private List<UniProtDBCrossReference> databaseCrossReferences = new ArrayList<>();
        private Sequence sequence = null;
        private InternalSection internalSection = null;
        private EntryInactiveReason inactiveReason = null;

        private EntryBuilder(UniProtAccession primaryAccession) {
            this.primaryAccession = primaryAccession;
        }

        @Override
        public EntryTypeBuilder uniProtId(UniProtId uniProtId) {
            this.uniProtId = uniProtId;
            return this;
        }

        @Override
        public ActiveEntryBuilder trembl() {
            this.entryType = UniProtEntryType.TREMBL;
            return this;
        }

        @Override
        public ActiveEntryBuilder swissProt() {
            this.entryType = UniProtEntryType.SWISSPROT;
            return this;
        }

        @Override
        public InactiveEntryBuilder inactive() {
            this.entryType = UniProtEntryType.INACTIVE;
            return this;
        }

        @Override
        public InactiveEntryBuilder inactiveReason(EntryInactiveReason inactiveReason) {
            this.inactiveReason = inactiveReason;
            return this;
        }

        @Override
        public ActiveEntryBuilder addSecondaryAccession(UniProtAccession secondaryAccession) {
            this.secondaryAccessions.add(secondaryAccession);
            return this;
        }

        @Override
        public ActiveEntryBuilder secondaryAccessions(List<UniProtAccession> secondaryAccessions) {
            nonNullAddAll(secondaryAccessions, this.secondaryAccessions);
            return this;
        }

        @Override
        public ActiveEntryBuilder entryAudit(EntryAudit entryAudit) {
            this.entryAudit = entryAudit;
            return this;
        }

        @Override
        public ActiveEntryBuilder organism(Organism organism) {
            this.organism = organism;
            return this;
        }

        @Override
        public ActiveEntryBuilder addOrganismHost(OrganismHost organismHost) {
            this.organismHosts.add(organismHost);
            return this;
        }

        @Override
        public ActiveEntryBuilder organismHosts(List<OrganismHost> organismHosts) {
            nonNullAddAll(organismHosts, this.organismHosts);
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
            this.genes.add(gene);
            return this;
        }

        @Override
        public ActiveEntryBuilder genes(List<Gene> genes) {
            nonNullAddAll(genes, this.genes);
            return this;
        }

        @Override
        public ActiveEntryBuilder addComment(Comment comment) {
            this.comments.add(comment);
            return this;
        }

        @Override
        public ActiveEntryBuilder comments(List<Comment> comments) {
            nonNullAddAll(comments, this.comments);
            return this;
        }

        @Override
        public ActiveEntryBuilder addFeature(Feature feature) {
            this.features.add(feature);
            return this;
        }

        @Override
        public ActiveEntryBuilder features(List<Feature> features) {
            nonNullAddAll(features, this.features);
            return this;
        }

        @Override
        public ActiveEntryBuilder addOrganelle(Organelle organelle) {
            this.organelles.add(organelle);
            return this;
        }

        @Override
        public ActiveEntryBuilder organelles(List<Organelle> organelles) {
            nonNullAddAll(organelles, this.organelles);
            return this;
        }

        @Override
        public ActiveEntryBuilder addKeyword(Keyword keyword) {
            this.keywords.add(keyword);
            return this;
        }

        @Override
        public ActiveEntryBuilder keywords(List<Keyword> keywords) {
            nonNullAddAll(keywords, this.keywords);
            return this;
        }

        @Override
        public ActiveEntryBuilder addReference(UniProtReference reference) {
            this.references.add(reference);
            return this;
        }

        @Override
        public ActiveEntryBuilder references(List<UniProtReference> references) {
            nonNullAddAll(references, this.references);
            return this;
        }

        @Override
        public ActiveEntryBuilder addDatabaseCrossReference(UniProtDBCrossReference databaseCrossReference) {
            this.databaseCrossReferences.add(databaseCrossReference);
            return this;
        }

        @Override
        public ActiveEntryBuilder databaseCrossReferences(List<UniProtDBCrossReference> databaseCrossReferences) {
            nonNullAddAll(databaseCrossReferences, this.databaseCrossReferences);
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
            return new UniProtEntryImpl(entryType, primaryAccession, secondaryAccessions, uniProtId, entryAudit, organism,
                                        organismHosts, proteinExistence, proteinDescription, genes, comments, features, organelles, keywords,
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
            this.organelles = instance.getOrganelles();
            this.keywords = instance.getKeywords();
            this.references = instance.getReferences();
            this.databaseCrossReferences = instance.getDatabaseCrossReferences();
            this.sequence = instance.getSequence();
            this.internalSection = instance.getInternalSection();
            this.inactiveReason = instance.getInactiveReason();
            return this;
        }
    }
}