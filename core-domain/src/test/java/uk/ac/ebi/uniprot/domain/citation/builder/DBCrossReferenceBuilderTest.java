package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.builder.DBCrossReferenceBuilder;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
class DBCrossReferenceBuilderTest {
    @Test
    void canCreateInstance() {
        DBCrossReference<CitationXrefType> xref = new DBCrossReferenceBuilder<CitationXrefType>()
                .databaseType(CitationXrefType.PUBMED)
                .id("id1")
                .build();

        DBCrossReference<CitationXrefType> copiedInstance = new DBCrossReferenceBuilder<CitationXrefType>()
                .from(xref)
                .build();

        assertThat(xref, is(copiedInstance));
    }
}