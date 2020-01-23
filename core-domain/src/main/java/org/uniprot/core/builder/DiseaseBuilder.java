package org.uniprot.core.builder;

import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.disease.CrossReference;
import org.uniprot.core.cv.disease.Disease;
import org.uniprot.core.cv.disease.impl.DiseaseImpl;
import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.core.util.Utils;

public class DiseaseBuilder implements Builder<DiseaseBuilder, Disease> {
    private String id;
    private String accession;
    private String acronym;
    private String definition;
    private List<String> alternativeNames;
    private List<CrossReference> crossReferences;
    private List<Keyword> keywords;
    private Long reviewedProteinCount;
    private Long unreviewedProteinCount;

    public static @Nonnull DiseaseBuilder newInstance() {
        return new DiseaseBuilder();
    }

    @Override
    public @Nonnull Disease build() {
        return new DiseaseImpl(
                this.id,
                this.accession,
                this.acronym,
                this.definition,
                this.alternativeNames,
                this.crossReferences,
                this.keywords,
                this.reviewedProteinCount,
                this.unreviewedProteinCount);
    }

    public static @Nonnull DiseaseBuilder from(@Nonnull Disease instance) {
        DiseaseBuilder builder = new DiseaseBuilder();
        builder.id(instance.getId())
        .accession(instance.getAccession())
        .acronym(instance.getAcronym())
        .definition(instance.getDefinition())
        .alternativeNames(instance.getAlternativeNames())
        .crossReferences(instance.getCrossReferences())
        .keywords(instance.getKeywords())
        .reviewedProteinCount(instance.getReviewedProteinCount())
        .unreviewedProteinCount(instance.getUnreviewedProteinCount());
        return builder;
    }

    public @Nonnull DiseaseBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull DiseaseBuilder accession(String accession) {
        this.accession = accession;
        return this;
    }

    public @Nonnull DiseaseBuilder acronym(String acronym) {
        this.acronym = acronym;
        return this;
    }

    public @Nonnull DiseaseBuilder definition(String definition) {
        this.definition = definition;
        return this;
    }

    // for single string
    public @Nonnull DiseaseBuilder alternativeNames(String alternativeName) {
        this.alternativeNames = Utils.modifiableList(this.alternativeNames);
        this.alternativeNames.add(alternativeName);
        return this;
    }

    public @Nonnull DiseaseBuilder alternativeNames(List<String> alternativeNames) {
        this.alternativeNames = alternativeNames;
        return this;
    }

    // setter for single object
    public @Nonnull DiseaseBuilder crossReferences(CrossReference crossReference) {
        this.crossReferences = Utils.modifiableList(this.crossReferences);
        this.crossReferences.add(crossReference);
        return this;
    }

    public @Nonnull DiseaseBuilder crossReferences(List<CrossReference> crossReferences) {
        this.crossReferences = crossReferences;
        return this;
    }

    // setter for single object
    public @Nonnull DiseaseBuilder keywords(Keyword keyword) {
        this.keywords = Utils.modifiableList(this.keywords);
        this.keywords.add(keyword);
        return this;
    }

    public @Nonnull DiseaseBuilder keywords(List<Keyword> keywords) {
        this.keywords = keywords;
        return this;
    }

    public @Nonnull DiseaseBuilder reviewedProteinCount(Long reviewedProteinCount) {
        this.reviewedProteinCount = reviewedProteinCount;
        return this;
    }

    public @Nonnull DiseaseBuilder unreviewedProteinCount(Long unreviewedProteinCount) {
        this.unreviewedProteinCount = unreviewedProteinCount;
        return this;
    }
}
