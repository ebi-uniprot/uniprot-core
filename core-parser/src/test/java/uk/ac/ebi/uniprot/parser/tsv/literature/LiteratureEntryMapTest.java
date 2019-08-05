package uk.ac.ebi.uniprot.parser.tsv.literature;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.impl.AuthorImpl;
import org.uniprot.core.citation.impl.PublicationDateImpl;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.literature.LiteratureStatistics;
import org.uniprot.core.literature.builder.LiteratureEntryBuilder;
import org.uniprot.core.literature.builder.LiteratureMappedReferenceBuilder;
import org.uniprot.core.literature.builder.LiteratureStatisticsBuilder;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

import java.util.Map;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author lgonzales
 * @since 2019-07-08
 */
class LiteratureEntryMapTest {

    @Test
    void checkSimpleEntryAttributeValues() {
        LiteratureEntry entry = new LiteratureEntryBuilder().pubmedId(10L).build();
        Map<String, String> mappedEntries = new LiteratureEntryMap(entry).attributeValues();
        assertThat(mappedEntries, notNullValue());
        assertEquals(12, mappedEntries.size());
        assertEquals("10", mappedEntries.get("id"));
        mappedEntries.remove("id");

        mappedEntries.values().forEach(value -> assertEquals("", value));
    }

    @Test
    void checkCompleteEntryAttributeValues() {
        LiteratureEntry entry = createLiteratureEntry();

        Map<String, String> mappedEntries = new LiteratureEntryMap(entry).attributeValues();

        assertEquals(12, mappedEntries.size());
        assertEquals("100", mappedEntries.get("id"));
        assertEquals("doi Id", mappedEntries.get("doi"));
        assertEquals("title", mappedEntries.get("title"));
        assertEquals("journal Name volume:first Page-last Page(21-06-2019)", mappedEntries.get("reference"));
        assertEquals("authoring group; author name", mappedEntries.get("author_and_group"));
        assertEquals("journal Name", mappedEntries.get("journal"));
        assertEquals("source value; source Id; source category; annotation value", mappedEntries.get("mapped_references"));
        assertEquals("author name", mappedEntries.get("author"));
        assertEquals("21-06-2019", mappedEntries.get("publication"));
        assertEquals("authoring group", mappedEntries.get("authoring_group"));
        assertEquals("literature Abstract", mappedEntries.get("lit_abstract"));
        assertEquals("mapped:30; reviewed:10; annotated:20", mappedEntries.get("statistics"));
    }

    private LiteratureEntry createLiteratureEntry() {
        return new LiteratureEntryBuilder()
                .doiId("doi Id")
                .pubmedId(100L)
                .firstPage("first Page")
                .journal("journal Name")
                .volume("volume")
                .lastPage("last Page")
                .literatureAbstract("literature Abstract")
                .publicationDate(new PublicationDateImpl("21-06-2019"))
                .statistics(createCompleteLiteratureStatistics())
                .title("title")
                .completeAuthorList(false)
                .addAuthor(new AuthorImpl("author name"))
                .addAuthoringGroup("authoring group")
                .addLiteratureMappedReference(createCompleteLiteratureMappedReference())
                .build();
    }

    private LiteratureMappedReference createCompleteLiteratureMappedReference() {
        return new LiteratureMappedReferenceBuilder()
                .annotation("annotation value")
                .source("source value")
                .sourceId("source Id")
                .addSourceCategory("source category")
                .uniprotAccession(new UniProtAccessionImpl("P12345"))
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