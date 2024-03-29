package org.uniprot.core.proteome.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.proteome.*;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.util.Utils;

public class ProteomeEntryBuilder implements Builder<ProteomeEntry> {
    private ProteomeId id;
    private String description;
    private Taxonomy taxonomy;
    private LocalDate modified;
    private ProteomeType proteomeType = ProteomeType.NORMAL;
    private ProteomeId redundantTo;
    private String strain;
    private String isolate;
    private List<Component> components = new ArrayList<>();
    private List<Citation> citations = new ArrayList<>();
    private List<RedundantProteome> redundantProteomes = new ArrayList<>();
    private ProteomeId panproteome;
    private Integer annotationScore;
    private Integer geneCount;
    private Superkingdom superkingdom;
    private List<TaxonomyLineage> taxonLineages = new ArrayList<>();
    private ProteomeCompletenessReport proteomeCompletenessReport;
    private GenomeAssembly genomeAssembly;
    private GenomeAnnotation genomeAnnotation;
    private List<ExclusionReason> exclusionReasons = new ArrayList<>();
    private Integer proteinCount;
    private ProteomeStatistics proteomeStatistics;

    @Override
    public @Nonnull ProteomeEntry build() {
        return new ProteomeEntryImpl(
                id,
                taxonomy,
                description,
                modified,
                proteomeType,
                redundantTo,
                strain,
                isolate,
                components,
                citations,
                redundantProteomes,
                panproteome,
                annotationScore,
                superkingdom,
                geneCount,
                taxonLineages,
                proteomeCompletenessReport,
                genomeAssembly,
                genomeAnnotation,
                exclusionReasons,
                proteinCount,
                proteomeStatistics);
    }

    public static @Nonnull ProteomeEntryBuilder from(@Nonnull ProteomeEntry instance) {
        return new ProteomeEntryBuilder()
                .proteomeId(instance.getId())
                .taxonomy(instance.getTaxonomy())
                .description(instance.getDescription())
                .modified(instance.getModified())
                .proteomeType(instance.getProteomeType())
                .redundantTo(instance.getRedundantTo())
                .strain(instance.getStrain())
                .isolate(instance.getIsolate())
                .componentsSet(instance.getComponents())
                .citationsSet(instance.getCitations())
                .redundantProteomesSet(instance.getRedudantProteomes())
                .panproteome(instance.getPanproteome())
                .annotationScore(instance.getAnnotationScore())
                .superkingdom(instance.getSuperkingdom())
                .geneCount(instance.getGeneCount())
                .taxonLineagesSet(instance.getTaxonLineages())
                .proteomeCompletenessReport(instance.getProteomeCompletenessReport())
                .genomeAssembly(instance.getGenomeAssembly())
                .genomeAnnotation(instance.getGenomeAnnotation())
                .exclusionReasonsSet(instance.getExclusionReasons())
                .proteinCount(instance.getProteinCount())
                .proteomeStatistics(instance.getProteomeStatistics());
    }

    public @Nonnull ProteomeEntryBuilder proteomeId(ProteomeId id) {
        this.id = id;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder proteinCount(Integer proteinCount) {
        this.proteinCount = proteinCount;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder proteomeId(String id) {
        this.id = new ProteomeIdBuilder(id).build();
        return this;
    }

    public @Nonnull ProteomeEntryBuilder description(String description) {
        this.description = description;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder taxonomy(Taxonomy taxonomy) {
        this.taxonomy = taxonomy;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder modified(LocalDate modified) {
        this.modified = modified;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder proteomeType(ProteomeType proteomeType) {
        this.proteomeType = proteomeType;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder redundantTo(ProteomeId redundantTo) {
        this.redundantTo = redundantTo;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder strain(String strain) {
        this.strain = strain;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder isolate(String isolate) {
        this.isolate = isolate;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder componentsSet(List<Component> components) {
        this.components = Utils.modifiableList(components);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder componentsAdd(Component component) {
        Utils.addOrIgnoreNull(component, components);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder citationsSet(List<Citation> citations) {
        this.citations = Utils.modifiableList(citations);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder citationsAdd(Citation citation) {
        Utils.addOrIgnoreNull(citation, citations);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder redundantProteomesSet(
            List<RedundantProteome> redundantProteomes) {
        this.redundantProteomes = Utils.modifiableList(redundantProteomes);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder redundantProteomesAdd(
            RedundantProteome redundantProteome) {
        Utils.addOrIgnoreNull(redundantProteome, redundantProteomes);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder panproteome(ProteomeId panproteome) {
        this.panproteome = panproteome;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder annotationScore(Integer annotationScore) {
        this.annotationScore = annotationScore;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder geneCount(Integer geneCount) {
        this.geneCount = geneCount;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder superkingdom(Superkingdom superkingdom) {
        this.superkingdom = superkingdom;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder taxonLineagesSet(List<TaxonomyLineage> taxonLineage) {
        this.taxonLineages = Utils.modifiableList(taxonLineage);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder taxonLineagesAdd(TaxonomyLineage taxon) {
        Utils.addOrIgnoreNull(taxon, taxonLineages);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder proteomeCompletenessReport(
            ProteomeCompletenessReport proteomeCompletenessReport) {
        this.proteomeCompletenessReport = proteomeCompletenessReport;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder genomeAssembly(GenomeAssembly genomeAssembly) {
        this.genomeAssembly = genomeAssembly;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder genomeAnnotation(GenomeAnnotation genomeAnnotation) {
        this.genomeAnnotation = genomeAnnotation;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder exclusionReasonsSet(
            List<ExclusionReason> exclusionReasons) {
        this.exclusionReasons = Utils.modifiableList(exclusionReasons);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder exclusionReasonsAdd(ExclusionReason exclusionReason) {
        Utils.addOrIgnoreNull(exclusionReason, exclusionReasons);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder proteomeStatistics(ProteomeStatistics proteomeStatistics) {
        this.proteomeStatistics = proteomeStatistics;
        return this;
    }
}
