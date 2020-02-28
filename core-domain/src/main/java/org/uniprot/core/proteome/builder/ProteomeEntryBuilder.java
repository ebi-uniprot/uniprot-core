package org.uniprot.core.proteome.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.proteome.*;
import org.uniprot.core.proteome.impl.ProteomeEntryImpl;
import org.uniprot.core.taxonomy.TaxonomyLineage;
import org.uniprot.core.uniprot.taxonomy.Taxonomy;
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
    private List<DBCrossReference<ProteomeDatabase>> dbXReferences = new ArrayList<>();
    private List<Component> components = new ArrayList<>();
    private List<Citation> references = new ArrayList<>();
    private List<RedundantProteome> redundantProteomes = new ArrayList<>();
    private ProteomeId panproteome;
    private int annotationScore;
    private Superkingdom superkingdom;
    private int geneCount;
    private List<TaxonomyLineage> taxonLineages = new ArrayList<>();
    private List<CanonicalProtein> canonicalProteins = new ArrayList<>();
    private String sourceDb;

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
                dbXReferences,
                components,
                references,
                redundantProteomes,
                panproteome,
                annotationScore,
                superkingdom,
                geneCount,
                taxonLineages,
                canonicalProteins,
                sourceDb);
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
                .dbXReferencesSet(instance.getDbXReferences())
                .componentsSet(instance.getComponents())
                .referencesSet(instance.getReferences())
                .redundantProteomesSet(instance.getRedudantProteomes())
                .canonicalProteinsSet(instance.getCanonicalProteins())
                .panproteome(instance.getPanproteome())
                .annotationScore(instance.getAnnotationScore())
                .superkingdom(instance.getSuperkingdom())
                .geneCount(instance.getGeneCount())
                .taxonLineagesSet(instance.getTaxonLineages())
                .sourceDb(instance.getSourceDb());
    }

    public @Nonnull ProteomeEntryBuilder proteomeId(ProteomeId id) {
        this.id = id;
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

    public @Nonnull ProteomeEntryBuilder sourceDb(String sourceDb) {
        this.sourceDb = sourceDb;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder dbXReferencesSet(
            List<DBCrossReference<ProteomeDatabase>> dbXReferences) {
        this.dbXReferences = Utils.modifiableList(dbXReferences);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder dbXReferencesAdd(
            DBCrossReference<ProteomeDatabase> dbXReference) {
        Utils.addOrIgnoreNull(dbXReference, dbXReferences);
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

    public @Nonnull ProteomeEntryBuilder referencesSet(List<Citation> references) {
        this.references = Utils.modifiableList(references);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder referencesAdd(Citation reference) {
        Utils.addOrIgnoreNull(reference, references);
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

    public @Nonnull ProteomeEntryBuilder annotationScore(int annotationScore) {
        this.annotationScore = annotationScore;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder superkingdom(Superkingdom superkingdom) {
        this.superkingdom = superkingdom;
        return this;
    }

    public @Nonnull ProteomeEntryBuilder geneCount(int geneCount) {
        this.geneCount = geneCount;
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

    public @Nonnull ProteomeEntryBuilder canonicalProteinsSet(
            List<CanonicalProtein> canonicalProteins) {
        this.canonicalProteins = Utils.modifiableList(canonicalProteins);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder canonicalProteinsAdd(CanonicalProtein canonicalProtein) {
        Utils.addOrIgnoreNull(canonicalProtein, canonicalProteins);
        return this;
    }
}
