package org.uniprot.core.uniprotkb.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinSubName;

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
        ProteinSubName obj =
                new ProteinSubNameBuilder()
                        .ecNumbersSet(Collections.emptyList())
                        .ecNumbersAdd(ecNumber)
                        .build();
        assertNotNull(obj.getEcNumbers());
        assertFalse(obj.getEcNumbers().isEmpty());
        assertTrue(obj.hasEcNumbers());
    }

    @Test
    void canAddListEcNumber() {
        ProteinSubName obj =
                new ProteinSubNameBuilder()
                        .ecNumbersSet(Collections.singletonList(ecNumber))
                        .build();
        assertNotNull(obj.getEcNumbers());
        assertFalse(obj.getEcNumbers().isEmpty());
        assertTrue(obj.hasEcNumbers());
    }

    @Test
    void canAddSingleEcNumber() {
        ProteinSubName obj =
                new ProteinSubNameBuilder().ecNumbersAdd(new ECBuilder().build()).build();
        assertNotNull(obj.getEcNumbers());
        assertFalse(obj.getEcNumbers().isEmpty());
        assertTrue(obj.hasEcNumbers());
    }

    @Test
    void nullEcNumber_willBeIgnore() {
        ProteinSubName obj = new ProteinSubNameBuilder().ecNumbersAdd(null).build();
        assertNotNull(obj.getEcNumbers());
        assertTrue(obj.getEcNumbers().isEmpty());
        assertFalse(obj.hasEcNumbers());
    }

    @Test
    void canCreateBuilderFromInstance() {
        ProteinSubName obj = new ProteinSubNameBuilder().build();
        ProteinSubNameBuilder builder = ProteinSubNameBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        ProteinSubName obj = new ProteinSubNameBuilder().build();
        ProteinSubName obj2 = new ProteinSubNameBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    public static ProteinSubName createObject() {
        ProteinSubNameBuilder builder = new ProteinSubNameBuilder();
        Name fullName = NameBuilderTest.createObject();
        builder.fullName(fullName);
        List<EC> ecNumbers = ECBuilderTest.createObjects(3);
        builder.ecNumbersSet(ecNumbers);
        return builder.build();
    }

    public static List<ProteinSubName> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
