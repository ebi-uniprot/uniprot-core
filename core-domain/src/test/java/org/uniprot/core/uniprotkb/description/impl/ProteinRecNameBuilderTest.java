package org.uniprot.core.uniprotkb.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinRecName;

public class ProteinRecNameBuilderTest {

    @Test
    void canAddSingleShortName() {
        ProteinRecName obj =
                new ProteinRecNameBuilder().shortNamesAdd(new NameBuilder().build()).build();
        assertNotNull(obj.getShortNames());
        assertFalse(obj.getShortNames().isEmpty());
        assertTrue(obj.hasShortNames());
    }

    @Test
    void nullShortName_willBeIgnore() {
        ProteinRecName obj = new ProteinRecNameBuilder().shortNamesAdd(null).build();
        assertNotNull(obj.getShortNames());
        assertTrue(obj.getShortNames().isEmpty());
        assertFalse(obj.hasShortNames());
    }

    @Test
    void canAddSingleEcNumber() {
        ProteinRecName obj =
                new ProteinRecNameBuilder().ecNumbersAdd(new ECBuilder().build()).build();
        assertNotNull(obj.getEcNumbers());
        assertFalse(obj.getEcNumbers().isEmpty());
        assertTrue(obj.hasEcNumbers());
    }

    @Test
    void nullEcNumber_willBeIgnore() {
        ProteinRecName obj = new ProteinRecNameBuilder().ecNumbersAdd(null).build();
        assertNotNull(obj.getEcNumbers());
        assertTrue(obj.getEcNumbers().isEmpty());
        assertFalse(obj.hasEcNumbers());
    }

    @Test
    void canCreateBuilderFromInstance() {
        ProteinRecName obj = new ProteinRecNameBuilder().build();
        ProteinRecNameBuilder builder = ProteinRecNameBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        ProteinRecName obj = new ProteinRecNameBuilder().build();
        ProteinRecName obj2 = new ProteinRecNameBuilder().build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }

    public static ProteinRecName createObject(int listSize, boolean includeEvidences) {
        ProteinRecNameBuilder builder = new ProteinRecNameBuilder();
        Name fullName = NameBuilderTest.createObject(listSize, includeEvidences);
        builder.fullName(fullName);
        List<Name> shortNames = NameBuilderTest.createObjects(listSize, includeEvidences);
        builder.shortNamesSet(shortNames);
        List<EC> ecNumbers = ECBuilderTest.createObjects(listSize, includeEvidences);
        builder.ecNumbersSet(ecNumbers);
        return builder.build();
    }

    public static ProteinRecName createObject(int listSize) {
        return createObject(listSize, false);
    }

    public static ProteinRecName createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }
}
