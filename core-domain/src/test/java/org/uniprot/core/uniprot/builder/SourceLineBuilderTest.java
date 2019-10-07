package org.uniprot.core.uniprot.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.SourceLine;

import static org.junit.jupiter.api.Assertions.*;

class SourceLineBuilderTest {
  @Test
  void canCreateBuilderFromInstance() {
    SourceLine obj = new SourceLineBuilder("val").build();
    SourceLineBuilder builder = new SourceLineBuilder("val").from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    SourceLine obj = new SourceLineBuilder("val").build();
    SourceLine obj2 = new SourceLineBuilder("val").build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}