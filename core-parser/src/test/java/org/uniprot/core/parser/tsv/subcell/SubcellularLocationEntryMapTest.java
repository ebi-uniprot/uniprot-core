package org.uniprot.core.parser.tsv.subcell;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.impl.KeywordImpl;
import org.uniprot.cv.keyword.impl.GeneOntologyImpl;
import org.uniprot.cv.subcell.SubcellLocationCategory;
import org.uniprot.cv.subcell.SubcellularLocationEntry;
import org.uniprot.cv.subcell.impl.SubcellularLocationEntryImpl;
import org.uniprot.cv.subcell.impl.SubcellularLocationStatisticsImpl;

/**
 * @author lgonzales
 * @since 2019-08-30
 */
class SubcellularLocationEntryMapTest {

    @Test
    void checkSimpleEntryAttributeValues() {
        SubcellularLocationEntryImpl entry = new SubcellularLocationEntryImpl();
        entry.setAccession("SL-0001");
        Map<String, String> mappedEntries =
                new SubcellularLocationEntryMap(entry).attributeValues();
        assertThat(mappedEntries, notNullValue());
        assertEquals(14, mappedEntries.size());
        assertEquals("SL-0001", mappedEntries.get("accession"));
        mappedEntries.remove("accession");

        mappedEntries.values().forEach(value -> assertEquals("", value));
    }

    @Test
    void checkCompleteEntryAttributeValues() {
        SubcellularLocationEntry entry = createSubcellularLocationEntry(true);

        Map<String, String> mappedEntries =
                new SubcellularLocationEntryMap(entry).attributeValues();

        assertEquals(14, mappedEntries.size());
        assertEquals("noteValue", mappedEntries.get("note"));
        assertEquals("referenceValue", mappedEntries.get("references"));
        assertEquals("synonymValue", mappedEntries.get("synonyms"));
        assertEquals("goIdValue:goTermValue", mappedEntries.get("gene_ontologies"));
        assertEquals("SL-0001", mappedEntries.get("accession"));
        assertEquals("content value", mappedEntries.get("content"));
        assertEquals("definition value", mappedEntries.get("definition"));
        assertEquals("linkValue", mappedEntries.get("links"));
        assertEquals("idValue", mappedEntries.get("id"));
        assertEquals("Cellular component", mappedEntries.get("category"));
        assertEquals("keywordAccessionValue", mappedEntries.get("keyword"));
        assertEquals("SL-0001; idValue; LOCATION", mappedEntries.get("is_a"));
        assertEquals("SL-0001; idValue; LOCATION", mappedEntries.get("part_of"));
        assertEquals("reviewed:10; annotated:20", mappedEntries.get("statistics"));
    }

    private SubcellularLocationEntry createSubcellularLocationEntry(boolean hasChild) {
        SubcellularLocationEntryImpl entry = new SubcellularLocationEntryImpl();
        entry.setAccession("SL-0001");
        entry.setContent("content value");
        entry.setDefinition("definition value");
        entry.setGeneOntologies(
                Collections.singletonList(new GeneOntologyImpl("goIdValue", "goTermValue")));
        entry.setId("idValue");
        entry.setKeyword(new KeywordImpl("keywordIdValue", "keywordAccessionValue"));
        entry.setLinks(Collections.singletonList("linkValue"));
        entry.setNote("noteValue");
        entry.setReferences(Collections.singletonList("referenceValue"));
        entry.setStatistics(new SubcellularLocationStatisticsImpl(10L, 20L));
        entry.setSynonyms(Collections.singletonList("synonymValue"));
        entry.setCategory(SubcellLocationCategory.LOCATION);
        if (hasChild) {
            entry.setIsA(Collections.singletonList(createSubcellularLocationEntry(false)));
            entry.setPartOf(Collections.singletonList(createSubcellularLocationEntry(false)));
        }
        return entry;
    }
}
