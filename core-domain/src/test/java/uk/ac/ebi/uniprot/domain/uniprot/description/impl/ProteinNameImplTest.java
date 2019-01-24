package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

class ProteinNameImplTest {

    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
        ProteinName recName = new ProteinNameBuilder().setFullName(fullName).setShortNames(shortNames).setEcNumbers(ecNumbers).createProteinNameImpl();
        assertEquals(fullName, recName.getFullName());
        assertEquals(shortNames, recName.getShortNames());
        assertEquals(ecNumbers, recName.getEcNumbers());
        assertTrue(recName.isValid());

        TestHelper.verifyJson(recName);

    }

    @Test
    void testNotFull() {
        //	List<Evidence> evidences = createEvidences();
        //	Name fullName = new NameImpl("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
        ProteinName recName = new ProteinNameBuilder().setFullName(null).setShortNames(shortNames).setEcNumbers(ecNumbers).createProteinNameImpl();

        assertFalse(recName.isValid());

        //	TestHelper.verifyJson(recName);

    }

    @Test
    void testOnlyFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        //	List<Name> shortNames = createShortNames();
        //	List<EC> ecNumbers = createECNumbers();
        ProteinName recName = new ProteinNameBuilder().setFullName(fullName).setShortNames(null).setEcNumbers(null).createProteinNameImpl();
        assertEquals(fullName, recName.getFullName());
        assertEquals(0, recName.getShortNames().size());
        assertEquals(0, recName.getEcNumbers().size());
        assertTrue(recName.isValid());

        TestHelper.verifyJson(recName);

    }

    @Test
    void testOnlyFullAndEC() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        //	List<Name> shortNames = createShortNames();
        List<EC> ecNumbers = createECNumbers();
        ProteinName recName = new ProteinNameBuilder().setFullName(fullName).setShortNames(null).setEcNumbers(ecNumbers).createProteinNameImpl();
        assertEquals(fullName, recName.getFullName());
        assertEquals(0, recName.getShortNames().size());
        assertEquals(ecNumbers, recName.getEcNumbers());
        assertTrue(recName.isValid());

        TestHelper.verifyJson(recName);

    }

    @Test
    void testOnlyFullAndShort() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        //	List<EC> ecNumbers = createECNumbers();
        ProteinName recName = new ProteinNameBuilder().setFullName(fullName).setShortNames(shortNames).setEcNumbers(null).createProteinNameImpl();
        assertEquals(fullName, recName.getFullName());
        assertEquals(shortNames, recName.getShortNames());
        assertEquals(0, recName.getEcNumbers().size());
        assertTrue(recName.isValid());

        TestHelper.verifyJson(recName);

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
        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));
        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.3.4.3", evidences));
        return ecNumbers;
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

}
