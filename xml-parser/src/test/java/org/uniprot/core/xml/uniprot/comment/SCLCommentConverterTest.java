package org.uniprot.core.xml.uniprot.comment;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.Note;
import org.uniprot.core.uniprot.comment.SubcellularLocation;
import org.uniprot.core.uniprot.comment.SubcellularLocationComment;
import org.uniprot.core.uniprot.comment.SubcellularLocationValue;
import org.uniprot.core.uniprot.comment.builder.NoteBuilder;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationBuilder;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.SubcellularLocationValueBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;
import org.uniprot.core.xml.uniprot.comment.SCLCommentConverter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

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
