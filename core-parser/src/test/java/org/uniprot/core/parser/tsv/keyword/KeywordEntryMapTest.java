package org.uniprot.core.parser.tsv.keyword;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.impl.KeywordEntryBuilder;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.impl.StatisticsBuilder;

class KeywordEntryMapTest {

    @Test
    void checkSimpleEntryAttributeValues() {
        KeywordEntry entry = new KeywordEntryBuilder().keyword(getKeyword()).build();
        Map<String, String> mappedEntries = new KeywordEntryMap(entry).attributeValues();
        assertThat(mappedEntries, notNullValue());
        assertEquals(9, mappedEntries.size());
        assertEquals("KW-9993", mappedEntries.get("id"));
        mappedEntries.remove("id");
        assertEquals("Ligand", mappedEntries.get("name"));
        mappedEntries.remove("name");
        Optional<String> result =
                mappedEntries.values().stream().filter(val -> !val.isEmpty()).findAny();
        assertFalse(result.isPresent());
    }

    @Test
    void checkCompleteEntryAttributeValues() {
        KeywordEntry entry = getKeywordEntry(true);

        Map<String, String> mappedEntries = new KeywordEntryMap(entry).attributeValues();

        assertEquals(10, mappedEntries.size());
        assertEquals("KW-9993", mappedEntries.get("id"));
        assertEquals("Ligand", mappedEntries.get("name"));
        assertEquals("Definition value", mappedEntries.get("description"));
        assertEquals("Ligand", mappedEntries.get("category"));
        assertEquals("synonym", mappedEntries.get("synonym"));
        assertEquals("goId:goTerm", mappedEntries.get("gene_ontology"));
        assertEquals("site", mappedEntries.get("sites"));
        assertEquals("Ligand, Ligand", mappedEntries.get("children"));
        assertEquals("Ligand, Ligand", mappedEntries.get("parent"));
        assertEquals("reviewed:10; annotated:20", mappedEntries.get("statistics"));
    }

    private KeywordEntry getKeywordEntry(boolean hierarchy) {
        Statistics statistics =
                new StatisticsBuilder().reviewedProteinCount(10).unreviewedProteinCount(20).build();
        GeneOntologyEntry go = new GeneOntologyEntryBuilder().id("goId").name("goTerm").build();
        KeywordEntry parent =
                KeywordEntryBuilder.from(getKeywordEntryParent())
                        .parentsSet(Collections.singleton(getKeywordEntryParent()))
                        .build();

        KeywordEntry keywordEntry =
                new KeywordEntryBuilder()
                        .definition("Definition value")
                        .keyword(getKeyword())
                        .geneOntologiesAdd(go)
                        .synonymsAdd("synonym")
                        .sitesAdd("site")
                        .parentsAdd(parent)
                        .category(getKeyword())
                        .statistics(statistics)
                        .build();

        if (hierarchy) {
            KeywordEntry children =
                    KeywordEntryBuilder.from(getKeywordEntry(false))
                            .childrenSet(Collections.singletonList(getKeywordEntry(false)))
                            .build();

            keywordEntry =
                    KeywordEntryBuilder.from(keywordEntry)
                            .childrenSet(Collections.singletonList(children))
                            .build();
        }
        return keywordEntry;
    }

    private KeywordId getKeyword() {
        return new KeywordIdBuilder().name("Ligand").id("KW-9993").build();
    }

    private KeywordEntry getKeywordEntryParent() {
        return new KeywordEntryBuilder().keyword(getKeyword()).build();
    }
}
