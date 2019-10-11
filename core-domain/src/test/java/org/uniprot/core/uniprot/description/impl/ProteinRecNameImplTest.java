package org.uniprot.core.uniprot.description.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.*;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinRecName;
import org.uniprot.core.uniprot.description.builder.ProteinRecNameBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

class ProteinRecNameImplTest {

    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        List<Name> shortNames = shortNames();
        List<EC> ecNumbers = eCNumbers();
        ProteinRecName recName =
                new ProteinRecNameBuilder()
                        .fullName(fullName)
                        .shortNames(shortNames)
                        .ecNumbers(ecNumbers)
                        .build();
        assertEquals(fullName, recName.getFullName());
        assertEquals(shortNames, recName.getShortNames());
        assertEquals(ecNumbers, recName.getEcNumbers());
        assertTrue(recName.isValid());
    }

    @Test
    void testNotFull() {
        //	List<Evidence> evidences = createEvidences();
        //	Name fullName = new NameImpl("a full Name", evidences);
        List<Name> shortNames = shortNames();
        List<EC> ecNumbers = eCNumbers();
        ProteinRecName recName =
                new ProteinRecNameBuilder()
                        .fullName(null)
                        .shortNames(shortNames)
                        .ecNumbers(ecNumbers)
                        .build();
        assertFalse(recName.isValid());
    }

    @Test
    void testOnlyFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        //	List<Name> shortNames = createShortNames();
        //	List<EC> ecNumbers = createECNumbers();
        ProteinRecName recName =
                new ProteinRecNameBuilder()
                        .fullName(fullName)
                        .shortNames(null)
                        .ecNumbers(null)
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
        //	List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = eCNumbers();
        ProteinRecName recName =
                new ProteinRecNameBuilder()
                        .fullName(fullName)
                        .shortNames(null)
                        .ecNumbers(ecNumbers)
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
        //	List<EC> ecNumbers = createECNumbers();
        ProteinRecName recName =
                new ProteinRecNameBuilder()
                        .fullName(fullName)
                        .shortNames(shortNames)
                        .ecNumbers(null)
                        .build();
        assertEquals(fullName, recName.getFullName());
        assertEquals(shortNames, recName.getShortNames());
        assertEquals(0, recName.getEcNumbers().size());
        assertTrue(recName.isValid());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ProteinRecName obj = new ProteinRecNameImpl();
        assertNotNull(obj);
        assertFalse(obj.hasFullName());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ProteinRecName impl =
                new ProteinRecNameImpl(
                        new NameImpl(), Collections.emptyList(), Collections.emptyList());
        ProteinRecName obj = new ProteinRecNameBuilder().from(impl).build();

        assertTrue(impl.hasFullName());
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }
}
