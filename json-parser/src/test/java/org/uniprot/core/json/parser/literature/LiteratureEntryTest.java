package org.uniprot.core.json.parser.literature;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.impl.AuthorImpl;
import org.uniprot.core.citation.impl.PublicationDateImpl;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.literature.LiteratureEntry;
import org.uniprot.core.literature.builder.LiteratureEntryBuilder;

/** @author lgonzales */
class LiteratureEntryTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureEntryBuilder builder = new LiteratureEntryBuilder();

        LiteratureEntry literatureEntry = builder.completeAuthorList(false).build();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureEntry);
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureEntry literatureEntry = getCompleteLiteratureEntry();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureEntry);
        ValidateJson.verifyEmptyFields(literatureEntry);
    }

    static LiteratureEntry getCompleteLiteratureEntry() {
        return new LiteratureEntryBuilder()
                .doiId("doi Id")
                .pubmedId(100L)
                .authorsAdd(new AuthorImpl("author name"))
                .authoringGroupsAdd("authoring group")
                .completeAuthorList(true)
                .firstPage("first Page")
                .journal("journal Name")
                .volume("volume")
                .lastPage("last Page")
                .literatureAbstract("literature Abstract")
                .publicationDate(new PublicationDateImpl("21-06-2019"))
                .title("title")
                .statistics(LiteratureStatisticsTest.getCompleteLiteratureStatistics())
                .build();
    }
}
