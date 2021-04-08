package org.uniprot.core.cv.disease.impl;

import static org.uniprot.core.util.Utils.unmodifiableList;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.Statistics;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.keyword.KeywordId;

public class DiseaseEntryImpl implements DiseaseEntry {

    private static final long serialVersionUID = 3059038050252487022L;
    private final String name;

    private final String id;

    private final String acronym;
    private final String definition;
    private final List<String> alternativeNames;
    private final List<DiseaseCrossReference> crossReferences;
    private final List<KeywordId> keywords;
    private final Statistics statistics;

    DiseaseEntryImpl() {
        this(null, null, null, null, null, null, null, null);
    }

    DiseaseEntryImpl(
            String name,
            String id,
            String acronym,
            String definition,
            List<String> alternativeNames,
            List<DiseaseCrossReference> crossReferences,
            List<KeywordId> keywords,
            Statistics statistics) {

        super();
        this.name = name;
        this.id = id;
        this.acronym = acronym;
        this.definition = definition;
        this.alternativeNames = unmodifiableList(alternativeNames);
        this.crossReferences = unmodifiableList(crossReferences);
        this.keywords = unmodifiableList(keywords);
        this.statistics = statistics;
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

    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    public String getId() {
        return id;
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
                && Objects.equals(statistics, disease.statistics);
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
                statistics);
    }
}
