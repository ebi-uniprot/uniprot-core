package org.uniprot.core.uniprot.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.EntryInactiveReason;

import static org.junit.jupiter.api.Assertions.*;

class EntryInactiveReasonBuilderTest {

  @Test
  void canBuildWithMergeDemergeTo() {
    EntryInactiveReason reason = new EntryInactiveReasonBuilder().addMergeDemergeTo("merge").build();
    assertNotNull(reason.getMergeDemergeTo());
    assertFalse(reason.getMergeDemergeTo().isEmpty());
    assertEquals("merge", reason.getMergeDemergeTo().get(0));
  }

  @Test
  void canCreateBuilderFromInstance() {
    EntryInactiveReason reason = new EntryInactiveReasonBuilder().build();
    EntryInactiveReasonBuilder builder = new EntryInactiveReasonBuilder().from(reason);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    EntryInactiveReason reason = new EntryInactiveReasonBuilder().build();
    EntryInactiveReason reason2 = new EntryInactiveReasonBuilder().build();
    assertTrue(reason.equals(reason2) && reason2.equals(reason));
    assertEquals(reason.hashCode(), reason2.hashCode());
  }
}