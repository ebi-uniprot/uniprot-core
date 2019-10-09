package org.uniprot.core.uniprot.description.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.ProteinAltName;
import org.uniprot.core.uniprot.description.builder.NameBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinAltNameBuilder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProteinAltNameImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    ProteinAltName obj = new ProteinAltNameImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    ProteinAltName impl = new ProteinAltNameImpl(new NameBuilder().build(), Collections.emptyList(), Collections.emptyList());
    ProteinAltName obj = new ProteinAltNameBuilder().from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }

  @Test
  void canTestNameValidity() {
    ProteinAltName altName = new ProteinAltNameBuilder().fullName(new NameBuilder().value("name").build()).build();
    assertTrue(altName.hasFullName());
    assertTrue(altName.isValid());
  }
}