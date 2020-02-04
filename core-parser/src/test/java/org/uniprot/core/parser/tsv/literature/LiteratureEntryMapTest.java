package org.uniprot.core.parser.tsv.literature;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.builder.LiteratureBuilder;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.builder.LiteratureEntryBuilder;
import org.uniprot.core.literature.builder.LiteratureStatisticsBuilder;

/**
 * @author lgonzales
 * @since 2019-07-08
 */
class LiteratureEntryMapTest {

    @Test
    void checkSimpleEntryAttributeValues() {
        LiteratureEntry entry =
                new LiteratureEntryBuilder()
                        .statistics(createCompleteLiteratureStatistics())
                        .build();
        Map<String, String> mappedEntries = new LiteratureEntryMap(entry).attributeValues();
        assertThat(mappedEntries, notNullValue());
        assertEquals(11, mappedEntries.size());
        assertEquals("mapped:30; reviewed:10; annotated:20", mappedEntries.get("statistics"));
        mappedEntries.remove("statistics");

        mappedEntries.values().forEach(value -> assertEquals("", value));
    }

    @Test
    void checkCompleteEntryAttributeValues() {
        LiteratureEntry entry = createCompleteLiteratureEntry();

        Map<String, String> mappedEntries = new LiteratureEntryMap(entry).attributeValues();

        assertEquals(11, mappedEntries.size());
        assertEquals("100", mappedEntries.get("id"));
        assertEquals("doi Id", mappedEntries.get("doi"));
        assertEquals("title", mappedEntries.get("title"));
        assertEquals(
                "journal Name volume:first Page-last Page(21-06-2019)",
                mappedEntries.get("reference"));
        assertEquals("authoring group; author name", mappedEntries.get("author_and_group"));
        assertEquals("journal Name", mappedEntries.get("journal"));
        assertEquals("author name", mappedEntries.get("author"));
        assertEquals("21-06-2019", mappedEntries.get("publication"));
        assertEquals("authoring group", mappedEntries.get("authoring_group"));
        assertEquals("literature Abstract", mappedEntries.get("lit_abstract"));
        assertEquals("mapped:30; reviewed:10; annotated:20", mappedEntries.get("statistics"));
    }

    private LiteratureEntry createCompleteLiteratureEntry() {
        return new LiteratureEntryBuilder()
                .citation(createCompleteLiteratureCitation())
                .statistics(createCompleteLiteratureStatistics())
                .build();
    }

    private Citation createCompleteLiteratureCitation() {
        DBCrossReference<CitationXrefType> pubmed =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
                        .id("100")
                        .build();

        DBCrossReference<CitationXrefType> doi =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.DOI)
                        .id("doi Id")
                        .build();

        return new LiteratureBuilder()
                .literatureAbstract("literature Abstract")
                .completeAuthorList(true)
                .firstPage("first Page")
                .lastPage("last Page")
                .volume("volume")
                .journalName("journal Name")
                .addAuthor("author name")
                .addAuthorGroup("authoring group")
                .addCitationXrefs(pubmed)
                .addCitationXrefs(doi)
                .publicationDate("21-06-2019")
                .title("title")
                .completeAuthorList(false)
                .authorsAdd(new AuthorImpl("author name"))
                .authoringGroupsAdd("authoring group")
                .build();
    }

    private LiteratureStatistics createCompleteLiteratureStatistics() {
        return new LiteratureStatisticsBuilder()
                .reviewedProteinCount(10)
                .unreviewedProteinCount(20)
                .mappedProteinCount(30)
                .build();
    }
}
