package org.uniprot.core.uniprot.description.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.ProteinDescription;
import org.uniprot.core.uniprot.description.builder.ProteinDescriptionBuilder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProteinDescriptionImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    ProteinDescription obj = new ProteinDescriptionImpl();
    assertNotNull(obj);
    assertTrue(obj.getCdAntigenNames().isEmpty());
    assertTrue(obj.getSubmissionNames().isEmpty());
    assertTrue(obj.getIncludes().isEmpty());
    assertTrue(obj.getCdAntigenNames().isEmpty());
    assertTrue(obj.getAlternativeNames().isEmpty());
    assertTrue(obj.getContains().isEmpty());
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    ProteinDescriptionImpl impl = new ProteinDescriptionImpl(new ProteinRecNameImpl(), Collections.emptyList(), new NameImpl(), new NameImpl(),
      Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), new FlagImpl(), Collections.emptyList(),
      Collections.emptyList());
    ProteinDescription obj = new ProteinDescriptionBuilder().from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}