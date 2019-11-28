package org.uniprot.core.citation.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.PublicationDate;

import static org.junit.jupiter.api.Assertions.*;

class PublicationDateBuilderTest {
  @Test
  void canCreateWithAnyStringVal() {
    String str = "any string";
    PublicationDate publicationDate = new PublicationDateBuilder(str).build();
    assertNotNull(publicationDate);
    assertEquals(str, publicationDate.getValue());
  }
}