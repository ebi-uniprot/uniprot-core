package org.uniprot.core.cv.ec.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.ec.ECEntry;

import static org.junit.jupiter.api.Assertions.*;

class ECEntryBuilderTest {

  @Test
  void canSetId() {
    String id = "id";
    ECEntry ecEntry = new ECEntryBuilder()
      .id(id)
      .build();

    assertEquals(id, ecEntry.getId());
    assertNull(ecEntry.getLabel());
  }

  @Test
  void canSetLabel() {
    String label = "label";
    ECEntry ec = new ECEntryBuilder()
      .label(label)
      .build();

    assertEquals(label, ec.getLabel());
    assertNull(ec.getId());
  }

  @Test
  void canCreateFullObjectFromBuilder() {
    String id = "id";
    String label = "label";
    ECEntry ecEntry = new ECEntryBuilder()
      .id(id)
      .label(label)
      .build();

    assertEquals(id, ecEntry.getId());
    assertEquals(label, ecEntry.getLabel());
  }

  @Test
  void canCreateBuilderFromInstance() {
    ECEntry obj = new ECEntryBuilder().build();
    ECEntryBuilder builder = ECEntryBuilder.from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    ECEntry obj = new ECEntryBuilder().build();
    ECEntry obj2 = new ECEntryBuilder().build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}