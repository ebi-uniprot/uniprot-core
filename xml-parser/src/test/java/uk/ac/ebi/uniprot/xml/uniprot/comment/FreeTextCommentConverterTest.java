package uk.ac.ebi.uniprot.xml.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.FreeTextCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidencedValueBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.comment.FreeTextCommentConverter;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

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

        String text1 = "Epithelial ion channel that plays an important role in the regulation of epithelial ion "
                + "and water transport and fluid homeostasis. Mediates the transport of chloride ions across "
                + "he cell membrane (By similarity)";
        String text2 = "Second comment";
        List<EvidencedValue> texts = new ArrayList<>();
        texts.add(new EvidencedValueBuilder(text1, evids).build());
        texts.add(new EvidencedValueBuilder(text2, evids2).build());
        uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType type =
                uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType.FUNCTION;
        FreeTextComment comment = new FreeTextCommentBuilder().commentType(type).texts(texts).build();
        FreeTextCommentConverter converter = new FreeTextCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
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

        String text1 = "Epithelial ion channel that plays an important role in the regulation of epithelial ion "
                + "and water transport and fluid homeostasis. Mediates the transport of chloride ions across "
                + "he cell membrane (By similarity)";
        String text2 = "Second comment";
        List<EvidencedValue> texts = new ArrayList<>();
        texts.add(new EvidencedValueBuilder(text1, evids).build());
        texts.add(new EvidencedValueBuilder(text2, evids2).build());
        uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType type =
                uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType.DOMAIN;
        FreeTextComment comment = new FreeTextCommentBuilder().commentType(type).texts(texts).build();
        FreeTextCommentConverter converter = new FreeTextCommentConverter(new EvidenceIndexMapper());
        CommentType xmlComment = converter.toXml(comment);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        FreeTextComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);

    }

}
