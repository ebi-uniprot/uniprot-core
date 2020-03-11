package org.uniprot.core.cv.pathway.impl;

import static org.uniprot.core.util.Utils.unmodifiableList;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.pathway.PathwayEntry;

public class PathwayEntryImpl implements PathwayEntry {
    private String accession;
    private String id;
    private String pathwayClass;
    private String definition;
    private List<String> synonyms;
    private List<PathwayEntry> isAParents;
    private List<PathwayEntry> partOfParents;
    private List<DiseaseCrossReference> crossReferences;

    PathwayEntryImpl() {
        this.synonyms = Collections.emptyList();
        this.isAParents = Collections.emptyList();
        this.partOfParents = Collections.emptyList();
        this.crossReferences = Collections.emptyList();
    }

    PathwayEntryImpl(
            String accession,
            String id,
            String pathwayClass,
            String definition,
            List<String> synonyms,
            List<PathwayEntry> isAParents,
            List<PathwayEntry> partOfParents,
            List<DiseaseCrossReference> crossReferences) {
        this.accession = accession;
        this.id = id;
        this.pathwayClass = pathwayClass;
        this.definition = definition;
        this.synonyms = unmodifiableList(synonyms);
        this.isAParents = unmodifiableList(isAParents);
        this.partOfParents = unmodifiableList(partOfParents);
        this.crossReferences = unmodifiableList(crossReferences);
    }

    public String getAccession() {
        return accession;
    }

    public String getId() {
        return id;
    }

    public String getPathwayClass() {
        return pathwayClass;
    }

    public String getDefinition() {
        return definition;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public List<PathwayEntry> getIsAParents() {
        return isAParents;
    }

    public List<PathwayEntry> getPartOfParents() {
        return partOfParents;
    }

    public List<DiseaseCrossReference> getCrossReferences() {
        return crossReferences;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PathwayEntryImpl that = (PathwayEntryImpl) o;
        return Objects.equals(accession, that.accession) &&
          Objects.equals(id, that.id) &&
          Objects.equals(pathwayClass, that.pathwayClass) &&
          Objects.equals(definition, that.definition) &&
          Objects.equals(synonyms, that.synonyms) &&
          Objects.equals(isAParents, that.isAParents) &&
          Objects.equals(partOfParents, that.partOfParents) &&
          Objects.equals(crossReferences, that.crossReferences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accession, id, pathwayClass, definition, synonyms, isAParents, partOfParents, crossReferences);
    }
}
