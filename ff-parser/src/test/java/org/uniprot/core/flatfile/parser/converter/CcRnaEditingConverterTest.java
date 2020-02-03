package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.EvidencedString;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.RnaEditing;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.RnaEditingComment;
import org.uniprot.core.uniprot.comment.RnaEditingLocationType;

class CcRnaEditingConverterTest {
    private final CcLineConverter converter = new CcLineConverter(null, null);

    @Test
    void testRNAEditing() {
        // CC   -!- RNA EDITING: Modified_positions=1, 56, 89, 103, 126, 164;
        // CC       Note=The initiator methionine is created by RNA editing.
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CC.CCTopicEnum.RNA_EDITING);
        RnaEditing rnaEd = new RnaEditing();
        rnaEd.getNote()
                .add(
                        new EvidencedString(
                                "The initiator methionine is created by RNA editing.",
                                new ArrayList<String>()));
        rnaEd.getLocations().add(1);
        rnaEd.getLocations().add(56);
        rnaEd.getLocations().add(89);
        rnaEd.getLocations().add(103);
        rnaEd.getLocations().add(126);
        rnaEd.getLocations().add(164);

        cc1.setObject(rnaEd);
        ccLineO.getCcs().add(cc1);

        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals(CommentType.RNA_EDITING, comment1.getCommentType());
        assertTrue(comment1 instanceof RnaEditingComment);

        RnaEditingComment wcomment = (RnaEditingComment) comment1;

        assertEquals(
                "The initiator methionine is created by RNA editing",
                wcomment.getNote().getTexts().get(0).getValue());
        assertEquals(RnaEditingLocationType.Known, wcomment.getLocationType());
        assertEquals(6, wcomment.getPositions().size());
        assertEquals("1", wcomment.getPositions().get(0).getPosition());
        assertEquals("126", wcomment.getPositions().get(4).getPosition());
    }
}
