package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comments;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.WebResourceComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.WebResourceCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

class CommentsImplTest {

	@Test
	void testCommentsImplEmpty() {
		List<Comment> commentList = new ArrayList<>();
		Comments comments = new CommentsImpl(commentList);
		assertTrue(comments.getComments().isEmpty());
		assertTrue(comments.getCommentByType(CommentType.ALLERGEN).isEmpty());
		TestHelper.verifyJson(comments);
		
	}
	@Test
	void testCommentsImpl() {
		List<Comment> commentList = new ArrayList<>();
		commentList.add(createFreeTextComment());
	
		commentList.add(createCofactorComment());
		commentList.add(createDiseaseComment());
		commentList.add(createWebResourceComment());
		commentList.add(createInteractionComment());
		Comments comments = new CommentsImpl(commentList);
		assertEquals(5, comments.getComments().size());
		assertEquals(1, comments.getCommentByType(CommentType.ALLERGEN).size());
		TestHelper.verifyJson(comments);
		
	}
	
	private InteractionComment createInteractionComment() {
		 InteractionBuilder builder = InteractionCommentBuilder.newInteractionBuilder();
	        Interaction interaction =builder.interactionType(InteractionType.BINARY)
	                .geneName("gn22")
	                .numberOfExperiments(3)
	                .firstInteractor(InteractionBuilder.createInteractor("first1"))
	                .secondInteractor(InteractionBuilder.createInteractor("first2"))
	                .uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession("P12345"))
	                .build();
	        List<Interaction> interactions = Arrays.asList(interaction);
	        InteractionCommentBuilder commentBuilder = InteractionCommentBuilder.newInstance();
	        
	        InteractionComment comment = 
	                commentBuilder.interactions(interactions)
	                .build();
	        return comment;
	}
	private WebResourceComment createWebResourceComment() {
		  WebResourceCommentBuilder builder =  WebResourceCommentBuilder.newInstance();
	        String databaseName ="someDbName";
	        WebResourceComment comment = builder.resourceName(databaseName)
	                .build();
	        return comment;
	}
	private FreeTextComment createFreeTextComment() {
		List<EvidencedValue> texts = createEvidenceValues();
		return new FreeTextCommentImpl(CommentType.ALLERGEN, texts);
	}
	
	private CofactorComment createCofactorComment() {
		String name = "someName";
		DBCrossReference<CofactorReferenceType> reference = new DBCrossReferenceImpl<>(CofactorReferenceType.CHEBI,
				"CHEBI:324");
		Cofactor cofactor = CofactorCommentBuilder.createCofactor(name, reference, createEvidences());
		List<Cofactor> cofactors = Arrays.asList(cofactor);

		Note note = CommentFactory.INSTANCE.createNote(createEvidenceValues());
		String molecule = "Some molecule";
		CofactorComment comment = new CofactorCommentImpl (molecule, cofactors, note);
		
		return comment;
	}
	
	private DiseaseComment createDiseaseComment() {
        String description ="some description";
        List<Evidence> evidences =  createEvidences();
     
        DiseaseReferenceType referenceType = DiseaseReferenceType.MIM;
        String referenceId = "3124";
        DBCrossReference<DiseaseReferenceType> reference = new DBCrossReferenceImpl<>(referenceType, referenceId);
        String diseaseId = "someId";
        String diseaseAc = "DI-00742";
        Disease disease =new DiseaseImpl(
        		 diseaseId,diseaseAc,  "someAcron", description, reference, evidences);
    		
        Note note =new NoteImpl(createEvidenceValues());
        
        DiseaseComment comment = new DiseaseCommentImpl(disease, note);
        return comment;
	}
	private List<Evidence> createEvidences() {
		List<Evidence> evidences = new ArrayList<>();
		evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
		evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
		return evidences;
	}
	private List<EvidencedValue> createEvidenceValues() {
		List<EvidencedValue> evidencedValues = new ArrayList<>();
		evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
		evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
		return evidencedValues;
	}
	
}
