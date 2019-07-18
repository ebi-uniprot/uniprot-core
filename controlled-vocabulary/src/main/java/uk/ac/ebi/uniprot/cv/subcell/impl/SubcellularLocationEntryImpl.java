package uk.ac.ebi.uniprot.cv.subcell.impl;

import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.subcell.SubcellLocationCategory;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationEntry;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationStatistics;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SubcellularLocationEntryImpl implements SubcellularLocationEntry {
    private SubcellLocationCategory category;
    private String id;
    private String accession;
    private String definition;
    private String content;
    private List<String> synonyms = Collections.emptyList();
    private Keyword keyword;
    private List<GeneOntology> geneOntologies = Collections.emptyList();
    private String note;
    private List<String> references = Collections.emptyList();
    private List<String> links = Collections.emptyList();
    private List<SubcellularLocationEntry> isA = Collections.emptyList();
    private List<SubcellularLocationEntry> partOf = Collections.emptyList();
    private SubcellularLocationStatistics statistics;

    @Override
    public SubcellLocationCategory getCategory() {
        return category;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getAccession() {
        return accession;
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
    public Optional<Keyword> getKeyword() {
        return Optional.ofNullable(keyword);
    }

    @Override
    public List<GeneOntology> getGeneOntologies() {
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
    public SubcellularLocationStatistics getStatistics() {
        return statistics;
    }

    public void setCategory(SubcellLocationCategory category) {
        this.category = category;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAccession(String accession) {
        this.accession = accession;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public void setGeneOntologies(List<GeneOntology> geneOntologies) {
        this.geneOntologies = geneOntologies;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setReferences(List<String> references) {
        this.references = references;
    }

    public void setLinks(List<String> links) {
        this.links = links;
    }

    public void setIsA(List<SubcellularLocationEntry> isA) {
        this.isA = isA;
    }

    public void setPartOf(List<SubcellularLocationEntry> partOf) {
        this.partOf = partOf;
    }

    public void setStatistics(SubcellularLocationStatistics statistics) {
        this.statistics = statistics;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubcellularLocationEntryImpl that = (SubcellularLocationEntryImpl) o;
        return getCategory() == that.getCategory() &&
                Objects.equals(getId(), that.getId()) &&
                Objects.equals(getAccession(), that.getAccession()) &&
                Objects.equals(getDefinition(), that.getDefinition()) &&
                Objects.equals(getContent(), that.getContent()) &&
                Objects.equals(getSynonyms(), that.getSynonyms()) &&
                Objects.equals(getKeyword(), that.getKeyword()) &&
                Objects.equals(getGeneOntologies(), that.getGeneOntologies()) &&
                Objects.equals(getNote(), that.getNote()) &&
                Objects.equals(getReferences(), that.getReferences()) &&
                Objects.equals(getLinks(), that.getLinks()) &&
                Objects.equals(getIsA(), that.getIsA()) &&
                Objects.equals(getPartOf(), that.getPartOf()) &&
                Objects.equals(getStatistics(), that.getStatistics());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCategory(), getId(), getAccession(), getDefinition(), getContent(), getSynonyms(),
                getKeyword(), getGeneOntologies(), getNote(), getReferences(), getLinks(), getIsA(),
                getPartOf(), getStatistics());
    }
}
