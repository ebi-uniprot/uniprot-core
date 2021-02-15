package org.uniprot.core.xml.uniprot.comment;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryComment;
import org.uniprot.core.uniprotkb.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprotkb.comment.impl.MassSpectrometryCommentBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

@Slf4j
class MSCommentConverterTest {

    @Test
    void test() {

        Evidence evidence = parseEvidenceLine("ECO:0000269|PubMed:22887697");

        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =
                builder.molWeight(3042.79f)
                        .method(MassSpectrometryMethod.ELECTROSPRAY)
                        .note("Monoisotopic mass.")
                        .evidencesSet(singletonList(evidence))
                        .build();

        MSCommentConverter converter = new MSCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        MassSpectrometryComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testWithError() {

        Evidence evidence = parseEvidenceLine("ECO:0000269|PubMed:22887697");
        String isoformId = "isoform 1";

        MassSpectrometryCommentBuilder builder = new MassSpectrometryCommentBuilder();
        MassSpectrometryComment comment =
                builder.molWeight(3042.79f)
                        .molecule(isoformId)
                        .molWeightError(0.023f)
                        .method(MassSpectrometryMethod.ELECTROSPRAY)
                        .note("Monoisotopic mass.")
                        .evidencesSet(Arrays.asList(evidence))
                        .build();

        MSCommentConverter converter = new MSCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        MassSpectrometryComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }
}
