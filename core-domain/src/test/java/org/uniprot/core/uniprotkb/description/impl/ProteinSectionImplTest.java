package org.uniprot.core.uniprotkb.description.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.description.ProteinName;
import org.uniprot.core.uniprotkb.description.ProteinSection;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;

class ProteinSectionImplTest {

    @Test
    void testFull() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameBuilder().value("a full Name").evidencesSet(evidences).build();
        List<Name> shortNames = createShortNames();
        ProteinName recName =
                new ProteinNameBuilder()
                        .fullName(fullName)
                        .shortNamesSet(shortNames)
                        .ecNumbersSet(null)
                        .build();
        List<ProteinName> altNames = createAltName();
        ProteinSection section =
                new ProteinSectionBuilder()
                        .recommendedName(recName)
                        .alternativeNamesSet(altNames)
                        .build();
        assertEquals(recName, section.getRecommendedName());
        assertEquals(altNames, section.getAlternativeNames());
    }

    @Test
    void testOnlyRecName() {
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameBuilder().value("a full Name").evidencesSet(evidences).build();
        List<Name> shortNames = createShortNames();
        ProteinName recName =
                new ProteinNameBuilder()
                        .fullName(fullName)
                        .shortNamesSet(shortNames)
                        .ecNumbersSet(null)
                        .build();
        List<ProteinName> altNames = null;
        ProteinSection section =
                new ProteinSectionBuilder()
                        .recommendedName(recName)
                        .alternativeNamesSet(altNames)
                        .build();
        assertEquals(recName, section.getRecommendedName());
        assertEquals(0, section.getAlternativeNames().size());
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ProteinSection obj = new ProteinSectionImpl();
        assertNotNull(obj);
        assertTrue(obj.getCdAntigenNames().isEmpty());
        assertTrue(obj.getAlternativeNames().isEmpty());
        assertTrue(obj.getInnNames().isEmpty());
        assertFalse(obj.hasRecommendedName());
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ProteinSection impl =
                new ProteinSectionImpl(
                        new ProteinNameImpl(),
                        Collections.emptyList(),
                        new NameImpl(),
                        new NameImpl(),
                        Collections.emptyList(),
                        Collections.emptyList());
        ProteinSection obj = ProteinSectionBuilder.from(impl).build();

        assertTrue(obj.hasRecommendedName());
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

    private List<ProteinName> createAltName() {
        List<ProteinName> alternativeNames = new ArrayList<>();
        List<Evidence> evidences = createEvidences();
        Name fullName = new NameBuilder().value("a full alt Name").evidencesSet(evidences).build();
        List<Name> shortNames = new ArrayList<>();
        shortNames.add(new NameBuilder().value("short name1").evidencesSet(evidences).build());
        shortNames.add(new NameBuilder().value("short name2").evidencesSet(evidences).build());
        List<EC> ecNumbers = new ArrayList<>();
        ecNumbers.add(new ECBuilder().value("1.2.3.4").evidencesSet(evidences).build());

        alternativeNames.add(
                new ProteinNameBuilder()
                        .fullName(fullName)
                        .shortNamesSet(shortNames)
                        .ecNumbersSet(ecNumbers)
                        .build());

        return alternativeNames;
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
