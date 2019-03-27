package uk.ac.ebi.uniprot.xml.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.SubcellularLocationType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.comment.SubcellularLocationConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

class SubcellularLocationConverterTest {

    @Test
    void test() {
        SubcellularLocationValue location = create("Membrane, caveola", Arrays.asList("ECO:0000256|PIRNR:PIRNR037393",
                                                                                      "ECO:0000256|RuleBase:RU361271"));
        SubcellularLocationValue topology = create("Lipid-anchor, GPI-like-anchor", Arrays
                .asList("ECO:0000256|PIRNR:PIRNR0373943",
                        "ECO:0000256|RuleBase:RU361271"));

        SubcellularLocationValue orientation = create("Peripheral membrane protein", Arrays
                .asList("ECO:0000256|RuleBase:RU000680",
                        "ECO:0000256|SAAS:SAAS00583323"));
        SubcellularLocation subcelLocation = new SubcellularLocationBuilder().location(location)
                .topology(topology).orientation(orientation).build();
        SubcellularLocationConverter converter = new SubcellularLocationConverter(new EvidenceIndexMapper());
        SubcellularLocationType xmlsubcelLocation = converter.toXml(subcelLocation);
        System.out.println(UniProtXmlTestHelper
                                   .toXmlString(xmlsubcelLocation, SubcellularLocationType.class, "subcellularLocation"));

        SubcellularLocation converted = converter.fromXml(xmlsubcelLocation);
        assertEquals(subcelLocation, converted);
    }

    private SubcellularLocationValue create(String val, List<String> evidences) {
        return new SubcellularLocationValueBuilder("", val, createEvidence(evidences)).build();
    }

    private List<Evidence> createEvidence(List<String> evids) {
        List<Evidence> evidences = new ArrayList<>();
        for (String ev : evids) {
            evidences.add(parseEvidenceLine(ev));
        }
        return evidences;
    }
}
