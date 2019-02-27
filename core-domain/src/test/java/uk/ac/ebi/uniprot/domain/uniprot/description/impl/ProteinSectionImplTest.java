package uk.ac.ebi.uniprot.domain.uniprot.description.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ECBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.NameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinAltNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinRecNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinSectionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidenceBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProteinSectionImplTest {

    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameBuilder().value("a full Name").evidences(evidences).build();
        List<Name> shortNames = createShortNames();
        ProteinRecName recName = new ProteinRecNameBuilder().fullName(fullName).shortNames(shortNames).ecNumbers(null).build();
        List<ProteinAltName> altNames = createAltName();
        ProteinSection section = new ProteinSectionBuilder().recommendedName(recName).alternativeNames(altNames).build();
        assertEquals(recName, section.getRecommendedName());
        assertEquals(altNames, section.getAlternativeNames());
    }

    @Test
    void testOnlyRecName() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameBuilder().value("a full Name").evidences(evidences).build();
        List<Name> shortNames = createShortNames();
        ProteinRecName recName = new ProteinRecNameBuilder().fullName(fullName).shortNames(shortNames).ecNumbers(null).build();
        List<ProteinAltName> altNames = null;
        ProteinSection section = new ProteinSectionBuilder().recommendedName(recName).alternativeNames(altNames).build();
        assertEquals(recName, section.getRecommendedName());
        assertEquals(0, section.getAlternativeNames().size());
    }

    private List<Name> createShortNames() {
        List<Evidence> evidences = createEvidences();
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(new NameImpl("short name1", evidences));
        shortNames.add(new NameImpl("short name2", evidences));
        return shortNames;
    }

    private List<ProteinAltName> createAltName() {
        List<ProteinAltName> alternativeNames = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameBuilder().value("a full alt Name").evidences(evidences).build();
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(new NameBuilder().value("short name1").evidences(evidences).build());
        shortNames.add(new NameBuilder().value("short name2").evidences(evidences).build());
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(new ECBuilder().value("1.2.3.4").evidences( evidences).build());

        alternativeNames.add(new ProteinAltNameBuilder().fullName(fullName).shortNames(shortNames).ecNumbers(ecNumbers).build());

        return alternativeNames;
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceBuilder()
                .evidenceCode(EvidenceCode.ECO_0000255)
                .databaseName("PROSITE-ProRule")
                .databaseId("PRU10028")
                .build());
        evidences.add(new EvidenceBuilder()
                .evidenceCode(EvidenceCode.ECO_0000256)
                .databaseName("PIRNR")
                .databaseId("PIRNR001361")
                .build());
        return evidences;
    }
}
