package org.uniprot.core.citation.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.builder.SubmissionBuilder;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class SubmissionImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    Submission obj = new SubmissionImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    DBCrossReference<CitationXrefType> XREF1 =
      new DBCrossReferenceBuilder<CitationXrefType>()
        .databaseType(CitationXrefType.PUBMED)
        .id("id1")
        .build();

    Submission impl = new SubmissionImpl(asList("T1", "T2"), Collections.singletonList(new AuthorImpl("auth")), Collections.singletonList(XREF1),
      "title", new PublicationDateImpl("date"), SubmissionDatabase.EMBL_GENBANK_DDBJ);
    Submission obj = new SubmissionBuilder().from(impl).build();

    assertTrue(impl.hasSubmissionDatabase());

    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}