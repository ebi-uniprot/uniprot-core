package org.uniprot.core.uniprot.description.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinAltName;
import org.uniprot.core.uniprot.description.ProteinSection;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProteinSectionBuilderTest {
  private ProteinAltName altName = new ProteinAltNameBuilder().build();
  private Name name = new NameBuilder().value("name").build();
  private List<Name> names = Collections.singletonList(name);

  @Test
  void canAddSingleAlternativeName() {
    ProteinSection obj = new ProteinSectionBuilder().addAlternativeNames(altName).build();
    assertNotNull(obj.getAlternativeNames());
    assertFalse(obj.getAlternativeNames().isEmpty());
    assertTrue(obj.hasAlternativeNames());
  }

  @Test
  void nullAlternativeName_willBeIgnore() {
    ProteinSection obj = new ProteinSectionBuilder().addAlternativeNames(null).build();
    assertNotNull(obj.getAlternativeNames());
    assertTrue(obj.getAlternativeNames().isEmpty());
    assertFalse(obj.hasAlternativeNames());
  }

  @Test
  void alternativeNames_willConvertUnModifiable_toModifiable() {
    ProteinSection obj = new ProteinSectionBuilder().alternativeNames(Collections.emptyList()).addAlternativeNames(altName).build();
    assertNotNull(obj.getAlternativeNames());
    assertFalse(obj.getAlternativeNames().isEmpty());
    assertTrue(obj.hasAlternativeNames());
  }

  @Test
  void canAddListAlternativeNames() {
    ProteinSection obj = new ProteinSectionBuilder().alternativeNames(Collections.singletonList(altName)).build();
    assertNotNull(obj.getAlternativeNames());
    assertFalse(obj.getAlternativeNames().isEmpty());
    assertTrue(obj.hasAlternativeNames());
  }

  @Test
  void canSetAllergenName() {
    ProteinSection obj = new ProteinSectionBuilder().allergenName(name).build();
    assertTrue(obj.hasAllergenName());
    assertEquals(name, obj.getAllergenName());
  }

  @Test
  void canSetBioTechName() {
    ProteinSection obj = new ProteinSectionBuilder().biotechName(name).build();
    assertTrue(obj.hasBiotechName());
    assertEquals(name, obj.getBiotechName());
  }

  @Test
  void canAddSingleCdAntigenNames() {
    ProteinSection obj = new ProteinSectionBuilder().addCdAntigenNames(name).build();
    assertNotNull(obj.getCdAntigenNames());
    assertFalse(obj.getCdAntigenNames().isEmpty());
    assertTrue(obj.hasCdAntigenNames());
  }

  @Test
  void nullCdAntigenNames_willBeIgnore() {
    ProteinSection obj = new ProteinSectionBuilder().addCdAntigenNames(null).build();
    assertNotNull(obj.getCdAntigenNames());
    assertTrue(obj.getCdAntigenNames().isEmpty());
    assertFalse(obj.hasCdAntigenNames());
  }

  @Test
  void cdAntigenNames_willConvertUnModifiable_toModifiable() {
    ProteinSection obj = new ProteinSectionBuilder().cdAntigenNames(Collections.emptyList()).addCdAntigenNames(name).build();
    assertNotNull(obj.getCdAntigenNames());
    assertFalse(obj.getCdAntigenNames().isEmpty());
    assertTrue(obj.hasCdAntigenNames());
  }

  @Test
  void canAddListCdAntigenNames() {
    ProteinSection obj = new ProteinSectionBuilder().cdAntigenNames(names).build();
    assertNotNull(obj.getCdAntigenNames());
    assertFalse(obj.getCdAntigenNames().isEmpty());
    assertTrue(obj.hasCdAntigenNames());
  }

  @Test
  void canAddSingleInnNames() {
    ProteinSection obj = new ProteinSectionBuilder().addInnNames(name).build();
    assertNotNull(obj.getInnNames());
    assertFalse(obj.getInnNames().isEmpty());
    assertTrue(obj.hasInnNames());
  }

  @Test
  void nullInnNames_willBeIgnore() {
    ProteinSection obj = new ProteinSectionBuilder().addInnNames(null).build();
    assertNotNull(obj.getInnNames());
    assertTrue(obj.getInnNames().isEmpty());
    assertFalse(obj.hasInnNames());
  }

  @Test
  void innNames_willConvertUnModifiable_toModifiable() {
    ProteinSection obj = new ProteinSectionBuilder().innNames(Collections.emptyList()).addInnNames(name).build();
    assertNotNull(obj.getInnNames());
    assertFalse(obj.getInnNames().isEmpty());
    assertTrue(obj.hasInnNames());
  }

  @Test
  void canAddListInnNames() {
    ProteinSection obj = new ProteinSectionBuilder().innNames(names).build();
    assertNotNull(obj.getInnNames());
    assertFalse(obj.getInnNames().isEmpty());
    assertTrue(obj.hasInnNames());
  }

  @Test
  void canCreateBuilderFromInstance() {
    ProteinSection obj = new ProteinSectionBuilder().build();
    ProteinSectionBuilder builder = new ProteinSectionBuilder().from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    ProteinSection obj = new ProteinSectionBuilder().build();
    ProteinSection obj2 = new ProteinSectionBuilder().build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}
