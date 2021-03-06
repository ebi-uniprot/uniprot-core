package org.uniprot.core.citation.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.impl.CrossReferenceBuilder;

class SubmissionImplTest {
    @Test
    void needDefaultConstructorForJsonDeserialization() {
        Submission obj = new SubmissionImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CrossReference<CitationDatabase> XREF1 =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.PUBMED)
                        .id("id1")
                        .build();

        Submission impl =
                new SubmissionImpl(
                        asList("T1", "T2"),
                        Collections.singletonList(new AuthorImpl("auth")),
                        Collections.singletonList(XREF1),
                        "title",
                        new PublicationDateImpl("date"),
                        SubmissionDatabase.EMBL_GENBANK_DDBJ);
        Submission obj = SubmissionBuilder.from(impl).build();

        assertTrue(impl.hasSubmissionDatabase());

        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
