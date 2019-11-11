package org.uniprot.core.uniprot.comment.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.MaximumVelocity;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidence;
import static org.uniprot.core.ObjectsForTests.createEvidences;

class MaximumVelocityBuilderTest {
  @Test
  void canCreateBuilderFromInstance() {
    MaximumVelocity obj = new MaximumVelocityBuilder().build();
    MaximumVelocityBuilder builder = new MaximumVelocityBuilder().from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    MaximumVelocity obj = new MaximumVelocityBuilder().build();
    MaximumVelocity obj2 = new MaximumVelocityBuilder().build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }

  @Test
  void canAddSingleEvidence() {
    MaximumVelocity obj = new MaximumVelocityBuilder().addEvidence(createEvidence()).build();
    assertNotNull(obj.getEvidences());
    assertFalse(obj.getEvidences().isEmpty());
    assertTrue(obj.hasEvidences());
  }

  @Test
  void nullEvidence_willBeIgnore() {
    MaximumVelocity obj = new MaximumVelocityBuilder().addEvidence(null).build();
    assertNotNull(obj.getEvidences());
    assertTrue(obj.getEvidences().isEmpty());
    assertFalse(obj.hasEvidences());
  }

  @Test
  void evidences_willConvertUnModifiable_toModifiable() {
    MaximumVelocity obj = new MaximumVelocityBuilder().evidences(Collections.emptyList()).addEvidence(createEvidence()).build();
    assertNotNull(obj.getEvidences());
    assertFalse(obj.getEvidences().isEmpty());
    assertTrue(obj.hasEvidences());
  }

  @Test
  void canAddListEvidences() {
    MaximumVelocity obj = new MaximumVelocityBuilder().evidences(createEvidences()).build();
    assertNotNull(obj.getEvidences());
    assertFalse(obj.getEvidences().isEmpty());
    assertTrue(obj.hasEvidences());
  }
}