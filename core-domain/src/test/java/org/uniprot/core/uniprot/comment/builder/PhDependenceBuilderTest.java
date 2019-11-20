package org.uniprot.core.uniprot.comment.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.PhDependence;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.evidenceValues;

class PhDependenceBuilderTest {
  @Test
  void canCreateBuilderFromInstance() {
    PhDependence obj = new PhDependenceBuilder(evidenceValues()).build();
    PhDependenceBuilder builder = new PhDependenceBuilder(Collections.emptyList()).from(obj);
    assertNotNull(builder);

    assertIterableEquals(evidenceValues(), builder.build().getTexts());
  }

  @Test
  void defaultBuild_objsAreEqual() {
    PhDependence obj = new PhDependenceBuilder(evidenceValues()).build();
    PhDependence obj2 = new PhDependenceBuilder(evidenceValues()).build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}