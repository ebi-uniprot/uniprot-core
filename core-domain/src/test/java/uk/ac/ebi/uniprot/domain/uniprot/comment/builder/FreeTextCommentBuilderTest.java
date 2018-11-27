package uk.ac.ebi.uniprot.domain.uniprot.comment.builder;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.EvidenceImpl;

public class FreeTextCommentBuilderTest {
   
    @Test
    public void testBuildAllergenComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.ALLERGEN, texts);
        assertEquals(CommentType.ALLERGEN, comment.getCommentType());
        assertEquals(texts, comment.getTexts());
        TestHelper.verifyJson(comment);
    }

    @Test
    public void testBuildBiotechnologyComment() {
        List<EvidencedValue> texts= createEvidenceValues2();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.BIOTECHNOLOGY, texts);
        assertEquals(CommentType.BIOTECHNOLOGY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildCatalyticActivityComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.CATALYTIC_ACTIVITY, texts);
        assertEquals(CommentType.CATALYTIC_ACTIVITY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildCautionComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.CAUTION, texts);
        assertEquals(CommentType.CAUTION, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildDevelopmentalStageComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.DEVELOPMENTAL_STAGE, texts);
        assertEquals(CommentType.DEVELOPMENTAL_STAGE, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildDisruptionPhenotypeComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.DISRUPTION_PHENOTYPE, texts);
        assertEquals(CommentType.DISRUPTION_PHENOTYPE, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    @Test
    public void testBuildDomainComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.DOMAIN, texts);
        assertEquals(CommentType.DOMAIN, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildEnzymeRegulationComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.ACTIVITY_REGULATION, texts);
        assertEquals(CommentType.ACTIVITY_REGULATION, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildFunctionComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.FUNCTION, texts);
        assertEquals(CommentType.FUNCTION, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildInductionComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.INDUCTION, texts);
        assertEquals(CommentType.INDUCTION, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildMiscellaneousComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.MISCELLANEOUS, texts);
        assertEquals(CommentType.MISCELLANEOUS, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildPathwayComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.PATHWAY, texts);
        assertEquals(CommentType.PATHWAY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildPharmaceuticalComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.PHARMACEUTICAL, texts);
        assertEquals(CommentType.PHARMACEUTICAL, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildPolymorphismComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.POLYMORPHISM, texts);
        assertEquals(CommentType.POLYMORPHISM, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildPtmComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.PTM, texts);
        assertEquals(CommentType.PTM, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildSimiarityComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.SIMILARITY, texts);
        assertEquals(CommentType.SIMILARITY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildSubunitComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.SUBUNIT, texts);
        assertEquals(CommentType.SUBUNIT, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildTissueSpecificityComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.TISSUE_SPECIFICITY, texts);
        assertEquals(CommentType.TISSUE_SPECIFICITY, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test
    public void testBuildToxicDoseComment() {
        List<EvidencedValue> texts= createEvidenceValues();
        FreeTextComment comment = FreeTextCommentBuilder.buildFreeTextComment(CommentType.TOXIC_DOSE, texts);
        assertEquals(CommentType.TOXIC_DOSE, comment.getCommentType());
        TestHelper.verifyJson(comment);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testBuildNoneFreeTextComment() {
        List<EvidencedValue> texts= createEvidenceValues();
       FreeTextCommentBuilder.buildFreeTextComment(CommentType.COFACTOR, texts);
      
    }
    
    private List<EvidencedValue> createEvidenceValues(){
        List<EvidencedValue>  evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
     return evidencedValues;   
    }
    
    private List<EvidencedValue> createEvidenceValues2(){
    	List<Evidence> evidences =new ArrayList<>();
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));
        evidences.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000256, "PIRNR", "PIRNR001361"
                ));
        
    	List<Evidence> evidences2 =new ArrayList<>();
        evidences2.add(new EvidenceImpl(
        		EvidenceCode.ECO_0000313,  "Ensembl", "ENSP0001324"
                ));

        List<EvidencedValue>  evidencedValues = new ArrayList<>();
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", evidences));
        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", evidences2));
     return evidencedValues;   
    }
}
