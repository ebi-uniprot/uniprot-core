package uk.ac.ebi.uniprot.xml.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEdPosition;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.NoteBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.RnaEditingCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.RnaEditingPositionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidencedValueBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.LocationType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.comment.RnaEdPositionConverter;
import uk.ac.ebi.uniprot.xml.uniprot.comment.RnaEditingCommentConverter;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.xml.uniprot.EvidenceHelper.evidencesFromLines;

class RnaEditingCommentConverterTest {

    @Test
    void testPositionConverter() {
        // Modified_positions=320 {ECO:0000269|PubMed:10574461,
        // ECO:0000269|PubMed:11230166
        String pos = "320";
        RnaEdPosition position =
                new RnaEditingPositionBuilder(
                        pos,
                        evidencesFromLines("ECO:0000269|PubMed:10574461", "ECO:0000269|PubMed:11230166"))
                        .build();
        RnaEdPositionConverter converter = new RnaEdPositionConverter(new EvidenceIndexMapper());

        LocationType xmlLocation = converter.toXml(position);

        System.out.println(UniProtXmlTestHelper.toXmlString(xmlLocation, LocationType.class, "location"));
        RnaEdPosition converted = converter.fromXml(xmlLocation);
        assertEquals(position, converted);
    }

    @Test
    void test() {
        String pos = "320";
        RnaEdPosition position = new RnaEditingPositionBuilder(
                pos,
                evidencesFromLines("ECO:0000269|PubMed:10574461",
                                   "ECO:0000269|PubMed:11230166",
                                   "ECO:0000269|PubMed:15731336"))
                .build();
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        builder.positions(Arrays.asList(position));
        EvidencedValue evidencedValue = new EvidencedValueBuilder("Partially edited. Editing appears to be brain-specific",
                                                                  evidencesFromLines("ECO:0000269|PubMed:15731336"))
                .build();
        builder.note(new NoteBuilder(Collections.singletonList(evidencedValue)).build());
        RnaEditingComment comment = builder.build();
        RnaEditingCommentConverter converter = new RnaEditingCommentConverter(new EvidenceIndexMapper());

        CommentType xmlComment = converter.toXml(comment);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        RnaEditingComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }
}
