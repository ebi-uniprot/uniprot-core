package org.uniprot.core.citation.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.citation.CitationXrefType;
import org.uniprot.core.citation.Patent;
import org.uniprot.core.citation.builder.PatentBuilder;

import java.util.Collections;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class PatentImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    Patent obj = new PatentImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    DBCrossReference<CitationXrefType> XREF1 =
      new DBCrossReferenceBuilder<CitationXrefType>()
        .databaseType(CitationXrefType.PUBMED)
        .id("id1")
        .build();
    Patent impl = new PatentImpl(asList("J1", "j2"), Collections.singletonList(new AuthorImpl("auth")),
      Collections.singletonList(XREF1), "ptitle", new PublicationDateImpl("date"), "pname");
    Patent obj = new PatentBuilder().from(impl).build();

    assertTrue(impl.hasPatentNumber());

    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalNullObject() {
    Patent impl = new PatentImpl(null, null, null, null, null, null);
    Patent obj = new PatentBuilder().from(impl).build();

    assertFalse(impl.hasPatentNumber());

    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}