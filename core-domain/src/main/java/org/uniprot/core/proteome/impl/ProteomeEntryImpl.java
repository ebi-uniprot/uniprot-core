package org.uniprot.core.proteome.impl;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.proteome.*;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprotkb.taxonomy.Taxonomy;
import org.uniprot.core.util.Utils;

public class ProteomeEntryImpl implements ProteomeEntry {

    private static final long serialVersionUID = 1962327704149624243L;
    private ProteomeId id;
    private String description;
    private Taxonomy taxonomy;
    private LocalDate modified;
    private ProteomeType proteomeType;
    private ProteomeId redundantTo;
    private String strain;
    private String isolate;
    private List<CrossReference<ProteomeDatabase>> proteomeCrossReferences;
    private List<Component> components;
    private List<Citation> citations;
    private List<RedundantProteome> redundantProteomes;
    private ProteomeId panproteome;
    private int annotationScore;
    private Superkingdom superkingdom;

    private int geneCount;
    List<TaxonomyLineage> taxonLineage;
    private List<CanonicalProtein> canonicalProteins;
    private String sourceDb;

    // no arg constructor for JSON deserialization
    ProteomeEntryImpl() {
        proteomeCrossReferences = Collections.emptyList();
        components = Collections.emptyList();
        citations = Collections.emptyList();
        redundantProteomes = Collections.emptyList();
        taxonLineage = Collections.emptyList();
        canonicalProteins = Collections.emptyList();
    }

    ProteomeEntryImpl(
            ProteomeId id,
            Taxonomy taxonomy,
            String description,
            LocalDate modified,
            ProteomeType proteomeType,
            ProteomeId redundantTo,
            String strain,
            String isolate,
            List<CrossReference<ProteomeDatabase>> proteomeCrossReferences,
            List<Component> components,
            List<Citation> citations,
            List<RedundantProteome> redundantProteomes,
            ProteomeId panproteome,
            int annotationScore,
            Superkingdom superkingdom,
            int geneCount,
            List<TaxonomyLineage> taxonLineage,
            List<CanonicalProtein> canonicalProteins,
            String sourceDb) {
        super();
        this.id = id;
        this.taxonomy = taxonomy;
        this.description = description;

        this.modified = modified;
        this.proteomeType = proteomeType;
        this.redundantTo = redundantTo;
        this.strain = strain;
        this.isolate = isolate;
        this.proteomeCrossReferences = Utils.unmodifiableList(proteomeCrossReferences);
        this.components = Utils.unmodifiableList(components);
        this.citations = Utils.unmodifiableList(citations);
        this.redundantProteomes = Utils.unmodifiableList(redundantProteomes);
        this.panproteome = panproteome;
        this.annotationScore = annotationScore;
        this.superkingdom = superkingdom;
        this.geneCount = geneCount;
        this.taxonLineage = Utils.unmodifiableList(taxonLineage);
        this.canonicalProteins = Utils.unmodifiableList(canonicalProteins);
        this.sourceDb = sourceDb;
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
    public ProteomeId getRedundantTo() {
        return redundantTo;
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
    public List<CrossReference<ProteomeDatabase>> getProteomeCrossReferences() {
        return proteomeCrossReferences;
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
    public List<RedundantProteome> getRedudantProteomes() {
        return redundantProteomes;
    }

    @Override
    public ProteomeId getPanproteome() {
        return panproteome;
    }

    @Override
    public int getAnnotationScore() {
        return annotationScore;
    }

    @Override
    public Superkingdom getSuperkingdom() {
        return superkingdom;
    }

    @Override
    public int getProteinCount() {
        return components.stream().mapToInt(Component::getProteinCount).sum();
    }

    @Override
    public int getGeneCount() {
        return geneCount;
    }

    @Override
    public List<TaxonomyLineage> getTaxonLineages() {
        return taxonLineage;
    }

    @Override
    public List<CanonicalProtein> getCanonicalProteins() {
        return this.canonicalProteins;
    }

    @Override
    public String getSourceDb() {
        return sourceDb;
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                components,
                proteomeCrossReferences,
                description,
                id,
                isolate,
                modified,
                panproteome,
                redundantProteomes,
                redundantTo,
                citations,
                strain,
                superkingdom,
                taxonomy,
                proteomeType,
                sourceDb);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        ProteomeEntryImpl other = (ProteomeEntryImpl) obj;
        return Objects.equals(components, other.components)
                && Objects.equals(proteomeCrossReferences, other.proteomeCrossReferences)
                && Objects.equals(description, other.description)
                && Objects.equals(id, other.id)
                && Objects.equals(isolate, other.isolate)
                && Objects.equals(modified, other.modified)
                && Objects.equals(panproteome, other.panproteome)
                && Objects.equals(redundantProteomes, other.redundantProteomes)
                && Objects.equals(redundantTo, other.redundantTo)
                && Objects.equals(citations, other.citations)
                && Objects.equals(strain, other.strain)
                && Objects.equals(superkingdom, other.superkingdom)
                && Objects.equals(taxonomy, other.taxonomy)
                && Objects.equals(proteomeType, other.proteomeType)
                && Objects.equals(sourceDb, other.sourceDb);
    }
}
