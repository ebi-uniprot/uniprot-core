package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinSectionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.ProteinDescriptionFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProteinSectionImplTest {

    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        ProteinName recName = new ProteinNameBuilder().setFullName(fullName).setShortNames(shortNames).setEcNumbers(null).createProteinNameImpl();
        List<ProteinName> altNames = createAltName();
        ProteinSectionImpl section = new ProteinSectionBuilder().setRecommendedName(recName).setAlternativeNames(altNames).createProteinSectionImpl();
        assertEquals(recName, section.getRecommendedName());
        assertEquals(altNames, section.getAlternativeNames());

        TestHelper.verifyJson(section);
    }

    @Test
    void testOnlyRecName() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameImpl("a full Name", evidences);
        List<Name> shortNames = createShortNames();
        ProteinName recName = new ProteinNameBuilder().setFullName(fullName).setShortNames(shortNames).setEcNumbers(null).createProteinNameImpl();
        List<ProteinName> altNames = null;
        ProteinSectionImpl section = new ProteinSectionBuilder().setRecommendedName(recName).setAlternativeNames(altNames).createProteinSectionImpl();
        assertEquals(recName, section.getRecommendedName());
        assertEquals(0, section.getAlternativeNames().size());

        TestHelper.verifyJson(section);
    }

    private List<Name> createShortNames() {
        List<Evidence> evidences = createEvidences();
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(new NameImpl("short name1", evidences));
        shortNames.add(new NameImpl("short name2", evidences));
        return shortNames;
    }

    private List<ProteinName> createAltName() {
        List<ProteinName> alternativeNames = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        Name fullName = ProteinDescriptionFactory.INSTANCE.createName("a full alt Name", evidences);
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name1", evidences));
        shortNames.add(ProteinDescriptionFactory.INSTANCE.createName("short name2", evidences));
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(ProteinDescriptionFactory.INSTANCE.createECNumber("1.2.3.4", evidences));

        alternativeNames.add(ProteinDescriptionFactory.INSTANCE.createProteinName(fullName, shortNames, ecNumbers));

        return alternativeNames;
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
