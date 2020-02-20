package org.uniprot.core.cv.keyword.builder;

import static org.uniprot.core.util.Utils.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordEntryKeyword;
import org.uniprot.core.cv.keyword.KeywordGeneOntology;

public class KeywordEntryBuilder implements Builder<KeywordEntry> {
    private KeywordEntryKeyword keyword;
    private String definition;
    private List<String> synonyms = new ArrayList<>();
    private List<KeywordGeneOntology> geneOntologies = new ArrayList<>();
    private Set<KeywordEntry> parents = new HashSet<>();
    private List<String> sites = new ArrayList<>();
    private KeywordEntryKeyword category;
    private List<KeywordEntry> children = new ArrayList<>();
    private Statistics statistics;

    public @Nonnull KeywordEntryBuilder keyword(KeywordEntryKeyword keyword) {
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

    public @Nonnull KeywordEntryBuilder geneOntologiesSet(
            List<KeywordGeneOntology> geneOntologies) {
        this.geneOntologies = modifiableList(geneOntologies);
        return this;
    }

    public @Nonnull KeywordEntryBuilder geneOntologiesAdd(KeywordGeneOntology geneOntology) {
        addOrIgnoreNull(geneOntology, this.geneOntologies);
        return this;
    }

    public @Nonnull KeywordEntryBuilder parentsSet(Set<KeywordEntry> parents) {
        this.parents = modifiableSet(parents);
        return this;
    }

    public @Nonnull KeywordEntryBuilder parentsAdd(KeywordEntry parent) {
        addOrIgnoreNull(parent, this.parents);
        return this;
    }

    public @Nonnull KeywordEntryBuilder sitesSet(List<String> sites) {
        this.sites = modifiableList(sites);
        return this;
    }

    public @Nonnull KeywordEntryBuilder sitesAdd(String site) {
        addOrIgnoreNull(site, this.sites);
        return this;
    }

    public @Nonnull KeywordEntryBuilder category(KeywordEntryKeyword category) {
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
                sites,
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
                .sitesSet(instance.getSites())
                .category(instance.getCategory())
                .childrenSet(instance.getChildren())
                .statistics(instance.getStatistics());
    }
}
