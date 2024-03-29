package org.uniprot.core.cv.keyword.impl;

import static org.uniprot.core.util.Utils.unmodifiableList;

import java.util.List;
import java.util.Objects;

import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.util.Utils;

public class KeywordEntryImpl implements KeywordEntry {

    private static final long serialVersionUID = 226389200105459588L;
    private final KeywordId keyword;
    private final String definition;
    private final List<String> synonyms;
    private final List<GoTerm> geneOntologies;
    private final List<KeywordEntry> parents;
    private final List<String> links;
    private KeywordCategory category;
    private final List<KeywordEntry> children;
    private final Statistics statistics;

    KeywordEntryImpl() {
        this(null, null, null, null, null, null, null, null, null);
    }

    KeywordEntryImpl(
            KeywordId keyword,
            String definition,
            List<String> synonyms,
            List<GoTerm> geneOntologies,
            List<KeywordEntry> parents,
            List<String> links,
            KeywordId category,
            List<KeywordEntry> children,
            Statistics statistics) {
        this.keyword = keyword;
        this.definition = definition;
        this.synonyms = unmodifiableList(synonyms);
        this.geneOntologies = unmodifiableList(geneOntologies);
        this.parents = unmodifiableList(parents);
        this.links = unmodifiableList(links);
        if (Utils.notNull(category)) this.category = KeywordCategory.typeOf(category.getName());
        this.children = unmodifiableList(children);
        this.statistics = statistics;
    }

    public List<KeywordEntry> getParents() {
        return parents;
    }

    public KeywordCategory getCategory() {
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

    public List<GoTerm> getGeneOntologies() {
        return geneOntologies;
    }

    public List<String> getLinks() {
        return links;
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
                && Objects.equals(getLinks(), that.getLinks())
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
                getLinks(),
                getCategory(),
                getChildren(),
                getStatistics());
    }
}
