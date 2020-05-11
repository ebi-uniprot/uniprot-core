package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.MassSpectrometry;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryComment;
import org.uniprot.core.uniprotkb.evidence.Evidence;

import java.util.List;

class CcMSConverterTest {
    private final CcLineConverter converter = new CcLineConverter(null, null);

    @Test
    void testMassSpectrometry() {
        // CC   -!- MASS SPECTROMETRY: Mass=13822; Method=MALDI; Range=19-140 (P15522-
        // CC       2); Source=PubMed:10531593;
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CC.CCTopicEnum.MASS_SPECTROMETRY);
        MassSpectrometry wr = new MassSpectrometry();
        wr.setMass(13822);
        wr.setMethod("MALDI");
        wr.setMolecule("P15522-2");
        wr.getSources().add("ECO:0000269|PubMed:15208022");
        cc1.setObject(wr);
        ccLineO.getCcs().add(cc1);

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

        assertEquals("P15522-2", wcomment.getMolecule());

        List<Evidence> sources = wcomment.getEvidences();
        assertEquals(1, sources.size());
        Evidence source = sources.get(0);
        assertEquals("ECO:0000269|PubMed:15208022", source.getValue());
    }
}
