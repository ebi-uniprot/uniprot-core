package uk.ac.ebi.uniprot.xmlparser.uniprot.description;

import uk.ac.ebi.uniprot.domain.uniprot.description.EC;
import uk.ac.ebi.uniprot.domain.uniprot.description.Name;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinName;
import uk.ac.ebi.uniprot.domain.uniprot.description.ProteinSection;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ECBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.NameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.description.builder.ProteinSectionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.domain.uniprot.evidence2.impl.EvidenceHelper.parseEvidenceLine;

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

    public static ProteinSection createProteinNameSection(ProteinName name, List<ProteinName> altNames) {
        return new ProteinSectionBuilder()
                .recommendedName(name)
                .alternativeNames(altNames)
                .build();
    }

    public static ProteinName createProteinName(Name fullName, List<Name> shortNames, List<EC> ecNumbers) {
        return new ProteinNameBuilder()
                .fullName(fullName)
                .shortNames(shortNames)
                .ecNumbers(ecNumbers)
                .build();
    }
}
