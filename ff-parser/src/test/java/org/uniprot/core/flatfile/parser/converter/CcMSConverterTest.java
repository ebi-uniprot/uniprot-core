package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineObject;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryRange;
import org.uniprot.core.uniprot.evidence.Evidence;

class CcMSConverterTest {
    private final CcLineConverter converter = new CcLineConverter(null, null);

    @Test
    void testMassSpectrometry() {
        // CC   -!- MASS SPECTROMETRY: Mass=13822; Method=MALDI; Range=19-140 (P15522-
        // CC       2); Source=PubMed:10531593;
        CcLineObject ccLineO = new CcLineObject();
        CcLineObject.CC cc1 = new CcLineObject.CC();
        cc1.topic = CcLineObject.CCTopicEnum.MASS_SPECTROMETRY;
        CcLineObject.MassSpectrometry wr = new CcLineObject.MassSpectrometry();
        wr.mass = 13822;
        wr.method = "MALDI";
        CcLineObject.MassSpectrometryRange mrange = new CcLineObject.MassSpectrometryRange();
        mrange.start = 19;
        mrange.end = 140;
        wr.ranges.add(mrange);
        mrange.rangeIsoform = "P15522-2";
        wr.sources.add("ECO:0000269|PubMed:15208022");
        cc1.object = wr;
        ccLineO.ccs.add(cc1);

        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals(CommentType.MASS_SPECTROMETRY, comment1.getCommentType());
        assertTrue(comment1 instanceof MassSpectrometryComment);

        MassSpectrometryComment wcomment = (MassSpectrometryComment) comment1;

        assertEquals(13822.0, wcomment.getMolWeight(), 0.0001);
        assertTrue(wcomment.getMolWeightError() != null);
        assertEquals(0, wcomment.getMolWeightError(), 0.0001);
        //	assertEquals(null, wcomment.getNote());
        List<MassSpectrometryRange> ranges = wcomment.getRanges();
        assertEquals(1, ranges.size());
        MassSpectrometryRange range = ranges.get(0);
        assertEquals("P15522-2", range.getIsoformId());
        assertEquals(19, range.getRange().getStart().getValue().intValue());
        assertEquals(140, range.getRange().getEnd().getValue().intValue());
        List<Evidence> sources = wcomment.getEvidences();
        assertEquals(1, sources.size());
        Evidence source = sources.get(0);
        assertEquals("ECO:0000269|PubMed:15208022", source.getValue());
    }
}
