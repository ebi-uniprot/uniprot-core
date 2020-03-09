package org.uniprot.core.cv.subcell.impl;

import static org.uniprot.core.util.Utils.addOrIgnoreNull;
import static org.uniprot.core.util.Utils.modifiableList;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.uniprot.core.Builder;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.subcell.SubcellLocationCategory;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;

public class SubcellularLocationEntryBuilder implements Builder<SubcellularLocationEntry> {
    private String id;
    private String accession;
    private String definition;
    private String content;
    private KeywordId keyword;
    private String note;
    private Statistics statistics;
    private SubcellLocationCategory category;
    private List<GoTerm> geneOntologies = new ArrayList<>();
    private List<String> synonyms = new ArrayList<>();
    private List<String> references = new ArrayList<>();
    private List<String> links = new ArrayList<>();
    private List<SubcellularLocationEntry> isA = new ArrayList<>();
    private List<SubcellularLocationEntry> partOf = new ArrayList<>();

    public @Nonnull SubcellularLocationEntryBuilder id(String id) {
        this.id = id;
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder accession(String accession) {
        this.accession = accession;
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder definition(String definition) {
        this.definition = definition;
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder content(String content) {
        this.content = content;
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder keyword(KeywordId keyword) {
        this.keyword = keyword;
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder note(String note) {
        this.note = note;
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder statistics(Statistics statistics) {
        this.statistics = statistics;
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder category(SubcellLocationCategory category) {
        this.category = category;
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder geneOntologiesSet(List<GoTerm> geneOntologies) {
        this.geneOntologies = modifiableList(geneOntologies);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder geneOntologiesAdd(GoTerm geneOntology) {
        addOrIgnoreNull(geneOntology, this.geneOntologies);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder synonymsAdd(String synonym) {
        addOrIgnoreNull(synonym, this.synonyms);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder referencesAdd(String reference) {
        addOrIgnoreNull(reference, this.references);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder linksAdd(String link) {
        addOrIgnoreNull(link, this.links);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder isAAdd(SubcellularLocationEntry isA) {
        addOrIgnoreNull(isA, this.isA);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder partOfAdd(SubcellularLocationEntry partOf) {
        addOrIgnoreNull(partOf, this.partOf);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder synonymsSet(List<String> synonyms) {
        this.synonyms = modifiableList(synonyms);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder referencesSet(List<String> references) {
        this.references = modifiableList(references);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder linksSet(List<String> links) {
        this.links = modifiableList(links);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder isASet(List<SubcellularLocationEntry> isA) {
        this.isA = modifiableList(isA);
        return this;
    }

    public @Nonnull SubcellularLocationEntryBuilder partOfSet(
            List<SubcellularLocationEntry> partOf) {
        this.partOf = modifiableList(partOf);
        return this;
    }

    public @Nonnull SubcellularLocationEntry build() {
        return new SubcellularLocationEntryImpl(
                id,
                accession,
                definition,
                content,
                keyword,
                note,
                statistics,
                category,
                geneOntologies,
                synonyms,
                references,
                links,
                isA,
                partOf);
    }

    public static @Nonnull SubcellularLocationEntryBuilder from(
            @Nonnull SubcellularLocationEntry instance) {
        return new SubcellularLocationEntryBuilder()
                .id(instance.getId())
                .accession(instance.getAccession())
                .definition(instance.getDefinition())
                .content(instance.getContent())
                .keyword(instance.getKeyword().orElse(null))
                .note(instance.getNote())
                .statistics(instance.getStatistics())
                .category(instance.getCategory())
                .geneOntologiesSet(instance.getGeneOntologies())
                .synonymsSet(instance.getSynonyms())
                .isASet(instance.getIsA())
                .referencesSet(instance.getReferences())
                .linksSet(instance.getLinks())
                .partOfSet(instance.getPartOf());
    }
}
