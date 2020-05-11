package org.uniprot.core.cv.pathway.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.pathway.PathwayEntry;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

public class PathwayEntryBuilder implements Builder<PathwayEntry> {
    private String id;
    private String name;
    private String pathwayClass;
    private String definition;
    private List<String> synonyms = new ArrayList<>();
    private List<PathwayEntry> isAParents = new ArrayList<>();
    private List<PathwayEntry> partOfParents = new ArrayList<>();
    private List<DiseaseCrossReference> crossReferences = new ArrayList<>();

    public @Nonnull PathwayEntryBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull PathwayEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull PathwayEntryBuilder pathwayClass(String pathwayClass) {
        this.pathwayClass = pathwayClass;
        return this;
    }

    public @Nonnull PathwayEntryBuilder definition(String definition) {
        this.definition = definition;
        return this;
    }

    public @Nonnull PathwayEntryBuilder synonymsSet(List<String> synonyms) {
        this.synonyms = modifiableList(synonyms);
        return this;
    }

    public @Nonnull PathwayEntryBuilder synonymsAdd(String synonym) {
        addOrIgnoreNull(synonym, this.synonyms);
        return this;
    }

    public @Nonnull PathwayEntryBuilder isAParentsSet(List<PathwayEntry> isAParents) {
        this.isAParents = modifiableList(isAParents);
        return this;
    }

    public @Nonnull PathwayEntryBuilder isAParentsAdd(PathwayEntry isAParent) {
        addOrIgnoreNull(isAParent, this.isAParents);
        return this;
    }

    public @Nonnull PathwayEntryBuilder partOfParentsSet(List<PathwayEntry> partOfParents) {
        this.partOfParents = modifiableList(partOfParents);
        return this;
    }

    public @Nonnull PathwayEntryBuilder partOfParentsAdd(PathwayEntry partOfParent) {
        addOrIgnoreNull(partOfParent, this.partOfParents);
        return this;
    }

    public @Nonnull PathwayEntryBuilder crossReferencesSet(
            List<DiseaseCrossReference> crossReferences) {
        this.crossReferences = modifiableList(crossReferences);
        return this;
    }

    public @Nonnull PathwayEntryBuilder crossReferencesAdd(DiseaseCrossReference crossReference) {
        addOrIgnoreNull(crossReference, this.crossReferences);
        return this;
    }

    public @Nonnull PathwayEntry build() {
        return new PathwayEntryImpl(
                id,
                name,
                pathwayClass,
                definition,
                synonyms,
                isAParents,
                partOfParents,
                crossReferences);
    }

    public static @Nonnull PathwayEntryBuilder from(@Nonnull PathwayEntry instance) {
        return new PathwayEntryBuilder()
                .name(instance.getName())
                .id(instance.getId())
                .pathwayClass(instance.getPathwayClass())
                .definition(instance.getDefinition())
                .synonymsSet(instance.getSynonyms())
                .isAParentsSet(instance.getIsAParents())
                .partOfParentsSet(instance.getPartOfParents())
                .crossReferencesSet(instance.getCrossReferences());
    }
}
