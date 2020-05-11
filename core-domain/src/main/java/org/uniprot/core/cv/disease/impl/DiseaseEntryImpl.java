package org.uniprot.core.cv.disease.impl;

import static org.uniprot.core.util.Utils.unmodifiableList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.keyword.KeywordId;

public class DiseaseEntryImpl implements DiseaseEntry {

    private static final long serialVersionUID = 3059038050252487022L;
    private String name;

    private final String id;

    private String acronym;
    private String definition;
    private List<String> alternativeNames;
    private List<DiseaseCrossReference> crossReferences;
    private List<KeywordId> keywords;
    private Long reviewedProteinCount;
    private Long unreviewedProteinCount;

    DiseaseEntryImpl() {
        // do nothing.. just to satisfy the objectmapper
        this.id = null;
        this.alternativeNames = Collections.emptyList();
        this.crossReferences = Collections.emptyList();
        this.keywords = Collections.emptyList();
    }

    DiseaseEntryImpl(
            String name,
            String id,
            String acronym,
            String definition,
            List<String> alternativeNames,
            List<DiseaseCrossReference> crossReferences,
            List<KeywordId> keywords,
            Long reviewedProteinCount,
            Long unreviewedProteinCount) {

        super();
        this.name = name;
        this.id = id;
        this.acronym = acronym;
        this.definition = definition;
        this.alternativeNames = unmodifiableList(alternativeNames);
        this.crossReferences = unmodifiableList(crossReferences);
        this.keywords = unmodifiableList(keywords);
        this.reviewedProteinCount = reviewedProteinCount;
        this.unreviewedProteinCount = unreviewedProteinCount;
    }

    public String getName() {
        return name;
    }

    public String getAcronym() {
        return acronym;
    }

    public String getDefinition() {
        return definition;
    }

    public List<String> getAlternativeNames() {
        return alternativeNames;
    }

    public List<DiseaseCrossReference> getCrossReferences() {
        return crossReferences;
    }

    public List<KeywordId> getKeywords() {
        return keywords;
    }

    public String getId() {
        return id;
    }

    @Override
    public Long getReviewedProteinCount() {
        return this.reviewedProteinCount;
    }

    @Override
    public Long getUnreviewedProteinCount() {
        return this.unreviewedProteinCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiseaseEntryImpl disease = (DiseaseEntryImpl) o;
        return Objects.equals(name, disease.name)
                && Objects.equals(id, disease.id)
                && Objects.equals(acronym, disease.acronym)
                && Objects.equals(definition, disease.definition)
                && Objects.equals(alternativeNames, disease.alternativeNames)
                && Objects.equals(crossReferences, disease.crossReferences)
                && Objects.equals(keywords, disease.keywords)
                && Objects.equals(reviewedProteinCount, disease.reviewedProteinCount)
                && Objects.equals(unreviewedProteinCount, disease.unreviewedProteinCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                name,
                id,
                acronym,
                definition,
                alternativeNames,
                crossReferences,
                keywords,
                reviewedProteinCount,
                unreviewedProteinCount);
    }
}
