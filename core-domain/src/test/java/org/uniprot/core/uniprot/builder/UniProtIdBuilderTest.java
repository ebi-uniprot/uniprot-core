package org.uniprot.core.uniprot.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtId;

import static org.junit.jupiter.api.Assertions.*;

class UniProtIdBuilderTest {

  @Test
  void canCreateBuilderFromInstance() {
    UniProtId obj = new UniProtIdBuilder("val").build();
    UniProtIdBuilder builder = new UniProtIdBuilder("val").from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    UniProtId obj = new UniProtIdBuilder("val").build();
    UniProtId obj2 = new UniProtIdBuilder("val").build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}