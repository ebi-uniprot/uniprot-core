package org.uniprot.core.uniprot.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprot.*;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.description.ProteinDescription;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.impl.UniProtEntryImpl;
import org.uniprot.core.uniprot.taxonomy.Organism;
import org.uniprot.core.uniprot.taxonomy.OrganismHost;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;

public class UniProtEntryBuilder implements Builder<UniProtEntryBuilder, UniProtEntry> {

    private UniProtAccession primaryAccession;
    private UniProtEntryType entryType;
    private List<UniProtAccession> secondaryAccessions = new ArrayList<>();
    private UniProtId uniProtId;
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
    private EntryInactiveReason inactiveReason;
    private List<TaxonomyLineage> lineages;

    public UniProtEntryBuilder(String primaryAccession, String uniProtId, UniProtEntryType type) {
        this(
                new UniProtAccessionBuilder(primaryAccession).build(),
                new UniProtIdBuilder(uniProtId).build(),
                type);
    }

    public UniProtEntryBuilder(
            UniProtAccession primaryAccession, UniProtId uniProtId, UniProtEntryType type) {
        this(primaryAccession, uniProtId, type, null);
    }

    public UniProtEntryBuilder(
            String primaryAccession, String uniProtId, EntryInactiveReason inactiveReason) {
        this(
                new UniProtAccessionBuilder(primaryAccession).build(),
                new UniProtIdBuilder(uniProtId).build(),
                inactiveReason);
    }

    public UniProtEntryBuilder(String primaryAccession, EntryInactiveReason inactiveReason) {
        this(new UniProtAccessionBuilder(primaryAccession).build(), inactiveReason);
    }

    public UniProtEntryBuilder(
            UniProtAccession primaryAccession, EntryInactiveReason inactiveReason) {
        this(primaryAccession, null, inactiveReason);
    }

    public UniProtEntryBuilder(
            UniProtAccession primaryAccession,
            UniProtId uniProtId,
            EntryInactiveReason inactiveReason) {
        this(primaryAccession, uniProtId, UniProtEntryType.INACTIVE, inactiveReason);
    }

    private UniProtEntryBuilder(
            UniProtAccession primaryAccession,
            UniProtId uniProtId,
            UniProtEntryType type,
            EntryInactiveReason inactiveReason) {
        this.entryType = inactiveReason == null ? type : UniProtEntryType.INACTIVE;
        this.inactiveReason = inactiveReason;
        this.primaryAccession = primaryAccession;
        this.uniProtId = uniProtId;
    }

    public @Nonnull UniProtEntryBuilder primaryAccession(String primaryAccession) {
        return primaryAccession(new UniProtAccessionBuilder(primaryAccession).build());
    }

    public @Nonnull UniProtEntryBuilder primaryAccession(UniProtAccession primaryAccession) {
        this.primaryAccession = primaryAccession;
        return this;
    }

    public @Nonnull UniProtEntryBuilder uniProtId(String uniProtId) {
        return uniProtId(new UniProtIdBuilder(uniProtId).build());
    }

    public @Nonnull UniProtEntryBuilder uniProtId(UniProtId uniProtId) {
        this.uniProtId = uniProtId;
        return this;
    }

    public @Nonnull UniProtEntryBuilder entryType(UniProtEntryType entryType) {
        this.entryType = entryType;
        return this;
    }

    public @Nonnull UniProtEntryBuilder secondaryAccessionAdd(UniProtAccession secondaryAccession) {
        addOrIgnoreNull(secondaryAccession, this.secondaryAccessions);
        return this;
    }

    public @Nonnull UniProtEntryBuilder secondaryAccessionsSet(
            List<UniProtAccession> secondaryAccessions) {
        this.secondaryAccessions = modifiableList(secondaryAccessions);
        return this;
    }

    public @Nonnull UniProtEntryBuilder entryAudit(EntryAudit entryAudit) {
        this.entryAudit = entryAudit;
        return this;
    }

    public @Nonnull UniProtEntryBuilder annotationScore(double annotationScore) {
        this.annotationScore = annotationScore;
        return this;
    }

    public @Nonnull UniProtEntryBuilder organism(Organism organism) {
        this.organism = organism;
        return this;
    }

    public @Nonnull UniProtEntryBuilder organismHostAdd(OrganismHost organismHost) {
        addOrIgnoreNull(organismHost, this.organismHosts);
        return this;
    }

    public @Nonnull UniProtEntryBuilder organismHostsSet(List<OrganismHost> organismHosts) {
        this.organismHosts = modifiableList(organismHosts);
        return this;
    }

    public @Nonnull UniProtEntryBuilder proteinExistence(ProteinExistence proteinExistence) {
        this.proteinExistence = proteinExistence;
        return this;
    }

    public @Nonnull UniProtEntryBuilder proteinDescription(ProteinDescription proteinDescription) {
        this.proteinDescription = proteinDescription;
        return this;
    }

