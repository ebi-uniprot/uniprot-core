package org.uniprot.core.citation.builder;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationXrefType;

/**
 * Created 22/01/19
 *
 * @author Edd
 */
class DBCrossReferenceBuilderTest {
    @Test
    void canCreateInstance() {
        DBCrossReference<CitationXrefType> xref =
                new DBCrossReferenceBuilder<CitationXrefType>()
                        .databaseType(CitationXrefType.PUBMED)
                        .id("id1")
                        .build();

        DBCrossReference<CitationXrefType> copiedInstance =
                new DBCrossReferenceBuilder<CitationXrefType>().from(xref).build();

        assertThat(xref, is(copiedInstance));
    }
}
