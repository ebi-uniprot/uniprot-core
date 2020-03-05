package org.uniprot.core.cv.pathway.builder;

import static org.uniprot.core.util.Utils.unmodifiableList;

import java.util.Collections;
import java.util.List;

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
}
