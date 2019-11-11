package org.uniprot.core.uniprot.comment.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;

import static org.junit.jupiter.api.Assertions.*;

class MassSpectrometryRangeBuilderTest {
  @Test
  void canCreateBuilderFromInstance() {
    MassSpectrometryRange obj = new MassSpectrometryRangeBuilder().build();
    MassSpectrometryRangeBuilder builder = new MassSpectrometryRangeBuilder().from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    MassSpectrometryRange obj = new MassSpectrometryRangeBuilder().build();
    MassSpectrometryRange obj2 = new MassSpectrometryRangeBuilder().build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}