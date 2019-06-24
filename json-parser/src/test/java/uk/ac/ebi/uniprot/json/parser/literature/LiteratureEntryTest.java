package uk.ac.ebi.uniprot.json.parser.literature;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.citation.impl.AuthorImpl;
import uk.ac.ebi.uniprot.domain.citation.impl.PublicationDateImpl;
import uk.ac.ebi.uniprot.domain.literature.LiteratureEntry;
import uk.ac.ebi.uniprot.domain.literature.LiteratureStatistics;
import uk.ac.ebi.uniprot.domain.literature.builder.LiteratureEntryBuilder;
import uk.ac.ebi.uniprot.domain.literature.builder.LiteratureStatisticsBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

/**
 * @author lgonzales
 */
class LiteratureEntryTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureEntryBuilder builder = new LiteratureEntryBuilder();

        LiteratureEntry literatureEntry = builder.build();
        ValidateJson.verifyJsonRoundTripParser(LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureEntry);
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureEntry literatureEntry = getCompleteLiteratureEntry();
        ValidateJson.verifyJsonRoundTripParser(LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureEntry);
        ValidateJson.verifyEmptyFields(literatureEntry);
    }

    private LiteratureEntry getCompleteLiteratureEntry() {
        return new LiteratureEntryBuilder()
                .doiId("doi Id")
                .pubmedId("pubmed Id")
                .addAuthor(new AuthorImpl("author name"))
                .addAuthoringGroup("authoring group")
                .firstPage("first Page")
                .journal("journal Name")
                .volume("volume")
                .lastPage("last Page")
                .literatureAbstract("literature Abstract")
                .publicationDate(new PublicationDateImpl("21-06-2019"))
                .title("title")
                .statistics(createCompleteLiteratureStatistics())
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