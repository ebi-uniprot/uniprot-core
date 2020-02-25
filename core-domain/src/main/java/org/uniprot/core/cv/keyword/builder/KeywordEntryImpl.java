package org.uniprot.core.cv.keyword.builder;

import static org.uniprot.core.util.Utils.unmodifiableList;
import static org.uniprot.core.util.Utils.unmodifiableSet;

import java.util.*;

import org.uniprot.core.Statistics;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.KeywordGeneOntology;

public class KeywordEntryImpl implements KeywordEntry {

    private static final long serialVersionUID = 226389200105459588L;
    private KeywordId keyword;
    private String definition;
    private List<String> synonyms;
    private List<KeywordGeneOntology> geneOntologies;
    private Set<KeywordEntry> parents;
    private List<String> sites;
    private KeywordId category;
    private List<KeywordEntry> children;
    private Statistics statistics;

    KeywordEntryImpl() {
        children = Collections.emptyList();
        parents = Collections.emptySet();
        synonyms = Collections.emptyList();
        geneOntologies = Collections.emptyList();
        sites = Collections.emptyList();
    }

    KeywordEntryImpl(
            KeywordId keyword,
            String definition,
            List<String> synonyms,
            List<KeywordGeneOntology> geneOntologies,
            Set<KeywordEntry> parents,
            List<String> sites,
            KeywordId category,
            List<KeywordEntry> children,
            Statistics statistics) {
        this.keyword = keyword;
        this.definition = definition;
        this.synonyms = unmodifiableList(synonyms);
        this.geneOntologies = unmodifiableList(geneOntologies);
        this.parents = unmodifiableSet(parents);
        this.sites = unmodifiableList(sites);
        this.category = category;
        this.children = unmodifiableList(children);
        this.statistics = statistics;
    }

    public Set<KeywordEntry> getParents() {
        return parents;
    }

    public KeywordId getCategory() {
        return category;
    }

    public KeywordId getKeyword() {
        return keyword;
    }

    public String getDefinition() {
        return definition;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public List<KeywordGeneOntology> getGeneOntologies() {
        return geneOntologies;
    }

    public List<String> getSites() {
        return sites;
    }

    public List<KeywordEntry> getChildren() {
        return children;
    }

    public String getAccession() {
        return getKeyword().getId();
    }

    public Statistics getStatistics() {
        return statistics;
    }

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
