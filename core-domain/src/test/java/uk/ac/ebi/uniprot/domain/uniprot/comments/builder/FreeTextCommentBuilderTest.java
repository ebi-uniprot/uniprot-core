package uk.ac.ebi.uniprot.domain.uniprot.comments.builder;

import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comments.AllergenComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.BiotechnologyComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DevelopmentalStageComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DisruptionPhenotypeComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.DomainComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.EnzymeRegulationComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.FunctionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.InductionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.MiscellaneousComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PathwayComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PharmaceuticalComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PolymorphismComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.PtmComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SimilarityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.SubunitComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.TissueSpecificityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comments.ToxicDoseComment;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FreeTextCommentBuilderTest {

    @Test
    public void testBuildAllergenComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        AllergenComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.ALLERGEN, texts);
        assertEquals(CommentType.ALLERGEN, comment.getCommentType());
    }

    @Test
    public void testBuildBiotechnologyComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        BiotechnologyComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.BIOTECHNOLOGY, texts);
        assertEquals(CommentType.BIOTECHNOLOGY, comment.getCommentType());
    }
    
    @Test
    public void testBuildCatalyticActivityComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        CatalyticActivityComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.CATALYTIC_ACTIVITY, texts);
        assertEquals(CommentType.CATALYTIC_ACTIVITY, comment.getCommentType());
    }
    
    @Test
    public void testBuildCautionComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        CautionComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.CAUTION, texts);
        assertEquals(CommentType.CAUTION, comment.getCommentType());
    }
    
    @Test
    public void testBuildDevelopmentalStageComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        DevelopmentalStageComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.DEVELOPMENTAL_STAGE, texts);
        assertEquals(CommentType.DEVELOPMENTAL_STAGE, comment.getCommentType());
    }
    
    @Test
    public void testBuildDisruptionPhenotypeComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        DisruptionPhenotypeComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.DISRUPTION_PHENOTYPE, texts);
        assertEquals(CommentType.DISRUPTION_PHENOTYPE, comment.getCommentType());
    }
    @Test
    public void testBuildDomainComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        DomainComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.DOMAIN, texts);
        assertEquals(CommentType.DOMAIN, comment.getCommentType());
    }
    
    @Test
    public void testBuildEnzymeRegulationComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        EnzymeRegulationComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.ENZYME_REGULATION, texts);
        assertEquals(CommentType.ENZYME_REGULATION, comment.getCommentType());
    }
    
    @Test
    public void testBuildFunctionComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FunctionComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.FUNCTION, texts);
        assertEquals(CommentType.FUNCTION, comment.getCommentType());
    }
    
    @Test
    public void testBuildInductionComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        InductionComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.INDUCTION, texts);
        assertEquals(CommentType.INDUCTION, comment.getCommentType());
    }
    
    @Test
    public void testBuildMiscellaneousComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        MiscellaneousComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.MISCELLANEOUS, texts);
        assertEquals(CommentType.MISCELLANEOUS, comment.getCommentType());
    }
    
    @Test
    public void testBuildPathwayComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        PathwayComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.PATHWAY, texts);
        assertEquals(CommentType.PATHWAY, comment.getCommentType());
    }
    
    @Test
    public void testBuildPharmaceuticalComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        PharmaceuticalComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.PHARMACEUTICAL, texts);
        assertEquals(CommentType.PHARMACEUTICAL, comment.getCommentType());
    }
    
    @Test
    public void testBuildPolymorphismComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        PolymorphismComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.POLYMORPHISM, texts);
        assertEquals(CommentType.POLYMORPHISM, comment.getCommentType());
    }
    
    @Test
    public void testBuildPtmComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        PtmComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.PTM, texts);
        assertEquals(CommentType.PTM, comment.getCommentType());
    }
    
    @Test
    public void testBuildSimiarityComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        SimilarityComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.SIMILARITY, texts);
        assertEquals(CommentType.SIMILARITY, comment.getCommentType());
    }
    
    @Test
    public void testBuildSubunitComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        SubunitComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.SUBUNIT, texts);
        assertEquals(CommentType.SUBUNIT, comment.getCommentType());
    }
    
    @Test
    public void testBuildTissueSpecificityComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        TissueSpecificityComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.TISSUE_SPECIFICITY, texts);
        assertEquals(CommentType.TISSUE_SPECIFICITY, comment.getCommentType());
    }
    
    @Test
    public void testBuildToxicDoseComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        ToxicDoseComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.TOXIC_DOSE, texts);
        assertEquals(CommentType.TOXIC_DOSE, comment.getCommentType());
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testBuildNoneFreeTextComment() {
        List<EvidencedValue> texts= createEvidenceValues();
       FreeTextCommentBuilder.buildFreeTextComment(CommentType.COFACTOR, texts);
      
    }
    
    private List<EvidencedValue> createEvidenceValues(){
        List<EvidencedValue>  evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.createEvidencedValue("value2", Collections.emptyList()));
     return evidencedValues;   
    }
}
