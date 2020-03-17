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
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

public class UniProtKBEntryBuilder implements Builder<UniProtKBEntry> {

    private UniProtKBAccession primaryAccession;
    private UniProtKBEntryType entryType;
    private List<UniProtKBAccession> secondaryAccessions = new ArrayList<>();
    private UniProtKBId uniProtkbId;
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
    private List<UniProtKBReference> references = new ArrayList<>();
    private List<UniProtKBCrossReference> uniProtKBCrossReferences = new ArrayList<>();
    private Sequence sequence = null;
    private InternalSection internalSection = null;
    private EntryInactiveReason inactiveReason;
    private List<TaxonomyLineage> lineages;

    public UniProtKBEntryBuilder(
            String primaryAccession, String uniProtId, UniProtKBEntryType type) {
        this(
                new UniProtKBAccessionBuilder(primaryAccession).build(),
                new UniProtKBIdBuilder(uniProtId).build(),
                type);
    }

    public UniProtKBEntryBuilder(
            UniProtKBAccession primaryAccession, UniProtKBId uniProtkbId, UniProtKBEntryType type) {
        this(primaryAccession, uniProtkbId, type, null);
    }

    public UniProtKBEntryBuilder(
            String primaryAccession, String uniProtId, EntryInactiveReason inactiveReason) {
        this(
                new UniProtKBAccessionBuilder(primaryAccession).build(),
                new UniProtKBIdBuilder(uniProtId).build(),
                inactiveReason);
    }

    public UniProtKBEntryBuilder(String primaryAccession, EntryInactiveReason inactiveReason) {
        this(new UniProtKBAccessionBuilder(primaryAccession).build(), inactiveReason);
    }

    public UniProtKBEntryBuilder(
            UniProtKBAccession primaryAccession, EntryInactiveReason inactiveReason) {
        this(primaryAccession, null, inactiveReason);
    }

    public UniProtKBEntryBuilder(
            UniProtKBAccession primaryAccession,
            UniProtKBId uniProtkbId,
            EntryInactiveReason inactiveReason) {
        this(primaryAccession, uniProtkbId, UniProtKBEntryType.INACTIVE, inactiveReason);
    }

    private UniProtKBEntryBuilder(
            UniProtKBAccession primaryAccession,
            UniProtKBId uniProtkbId,
            UniProtKBEntryType type,
            EntryInactiveReason inactiveReason) {
        this.entryType = inactiveReason == null ? type : UniProtKBEntryType.INACTIVE;
        this.inactiveReason = inactiveReason;
        this.primaryAccession = primaryAccession;
        this.uniProtkbId = uniProtkbId;
    }

    public @Nonnull UniProtKBEntryBuilder primaryAccession(String primaryAccession) {
        return primaryAccession(new UniProtKBAccessionBuilder(primaryAccession).build());
    }

    public @Nonnull UniProtKBEntryBuilder primaryAccession(UniProtKBAccession primaryAccession) {
        this.primaryAccession = primaryAccession;
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder uniProtId(String uniProtId) {
        return uniProtId(new UniProtKBIdBuilder(uniProtId).build());
    }

    public @Nonnull UniProtKBEntryBuilder uniProtId(UniProtKBId uniProtkbId) {
        this.uniProtkbId = uniProtkbId;
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder entryType(UniProtKBEntryType entryType) {
        this.entryType = entryType;
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder secondaryAccessionsAdd(
            UniProtKBAccession secondaryAccession) {
        addOrIgnoreNull(secondaryAccession, this.secondaryAccessions);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder secondaryAccessionsSet(
            List<UniProtKBAccession> secondaryAccessions) {
        this.secondaryAccessions = modifiableList(secondaryAccessions);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder entryAudit(EntryAudit entryAudit) {
        this.entryAudit = entryAudit;
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder annotationScore(double annotationScore) {
        this.annotationScore = annotationScore;
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder organism(Organism organism) {
        this.organism = organism;
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder organismHostsAdd(OrganismHost organismHost) {
        addOrIgnoreNull(organismHost, this.organismHosts);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder organismHostsSet(List<OrganismHost> organismHosts) {
        this.organismHosts = modifiableList(organismHosts);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder proteinExistence(ProteinExistence proteinExistence) {
        this.proteinExistence = proteinExistence;
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder proteinDescription(
            ProteinDescription proteinDescription) {
        this.proteinDescription = proteinDescription;
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder genesAdd(Gene gene) {
        addOrIgnoreNull(gene, this.genes);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder genesSet(List<Gene> genes) {
        this.genes = modifiableList(genes);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder commentsAdd(Comment comment) {
        addOrIgnoreNull(comment, this.comments);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder commentsSet(List<Comment> comments) {
        this.comments = modifiableList(comments);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder featuresAdd(Feature feature) {
        addOrIgnoreNull(feature, this.features);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder featuresSet(List<Feature> features) {
        this.features = modifiableList(features);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder geneLocationsAdd(GeneLocation geneLocation) {
        addOrIgnoreNull(geneLocation, this.geneLocations);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder geneLocationsSet(List<GeneLocation> geneLocations) {
        this.geneLocations = modifiableList(geneLocations);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder keywordsAdd(Keyword keyword) {
        addOrIgnoreNull(keyword, this.keywords);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder keywordsSet(List<Keyword> keywords) {
        this.keywords = modifiableList(keywords);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder referencesAdd(UniProtKBReference reference) {
        addOrIgnoreNull(reference, this.references);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder referencesSet(List<UniProtKBReference> references) {
        this.references = modifiableList(references);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder uniProtCrossReferencesAdd(
            UniProtKBCrossReference uniProtkbCrossReference) {
        addOrIgnoreNull(uniProtkbCrossReference, this.uniProtKBCrossReferences);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder uniProtCrossReferencesSet(
            List<UniProtKBCrossReference> uniProtKBCrossReferences) {
        this.uniProtKBCrossReferences = modifiableList(uniProtKBCrossReferences);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder sequence(Sequence sequence) {
        this.sequence = sequence;
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder internalSection(InternalSection internalSection) {
        this.internalSection = internalSection;
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder lineagesAdd(TaxonomyLineage lineage) {
        addOrIgnoreNull(lineage, this.lineages);
        return this;
    }

    public @Nonnull UniProtKBEntryBuilder lineagesSet(List<TaxonomyLineage> lineages) {
        this.lineages = modifiableList(lineages);
        return this;
    }

    @Override
    public @Nonnull UniProtKBEntry build() {
        return new UniProtKBEntryImpl(
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
                uniProtKBCrossReferences,
                sequence,
                internalSection,
                lineages,
                inactiveReason);
    }

    public static @Nonnull UniProtKBEntryBuilder from(@Nonnull UniProtKBEntry instance) {
        UniProtKBEntryBuilder builder =
                new UniProtKBEntryBuilder(
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
                .uniProtCrossReferencesSet(instance.getUniProtKBCrossReferences())
                .sequence(instance.getSequence())
                .internalSection(instance.getInternalSection())
                .annotationScore(instance.getAnnotationScore())
                .lineagesSet(instance.getLineages());
        builder.inactiveReason = instance.getInactiveReason();
        return builder;
    }
}
