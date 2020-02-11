package org.uniprot.core.xml.uniprot.description;

import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.Name;
import org.uniprot.core.uniprot.description.ProteinAltName;
import org.uniprot.core.uniprot.description.ProteinRecName;
import org.uniprot.core.uniprot.description.ProteinSection;
import org.uniprot.core.uniprot.description.ProteinSubName;
import org.uniprot.core.uniprot.description.builder.ECBuilder;
import org.uniprot.core.uniprot.description.builder.NameBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinAltNameBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinRecNameBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinSectionBuilder;
import org.uniprot.core.uniprot.description.builder.ProteinSubNameBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

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
            ProteinRecName name, List<ProteinAltName> altNames) {
        return new ProteinSectionBuilder()
                .recommendedName(name)
                .alternativeNamesSet(altNames)
                .build();
    }

    public static ProteinRecName createProteinRecName(
            Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
        return new ProteinRecNameBuilder()
                .fullName(fullName)
                .shortNamesSet(shortNames)
                .ecNumbersSet(ecNumbers)
                .build();
    }

    public static ProteinAltName createProteinAltName(
            Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
        return new ProteinAltNameBuilder()
                .fullName(fullName)
                .shortNamesSet(shortNames)
                .ecNumbersSet(ecNumbers)
                .build();
    }

    public static ProteinSubName createProteinSubName(Name fullName, List<EC> ecNumbers) {
        return new ProteinSubNameBuilder().fullName(fullName).ecNumbersSet(ecNumbers).build();
    }
}
