package org.uniprot.core.uniprot.description.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinSubName;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ProteinSubNameBuilderTest {

 private EC ecNumber = new ECBuilder().build();

  @Test
  void canSetFullName() {
    Name name = new NameBuilder().build();
    ProteinSubName proteinSubName = new ProteinSubNameBuilder().fullName(name).build();
    assertTrue(proteinSubName.hasFullName());
    assertEquals(name, proteinSubName.getFullName());
  }

  @Test
  void ecNumber_willConvertUnModifiable_toModifiable() {
    ProteinSubName obj = new ProteinSubNameBuilder().ecNumbers(Collections.emptyList()).addEcNumber(ecNumber).build();
    assertNotNull(obj.getEcNumbers());
    assertFalse(obj.getEcNumbers().isEmpty());
    assertTrue(obj.hasEcNumbers());
  }

  @Test
  void canAddListEcNumber() {
    ProteinSubName obj = new ProteinSubNameBuilder().ecNumbers(Collections.singletonList(ecNumber)).build();
    assertNotNull(obj.getEcNumbers());
    assertFalse(obj.getEcNumbers().isEmpty());
    assertTrue(obj.hasEcNumbers());
  }

  @Test
  void canAddSingleEcNumber() {
    ProteinSubName obj = new ProteinSubNameBuilder().addEcNumber(new ECBuilder().build()).build();
    assertNotNull(obj.getEcNumbers());
    assertFalse(obj.getEcNumbers().isEmpty());
    assertTrue(obj.hasEcNumbers());
  }

  @Test
  void nullEcNumber_willBeIgnore() {
    ProteinSubName obj = new ProteinSubNameBuilder().addEcNumber(null).build();
    assertNotNull(obj.getEcNumbers());
    assertTrue(obj.getEcNumbers().isEmpty());
    assertFalse(obj.hasEcNumbers());
  }

  @Test
  void canCreateBuilderFromInstance() {
    ProteinSubName obj = new ProteinSubNameBuilder().build();
    ProteinSubNameBuilder builder = new ProteinSubNameBuilder().from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    ProteinSubName obj = new ProteinSubNameBuilder().build();
    ProteinSubName obj2 = new ProteinSubNameBuilder().build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}