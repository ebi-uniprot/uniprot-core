package org.uniprot.core.uniprotkb.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinAltName;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ProteinAltNameBuilderTest {
    @Test
    void canAddSingleSortName() {
        ProteinAltName altName =
                new ProteinAltNameBuilder().shortNamesAdd(new NameBuilder().build()).build();
        assertTrue(altName.hasShortNames());
    }

    @Test
    void nullWillIgnoreWhileAddingSingleSortName() {
        ProteinAltName altName = new ProteinAltNameBuilder().shortNamesAdd(null).build();
        assertFalse(altName.hasShortNames());
    }

    @Test
    void canAddEcNumber() {
        ProteinAltName altName =
                new ProteinAltNameBuilder().ecNumbersAdd(new ECBuilder().build()).build();
        assertTrue(altName.hasEcNumbers());
    }

    @Test
    void nullWillIgnoreWhileAddingEcNumber() {
        ProteinAltName altName = new ProteinAltNameBuilder().ecNumbersAdd(null).build();
        assertFalse(altName.hasEcNumbers());
    }

    @Test
    void canCreateBuilderFromInstance() {
        ProteinAltName obj = new ProteinAltNameBuilder().build();
        ProteinAltNameBuilder builder = ProteinAltNameBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        ProteinAltName obj = new ProteinAltNameBuilder().build();
        ProteinAltName obj2 = new ProteinAltNameBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    public static ProteinAltName createObject(int listSize, boolean includeEvidences) {
        ProteinAltNameBuilder builder = new ProteinAltNameBuilder();
        Name fullName = NameBuilderTest.createObject(listSize, includeEvidences);
        List<Name> shortNames = NameBuilderTest.createObjects(listSize, includeEvidences);
        List<EC> ecNumbers = ECBuilderTest.createObjects(listSize, includeEvidences);
        builder.fullName(fullName);
        builder.shortNamesSet(shortNames);
        builder.ecNumbersSet(ecNumbers);
        return builder.build();
    }

    public static ProteinAltName createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static ProteinAltName createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<ProteinAltName> createObjects(int count) {
        return createObjects(count, false);
    }

    public static List<ProteinAltName> createObjects(int count, boolean includeEvidences) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count, includeEvidences))
                .collect(Collectors.toList());
    }
}
