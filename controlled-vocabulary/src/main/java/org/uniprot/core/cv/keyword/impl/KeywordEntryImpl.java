package org.uniprot.core.cv.keyword.impl;

import org.uniprot.core.cv.keyword.GeneOntology;
import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordStatistics;

import java.util.*;

public class KeywordEntryImpl implements KeywordEntry {

    private static final long serialVersionUID = 226389200105459588L;
    private Keyword keyword;
    private String definition;
    private List<String> synonyms;
    private List<GeneOntology> geneOntologies;
    private Set<KeywordEntry> parents;
    private List<String> sites;
    private Keyword category;
    private List<KeywordEntry> children;
    private KeywordStatistics statistics;

    public KeywordEntryImpl() {
        children = new ArrayList<>();
        parents = new HashSet<>();
    }

    public KeywordEntryImpl(
            Keyword keyword,
            String definition,
            List<String> synonyms,
            List<GeneOntology> geneOntologies,
            Set<KeywordEntry> parents,
            List<String> sites,
            Keyword category,
            List<KeywordEntry> children,
            KeywordStatistics statistics) {
        this.keyword = keyword;
        this.definition = definition;
        this.synonyms = synonyms;
        this.geneOntologies = geneOntologies;
        this.parents = parents;
        this.sites = sites;
        this.category = category;
        this.children = children;
        this.statistics = statistics;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public void setGeneOntologies(List<GeneOntology> geneOntologies) {
        this.geneOntologies = geneOntologies;
    }

    public void setSites(List<String> sites) {
        this.sites = sites;
    }

    @Override
    public Set<KeywordEntry> getParents() {
        return parents;
    }

    public void setParents(Set<KeywordEntry> parents) {
        this.parents = parents;
    }

    public void setChildren(List<KeywordEntry> children) {
        this.children = children;
    }

    @Override
    public Keyword getCategory() {
        return category;
    }

    public void setCategory(Keyword category) {
        this.category = category;
    }

    @Override
    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    @Override
    public String getDefinition() {
        return definition;
    }

    @Override
    public List<String> getSynonyms() {
        return synonyms;
    }

    public List<GeneOntology> getGeneOntologies() {
        return geneOntologies;
    }

    @Override
    public List<String> getSites() {
        return sites;
    }

    @Override
    public List<KeywordEntry> getChildren() {
        return children;
    }

    @Override
    public String getAccession() {
        return getKeyword().getAccession();
    }

    @Override
    public KeywordStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(KeywordStatistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeywordEntryImpl that = (KeywordEntryImpl) o;
        return Objects.equals(getKeyword(), that.getKeyword())
                && Objects.equals(getDefinition(), that.getDefinition())
                && Objects.equals(getSynonyms(), that.getSynonyms())
                && Objects.equals(getGeneOntologies(), that.getGeneOntologies())
                && Objects.equals(getParents(), that.getParents())
                && Objects.equals(getSites(), that.getSites())
                && Objects.equals(getCategory(), that.getCategory())
                && Objects.equals(getChildren(), that.getChildren())
                && Objects.equals(getStatistics(), that.getStatistics());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getKeyword(),
                getDefinition(),
                getSynonyms(),
                getGeneOntologies(),
                getSites(),
                getCategory(),
                getChildren(),
                getStatistics());
    }
}
