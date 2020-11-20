package org.uniprot.core.uniprotkb.description.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinName;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

class ProteinNameImplTest {
    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        List<Name> shortNames = shortNames();
        List<EC> ecNumbers = eCNumbers();
        ProteinName recName =
                new ProteinNameBuilder()
                        .fullName(fullName)
                        .shortNamesSet(shortNames)
                        .ecNumbersSet(ecNumbers)
                        .build();
        assertEquals(fullName, recName.getFullName());
        assertEquals(shortNames, recName.getShortNames());
        assertEquals(ecNumbers, recName.getEcNumbers());
        assertTrue(recName.isValid());
    }

    @Test
    void testNotFull() {
        List<Name> shortNames = shortNames();
        List<EC> ecNumbers = eCNumbers();
        ProteinName recName =
                new ProteinNameBuilder()
                        .fullName(null)
                        .shortNamesSet(shortNames)
                        .ecNumbersSet(ecNumbers)
                        .build();
        assertFalse(recName.isValid());
    }

    @Test
    void testOnlyFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        ProteinName recName =
                new ProteinNameBuilder()
                        .fullName(fullName)
                        .shortNamesSet(null)
                        .ecNumbersSet(null)
                        .build();
        assertEquals(fullName, recName.getFullName());
        assertEquals(0, recName.getShortNames().size());
        assertEquals(0, recName.getEcNumbers().size());
        assertTrue(recName.isValid());
    }

    @Test
    void testOnlyFullAndEC() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        List<EC> ecNumbers = eCNumbers();
        ProteinName recName =
                new ProteinNameBuilder()
                        .fullName(fullName)
                        .shortNamesSet(null)
                        .ecNumbersSet(ecNumbers)
                        .build();
        assertEquals(fullName, recName.getFullName());
        assertEquals(0, recName.getShortNames().size());
        assertEquals(ecNumbers, recName.getEcNumbers());
        assertTrue(recName.isValid());
    }

    @Test
    void testOnlyFullAndShort() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        List<Name> shortNames = shortNames();
        ProteinName recName =
                new ProteinNameBuilder()
                        .fullName(fullName)
                        .shortNamesSet(shortNames)
                        .ecNumbersSet(null)
                        .build();
        assertEquals(fullName, recName.getFullName());
        assertEquals(shortNames, recName.getShortNames());
        assertEquals(0, recName.getEcNumbers().size());
        assertTrue(recName.isValid());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ProteinName obj = new ProteinNameImpl();
        assertNotNull(obj);
        assertFalse(obj.hasFullName());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ProteinName impl =
                new ProteinNameImpl(
                        new NameImpl(), Collections.emptyList(), Collections.emptyList());
        ProteinName obj = ProteinNameBuilder.from(impl).build();

        assertTrue(impl.hasFullName());
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    public static ProteinName createProteinRecName(int listSize, boolean includeEvidences) {
        ProteinNameBuilder builder = new ProteinNameBuilder();
        Name fullName = NameBuilderTest.createObject(listSize, includeEvidences);
        builder.fullName(fullName);
        List<Name> shortNames = NameBuilderTest.createObjects(listSize, includeEvidences);
        builder.shortNamesSet(shortNames);
        List<EC> ecNumbers = ECBuilderTest.createObjects(listSize, includeEvidences);
        builder.ecNumbersSet(ecNumbers);
        return builder.build();
    }

    public static ProteinName createProteinAltName(int listSize, boolean includeEvidences) {
        ProteinNameBuilder builder = new ProteinNameBuilder();
        Name fullName = NameBuilderTest.createObject(listSize, includeEvidences);
        List<Name> shortNames = NameBuilderTest.createObjects(listSize, includeEvidences);
        List<EC> ecNumbers = ECBuilderTest.createObjects(listSize, includeEvidences);
        builder.fullName(fullName);
        builder.shortNamesSet(shortNames);
        builder.ecNumbersSet(ecNumbers);
        return builder.build();
    }

    public static ProteinName createProteinAltName(int listSize) {
        return createProteinAltName(listSize, false);
    }

    public static ProteinName createProteinAltName() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createProteinAltName(listSize);
    }

    public static List<ProteinName> createProteinAltNames(int count) {
        return createProteinAltNames(count, false);
    }

    public static List<ProteinName> createProteinAltNames(int count, boolean includeEvidences) {
        return IntStream.range(0, count)
                .mapToObj(i -> createProteinAltName(count, includeEvidences))
                .collect(Collectors.toList());
    }

    public static ProteinName createProteinRecName(int listSize) {
        return createProteinRecName(listSize, false);
    }

    public static ProteinName createProteinRecName() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createProteinRecName(listSize);
    }
}
