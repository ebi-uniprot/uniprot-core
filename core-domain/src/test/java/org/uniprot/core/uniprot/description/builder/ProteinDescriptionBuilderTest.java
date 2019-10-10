package org.uniprot.core.uniprot.description.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProteinDescriptionBuilderTest {

  private ProteinAltName altName = new ProteinAltNameBuilder().build();
  private Name name = new NameBuilder().value("name").build();
  private List<Name> names = Collections.singletonList(name);
  private ProteinSection pSection = new ProteinSectionBuilder().build();

  @Test
  void canSetRecommendedName_whichIsNotValid() {
    ProteinRecName recName = new ProteinRecNameBuilder().build();
    ProteinDescription obj = new ProteinDescriptionBuilder().recommendedName(recName).build();
    assertFalse(obj.hasRecommendedName());
    assertEquals(recName, obj.getRecommendedName());
  }

  @Test
  void canSetRecommendedName_valid() {
    ProteinRecName recName = new ProteinRecNameBuilder().fullName(name).build();
    ProteinDescription obj = new ProteinDescriptionBuilder().recommendedName(recName).build();
    assertTrue(obj.hasRecommendedName());
    assertEquals(recName, obj.getRecommendedName());
  }

  @Test
  void canAddSingleAlternativeName() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addAlternativeNames(altName).build();
    assertNotNull(obj.getAlternativeNames());
    assertFalse(obj.getAlternativeNames().isEmpty());
    assertTrue(obj.hasAlternativeNames());
  }

  @Test
  void nullAlternativeName_willBeIgnore() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addAlternativeNames(null).build();
    assertNotNull(obj.getAlternativeNames());
    assertTrue(obj.getAlternativeNames().isEmpty());
    assertFalse(obj.hasAlternativeNames());
  }

  @Test
  void alternativeNames_willConvertUnModifiable_toModifiable() {
    ProteinDescription obj = new ProteinDescriptionBuilder().alternativeNames(Collections.emptyList()).addAlternativeNames(altName).build();
    assertNotNull(obj.getAlternativeNames());
    assertFalse(obj.getAlternativeNames().isEmpty());
    assertTrue(obj.hasAlternativeNames());
  }

  @Test
  void canAddListAlternativeNames() {
    ProteinDescription obj = new ProteinDescriptionBuilder().alternativeNames(Collections.singletonList(altName)).build();
    assertNotNull(obj.getAlternativeNames());
    assertFalse(obj.getAlternativeNames().isEmpty());
    assertTrue(obj.hasAlternativeNames());
  }

  @Test
  void canSetAllergenName() {
    ProteinDescription obj = new ProteinDescriptionBuilder().allergenName(name).build();
    assertTrue(obj.hasAllergenName());
    assertEquals(name, obj.getAllergenName());
  }

  @Test
  void canSetBioTechName() {
    ProteinDescription obj = new ProteinDescriptionBuilder().biotechName(name).build();
    assertTrue(obj.hasBiotechName());
    assertEquals(name, obj.getBiotechName());
  }

  @Test
  void canAddSingleCdAntigenNames() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addCdAntigenNames(name).build();
    assertNotNull(obj.getCdAntigenNames());
    assertFalse(obj.getCdAntigenNames().isEmpty());
    assertTrue(obj.hasCdAntigenNames());
  }

  @Test
  void nullCdAntigenNames_willBeIgnore() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addCdAntigenNames(null).build();
    assertNotNull(obj.getCdAntigenNames());
    assertTrue(obj.getCdAntigenNames().isEmpty());
    assertFalse(obj.hasCdAntigenNames());
  }

  @Test
  void cdAntigenNames_willConvertUnModifiable_toModifiable() {
    ProteinDescription obj = new ProteinDescriptionBuilder().cdAntigenNames(Collections.emptyList()).addCdAntigenNames(name).build();
    assertNotNull(obj.getCdAntigenNames());
    assertFalse(obj.getCdAntigenNames().isEmpty());
    assertTrue(obj.hasCdAntigenNames());
  }

  @Test
  void canAddListCdAntigenNames() {
    ProteinDescription obj = new ProteinDescriptionBuilder().cdAntigenNames(names).build();
    assertNotNull(obj.getCdAntigenNames());
    assertFalse(obj.getCdAntigenNames().isEmpty());
    assertTrue(obj.hasCdAntigenNames());
  }

  @Test
  void canAddSingleInnNames() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addInnNames(name).build();
    assertNotNull(obj.getInnNames());
    assertFalse(obj.getInnNames().isEmpty());
    assertTrue(obj.hasInnNames());
  }

  @Test
  void nullInnNames_willBeIgnore() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addInnNames(null).build();
    assertNotNull(obj.getInnNames());
    assertTrue(obj.getInnNames().isEmpty());
    assertFalse(obj.hasInnNames());
  }

  @Test
  void innNames_willConvertUnModifiable_toModifiable() {
    ProteinDescription obj = new ProteinDescriptionBuilder().innNames(Collections.emptyList()).addInnNames(name).build();
    assertNotNull(obj.getInnNames());
    assertFalse(obj.getInnNames().isEmpty());
    assertTrue(obj.hasInnNames());
  }

  @Test
  void canAddListInnNames() {
    ProteinDescription obj = new ProteinDescriptionBuilder().innNames(names).build();
    assertNotNull(obj.getInnNames());
    assertFalse(obj.getInnNames().isEmpty());
    assertTrue(obj.hasInnNames());
  }

  @Test
  void addingNullInFlagWill_keepItNull() {
    ProteinDescription obj = new ProteinDescriptionBuilder().flag(null).build();
    assertNull(obj.getFlag());
  }

  @Test
  void canSetFlag() {
    ProteinDescription obj = new ProteinDescriptionBuilder().flag(FlagType.FRAGMENT).build();
    assertTrue(obj.hasFlag());
    assertEquals(FlagType.FRAGMENT, obj.getFlag().getType());
  }

  @Test
  void canAddSingleSubmissionNames() {
    ProteinSubName sub = new ProteinSubNameBuilder().build();
    ProteinDescription obj = new ProteinDescriptionBuilder().addSubmissionNames(sub).build();
    assertNotNull(obj.getSubmissionNames());
    assertFalse(obj.getSubmissionNames().isEmpty());
    assertTrue(obj.hasSubmissionNames());
  }

  @Test
  void submissionNames_willBeIgnore() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addSubmissionNames(null).build();
    assertNotNull(obj.getSubmissionNames());
    assertTrue(obj.getSubmissionNames().isEmpty());
    assertFalse(obj.hasSubmissionNames());
  }

  @Test
  void canAddSingleIncludes() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addIncludes(pSection).build();
    assertNotNull(obj.getIncludes());
    assertFalse(obj.getIncludes().isEmpty());
    assertTrue(obj.hasIncludes());
  }

  @Test
  void nullAddIncludes_willBeIgnore() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addIncludes(null).build();
    assertNotNull(obj.getIncludes());
    assertTrue(obj.getIncludes().isEmpty());
    assertFalse(obj.hasIncludes());
  }

  @Test
  void canAddSingleContains() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addContains(pSection).build();
    assertNotNull(obj.getContains());
    assertFalse(obj.getContains().isEmpty());
    assertTrue(obj.hasContains());
  }

  @Test
  void nullAddContains_willBeIgnore() {
    ProteinDescription obj = new ProteinDescriptionBuilder().addContains(null).build();
    assertNotNull(obj.getContains());
    assertTrue(obj.getContains().isEmpty());
    assertFalse(obj.hasContains());
  }

  @Test
  void canCreateBuilderFromInstance() {
    ProteinDescription obj = new ProteinDescriptionBuilder().build();
    ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder().from(obj);
    assertNotNull(builder);
  }

  @Test
  void defaultBuild_objsAreEqual() {
    ProteinDescription obj = new ProteinDescriptionBuilder().build();
    ProteinDescription obj2 = new ProteinDescriptionBuilder().build();
    assertTrue(obj.equals(obj2) && obj2.equals(obj));
    assertEquals(obj.hashCode(), obj2.hashCode());
  }
}
