package org.uniprot.core.cv.keyword.impl;

import org.uniprot.core.Builder;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordId;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nonnull;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

public class KeywordEntryBuilder implements Builder<KeywordEntry> {
    private KeywordId keyword;
    private String definition;
    private List<String> synonyms = new ArrayList<>();
    private List<GoTerm> geneOntologies = new ArrayList<>();
    private List<KeywordEntry> parents = new ArrayList<>();
    private List<String> links = new ArrayList<>();
    private KeywordId category;
    private List<KeywordEntry> children = new ArrayList<>();
    private Statistics statistics;

    public @Nonnull KeywordEntryBuilder keyword(KeywordId keyword) {
        this.keyword = keyword;
        return this;
    }

    public @Nonnull KeywordEntryBuilder definition(String definition) {
        this.definition = definition;
        return this;
    }

    public @Nonnull KeywordEntryBuilder synonymsSet(List<String> synonyms) {
        this.synonyms = modifiableList(synonyms);
        return this;
    }

    public @Nonnull KeywordEntryBuilder synonymsAdd(String synonym) {
        addOrIgnoreNull(synonym, this.synonyms);
        return this;
    }

    public @Nonnull KeywordEntryBuilder geneOntologiesSet(List<GoTerm> geneOntologies) {
        this.geneOntologies = modifiableList(geneOntologies);
        return this;
    }

    public @Nonnull KeywordEntryBuilder geneOntologiesAdd(GoTerm geneOntology) {
        addOrIgnoreNull(geneOntology, this.geneOntologies);
        return this;
    }

    public @Nonnull KeywordEntryBuilder parentsSet(List<KeywordEntry> parents) {
        this.parents = modifiableList(parents);
        return this;
    }

    public @Nonnull KeywordEntryBuilder parentsAdd(KeywordEntry parent) {
        addOrIgnoreNull(parent, this.parents);
        return this;
    }

    public @Nonnull KeywordEntryBuilder linksSet(List<String> links) {
        this.links = modifiableList(links);
        return this;
    }

    public @Nonnull KeywordEntryBuilder linksAdd(String link) {
        addOrIgnoreNull(link, this.links);
        return this;
    }

    public @Nonnull KeywordEntryBuilder category(KeywordId category) {
        this.category = category;
        return this;
    }

    public @Nonnull KeywordEntryBuilder category(KeywordCategory category) {
        this.category = category;
        return this;
    }

    public @Nonnull KeywordEntryBuilder childrenSet(List<KeywordEntry> children) {
        this.children = modifiableList(children);
        return this;
    }

    public @Nonnull KeywordEntryBuilder childrenAdd(KeywordEntry child) {
        addOrIgnoreNull(child, this.children);
        return this;
    }

    public @Nonnull KeywordEntryBuilder statistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public @Nonnull KeywordEntry build() {
        return new KeywordEntryImpl(
                keyword,
                definition,
                synonyms,
                geneOntologies,
                parents,
                links,
                category,
                children,
                statistics);
    }

    public static @Nonnull KeywordEntryBuilder from(@Nonnull KeywordEntry instance) {
        return new KeywordEntryBuilder()
                .keyword(instance.getKeyword())
                .definition(instance.getDefinition())
                .synonymsSet(instance.getSynonyms())
                .geneOntologiesSet(instance.getGeneOntologies())
                .parentsSet(instance.getParents())
                .linksSet(instance.getLinks())
                .category(instance.getCategory())
                .childrenSet(instance.getChildren())
                .statistics(instance.getStatistics());
    }
}
