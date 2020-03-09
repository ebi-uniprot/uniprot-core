package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.EvidenceHelper.evidencesFromLines;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.RnaEdPosition;
import org.uniprot.core.uniprot.comment.RnaEditingComment;
import org.uniprot.core.uniprot.comment.impl.NoteBuilder;
import org.uniprot.core.uniprot.comment.impl.RnaEditingCommentBuilder;
import org.uniprot.core.uniprot.comment.impl.RnaEditingPositionBuilder;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.jaxb.uniprot.LocationType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class RnaEditingCommentConverterTest {

    @Test
    void testPositionConverter() {
        // Modified_positions=320 {ECO:0000269|PubMed:10574461,
        // ECO:0000269|PubMed:11230166
        String pos = "320";
        RnaEdPosition position =
                new RnaEditingPositionBuilder()
                        .position(pos)
                        .evidencesSet(
                                evidencesFromLines(
                                        "ECO:0000269|PubMed:10574461",
                                        "ECO:0000269|PubMed:11230166"))
                        .build();
        RnaEdPositionConverter converter = new RnaEdPositionConverter(new EvidenceIndexMapper());

        LocationType xmlLocation = converter.toXml(position);

        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlLocation, LocationType.class, "location"));
        RnaEdPosition converted = converter.fromXml(xmlLocation);
        assertEquals(position, converted);
    }

    @Test
    void test() {
        String pos = "320";
        RnaEdPosition position =
                new RnaEditingPositionBuilder()
                        .position(pos)
                        .evidencesSet(
                                evidencesFromLines(
                                        "ECO:0000269|PubMed:10574461",
                                        "ECO:0000269|PubMed:11230166",
                                        "ECO:0000269|PubMed:15731336"))
                        .build();
        RnaEditingCommentBuilder builder = new RnaEditingCommentBuilder();
        builder.positionsSet(Arrays.asList(position));
        EvidencedValue evidencedValue =
                new EvidencedValueBuilder(
                                "Partially edited. Editing appears to be brain-specific",
                                evidencesFromLines("ECO:0000269|PubMed:15731336"))
                        .build();
        builder.note(new NoteBuilder(Collections.singletonList(evidencedValue)).build());
        RnaEditingComment comment = builder.build();
        RnaEditingCommentConverter converter =
                new RnaEditingCommentConverter(new EvidenceIndexMapper());

        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        RnaEditingComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }
}
