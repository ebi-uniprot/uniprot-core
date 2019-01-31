package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.SubcellularLocationValueBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidencedValueBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

class SCLCommentConverterTest {

    @Test
    void test() {
        SubcellularLocationValue location = create("Membrane, caveola",
                                                   Arrays.asList("ECO:0000256|PIRNR:PIRNR037393", "ECO:0000256|RuleBase:RU361271"));
        SubcellularLocationValue topology = create("Lipid-anchor, GPI-like-anchor",
                                                   Arrays.asList("ECO:0000256|PIRNR:PIRNR0373943", "ECO:0000256|RuleBase:RU361271"));

        SubcellularLocationValue orientation = create("Peripheral membrane protein",
                                                      Arrays.asList("ECO:0000256|RuleBase:RU000680", "ECO:0000256|SAAS:SAAS00583323"));
        SubcellularLocation subcelLocation = createSubcellularLocation(location,
                                                                       topology, orientation);

        List<SubcellularLocation> subcelLocations = new ArrayList<>();
        subcelLocations.add(subcelLocation);
        SubcellularLocationValue location2 = create("Cell membrane",
                                                    Arrays.asList("ECO:0000250"));
        SubcellularLocation subcelLocation2 = createSubcellularLocation(location2,
                                                                        null, null);
        subcelLocations.add(subcelLocation2);
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        builder.molecule("Some mol")
                .subcellularLocations(subcelLocations)
                .note(createNote("Some note", Arrays
                        .asList("ECO:0000256|RuleBase:RU361271", "ECO:0000256|SAAS:SAAS00583323")));
        SubcellularLocationComment comment = builder.build();
        SCLCommentConverter converter = new SCLCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));

        SubcellularLocationComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);

    }

    private SubcellularLocation createSubcellularLocation(SubcellularLocationValue location, SubcellularLocationValue topology, SubcellularLocationValue orientation) {
        return new SubcellularLocationBuilder()
                .location(location)
                .topology(topology)
                .orientation(orientation)
                .build();
    }

    private Note createNote(String val, List<String> evids) {
        List<EvidencedValue> texts = new ArrayList<>();
        texts.add(new EvidencedValueBuilder(val, createEvidence(evids)).build());
        return new NoteBuilder(texts).build();
    }

    private SubcellularLocationValue create(String val, List<String> evidences) {
        return new SubcellularLocationValueBuilder(val, createEvidence(evidences)).build();
    }

    private List<Evidence> createEvidence(List<String> evids) {
        List<Evidence> evidences = new ArrayList<>();
        for (String ev : evids) {
            evidences.add(parseEvidenceLine(ev));
        }
        return evidences;
    }
}
