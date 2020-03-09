package org.uniprot.core.parser.tsv.subcell;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.cv.subcell.SubcellLocationCategory;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.cv.subcell.impl.SubcellularLocationEntryBuilder;
import org.uniprot.core.impl.StatisticsBuilder;

/**
 * @author lgonzales
 * @since 2019-08-30
 */
class SubcellularLocationEntryMapTest {

    @Test
    void checkSimpleEntryAttributeValues() {
        SubcellularLocationEntry entry =
                new SubcellularLocationEntryBuilder().accession("SL-0001").build();
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
        Statistics statistics =
                new StatisticsBuilder().reviewedProteinCount(10).unreviewedProteinCount(20).build();
        SubcellularLocationEntryBuilder entry = new SubcellularLocationEntryBuilder();
        entry.accession("SL-0001");
        entry.content("content value");
        entry.definition("definition value");
        entry.geneOntologiesAdd(
                new GeneOntologyEntryBuilder().id("goIdValue").name("goTermValue").build());
        entry.id("idValue");
        entry.keyword(
                new KeywordIdBuilder()
                        .id("keywordIdValue")
                        .accession("keywordAccessionValue")
                        .build());
        entry.linksAdd("linkValue");
        entry.note("noteValue");
        entry.referencesAdd("referenceValue");
        entry.statistics(statistics);
        entry.synonymsAdd("synonymValue");
        entry.category(SubcellLocationCategory.LOCATION);
        if (hasChild) {
            entry.isAAdd(createSubcellularLocationEntry(false));
            entry.partOfAdd(createSubcellularLocationEntry(false));
        }
        return entry.build();
    }
}
