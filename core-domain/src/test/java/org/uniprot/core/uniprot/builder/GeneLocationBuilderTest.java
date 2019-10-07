package org.uniprot.core.uniprot.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.GeneLocation;

import static org.junit.jupiter.api.Assertions.*;

class GeneLocationBuilderTest {

  @Test
  void canCreateBuilderFromInstance() {
    GeneLocation obj = new GeneLocationBuilder().build();
    GeneLocationBuilder builder = new GeneLocationBuilder().from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    GeneLocation obj = new GeneLocationBuilder().build();
    GeneLocation obj2 = new GeneLocationBuilder().build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}