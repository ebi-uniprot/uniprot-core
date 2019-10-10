package org.uniprot.core.uniprot.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinRecName;
import org.uniprot.core.uniprot.description.builder.ECBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinRecNameBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.builder.EvidenceBuilder;

class ProteinRecNameImplTest {

    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
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
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
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
        List<EC> ecNumbers = createECNumbers();
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
        List<Name> shortNames = createShortNames();
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

    private List<Name> createShortNames() {
        List<Evidence> evidences = createEvidences();
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(new NameImpl("short name1", evidences));
        shortNames.add(new NameImpl("short name2", evidences));
        return shortNames;
    }

    private List<EC> createECNumbers() {
        List<Evidence> evidences = createEvidences();
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(new ECBuilder().value("1.2.3.4").evidences(evidences).build());
        ecNumbers.add(new ECBuilder().value("1.3.4.3").evidences(evidences).build());
        return ecNumbers;
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(
                new EvidenceBuilder()
                        .evidenceCode(EvidenceCode.ECO_0000255)
                        .databaseName("PROSITE-ProRule")
                        .databaseId("PRU10028")
                        .build());
        evidences.add(
                new EvidenceBuilder()
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .databaseName("PIRNR")
                        .databaseId("PIRNR001361")
                        .build());
        return evidences;
    }
}
