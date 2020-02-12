package org.uniprot.core.cv.pathway;

import java.util.List;

import org.uniprot.core.cv.disease.DiseaseCrossReference;

public class PathwayEntryImpl implements PathwayEntry {
    private String accession;
    private String id;
    private String pathwayClass;
    private String definition;
    private List<String> synonyms;
    private List<PathwayEntry> isAParents;
    private List<PathwayEntry> partOfParents;
    private List<DiseaseCrossReference> crossReferences;

    @Override
    public String getAccession() {
        return accession;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getPathwayClass() {
        return pathwayClass;
    }

    @Override
    public String getDefinition() {
        return definition;
    }

    @Override
    public List<String> getSynonyms() {
        return synonyms;
    }

    @Override
    public List<PathwayEntry> getIsAParents() {
        return isAParents;
    }

    @Override
    public List<PathwayEntry> getPartOfParents() {
        return partOfParents;
    }

    @Override
    public List<DiseaseCrossReference> getCrossReferences() {
        return crossReferences;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPathwayClass(String pathwayClass) {
        this.pathwayClass = pathwayClass;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public void setIsAParents(List<PathwayEntry> isAParents) {
        this.isAParents = isAParents;
    }

    public void setPartOfParents(List<PathwayEntry> partOfParents) {
        this.partOfParents = partOfParents;
    }

    public void setCrossReferences(List<DiseaseCrossReference> crossReferences) {
        this.crossReferences = crossReferences;
    }
}
