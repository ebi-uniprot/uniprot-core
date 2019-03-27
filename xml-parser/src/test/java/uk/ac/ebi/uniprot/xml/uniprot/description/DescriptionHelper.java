package uk.ac.ebi.uniprot.xml.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinAltName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinRecName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSubName;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ECBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.NameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinAltNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinRecNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinSectionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinSubNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

/**
 * Created 30/01/19
 *
 * @author Edd
 */
public class DescriptionHelper {
    public static EC createEC(String ec, List<Evidence> evidences) {
        return new ECBuilder()
                .value(ec)
                .evidences(evidences)
                .build();
    }

    public static List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(parseEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

    public static Name createName(String name, List<Evidence> evidences) {
        return new NameBuilder()
                .value(name)
                .evidences(evidences)
                .build();
    }

    public static ProteinSection createProteinNameSection(ProteinRecName name, List<ProteinAltName> altNames) {
        return new ProteinSectionBuilder()
                .recommendedName(name)
                .alternativeNames(altNames)
                .build();
    }

    public static ProteinRecName createProteinRecName(Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
        return new ProteinRecNameBuilder()
                .fullName(fullName)
                .shortNames(shortNames)
                .ecNumbers(ecNumbers)
                .build();
    }
    public static ProteinAltName createProteinAltName(Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
        return new ProteinAltNameBuilder()
                .fullName(fullName)
                .shortNames(shortNames)
                .ecNumbers(ecNumbers)
                .build();
    }
    public static ProteinSubName createProteinSubName(Name fullName, List<EC> ecNumbers) {
        return new ProteinSubNameBuilder()
                .fullName(fullName)
             
                .ecNumbers(ecNumbers)
                .build();
    }
}
