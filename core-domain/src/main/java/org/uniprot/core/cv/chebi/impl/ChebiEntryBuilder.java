package org.uniprot.core.cv.chebi.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.cv.chebi.ChebiEntry;
import org.uniprot.core.util.Utils;

/**
 * Created 07/06/19
 *
 * @author Edd
 */
public class ChebiEntryBuilder implements Builder<ChebiEntry> {
    private String id;
    private String name;
    private String inchiKey;
    private List<ChebiEntry> relatedIds = new ArrayList<>();
    private List<String> synonyms = new ArrayList<>();

    public @Nonnull ChebiEntryBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull ChebiEntryBuilder name(String name) {
        this.name = name;
        return this;
    }

    public @Nonnull ChebiEntryBuilder inchiKey(String inchiKey) {
        this.inchiKey = inchiKey;
        return this;
    }

    public @Nonnull ChebiEntryBuilder relatedIdsAdd(ChebiEntry relatedId) {
        Utils.addOrIgnoreNull(relatedId, this.relatedIds);
        return this;
    }

    public @Nonnull ChebiEntryBuilder relatedIdsSet(List<ChebiEntry> relatedIds) {
        this.relatedIds = Utils.modifiableList(relatedIds);
        return this;
    }

    public @Nonnull ChebiEntryBuilder synonymsAdd(String synonym) {
        Utils.addOrIgnoreNull(synonym, this.synonyms);
        return this;
    }

    public @Nonnull ChebiEntryBuilder synonymsSet(List<String> synonyms) {
        this.synonyms = Utils.modifiableList(synonyms);
        return this;
    }

    public @Nonnull ChebiEntry build() {
        return new ChebiEntryImpl(id, name, inchiKey, relatedIds, synonyms);
    }

    public static @Nonnull ChebiEntryBuilder from(@Nonnull ChebiEntry instance) {
        return new ChebiEntryBuilder()
                .id(instance.getId())
                .name(instance.getName())
                .inchiKey(instance.getInchiKey())
                .relatedIdsSet(instance.getRelatedIds())
                .synonymsSet(instance.getSynonyms());
    }
}
