package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.Note;
import org.uniprot.core.uniprotkb.comment.SubcellularLocation;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationValue;
import org.uniprot.core.uniprotkb.comment.impl.NoteBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationCommentBuilder;
import org.uniprot.core.uniprotkb.comment.impl.SubcellularLocationValueBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

@Slf4j
class SCLCommentConverterTest {

    @Test
    void test() {
        SubcellularLocationValue location =
                create(
                        "Membrane, caveola",
                        Arrays.asList(
                                "ECO:0000256|PIRNR:PIRNR037393", "ECO:0000256|RuleBase:RU361271"));
        SubcellularLocationValue topology =
                create(
                        "Lipid-anchor, GPI-like-anchor",
                        Arrays.asList(
                                "ECO:0000256|PIRNR:PIRNR0373943", "ECO:0000256|RuleBase:RU361271"));

        SubcellularLocationValue orientation =
                create(
                        "Peripheral membrane protein",
                        Arrays.asList(
                                "ECO:0000256|RuleBase:RU000680", "ECO:0000256|SAAS:SAAS00583323"));
        SubcellularLocation subcelLocation =
                createSubcellularLocation(location, topology, orientation);

        List<SubcellularLocation> subcelLocations = new ArrayList<>();
        subcelLocations.add(subcelLocation);
        SubcellularLocationValue location2 = create("Cell membrane", Arrays.asList("ECO:0000250"));
        SubcellularLocation subcelLocation2 = createSubcellularLocation(location2, null, null);
        subcelLocations.add(subcelLocation2);
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        builder.molecule("Some mol")
                .subcellularLocationsSet(subcelLocations)
                .note(
                        createNote(
                                "Some note",
                                Arrays.asList(
                                        "ECO:0000256|RuleBase:RU361271",
                                        "ECO:0000256|SAAS:SAAS00583323")));
        SubcellularLocationComment comment = builder.build();
        SCLCommentConverter converter = new SCLCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));

        SubcellularLocationComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void middlenameWithCapitalLetter() {
        SubcellularLocationValue location =
                create(
                        "Golgi apparatus, Golgi stack",
                        Arrays.asList("ECO:0000256|PIRNR:PIRNR037393"));
        SubcellularLocation subcelLocation = createSubcellularLocation(location, null, null);
        List<SubcellularLocation> subcelLocations = new ArrayList<>();
        subcelLocations.add(subcelLocation);
        SubcellularLocationValue location2 =
                create("Nucleus, Cajal body", Arrays.asList("ECO:0000250"));
        SubcellularLocation subcelLocation2 = createSubcellularLocation(location2, null, null);
        subcelLocations.add(subcelLocation2);
        SubcellularLocationCommentBuilder builder = new SubcellularLocationCommentBuilder();
        builder.molecule("Some mol").subcellularLocationsSet(subcelLocations);
        SubcellularLocationComment comment = builder.build();
        SCLCommentConverter converter = new SCLCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        SubcellularLocationComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    private SubcellularLocation createSubcellularLocation(
            SubcellularLocationValue location,
            SubcellularLocationValue topology,
            SubcellularLocationValue orientation) {
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
        return new SubcellularLocationValueBuilder()
                .id("")
                .value(val)
                .evidencesSet(createEvidence(evidences))
                .build();
    }

    private List<Evidence> createEvidence(List<String> evids) {
        List<Evidence> evidences = new ArrayList<>();
        for (String ev : evids) {
            evidences.add(parseEvidenceLine(ev));
        }
        return evidences;
    }
}
