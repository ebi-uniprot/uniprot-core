package org.uniprot.core.uniprotkb.description.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.uniprotkb.description.impl.ProteinNameImplTest.createProteinNames;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinName;
import org.uniprot.core.uniprotkb.description.ProteinSection;

class ProteinSectionBuilderTest {
    private ProteinName altName = new ProteinNameBuilder().build();
    private Name name = new NameBuilder().value("name").build();
    private List<Name> names = Collections.singletonList(name);

    @Test
    void canAddSingleAlternativeName() {
        ProteinSection obj = new ProteinSectionBuilder().alternativeNamesAdd(altName).build();
        assertNotNull(obj.getAlternativeNames());
        assertFalse(obj.getAlternativeNames().isEmpty());
        assertTrue(obj.hasAlternativeNames());
    }

    @Test
    void nullAlternativeName_willBeIgnore() {
        ProteinSection obj = new ProteinSectionBuilder().alternativeNamesAdd(null).build();
        assertNotNull(obj.getAlternativeNames());
        assertTrue(obj.getAlternativeNames().isEmpty());
        assertFalse(obj.hasAlternativeNames());
    }

    @Test
    void alternativeNames_willConvertUnModifiable_toModifiable() {
        ProteinSection obj =
                new ProteinSectionBuilder()
                        .alternativeNamesSet(Collections.emptyList())
                        .alternativeNamesAdd(altName)
                        .build();
        assertNotNull(obj.getAlternativeNames());
        assertFalse(obj.getAlternativeNames().isEmpty());
        assertTrue(obj.hasAlternativeNames());
    }

    @Test
    void canAddListAlternativeNames() {
        ProteinSection obj =
                new ProteinSectionBuilder()
                        .alternativeNamesSet(Collections.singletonList(altName))
                        .build();
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
        ProteinSection obj = new ProteinSectionBuilder().cdAntigenNamesAdd(name).build();
        assertNotNull(obj.getCdAntigenNames());
        assertFalse(obj.getCdAntigenNames().isEmpty());
        assertTrue(obj.hasCdAntigenNames());
    }

    @Test
    void nullCdAntigenNames_willBeIgnore() {
        ProteinSection obj = new ProteinSectionBuilder().cdAntigenNamesAdd(null).build();
        assertNotNull(obj.getCdAntigenNames());
        assertTrue(obj.getCdAntigenNames().isEmpty());
        assertFalse(obj.hasCdAntigenNames());
    }

    @Test
    void cdAntigenNames_willConvertUnModifiable_toModifiable() {
        ProteinSection obj =
                new ProteinSectionBuilder()
                        .cdAntigenNamesSet(Collections.emptyList())
                        .cdAntigenNamesAdd(name)
                        .build();
        assertNotNull(obj.getCdAntigenNames());
        assertFalse(obj.getCdAntigenNames().isEmpty());
        assertTrue(obj.hasCdAntigenNames());
    }

    @Test
    void canAddListCdAntigenNames() {
        ProteinSection obj = new ProteinSectionBuilder().cdAntigenNamesSet(names).build();
        assertNotNull(obj.getCdAntigenNames());
        assertFalse(obj.getCdAntigenNames().isEmpty());
        assertTrue(obj.hasCdAntigenNames());
    }

    @Test
    void canAddSingleInnNames() {
        ProteinSection obj = new ProteinSectionBuilder().innNamesAdd(name).build();
        assertNotNull(obj.getInnNames());
        assertFalse(obj.getInnNames().isEmpty());
        assertTrue(obj.hasInnNames());
    }

    @Test
    void nullInnNames_willBeIgnore() {
        ProteinSection obj = new ProteinSectionBuilder().innNamesAdd(null).build();
        assertNotNull(obj.getInnNames());
        assertTrue(obj.getInnNames().isEmpty());
        assertFalse(obj.hasInnNames());
    }

    @Test
    void innNames_willConvertUnModifiable_toModifiable() {
        ProteinSection obj =
                new ProteinSectionBuilder()
                        .innNamesSet(Collections.emptyList())
                        .innNamesAdd(name)
                        .build();
        assertNotNull(obj.getInnNames());
        assertFalse(obj.getInnNames().isEmpty());
        assertTrue(obj.hasInnNames());
    }

    @Test
    void canAddListInnNames() {
        ProteinSection obj = new ProteinSectionBuilder().innNamesSet(names).build();
        assertNotNull(obj.getInnNames());
        assertFalse(obj.getInnNames().isEmpty());
        assertTrue(obj.hasInnNames());
    }

    @Test
    void canCreateBuilderFromInstance() {
        ProteinSection obj = new ProteinSectionBuilder().build();
        ProteinSectionBuilder builder = ProteinSectionBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        ProteinSection obj = new ProteinSectionBuilder().build();
        ProteinSection obj2 = new ProteinSectionBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    public static ProteinSection createObject(int listSize, boolean includeEvidences) {
        ProteinSectionBuilder builder = new ProteinSectionBuilder();
        ProteinName recommendedName =
                ProteinNameImplTest.createProteinName(listSize, includeEvidences);
        builder.recommendedName(recommendedName);
        List<ProteinName> alternativeNames = createProteinNames(listSize, includeEvidences);
        builder.alternativeNamesSet(alternativeNames);
        Name allergenName = NameBuilderTest.createObject(listSize, includeEvidences);
        builder.allergenName(allergenName);
        Name biotechName = NameBuilderTest.createObject(listSize, includeEvidences);
        builder.biotechName(biotechName);
        List<Name> cdAntigenNames = NameBuilderTest.createObjects(listSize, includeEvidences);
        builder.cdAntigenNamesSet(cdAntigenNames);
        List<Name> innNames = NameBuilderTest.createObjects(listSize, includeEvidences);
        builder.innNamesSet(innNames);
        return builder.build();
    }

    public static ProteinSection createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static ProteinSection createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<ProteinSection> createObjects(int count) {
        return createObjects(count, false);
    }

    public static List<ProteinSection> createObjects(int count, boolean includeEvidences) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count, includeEvidences))
                .collect(Collectors.toList());
    }
}
