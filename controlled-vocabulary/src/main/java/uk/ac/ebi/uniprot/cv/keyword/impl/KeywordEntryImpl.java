package uk.ac.ebi.uniprot.cv.keyword.impl;

import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.keyword.KeywordEntry;
import uk.ac.ebi.uniprot.cv.keyword.KeywordStatistics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class KeywordEntryImpl implements KeywordEntry {

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

    public KeywordEntryImpl(Keyword keyword, String definition, List<String> synonyms,
                            List<GeneOntology> geneOntologies, Set<KeywordEntry> parents, List<String> sites,
                            Keyword category, List<KeywordEntry> children, KeywordStatistics statistics) {
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
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((category == null) ? 0 : category.hashCode());
        result = prime * result + ((definition == null) ? 0 : definition.hashCode());
        result = prime * result + ((geneOntologies == null) ? 0 : geneOntologies.hashCode());
        result = prime * result + ((parents == null) ? 0 : parents.hashCode());
        result = prime * result + ((keyword == null) ? 0 : keyword.hashCode());
        result = prime * result + ((sites == null) ? 0 : sites.hashCode());
        result = prime * result + ((synonyms == null) ? 0 : synonyms.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KeywordEntryImpl other = (KeywordEntryImpl) obj;
        if (category == null) {
            if (other.category != null)
                return false;
        } else if (!category.equals(other.category))
            return false;
        if (definition == null) {
            if (other.definition != null)
                return false;
        } else if (!definition.equals(other.definition))
            return false;
        if (geneOntologies == null) {
            if (other.geneOntologies != null)
                return false;
        } else if (!geneOntologies.equals(other.geneOntologies))
            return false;
        if (parents == null) {
            if (other.parents != null)
                return false;
        } else if (!parents.equals(other.parents))
            return false;
        if (keyword == null) {
            if (other.keyword != null)
                return false;
        } else if (!keyword.equals(other.keyword))
            return false;
        if (sites == null) {
            if (other.sites != null)
                return false;
        } else if (!sites.equals(other.sites))
            return false;
        if (synonyms == null) {
            if (other.synonyms != null)
                return false;
        } else if (!synonyms.equals(other.synonyms))
            return false;
        return true;
    }

}
