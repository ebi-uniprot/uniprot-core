package org.uniprot.core.uniprotkb.impl;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Sequence;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprotkb.*;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.feature.Feature;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.OrganismHost;
import org.uniprot.core.uniprotkb.xdb.UniProtkbCrossReference;

public class UniProtkbEntryBuilder implements Builder<UniProtkbEntry> {

    private UniProtkbAccession primaryAccession;
    private UniProtkbEntryType entryType;
    private List<UniProtkbAccession> secondaryAccessions = new ArrayList<>();
    private UniProtkbId uniProtkbId;
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
    private List<UniProtkbReference> references = new ArrayList<>();
    private List<UniProtkbCrossReference> uniProtkbCrossReferences = new ArrayList<>();
    private Sequence sequence = null;
    private InternalSection internalSection = null;
    private EntryInactiveReason inactiveReason;
    private List<TaxonomyLineage> lineages;

    public UniProtkbEntryBuilder(
            String primaryAccession, String uniProtId, UniProtkbEntryType type) {
        this(
                new UniProtkbAccessionBuilder(primaryAccession).build(),
                new UniProtkbIdBuilder(uniProtId).build(),
                type);
    }

    public UniProtkbEntryBuilder(
            UniProtkbAccession primaryAccession, UniProtkbId uniProtkbId, UniProtkbEntryType type) {
        this(primaryAccession, uniProtkbId, type, null);
    }

    public UniProtkbEntryBuilder(
            String primaryAccession, String uniProtId, EntryInactiveReason inactiveReason) {
        this(
                new UniProtkbAccessionBuilder(primaryAccession).build(),
                new UniProtkbIdBuilder(uniProtId).build(),
                inactiveReason);
    }

    public UniProtkbEntryBuilder(String primaryAccession, EntryInactiveReason inactiveReason) {
        this(new UniProtkbAccessionBuilder(primaryAccession).build(), inactiveReason);
    }

    public UniProtkbEntryBuilder(
            UniProtkbAccession primaryAccession, EntryInactiveReason inactiveReason) {
        this(primaryAccession, null, inactiveReason);
    }

    public UniProtkbEntryBuilder(
            UniProtkbAccession primaryAccession,
            UniProtkbId uniProtkbId,
            EntryInactiveReason inactiveReason) {
        this(primaryAccession, uniProtkbId, UniProtkbEntryType.INACTIVE, inactiveReason);
    }

    private UniProtkbEntryBuilder(
            UniProtkbAccession primaryAccession,
            UniProtkbId uniProtkbId,
            UniProtkbEntryType type,
            EntryInactiveReason inactiveReason) {
        this.entryType = inactiveReason == null ? type : UniProtkbEntryType.INACTIVE;
        this.inactiveReason = inactiveReason;
        this.primaryAccession = primaryAccession;
        this.uniProtkbId = uniProtkbId;
    }

    public @Nonnull UniProtkbEntryBuilder primaryAccession(String primaryAccession) {
        return primaryAccession(new UniProtkbAccessionBuilder(primaryAccession).build());
    }

    public @Nonnull UniProtkbEntryBuilder primaryAccession(UniProtkbAccession primaryAccession) {
        this.primaryAccession = primaryAccession;
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder uniProtId(String uniProtId) {
        return uniProtId(new UniProtkbIdBuilder(uniProtId).build());
    }

    public @Nonnull UniProtkbEntryBuilder uniProtId(UniProtkbId uniProtkbId) {
        this.uniProtkbId = uniProtkbId;
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder entryType(UniProtkbEntryType entryType) {
        this.entryType = entryType;
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder secondaryAccessionsAdd(
            UniProtkbAccession secondaryAccession) {
        addOrIgnoreNull(secondaryAccession, this.secondaryAccessions);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder secondaryAccessionsSet(
            List<UniProtkbAccession> secondaryAccessions) {
        this.secondaryAccessions = modifiableList(secondaryAccessions);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder entryAudit(EntryAudit entryAudit) {
        this.entryAudit = entryAudit;
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder annotationScore(double annotationScore) {
        this.annotationScore = annotationScore;
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder organism(Organism organism) {
        this.organism = organism;
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder organismHostsAdd(OrganismHost organismHost) {
        addOrIgnoreNull(organismHost, this.organismHosts);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder organismHostsSet(List<OrganismHost> organismHosts) {
        this.organismHosts = modifiableList(organismHosts);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder proteinExistence(ProteinExistence proteinExistence) {
        this.proteinExistence = proteinExistence;
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder proteinDescription(
            ProteinDescription proteinDescription) {
        this.proteinDescription = proteinDescription;
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder genesAdd(Gene gene) {
        addOrIgnoreNull(gene, this.genes);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder genesSet(List<Gene> genes) {
        this.genes = modifiableList(genes);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder commentsAdd(Comment comment) {
        addOrIgnoreNull(comment, this.comments);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder commentsSet(List<Comment> comments) {
        this.comments = modifiableList(comments);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder featuresAdd(Feature feature) {
        addOrIgnoreNull(feature, this.features);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder featuresSet(List<Feature> features) {
        this.features = modifiableList(features);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder geneLocationsAdd(GeneLocation geneLocation) {
        addOrIgnoreNull(geneLocation, this.geneLocations);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder geneLocationsSet(List<GeneLocation> geneLocations) {
        this.geneLocations = modifiableList(geneLocations);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder keywordsAdd(Keyword keyword) {
        addOrIgnoreNull(keyword, this.keywords);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder keywordsSet(List<Keyword> keywords) {
        this.keywords = modifiableList(keywords);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder referencesAdd(UniProtkbReference reference) {
        addOrIgnoreNull(reference, this.references);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder referencesSet(List<UniProtkbReference> references) {
        this.references = modifiableList(references);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder uniProtCrossReferencesAdd(
            UniProtkbCrossReference uniProtkbCrossReference) {
        addOrIgnoreNull(uniProtkbCrossReference, this.uniProtkbCrossReferences);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder uniProtCrossReferencesSet(
            List<UniProtkbCrossReference> uniProtkbCrossReferences) {
        this.uniProtkbCrossReferences = modifiableList(uniProtkbCrossReferences);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder internalSection(InternalSection internalSection) {
        this.internalSection = internalSection;
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder lineagesAdd(TaxonomyLineage lineage) {
        addOrIgnoreNull(lineage, this.lineages);
        return this;
    }

    public @Nonnull UniProtkbEntryBuilder lineagesSet(List<TaxonomyLineage> lineages) {
        this.lineages = modifiableList(lineages);
        return this;
    }

    @Override
    public @Nonnull UniProtkbEntry build() {
        return new UniProtkbEntryImpl(
                entryType,
                primaryAccession,
                secondaryAccessions,
                uniProtkbId,
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
                uniProtkbCrossReferences,
                sequence,
                internalSection,
                lineages,
                inactiveReason);
    }

    public static @Nonnull UniProtkbEntryBuilder from(@Nonnull UniProtkbEntry instance) {
        UniProtkbEntryBuilder builder =
                new UniProtkbEntryBuilder(
                        instance.getPrimaryAccession(),
                        instance.getUniProtkbId(),
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
                .uniProtCrossReferencesSet(instance.getUniProtkbCrossReferences())
                .sequence(instance.getSequence())
                .internalSection(instance.getInternalSection())
                .annotationScore(instance.getAnnotationScore())
                .lineagesSet(instance.getLineages());
        builder.inactiveReason = instance.getInactiveReason();
        return builder;
    }
}
