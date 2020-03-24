package org.uniprot.core.parser.tsv.literature;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.impl.AuthorBuilder;
import org.uniprot.core.citation.impl.LiteratureBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.impl.LiteratureEntryBuilder;
import org.uniprot.core.literature.impl.LiteratureStatisticsBuilder;

/**
 * @author lgonzales
 * @since 2019-07-08
 */
class LiteratureEntryValueMapperTest {

    @Test
    void checkSimpleEntryAttributeValues() {
        LiteratureEntry entry =
                new LiteratureEntryBuilder()
                        .statistics(createCompleteLiteratureStatistics())
                        .build();
        Map<String, String> mappedEntries =
                new LiteratureEntryMapper().mapEntity(entry, Collections.emptyList());
        assertThat(mappedEntries, notNullValue());
        assertEquals(11, mappedEntries.size());
        assertEquals("mapped:30; reviewed:10; annotated:20", mappedEntries.get("statistics"));
        mappedEntries.remove("statistics");

        mappedEntries.values().forEach(value -> assertEquals("", value));
    }

    @Test
    void checkCompleteEntryAttributeValues() {
        LiteratureEntry entry = createCompleteLiteratureEntry();

        Map<String, String> mappedEntries =
                new LiteratureEntryMapper().mapEntity(entry, Collections.emptyList());

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
        CrossReference<CitationDatabase> pubmed =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("100")
                        .build();

        CrossReference<CitationDatabase> doi =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.DOI)
                        .id("doi Id")
                        .build();

        return new LiteratureBuilder()
                .literatureAbstract("literature Abstract")
                .completeAuthorList(true)
                .firstPage("first Page")
                .lastPage("last Page")
                .volume("volume")
                .journalName("journal Name")
                .citationCrossReferencesAdd(pubmed)
                .citationCrossReferencesAdd(doi)
                .publicationDate("21-06-2019")
                .title("title")
                .completeAuthorList(false)
                .authorsAdd(new AuthorBuilder("author name").build())
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
