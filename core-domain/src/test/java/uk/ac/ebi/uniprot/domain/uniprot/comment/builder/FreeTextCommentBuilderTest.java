package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.builder.EvidencedValueBuilder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static uk.ac.ebi.uniprot.domain.uniprot.EvidenceHelper.createEvidenceValuesWithoutEvidences;

public class FreeTextCommentBuilderTest {
    @Test
    public void testBuildAllergenComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.ALLERGEN, texts);
        assertEquals(CommentType.ALLERGEN, comment.getCommentType());
        assertEquals(texts, comment.getTexts());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildBiotechnologyComment() {
        List<EvidencedValue> texts = createEvidenceValues2();
        FreeTextComment comment = buildFreeTextComment(CommentType.BIOTECHNOLOGY, texts);
        assertEquals(CommentType.BIOTECHNOLOGY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildCatalyticActivityComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.CATALYTIC_ACTIVITY, texts);
        assertEquals(CommentType.CATALYTIC_ACTIVITY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildCautionComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.CAUTION, texts);
        assertEquals(CommentType.CAUTION, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildDevelopmentalStageComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.DEVELOPMENTAL_STAGE, texts);
        assertEquals(CommentType.DEVELOPMENTAL_STAGE, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildDisruptionPhenotypeComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.DISRUPTION_PHENOTYPE, texts);
        assertEquals(CommentType.DISRUPTION_PHENOTYPE, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildDomainComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.DOMAIN, texts);
        assertEquals(CommentType.DOMAIN, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildEnzymeRegulationComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.ACTIVITY_REGULATION, texts);
        assertEquals(CommentType.ACTIVITY_REGULATION, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildFunctionComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.FUNCTION, texts);
        assertEquals(CommentType.FUNCTION, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildInductionComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.INDUCTION, texts);
        assertEquals(CommentType.INDUCTION, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildMiscellaneousComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.MISCELLANEOUS, texts);
        assertEquals(CommentType.MISCELLANEOUS, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildPathwayComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.PATHWAY, texts);
        assertEquals(CommentType.PATHWAY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildPharmaceuticalComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.PHARMACEUTICAL, texts);
        assertEquals(CommentType.PHARMACEUTICAL, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildPolymorphismComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.POLYMORPHISM, texts);
        assertEquals(CommentType.POLYMORPHISM, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildPtmComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.PTM, texts);
        assertEquals(CommentType.PTM, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildSimiarityComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.SIMILARITY, texts);
        assertEquals(CommentType.SIMILARITY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildSubunitComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.SUBUNIT, texts);
        assertEquals(CommentType.SUBUNIT, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildTissueSpecificityComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.TISSUE_SPECIFICITY, texts);
        assertEquals(CommentType.TISSUE_SPECIFICITY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildToxicDoseComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        FreeTextComment comment = buildFreeTextComment(CommentType.TOXIC_DOSE, texts);
        assertEquals(CommentType.TOXIC_DOSE, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }

    private FreeTextComment buildFreeTextComment(CommentType type, List<EvidencedValue> texts) {
        return new FreeTextCommentBuilder()
                .texts(texts)
                .commentType(type)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuildNoneFreeTextComment() {
        List<EvidencedValue> texts = createEvidenceValuesWithoutEvidences();
        buildFreeTextComment(CommentType.COFACTOR, texts);
    }

    private List<EvidencedValue> createEvidenceValues2() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(new EvidencedValueBuilder(
                "value1",
                asList(
                        new EvidenceBuilder()
                                .databaseId("ENSP0001324")
                                .databaseName("Ensembl")
                                .evidenceCode(EvidenceCode.ECO_0000313)
                                .build(),
                        new EvidenceBuilder()
                                .databaseId("PIRNR001361")
                                .databaseName("PIRNR")
                                .evidenceCode(EvidenceCode.ECO_0000256)
                                .build()
                )).build());
        evidencedValues.add(new EvidencedValueBuilder(
                "value2",
                singletonList(
                        new EvidenceBuilder()
                                .databaseId("ENSP0001324")
                                .databaseName("Ensembl")
                                .evidenceCode(EvidenceCode.ECO_0000313)
                                .build()
                )).build());
        return evidencedValues;
    }
}
