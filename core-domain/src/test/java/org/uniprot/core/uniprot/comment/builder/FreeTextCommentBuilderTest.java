package org.uniprot.core.uniprot.comment.builder;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidenceValuesWithoutEvidences;
import static org.uniprot.core.ObjectsForTests.evidenceValues;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.FreeTextComment;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.builder.EvidencedValueBuilder;

class FreeTextCommentBuilderTest {
    @Test
    void testBuildAllergenComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.ALLERGEN, texts);
        assertEquals(CommentType.ALLERGEN, comment.getCommentType());
        assertEquals(texts, comment.getTexts());
    }

    @Test
    void testBuildBiotechnologyComment() {
        List<EvidencedValue> texts = evidenceValues();
        FreeTextComment comment = buildFreeTextComment(CommentType.BIOTECHNOLOGY, texts);
        assertEquals(CommentType.BIOTECHNOLOGY, comment.getCommentType());
    }

    @Test
    void testBuildCatalyticActivityComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.CATALYTIC_ACTIVITY, texts);
        assertEquals(CommentType.CATALYTIC_ACTIVITY, comment.getCommentType());
    }

    @Test
    void testBuildCautionComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.CAUTION, texts);
        assertEquals(CommentType.CAUTION, comment.getCommentType());
    }

    @Test
    void testBuildDevelopmentalStageComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.DEVELOPMENTAL_STAGE, texts);
        assertEquals(CommentType.DEVELOPMENTAL_STAGE, comment.getCommentType());
    }

    @Test
    void testBuildDisruptionPhenotypeComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.DISRUPTION_PHENOTYPE, texts);
        assertEquals(CommentType.DISRUPTION_PHENOTYPE, comment.getCommentType());
    }

    @Test
    void testBuildDomainComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.DOMAIN, texts);
        assertEquals(CommentType.DOMAIN, comment.getCommentType());
    }

    @Test
    void testBuildEnzymeRegulationComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.ACTIVITY_REGULATION, texts);
        assertEquals(CommentType.ACTIVITY_REGULATION, comment.getCommentType());
    }

    @Test
    void testBuildFunctionComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.FUNCTION, texts);
        assertEquals(CommentType.FUNCTION, comment.getCommentType());
    }

    @Test
    void testBuildInductionComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.INDUCTION, texts);
        assertEquals(CommentType.INDUCTION, comment.getCommentType());
    }

    @Test
    void testBuildMiscellaneousComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.MISCELLANEOUS, texts);
        assertEquals(CommentType.MISCELLANEOUS, comment.getCommentType());
    }

    @Test
    void testBuildPathwayComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.PATHWAY, texts);
        assertEquals(CommentType.PATHWAY, comment.getCommentType());
    }

    @Test
    void testBuildPharmaceuticalComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.PHARMACEUTICAL, texts);
        assertEquals(CommentType.PHARMACEUTICAL, comment.getCommentType());
    }

    @Test
    void testBuildPolymorphismComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.POLYMORPHISM, texts);
        assertEquals(CommentType.POLYMORPHISM, comment.getCommentType());
    }

    @Test
    void testBuildPtmComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.PTM, texts);
        assertEquals(CommentType.PTM, comment.getCommentType());
    }

    @Test
    void testBuildSimiarityComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.SIMILARITY, texts);
        assertEquals(CommentType.SIMILARITY, comment.getCommentType());
    }

    @Test
    void testBuildSubunitComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.SUBUNIT, texts);
        assertEquals(CommentType.SUBUNIT, comment.getCommentType());
    }

    @Test
    void testBuildTissueSpecificityComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.TISSUE_SPECIFICITY, texts);
        assertEquals(CommentType.TISSUE_SPECIFICITY, comment.getCommentType());
    }

    @Test
    void testBuildToxicDoseComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.TOXIC_DOSE, texts);
        assertEquals(CommentType.TOXIC_DOSE, comment.getCommentType());
    }

    private FreeTextComment buildFreeTextComment(CommentType type, List<EvidencedValue> texts) {
        return new FreeTextCommentBuilder().texts(texts).commentType(type).build();
    }

    @Test
    void testBuildNoneFreeTextComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        assertThrows(
                IllegalArgumentException.class,
                () -> buildFreeTextComment(CommentType.COFACTOR, texts));
    }

    @Test
    void canAddSingleText() {
        FreeTextComment obj =
                new FreeTextCommentBuilder()
                        .commentType(CommentType.DOMAIN)
                        .addText(new EvidencedValueBuilder("value1", emptyList()).build())
                        .build();
        assertNotNull(obj.getTexts());
        assertFalse(obj.getTexts().isEmpty());
        assertTrue(obj.hasTexts());
    }

    @Test
    void nullText_willBeIgnore() {
        FreeTextComment obj =
                new FreeTextCommentBuilder().commentType(CommentType.DOMAIN).addText(null).build();
        assertNotNull(obj.getTexts());
        assertTrue(obj.getTexts().isEmpty());
        assertFalse(obj.hasTexts());
    }

    @Test
    void canCreateBuilderFromInstance() {
        FreeTextComment obj =
                new FreeTextCommentBuilder().commentType(CommentType.DISRUPTION_PHENOTYPE).build();
        FreeTextCommentBuilder builder = FreeTextCommentBuilder.from(obj);
        assertNotNull(builder);
    }

    @Test
    void defaultBuild_objsAreEqual() {
        FreeTextComment obj = new FreeTextCommentBuilder().commentType(CommentType.DOMAIN).build();
        FreeTextComment obj2 = new FreeTextCommentBuilder().commentType(CommentType.DOMAIN).build();
        assertTrue(obj.equals(obj2) && obj2.equals(obj));
        assertEquals(obj.hashCode(), obj2.hashCode());
    }
}
