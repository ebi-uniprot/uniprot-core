package org.uniprot.core.proteome.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.proteome.*;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.util.Utils;

public class ProteomeEntryImpl implements ProteomeEntry {

    private static final long serialVersionUID = 1962327704149624243L;
    private final ProteomeId id;
    private final String description;
    private final Taxonomy taxonomy;
    private final LocalDate modified;
    private final ProteomeType proteomeType;
    private final String strain;
    private final String isolate;
    private final List<Component> components;
    private final List<Citation> citations;
    private final Integer annotationScore;
    private final Superkingdom superkingdom;
    private final ProteomeCompletenessReport proteomeCompletenessReport;
    private final GenomeAssembly genomeAssembly;
    private final Integer geneCount;
    private final Integer proteinCount;
    private final GenomeAnnotation genomeAnnotation;
    private final List<TaxonomyLineage> taxonLineage;
    private final List<ExclusionReason> exclusionReasons;
    private final ProteomeStatistics proteomeStatistics;
    private final Taxonomy panproteomeTaxon;
    private final List<RelatedProteome> relatedProteomes;

    // no arg constructor for JSON deserialization
    ProteomeEntryImpl() {
        this(
                null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                null, null, null, null, null, null, null);
    }

    ProteomeEntryImpl(
            ProteomeId id,
            Taxonomy taxonomy,
            String description,
            LocalDate modified,
            ProteomeType proteomeType,
            String strain,
            String isolate,
            List<Component> components,
            List<Citation> citations,
            Integer annotationScore,
            Superkingdom superkingdom,
            Integer geneCount,
            List<TaxonomyLineage> taxonLineage,
            ProteomeCompletenessReport proteomeCompletenessReport,
            GenomeAssembly genomeAssembly,
            GenomeAnnotation genomeAnnotation,
            List<ExclusionReason> exclusionReasons,
            Integer proteinCount,
            ProteomeStatistics proteomeStatistics,
            Taxonomy panproteomeTaxon,
            List<RelatedProteome> relatedProteomes) {
        super();
        this.id = id;
        this.taxonomy = taxonomy;
        this.description = description;

        this.modified = modified;
        this.proteomeType = proteomeType;
        this.strain = strain;
        this.isolate = isolate;
        this.components = Utils.unmodifiableList(components);
        this.citations = Utils.unmodifiableList(citations);
        this.annotationScore = annotationScore;
        this.superkingdom = superkingdom;
        this.geneCount = geneCount;

        this.taxonLineage = Utils.unmodifiableList(taxonLineage);
        this.proteomeCompletenessReport = proteomeCompletenessReport;
        this.genomeAssembly = genomeAssembly;
        this.genomeAnnotation = genomeAnnotation;
        this.exclusionReasons = Utils.unmodifiableList(exclusionReasons);
        this.proteinCount = proteinCount;
        this.proteomeStatistics = proteomeStatistics;
        this.panproteomeTaxon = panproteomeTaxon;
        this.relatedProteomes = Utils.unmodifiableList(relatedProteomes);
    }

    @Override
    public ProteomeId getId() {
        return id;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Taxonomy getTaxonomy() {
        return taxonomy;
    }

    @Override
    public LocalDate getModified() {
        return modified;
    }

    @Override
    public ProteomeType getProteomeType() {
        return proteomeType;
    }

    @Override
    public String getStrain() {
        return strain;
    }

    @Override
    public String getIsolate() {
        return isolate;
    }

    @Override
    public List<Component> getComponents() {
        return components;
    }

    @Override
    public List<Citation> getCitations() {
        return citations;
    }

    @Override
    public Integer getAnnotationScore() {
        return annotationScore;
    }

    @Override
    public Superkingdom getSuperkingdom() {
        return superkingdom;
    }

    @Override
    public Integer getProteinCount() {
        return proteinCount;
    }

    @Override
    public Integer getGeneCount() {
        return geneCount;
    }

    @Override
    public List<TaxonomyLineage> getTaxonLineages() {
        return taxonLineage;
    }

    @Override
    public ProteomeCompletenessReport getProteomeCompletenessReport() {
        return proteomeCompletenessReport;
    }

    @Override
    public GenomeAssembly getGenomeAssembly() {
        return genomeAssembly;
    }

    @Override
    public GenomeAnnotation getGenomeAnnotation() {
        return genomeAnnotation;
    }

    @Override
    public List<ExclusionReason> getExclusionReasons() {
        return exclusionReasons;
    }

    @Override
    public ProteomeStatistics getProteomeStatistics() {
        return proteomeStatistics;
    }

    @Override
    public Taxonomy getPanproteomeTaxon() {
        return panproteomeTaxon;
    }

    @Override
    public List<RelatedProteome> getRelatedProteomes() {
        return relatedProteomes;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                components,
                description,
                id,
                isolate,
                modified,
                citations,
                strain,
                superkingdom,
                taxonomy,
                proteomeType,
                proteomeCompletenessReport,
                genomeAssembly,
                proteinCount,
                genomeAnnotation,
                proteomeStatistics,
                panproteomeTaxon,
                relatedProteomes);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProteomeEntryImpl other = (ProteomeEntryImpl) obj;
        return Objects.equals(components, other.components)
                && Objects.equals(description, other.description)
                && Objects.equals(id, other.id)
                && Objects.equals(isolate, other.isolate)
                && Objects.equals(modified, other.modified)
                && Objects.equals(citations, other.citations)
                && Objects.equals(strain, other.strain)
                && Objects.equals(superkingdom, other.superkingdom)
                && Objects.equals(taxonomy, other.taxonomy)
                && Objects.equals(proteomeType, other.proteomeType)
                && Objects.equals(proteomeCompletenessReport, other.proteomeCompletenessReport)
                && Objects.equals(genomeAssembly, other.genomeAssembly)
                && Objects.equals(geneCount, other.geneCount)
                && Objects.equals(proteinCount, other.proteinCount)
                && Objects.equals(genomeAnnotation, other.genomeAnnotation)
                && Objects.equals(proteomeStatistics, other.proteomeStatistics)
                && Objects.equals(panproteomeTaxon, other.panproteomeTaxon)
                && Objects.equals(relatedProteomes, other.relatedProteomes);
    }
}
