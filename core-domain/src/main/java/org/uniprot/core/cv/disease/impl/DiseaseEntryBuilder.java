package org.uniprot.core.cv.disease.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.util.Utils;

import java.util.List;

import javax.annotation.Nonnull;

public class DiseaseEntryBuilder implements Builder<DiseaseEntry> {
    private String name;
    private String id;
    private String acronym;
    private String definition;
    private List<String> alternativeNames;
    private List<DiseaseCrossReference> crossReferences;
    private List<KeywordId> keywords;
    private Long reviewedProteinCount;
    private Long unreviewedProteinCount;

    @Override
    public @Nonnull DiseaseEntry build() {
        return new DiseaseEntryImpl(
                this.name,
                this.id,
                this.acronym,
                this.definition,
                this.alternativeNames,
                this.crossReferences,
                this.keywords,
                this.reviewedProteinCount,
                this.unreviewedProteinCount);
    }

    public static @Nonnull DiseaseEntryBuilder from(@Nonnull DiseaseEntry instance) {
        DiseaseEntryBuilder builder = new DiseaseEntryBuilder();
        builder.name(instance.getName())
                .id(instance.getId())
                .acronym(instance.getAcronym())
                .definition(instance.getDefinition())
                .alternativeNamesSet(instance.getAlternativeNames())
                .crossReferencesSet(instance.getCrossReferences())
                .keywordsSet(instance.getKeywords())
                .reviewedProteinCount(instance.getReviewedProteinCount())
                .unreviewedProteinCount(instance.getUnreviewedProteinCount());
        return builder;
    }

    public @Nonnull DiseaseEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull DiseaseEntryBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull DiseaseEntryBuilder acronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public @Nonnull DiseaseEntryBuilder definition(String definition) {
        this.definition = definition;
        return this;
    }

    // for single string
    public @Nonnull DiseaseEntryBuilder alternativeNamesAdd(String alternativeName) {
        this.alternativeNames = Utils.modifiableList(this.alternativeNames);
        this.alternativeNames.add(alternativeName);
        return this;
    }

    public @Nonnull DiseaseEntryBuilder alternativeNamesSet(List<String> alternativeNames) {
        this.alternativeNames = alternativeNames;
        return this;
    }

    // setter for single object
    public @Nonnull DiseaseEntryBuilder crossReferencesAdd(DiseaseCrossReference crossReference) {
        this.crossReferences = Utils.modifiableList(this.crossReferences);
        this.crossReferences.add(crossReference);
        return this;
    }

    public @Nonnull DiseaseEntryBuilder crossReferencesSet(
            List<DiseaseCrossReference> crossReferences) {
        this.crossReferences = crossReferences;
        return this;
    }

    // setter for single object
    public @Nonnull DiseaseEntryBuilder keywordsAdd(KeywordId keyword) {
        this.keywords = Utils.modifiableList(this.keywords);
        this.keywords.add(keyword);
        return this;
    }

    public @Nonnull DiseaseEntryBuilder keywordsSet(List<KeywordId> keywords) {
        this.keywords = keywords;
        return this;
    }

    public @Nonnull DiseaseEntryBuilder reviewedProteinCount(Long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public @Nonnull DiseaseEntryBuilder unreviewedProteinCount(Long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }
}
