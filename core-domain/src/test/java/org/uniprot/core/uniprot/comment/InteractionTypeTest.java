package org.uniprot.core.uniprot.comment;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InteractionTypeTest {

  @Test
  void name_displayName_areSame() {
    assertSame(
      InteractionType.UNKNOWN.name(),
      InteractionType.UNKNOWN.toDisplayName());
  }

  @Test
  void selfLink() {
    assertEquals("https://www.ebi.ac.uk/intact/search/do/search?self=", InteractionType.SELF.getLink());
  }

  @Test
  void binaryLink() {
    assertEquals("https://www.ebi.ac.uk/intact/search/do/search?binary=", InteractionType.BINARY.getLink());
  }

  @Test
  void xenoLink() {
    assertEquals("https://www.ebi.ac.uk/intact/search/do/search?xeno=", InteractionType.XENO.getLink());
  }

  @Test
  void unknowEmpty() {
    assertTrue(InteractionType.UNKNOWN.getLink().isEmpty());
  }
}