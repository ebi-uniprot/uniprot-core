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
    private List<DBCrossReference<ProteomeXReferenceType>> dbXReferences = new ArrayList<>();
    private List<Component> components = new ArrayList<>();
    private List<Citation> references = new ArrayList<>();
    private List<RedundantProteome> redundantProteomes = new ArrayList<>();
    private ProteomeId panproteome;
    private int annotationScore;
    private Superkingdom superkingdom;
    private int geneCount;
    private List<TaxonomyLineage> taxonLineage = new ArrayList<>();
    private List<CanonicalProtein> canonicalProteins = new ArrayList<>();
    private String sourceDb;

    public static @Nonnull ProteomeEntryBuilder newInstance() {
        return new ProteomeEntryBuilder();
    }

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
                taxonLineage,
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
                .dbXReferences(instance.getDbXReferences())
                .components(instance.getComponents())
                .references(instance.getReferences())
                .redundantProteomes(instance.getRedudantProteomes())
                .canonicalProteins(instance.getCanonicalProteins())
                .panproteome(instance.getPanproteome())
                .annotationScore(instance.getAnnotationScore())
                .superkingdom(instance.getSuperkingdom())
                .geneCount(instance.getGeneCount())
                .taxonLineage(instance.getTaxonLineage())
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

    public @Nonnull ProteomeEntryBuilder dbXReferences(
            List<DBCrossReference<ProteomeXReferenceType>> dbXReferences) {
        this.dbXReferences = Utils.modifiableList(dbXReferences);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder addDbXReferences(
            DBCrossReference<ProteomeXReferenceType> dbXReference) {
        Utils.addOrIgnoreNull(dbXReference, dbXReferences);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder components(List<Component> components) {
        this.components = Utils.modifiableList(components);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder addComponent(Component component) {
        Utils.addOrIgnoreNull(component, components);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder references(List<Citation> references) {
        this.references = Utils.modifiableList(references);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder addReference(Citation reference) {
        Utils.addOrIgnoreNull(reference, references);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder redundantProteomes(
            List<RedundantProteome> redundantProteomes) {
        this.redundantProteomes = Utils.modifiableList(redundantProteomes);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder addRedundantProteome(RedundantProteome redundantProteome) {
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

    public @Nonnull ProteomeEntryBuilder taxonLineage(List<TaxonomyLineage> taxonLineage) {
        this.taxonLineage = Utils.modifiableList(taxonLineage);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder addTaxonLineage(TaxonomyLineage taxon) {
        Utils.addOrIgnoreNull(taxon, taxonLineage);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder canonicalProteins(
            List<CanonicalProtein> canonicalProteins) {
        this.canonicalProteins = Utils.modifiableList(canonicalProteins);
        return this;
    }

    public @Nonnull ProteomeEntryBuilder addCanonicalProtein(CanonicalProtein canonicalProtein) {
        Utils.addOrIgnoreNull(canonicalProtein, canonicalProteins);
        return this;
    }
}