    public @Nonnull UniProtEntryBuilder geneAdd(Gene gene) {
        addOrIgnoreNull(gene, this.genes);
        return this;
    }

    public @Nonnull UniProtEntryBuilder genesSet(List<Gene> genes) {
        this.genes = modifiableList(genes);
        return this;
    }

    public @Nonnull UniProtEntryBuilder commentAdd(Comment comment) {
        addOrIgnoreNull(comment, this.comments);
        return this;
    }

    public @Nonnull UniProtEntryBuilder commentsSet(List<Comment> comments) {
        this.comments = modifiableList(comments);
        return this;
    }

    public @Nonnull UniProtEntryBuilder featureAdd(Feature feature) {
        addOrIgnoreNull(feature, this.features);
        return this;
    }

    public @Nonnull UniProtEntryBuilder featuresSet(List<Feature> features) {
        this.features = modifiableList(features);
        return this;
    }

    public @Nonnull UniProtEntryBuilder geneLocationAdd(GeneLocation geneLocation) {
        addOrIgnoreNull(geneLocation, this.geneLocations);
        return this;
    }

    public @Nonnull UniProtEntryBuilder geneLocationsSet(List<GeneLocation> geneLocations) {
        this.geneLocations = modifiableList(geneLocations);
        return this;
    }

    public @Nonnull UniProtEntryBuilder keywordAdd(Keyword keyword) {
        addOrIgnoreNull(keyword, this.keywords);
        return this;
    }

    public @Nonnull UniProtEntryBuilder keywordsSet(List<Keyword> keywords) {
        this.keywords = modifiableList(keywords);
        return this;
    }

    public @Nonnull UniProtEntryBuilder referenceAdd(UniProtReference reference) {
        addOrIgnoreNull(reference, this.references);
        return this;
    }

    public @Nonnull UniProtEntryBuilder referencesSet(List<UniProtReference> references) {
        this.references = modifiableList(references);
        return this;
    }

    public @Nonnull UniProtEntryBuilder databaseCrossReferenceAdd(
            UniProtDBCrossReference databaseCrossReference) {
        addOrIgnoreNull(databaseCrossReference, this.databaseCrossReferences);
        return this;
    }

    public @Nonnull UniProtEntryBuilder databaseCrossReferencesSet(
            List<UniProtDBCrossReference> databaseCrossReferences) {
        this.databaseCrossReferences = modifiableList(databaseCrossReferences);
        return this;
    }

    public @Nonnull UniProtEntryBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public @Nonnull UniProtEntryBuilder internalSection(InternalSection internalSection) {
        this.internalSection = internalSection;
        return this;
    }

    public @Nonnull UniProtEntryBuilder lineageAdd(TaxonomyLineage lineage) {
        addOrIgnoreNull(lineage, this.lineages);
        return this;
    }

    public @Nonnull UniProtEntryBuilder lineagesSet(List<TaxonomyLineage> lineages) {
        this.lineages = modifiableList(lineages);
        return this;
    }

    @Override
    public @Nonnull UniProtEntry build() {
        return new UniProtEntryImpl(
                entryType,
                primaryAccession,
                secondaryAccessions,
                uniProtId,
                entryAudit,
                annotationScore,
                organism,
                organismHosts,
                proteinExistence,
                proteinDescription,
                genes,
                comments,
                features,
                geneLocations,
                keywords,
                references,
                databaseCrossReferences,
                sequence,
                internalSection,
                lineages,
                inactiveReason);
    }

    @Override
    @Deprecated
    // TODO delete this method in future
    public @Nonnull UniProtEntryBuilder from(@Nonnull UniProtEntry instance) {
        return UniProtEntryBuilder.fromInstance(instance);
    }

    // TODO rename this method to from
    public static @Nonnull UniProtEntryBuilder fromInstance(@Nonnull UniProtEntry instance) {
        UniProtEntryBuilder builder =
                new UniProtEntryBuilder(
                        instance.getPrimaryAccession(),
                        instance.getUniProtId(),
                        instance.getEntryType());
        builder.secondaryAccessionsSet(instance.getSecondaryAccessions())
                .organismHostsSet(instance.getOrganismHosts())
                .entryAudit(instance.getEntryAudit())
                .organism(instance.getOrganism())
                .proteinExistence(instance.getProteinExistence())
                .proteinDescription(instance.getProteinDescription())
                .genesSet(instance.getGenes())
                .commentsSet(instance.getComments())
                .featuresSet(instance.getFeatures())
                .geneLocationsSet(instance.getGeneLocations())
                .keywordsSet(instance.getKeywords())
                .referencesSet(instance.getReferences())
                .databaseCrossReferencesSet(instance.getDatabaseCrossReferences())
                .sequence(instance.getSequence())
                .internalSection(instance.getInternalSection())
                .annotationScore(instance.getAnnotationScore())
                .lineagesSet(instance.getLineages());
        builder.inactiveReason = instance.getInactiveReason();
        return builder;
    }
}
