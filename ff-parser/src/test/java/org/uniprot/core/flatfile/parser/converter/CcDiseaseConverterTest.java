package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CC;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.CcLineObject;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.Disease;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.EvidencedString;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.DiseaseComment;
import org.uniprot.core.uniprot.comment.DiseaseReferenceType;

class CcDiseaseConverterTest {
    private final CcLineConverter converter =
            new CcLineConverter(new HashMap<>(), new HashMap<>(), true);

    @Test
    void testFull() {
        /*
         * Kleefstra syndrome (KLESTS) [MIM:610253]: A syndrome characterized by
         * severe mental retardation, hypotonia, brachy(micro)cephaly, and facial
         * dysmorphisms. Additionally, congenital heart defects, urogenital defects,
         * epilepsy and behavioral problems are frequently observed. Note=The disease
         * is caused by mutations affecting the gene represented in this entry
         * (PubMed:16826528). The syndrome can be either caused by intragenic EHMT1
         * mutations leading to haploinsufficiency of the EHMT1 gene or by a submicroscopic
         * 9q34.3 deletion. Although it is not known if and to what extent other genes in
         * the 9q34.3 region contribute to the syndrome observed in deletion cases, EHMT1 seems to be the major
         * determinant of the core disease phenotype (PubMed:19264732)
         */
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CcLineObject.CCTopicEnum.DISEASE);
        Disease disease = new Disease();
        cc1.setObject(disease);
        disease.setName("Kleefstra syndrome");
        disease.setAbbr("KLESTS");
        disease.setMim("610253");

        disease.setDescription(
                "A syndrome characterized by severe mental retardation, hypotonia, brachy(micro)cephaly, and facial dysmorphisms. Additionally, congenital heart defects, urogenital defects, epilepsy and behavioral problems are frequently observed.");
        String disNote =
                "The disease is caused by mutations affecting the gene represented in this entry (PubMed:16826528)";
        disease.getNote().add(new EvidencedString(disNote, new ArrayList<>()));

        ccLineO.getCcs().add(cc1);
        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals(CommentType.DISEASE, comment1.getCommentType());
        assertTrue(comment1 instanceof DiseaseComment);

        DiseaseComment diseaseComment = (DiseaseComment) comment1;
        assertTrue(diseaseComment.hasDefinedDisease());
        assertEquals("Kleefstra syndrome", diseaseComment.getDisease().getDiseaseId());
        assertEquals("KLESTS", diseaseComment.getDisease().getAcronym());
        assertEquals(disease.getDescription(), diseaseComment.getDisease().getDescription());
        assertEquals(0, diseaseComment.getDisease().getEvidences().size());
        DBCrossReference<DiseaseReferenceType> diseaseRef =
                diseaseComment.getDisease().getReference();
        assertNotNull(diseaseRef);
        assertEquals(DiseaseReferenceType.MIM, diseaseRef.getDatabaseType());
        assertEquals("610253", diseaseRef.getId());
        assertEquals(
                "The disease is caused by mutations affecting the gene represented in this entry (PubMed:16826528)",
                diseaseComment.getNote().getTexts().get(0).getValue());
        assertEquals(0, diseaseComment.getNote().getTexts().get(0).getEvidences().size());
    }

    @Test
    void testFullWithEvidence() {

        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CcLineObject.CCTopicEnum.DISEASE);
        Disease disease = new Disease();
        cc1.setObject(disease);
        disease.setName("Kleefstra syndrome");
        disease.setAbbr("KLESTS");
        disease.setMim("610253");

        disease.setDescription(
                "A syndrome characterized by severe mental retardation, hypotonia, brachy(micro)cephaly, and facial dysmorphisms. Additionally, congenital heart defects, urogenital defects, epilepsy and behavioral problems are frequently observed.");
        String disNote =
                "The disease is caused by mutations affecting the gene represented in this entry (PubMed:16826528)";
        disease.getNote()
                .add(new EvidencedString(disNote, Arrays.asList("ECO:0000269|PubMed:20433554")));
        ccLineO.getEvidenceInfo()
                .getEvidences()
                .put(
                        disease.getDescription(),
                        Arrays.asList("ECO:0000269|PubMed:10433554", "ECO:0000303|Ref.6"));

        ccLineO.getCcs().add(cc1);
        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals(CommentType.DISEASE, comment1.getCommentType());
        assertTrue(comment1 instanceof DiseaseComment);

        DiseaseComment diseaseComment = (DiseaseComment) comment1;
        assertTrue(diseaseComment.hasDefinedDisease());
        assertEquals("Kleefstra syndrome", diseaseComment.getDisease().getDiseaseId());
        assertEquals("KLESTS", diseaseComment.getDisease().getAcronym());
        assertEquals(disease.getDescription(), diseaseComment.getDisease().getDescription());
        assertEquals(2, diseaseComment.getDisease().getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:10433554",
                diseaseComment.getDisease().getEvidences().get(0).getValue());
        DBCrossReference<DiseaseReferenceType> diseaseRef =
                diseaseComment.getDisease().getReference();
        assertNotNull(diseaseRef);
        assertEquals(DiseaseReferenceType.MIM, diseaseRef.getDatabaseType());
        assertEquals("610253", diseaseRef.getId());
        assertEquals(
                "The disease is caused by mutations affecting the gene represented in this entry (PubMed:16826528)",
                diseaseComment.getNote().getTexts().get(0).getValue());
        assertEquals(1, diseaseComment.getNote().getTexts().get(0).getEvidences().size());
        assertEquals(
                "ECO:0000269|PubMed:20433554",
                diseaseComment.getNote().getTexts().get(0).getEvidences().get(0).getValue());
    }

    @Test
    void testOnlyNote() {
        // Note=Frequently mutated in a variety of human cancers (PubMed:15155950)
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CcLineObject.CCTopicEnum.DISEASE);
        Disease disease = new Disease();
        cc1.setObject(disease);
        String disNote = "Frequently mutated in a variety of human cancers (PubMed:15155950).";

        disease.getNote().add(new EvidencedString(disNote, new ArrayList<>()));

        ccLineO.getCcs().add(cc1);
        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());

        Comment comment1 = comments.get(0);
        assertEquals(CommentType.DISEASE, comment1.getCommentType());
        assertTrue(comment1 instanceof DiseaseComment);

        DiseaseComment diseaseComment = (DiseaseComment) comment1;
        assertFalse(diseaseComment.hasDefinedDisease());
        //	assertEquals(null, diseaseComment.getDisease().getDiseaseId());
        //		assertEquals("", diseaseComment.getDisease().getAcronym());
        //		assertEquals("",
        //				diseaseComment.getDisease().getDescription().getValue());
        //		DiseaseReference diseaseRef = diseaseComment.getDisease().getReference();
        //		assertNotNull(diseaseRef);
        //		assertEquals(DiseaseReferenceType.NONE, diseaseRef.getDiseaseReferenceType());
        assertEquals(
                "Frequently mutated in a variety of human cancers (PubMed:15155950).",
                diseaseComment.getNote().getTexts().get(0).getValue());
    }
}
