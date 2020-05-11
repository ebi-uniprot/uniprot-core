package org.uniprot.core.cv.subcell.impl;

import static org.uniprot.core.util.Utils.unmodifiableList;

import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.subcell.SubcellLocationCategory;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SubcellularLocationEntryImpl implements SubcellularLocationEntry {
    private static final long serialVersionUID = 8881869836509747529L;
    private String name;
    private String id;
    private String definition;
    private String content;
    private KeywordId keyword;
    private String note;
    private Statistics statistics;
    private SubcellLocationCategory category;
    private List<GoTerm> geneOntologies = Collections.emptyList();
    private List<String> synonyms = Collections.emptyList();
    private List<String> references = Collections.emptyList();
    private List<String> links = Collections.emptyList();
    private List<SubcellularLocationEntry> isA = Collections.emptyList();
    private List<SubcellularLocationEntry> partOf = Collections.emptyList();

    SubcellularLocationEntryImpl() {}

    SubcellularLocationEntryImpl(
            String name,
            String id,
            String definition,
            String content,
            KeywordId keyword,
            String note,
            Statistics statistics,
            SubcellLocationCategory category,
            List<GoTerm> geneOntologies,
            List<String> synonyms,
            List<String> references,
            List<String> links,
            List<SubcellularLocationEntry> isA,
            List<SubcellularLocationEntry> partOf) {
        this.name = name;
        this.id = id;
        this.definition = definition;
        this.content = content;
        this.keyword = keyword;
        this.note = note;
        this.statistics = statistics;
        this.category = category;
        this.geneOntologies = unmodifiableList(geneOntologies);
        this.synonyms = unmodifiableList(synonyms);
        this.references = unmodifiableList(references);
        this.links = unmodifiableList(links);
        this.isA = unmodifiableList(isA);
        this.partOf = unmodifiableList(partOf);
    }

    @Override
    public SubcellLocationCategory getCategory() {
        return category;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDefinition() {
        return definition;
    }

    @Override
    public String getContent() {
        return content;
    }

    @Override
    public List<String> getSynonyms() {
        return synonyms;
    }

    @Override
    public Optional<KeywordId> getKeyword() {
        return Optional.ofNullable(keyword);
    }

    @Override
    public List<GoTerm> getGeneOntologies() {
        return geneOntologies;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public List<String> getReferences() {
        return references;
    }

    @Override
    public List<String> getLinks() {
        return links;
    }

    @Override
    public List<SubcellularLocationEntry> getIsA() {
        return isA;
    }

    @Override
    public List<SubcellularLocationEntry> getPartOf() {
        return partOf;
    }

    @Override
    public Statistics getStatistics() {
        return statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcellularLocationEntry that = (SubcellularLocationEntry) o;
        return getCategory() == that.getCategory()
                && Objects.equals(getName(), that.getName())
                && Objects.equals(getId(), that.getId())
                && Objects.equals(getDefinition(), that.getDefinition())
                && Objects.equals(getContent(), that.getContent())
                && Objects.equals(getSynonyms(), that.getSynonyms())
                && Objects.equals(getKeyword(), that.getKeyword())
                && Objects.equals(getGeneOntologies(), that.getGeneOntologies())
                && Objects.equals(getNote(), that.getNote())
                && Objects.equals(getReferences(), that.getReferences())
                && Objects.equals(getLinks(), that.getLinks())
                && Objects.equals(getIsA(), that.getIsA())
                && Objects.equals(getPartOf(), that.getPartOf())
                && Objects.equals(getStatistics(), that.getStatistics());
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                getCategory(),
                getName(),
                getId(),
                getDefinition(),
                getContent(),
                getSynonyms(),
                getKeyword(),
                getGeneOntologies(),
                getNote(),
                getReferences(),
                getLinks(),
                getIsA(),
                getPartOf(),
                getStatistics());
    }
}
