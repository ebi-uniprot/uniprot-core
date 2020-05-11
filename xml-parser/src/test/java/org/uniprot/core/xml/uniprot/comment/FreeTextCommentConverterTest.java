package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;
import org.uniprot.core.uniprotkb.comment.impl.FreeTextCommentBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;
import org.uniprot.core.uniprotkb.evidence.impl.EvidencedValueBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class FreeTextCommentConverterTest {

    @Test
    void testFunction() {
        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        Evidence evidence3 = parseEvidenceLine("ECO:0000269|PubMed:9060648");
        List<Evidence> evids = new ArrayList<>();
        evids.add(evidence1);
        evids.add(evidence2);
        List<Evidence> evids2 = new ArrayList<>();
        evids2.add(evidence1);
        evids2.add(evidence3);

        String text1 =
                "Epithelial ion channel that plays an important role in the regulation of"
                        + " epithelial ion and water transport and fluid homeostasis. Mediates the"
                        + " transport of chloride ions across he cell membrane (By similarity)";
        String text2 = "Second comment";
        List<EvidencedValue> texts = new ArrayList<>();
        texts.add(new EvidencedValueBuilder(text1, evids).build());
        texts.add(new EvidencedValueBuilder(text2, evids2).build());
        org.uniprot.core.uniprotkb.comment.CommentType type =
                org.uniprot.core.uniprotkb.comment.CommentType.FUNCTION;
        FreeTextComment comment =
                new FreeTextCommentBuilder().commentType(type).textsSet(texts).build();
        FreeTextCommentConverter converter =
                new FreeTextCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        FreeTextComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    @Test
    void testDomain() {
        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        Evidence evidence3 = parseEvidenceLine("ECO:0000269|PubMed:9060648");
        List<Evidence> evids = new ArrayList<>();
        evids.add(evidence1);
        evids.add(evidence2);
        List<Evidence> evids2 = new ArrayList<>();
        evids2.add(evidence1);
        evids2.add(evidence3);

        String text1 =
                "Epithelial ion channel that plays an important role in the regulation of"
                        + " epithelial ion and water transport and fluid homeostasis. Mediates the"
                        + " transport of chloride ions across he cell membrane (By similarity)";
        String text2 = "Second comment";
        List<EvidencedValue> texts = new ArrayList<>();
        texts.add(new EvidencedValueBuilder(text1, evids).build());
        texts.add(new EvidencedValueBuilder(text2, evids2).build());
        org.uniprot.core.uniprotkb.comment.CommentType type =
                org.uniprot.core.uniprotkb.comment.CommentType.DOMAIN;
        FreeTextComment comment =
                new FreeTextCommentBuilder().commentType(type).textsSet(texts).build();
        FreeTextCommentConverter converter =
                new FreeTextCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        FreeTextComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }
}
