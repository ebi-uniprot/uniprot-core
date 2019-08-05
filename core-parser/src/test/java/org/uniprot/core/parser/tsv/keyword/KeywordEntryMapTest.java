package org.uniprot.core.parser.tsv.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.impl.GeneOntologyImpl;
import org.uniprot.core.cv.keyword.impl.KeywordEntryImpl;
import org.uniprot.core.cv.keyword.impl.KeywordImpl;
import org.uniprot.core.cv.keyword.impl.KeywordStatisticsImpl;
import org.uniprot.core.parser.tsv.keyword.KeywordEntryMap;

import java.util.Collections;
import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class KeywordEntryMapTest {

    @Test
    void checkSimpleEntryAttributeValues() {
        KeywordEntryImpl entry = new KeywordEntryImpl();
        entry.setKeyword(getKeyword());
        Map<String, String> mappedEntries = new KeywordEntryMap(entry).attributeValues();
        assertThat(mappedEntries, notNullValue());
        assertEquals(9, mappedEntries.size());
        assertEquals("KW-0005", mappedEntries.get("id"));
        mappedEntries.remove("id");
        assertEquals("Ligand", mappedEntries.get("name"));
        mappedEntries.remove("name");

        mappedEntries.values().stream().peek(value -> assertEquals("", value));
    }

    @Test
    void checkCompleteEntryAttributeValues() {
        KeywordEntry entry = getKeywordEntry(true);

        Map<String, String> mappedEntries = new KeywordEntryMap(entry).attributeValues();

        assertEquals(10, mappedEntries.size());
        assertEquals("KW-0005", mappedEntries.get("id"));
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

    private KeywordEntryImpl getKeywordEntry(boolean hierarchy) {
        KeywordEntryImpl keywordEntry = new KeywordEntryImpl();
        keywordEntry.setDefinition("Definition value");
        keywordEntry.setKeyword(getKeyword());
        keywordEntry.setGeneOntologies(Collections.singletonList(new GeneOntologyImpl("goId", "goTerm")));
        keywordEntry.setSynonyms(Collections.singletonList("synonym"));
        keywordEntry.setSites(Collections.singletonList("site"));

        KeywordEntryImpl parent = getKeywordEntryParent();
        parent.setParents(Collections.singleton(getKeywordEntryParent()));
        keywordEntry.setParents(Collections.singleton(parent));

        keywordEntry.setCategory(getKeyword());
        keywordEntry.setStatistics(new KeywordStatisticsImpl(10, 20));

        if (hierarchy) {
            KeywordEntryImpl children = getKeywordEntry(false);
            children.setChildren(Collections.singletonList(getKeywordEntry(false)));
            keywordEntry.setChildren(Collections.singletonList(children));
        }
        return keywordEntry;
    }

    private Keyword getKeyword() {
        return new KeywordImpl("Ligand", "KW-0005");
    }

    private KeywordEntryImpl getKeywordEntryParent() {
        KeywordEntryImpl keywordEntry = new KeywordEntryImpl();
        keywordEntry.setKeyword(getKeyword());
        return keywordEntry;
    }


}