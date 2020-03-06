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
import org.uniprot.core.uniprot.xdb.UniProtCrossReference;

public class UniProtEntryBuilder implements Builder<UniProtEntry> {

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
    private List<UniProtCrossReference> uniProtCrossReferences = new ArrayList<>();
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

    public @Nonnull UniProtEntryBuilder secondaryAccessionsAdd(
            UniProtAccession secondaryAccession) {
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

    public @Nonnull UniProtEntryBuilder organismHostsAdd(OrganismHost organismHost) {
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

    public @Nonnull UniProtEntryBuilder genesAdd(Gene gene) {
        addOrIgnoreNull(gene, this.genes);
        return this;
    }

    public @Nonnull UniProtEntryBuilder genesSet(List<Gene> genes) {
        this.genes = modifiableList(genes);
        return this;
    }

    public @Nonnull UniProtEntryBuilder commentsAdd(Comment comment) {
        addOrIgnoreNull(comment, this.comments);
        return this;
    }

    public @Nonnull UniProtEntryBuilder commentsSet(List<Comment> comments) {
        this.comments = modifiableList(comments);
        return this;
    }

    public @Nonnull UniProtEntryBuilder featuresAdd(Feature feature) {
        addOrIgnoreNull(feature, this.features);
        return this;
    }

    public @Nonnull UniProtEntryBuilder featuresSet(List<Feature> features) {
        this.features = modifiableList(features);
        return this;
    }

    public @Nonnull UniProtEntryBuilder geneLocationsAdd(GeneLocation geneLocation) {
        addOrIgnoreNull(geneLocation, this.geneLocations);
        return this;
    }

    public @Nonnull UniProtEntryBuilder geneLocationsSet(List<GeneLocation> geneLocations) {
        this.geneLocations = modifiableList(geneLocations);
        return this;
    }

    public @Nonnull UniProtEntryBuilder keywordsAdd(Keyword keyword) {
        addOrIgnoreNull(keyword, this.keywords);
        return this;
    }

    public @Nonnull UniProtEntryBuilder keywordsSet(List<Keyword> keywords) {
        this.keywords = modifiableList(keywords);
        return this;
    }

    public @Nonnull UniProtEntryBuilder referencesAdd(UniProtReference reference) {
        addOrIgnoreNull(reference, this.references);
        return this;
    }

    public @Nonnull UniProtEntryBuilder referencesSet(List<UniProtReference> references) {
        this.references = modifiableList(references);
        return this;
    }

    public @Nonnull UniProtEntryBuilder uniProtCrossReferencesAdd(
            UniProtCrossReference uniProtCrossReference) {
        addOrIgnoreNull(uniProtCrossReference, this.uniProtCrossReferences);
        return this;
    }

    public @Nonnull UniProtEntryBuilder uniProtCrossReferencesSet(
            List<UniProtCrossReference> uniProtCrossReferences) {
        this.uniProtCrossReferences = modifiableList(uniProtCrossReferences);
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

    public @Nonnull UniProtEntryBuilder lineagesAdd(TaxonomyLineage lineage) {
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
                uniProtCrossReferences,
                sequence,
                internalSection,
                lineages,
                inactiveReason);
    }

    public static @Nonnull UniProtEntryBuilder from(@Nonnull UniProtEntry instance) {
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
                .uniProtCrossReferencesSet(instance.getUniProtCrossReferences())
                .sequence(instance.getSequence())
                .internalSection(instance.getInternalSection())
                .annotationScore(instance.getAnnotationScore())
                .lineagesSet(instance.getLineages());
        builder.inactiveReason = instance.getInactiveReason();
        return builder;
    }
}
