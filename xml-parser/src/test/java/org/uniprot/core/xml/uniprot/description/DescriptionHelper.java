package org.uniprot.core.xml.uniprot.description;

import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprotkb.description.*;
import org.uniprot.core.uniprotkb.description.impl.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;

/**
 * Created 30/01/19
 *
 * @author Edd
 */
public class DescriptionHelper {
    public static EC createEC(String ec, List<Evidence> evidences) {
        return new ECBuilder().value(ec).evidencesSet(evidences).build();
    }

    public static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(parseEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

    public static Name createName(String name, List<Evidence> evidences) {
        return new NameBuilder().value(name).evidencesSet(evidences).build();
    }

    public static ProteinSection createProteinNameSection(
            ProteinName name, List<ProteinName> altNames) {
        return new ProteinSectionBuilder()
                .recommendedName(name)
                .alternativeNamesSet(altNames)
                .build();
    }

    public static ProteinName createProteinRecName(
            Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
        return new ProteinNameBuilder()
                .fullName(fullName)
                .shortNamesSet(shortNames)
                .ecNumbersSet(ecNumbers)
                .build();
    }

    public static ProteinName createProteinAltName(
            Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
        return new ProteinNameBuilder()
                .fullName(fullName)
                .shortNamesSet(shortNames)
                .ecNumbersSet(ecNumbers)
                .build();
    }

    public static ProteinSubName createProteinSubName(Name fullName, List<EC> ecNumbers) {
        return new ProteinSubNameBuilder().fullName(fullName).ecNumbersSet(ecNumbers).build();
    }
}
