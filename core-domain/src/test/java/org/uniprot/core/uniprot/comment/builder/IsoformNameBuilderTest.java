package org.uniprot.core.uniprot.comment.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.IsoformName;

import static org.junit.jupiter.api.Assertions.*;

class IsoformNameBuilderTest {
  @Test
  void canCreateBuilderFromInstance() {
    IsoformName obj = new IsoformNameBuilder().build();
    IsoformNameBuilder builder = new IsoformNameBuilder().from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    IsoformName obj = new IsoformNameBuilder().build();
    IsoformName obj2 = new IsoformNameBuilder().build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}