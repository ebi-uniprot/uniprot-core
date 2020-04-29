package org.uniprot.core.uniprotkb.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.*;

public class ProteinDescriptionBuilderTest {

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
        ProteinDescription obj =
                new ProteinDescriptionBuilder().alternativeNamesAdd(altName).build();
        assertNotNull(obj.getAlternativeNames());
        assertFalse(obj.getAlternativeNames().isEmpty());
        assertTrue(obj.hasAlternativeNames());
    }

    @Test
    void nullAlternativeName_willBeIgnore() {
        ProteinDescription obj = new ProteinDescriptionBuilder().alternativeNamesAdd(null).build();
        assertNotNull(obj.getAlternativeNames());
        assertTrue(obj.getAlternativeNames().isEmpty());
        assertFalse(obj.hasAlternativeNames());
    }

    @Test
    void alternativeNames_willConvertUnModifiable_toModifiable() {
        ProteinDescription obj =
                new ProteinDescriptionBuilder()
                        .alternativeNamesSet(Collections.emptyList())
                        .alternativeNamesAdd(altName)
                        .build();
        assertNotNull(obj.getAlternativeNames());
        assertFalse(obj.getAlternativeNames().isEmpty());
        assertTrue(obj.hasAlternativeNames());
    }

    @Test
    void canAddListAlternativeNames() {
        ProteinDescription obj =
                new ProteinDescriptionBuilder()
                        .alternativeNamesSet(Collections.singletonList(altName))
                        .build();
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
        ProteinDescription obj = new ProteinDescriptionBuilder().cdAntigenNamesAdd(name).build();
        assertNotNull(obj.getCdAntigenNames());
        assertFalse(obj.getCdAntigenNames().isEmpty());
        assertTrue(obj.hasCdAntigenNames());
    }

    @Test
    void nullCdAntigenNames_willBeIgnore() {
        ProteinDescription obj = new ProteinDescriptionBuilder().cdAntigenNamesAdd(null).build();
        assertNotNull(obj.getCdAntigenNames());
        assertTrue(obj.getCdAntigenNames().isEmpty());
        assertFalse(obj.hasCdAntigenNames());
    }

    @Test
    void cdAntigenNames_willConvertUnModifiable_toModifiable() {
        ProteinDescription obj =
                new ProteinDescriptionBuilder()
                        .cdAntigenNamesSet(Collections.emptyList())
                        .cdAntigenNamesAdd(name)
                        .build();
        assertNotNull(obj.getCdAntigenNames());
        assertFalse(obj.getCdAntigenNames().isEmpty());
        assertTrue(obj.hasCdAntigenNames());
    }

    @Test
    void canAddListCdAntigenNames() {
        ProteinDescription obj = new ProteinDescriptionBuilder().cdAntigenNamesSet(names).build();
        assertNotNull(obj.getCdAntigenNames());
        assertFalse(obj.getCdAntigenNames().isEmpty());
        assertTrue(obj.hasCdAntigenNames());
    }

    @Test
    void canAddSingleInnNames() {
        ProteinDescription obj = new ProteinDescriptionBuilder().innNamesAdd(name).build();
        assertNotNull(obj.getInnNames());
        assertFalse(obj.getInnNames().isEmpty());
        assertTrue(obj.hasInnNames());
    }

    @Test
    void nullInnNames_willBeIgnore() {
        ProteinDescription obj = new ProteinDescriptionBuilder().innNamesAdd(null).build();
        assertNotNull(obj.getInnNames());
        assertTrue(obj.getInnNames().isEmpty());
        assertFalse(obj.hasInnNames());
    }

    @Test
    void innNames_willConvertUnModifiable_toModifiable() {
        ProteinDescription obj =
                new ProteinDescriptionBuilder()
                        .innNamesSet(Collections.emptyList())
                        .innNamesAdd(name)
                        .build();
        assertNotNull(obj.getInnNames());
        assertFalse(obj.getInnNames().isEmpty());
        assertTrue(obj.hasInnNames());
    }

    @Test
    void canAddListInnNames() {
        ProteinDescription obj = new ProteinDescriptionBuilder().innNamesSet(names).build();
        assertNotNull(obj.getInnNames());
        assertFalse(obj.getInnNames().isEmpty());
        assertTrue(obj.hasInnNames());
    }

    @Test
    void addingNullInFlagTypeWill_keepItNull() {
        ProteinDescription obj = new ProteinDescriptionBuilder().flag((FlagType) null).build();
        assertNull(obj.getFlag());
    }

    @Test
    void addingNullInFlagWill_keepItNull() {
        ProteinDescription obj = new ProteinDescriptionBuilder().flag((Flag) null).build();
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
        ProteinDescription obj = new ProteinDescriptionBuilder().submissionNamesAdd(sub).build();
        assertNotNull(obj.getSubmissionNames());
        assertFalse(obj.getSubmissionNames().isEmpty());
        assertTrue(obj.hasSubmissionNames());
    }

    @Test
    void submissionNames_willBeIgnore() {
        ProteinDescription obj = new ProteinDescriptionBuilder().submissionNamesAdd(null).build();
        assertNotNull(obj.getSubmissionNames());
        assertTrue(obj.getSubmissionNames().isEmpty());
        assertFalse(obj.hasSubmissionNames());
    }

    @Test
    void canAddSingleIncludes() {
        ProteinDescription obj = new ProteinDescriptionBuilder().includesAdd(pSection).build();
        assertNotNull(obj.getIncludes());
        assertFalse(obj.getIncludes().isEmpty());
        assertTrue(obj.hasIncludes());
    }

    @Test
    void nullAddIncludes_willBeIgnore() {
        ProteinDescription obj = new ProteinDescriptionBuilder().includesAdd(null).build();
        assertNotNull(obj.getIncludes());
        assertTrue(obj.getIncludes().isEmpty());
        assertFalse(obj.hasIncludes());
    }

    @Test
    void canAddSingleContains() {
        ProteinDescription obj = new ProteinDescriptionBuilder().containsAdd(pSection).build();
        assertNotNull(obj.getContains());
        assertFalse(obj.getContains().isEmpty());
        assertTrue(obj.hasContains());
    }

    @Test
    void nullAddContains_willBeIgnore() {
        ProteinDescription obj = new ProteinDescriptionBuilder().containsAdd(null).build();
        assertNotNull(obj.getContains());
        assertTrue(obj.getContains().isEmpty());
        assertFalse(obj.hasContains());
    }

    @Test
    void canCreateBuilderFromInstance() {
        ProteinDescription obj = new ProteinDescriptionBuilder().build();
        ProteinDescriptionBuilder builder = ProteinDescriptionBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        ProteinDescription obj = new ProteinDescriptionBuilder().build();
        ProteinDescription obj2 = new ProteinDescriptionBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    public static ProteinDescription createObject(int listSize) {
        ProteinDescriptionBuilder builder = new ProteinDescriptionBuilder();
        int rIndex = ThreadLocalRandom.current().nextInt(0, FlagType.values().length);
        FlagType flagType = FlagType.values()[rIndex];
        builder.flag(flagType);
        ProteinRecName recommendedName = ProteinRecNameBuilderTest.createObject(listSize);
        builder.recommendedName(recommendedName);
        List<ProteinSection> contains = ProteinSectionBuilderTest.createObjects(listSize);
        builder.containsSet(contains);
        List<ProteinSection> includes = ProteinSectionBuilderTest.createObjects(listSize);
        builder.includesSet(includes);
        List<ProteinSubName> submissionNames = ProteinSubNameBuilderTest.createObjects(listSize);
        builder.submissionNamesSet(submissionNames);
        List<Name> innNames = NameBuilderTest.createObjects(listSize);
        builder.innNamesSet(innNames);
        List<Name> cdAntigenNames = NameBuilderTest.createObjects(listSize);
        builder.cdAntigenNamesSet(cdAntigenNames);
        Name biotechName = NameBuilderTest.createObject(listSize);
        builder.biotechName(biotechName);
        Name allergenName = NameBuilderTest.createObject(listSize);
        builder.allergenName(allergenName);
        List<ProteinAltName> alternativeNames = ProteinAltNameBuilderTest.createObjects(listSize);
        builder.alternativeNamesSet(alternativeNames);
        return builder.build();
    }

    public static ProteinDescription createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }
}
