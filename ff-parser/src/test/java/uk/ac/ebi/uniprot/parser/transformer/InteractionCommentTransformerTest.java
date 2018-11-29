package uk.ac.ebi.uniprot.parser.transformer;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;



public class InteractionCommentTransformerTest {
	private final InteractionCommentTransformer transformer = new InteractionCommentTransformer();
	@Test
	public void test1(){
		String ccLineString =("INTERACTION:\n" +
				"Self; NbExp=1; IntAct=EBI-123485, EBI-123485;\n" +
				"Q9W158:CG4612; NbExp=1; IntAct=EBI-123485, EBI-89895;\n" +
				"Q9VYI0:fne; NbExp=1; IntAct=EBI-123485, EBI-126770;");
		InteractionComment comment =
		transformer.transform(CommentType.INTERACTION, ccLineString);	
		assertNotNull(comment);
		assertEquals(3, comment.getInteractions().size());
		Interaction interaction1 = comment.getInteractions().get(0);
		assertEquals(InteractionType.SELF, interaction1.getType());
		assertEquals(1, interaction1.getNumberOfExperiments());
		assertNull( interaction1.getUniProtAccession());
		assertNull(interaction1.getGeneName());
		assertEquals("EBI-123485", interaction1.getFirstInteractor().getValue());
		assertEquals("EBI-123485", interaction1.getSecondInteractor().getValue() );
		
		Interaction interaction2 = comment.getInteractions().get(1);
		assertEquals(InteractionType.BINARY, interaction2.getType());
		assertEquals(1, interaction2.getNumberOfExperiments());
		assertEquals("Q9W158", interaction2.getUniProtAccession().getValue());
		assertEquals("CG4612", interaction2.getGeneName());
		assertEquals("EBI-123485", interaction2.getFirstInteractor().getValue());
		assertEquals("EBI-89895", interaction2.getSecondInteractor().getValue() );
		
		Interaction interaction3 = comment.getInteractions().get(2);
		assertEquals(InteractionType.BINARY, interaction3.getType());
		assertEquals(1, interaction3.getNumberOfExperiments());
		assertEquals("Q9VYI0", interaction3.getUniProtAccession().getValue());
		assertEquals("fne", interaction3.getGeneName());
		assertEquals("EBI-123485", interaction3.getFirstInteractor().getValue());
		assertEquals("EBI-126770", interaction3.getSecondInteractor().getValue() );
	}
	@Test
	public void test2(){
		String ccLineString =("INTERACTION:\n" +
				"Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n" +
				"O96017:CHEK2; NbExp=4; IntAct=EBI-372428, EBI-1180783;\n" +
				"Q6ZWQ9:Myl12a (xeno); NbExp=3; IntAct=EBI-372428, EBI-8034418;");
		InteractionComment comment = 
		transformer.transform(CommentType.INTERACTION, ccLineString);	
		assertNotNull(comment);
		assertEquals(3, comment.getInteractions().size());
		Interaction interaction1 = comment.getInteractions().get(0);
		assertEquals(InteractionType.BINARY, interaction1.getType());
		assertEquals(1, interaction1.getNumberOfExperiments());
		assertEquals("Q9W1K5-1", interaction1.getUniProtAccession().getValue());
		assertEquals("CG11299", interaction1.getGeneName());
		assertEquals("EBI-133844", interaction1.getFirstInteractor().getValue());
		assertEquals("EBI-212772", interaction1.getSecondInteractor().getValue() );
		
		Interaction interaction2 = comment.getInteractions().get(1);
		assertEquals(InteractionType.BINARY, interaction2.getType());
		assertEquals(4, interaction2.getNumberOfExperiments());
		assertEquals("O96017", interaction2.getUniProtAccession().getValue());
		assertEquals("CHEK2", interaction2.getGeneName());
		assertEquals("EBI-372428", interaction2.getFirstInteractor().getValue());
		assertEquals("EBI-1180783", interaction2.getSecondInteractor().getValue() );
		
		Interaction interaction3 = comment.getInteractions().get(2);
		assertEquals(InteractionType.XENO, interaction3.getType());
		assertEquals(3, interaction3.getNumberOfExperiments());
		assertEquals("Q6ZWQ9", interaction3.getUniProtAccession().getValue());
		assertEquals("Myl12a", interaction3.getGeneName());
		assertEquals("EBI-372428", interaction3.getFirstInteractor().getValue());
		assertEquals("EBI-8034418", interaction3.getSecondInteractor().getValue() );
	}
	
	@Test
	public void test3(){
		String ccLineString =(
				"Q9W1K5-1:CG11299; NbExp=1; IntAct=EBI-133844, EBI-212772;\n" +
				"O96017:CHEK2; NbExp=4; IntAct=EBI-372428, EBI-1180783;\n" +
				"Q6ZWQ9:Myl12a (xeno); NbExp=3; IntAct=EBI-372428, EBI-8034418;");
		InteractionComment comment = 
		transformer.transform(CommentType.INTERACTION, ccLineString);	
		assertNotNull(comment);
		assertEquals(3, comment.getInteractions().size());
		Interaction interaction1 = comment.getInteractions().get(0);
		assertEquals(InteractionType.BINARY, interaction1.getType());
		assertEquals(1, interaction1.getNumberOfExperiments());
		assertEquals("Q9W1K5-1", interaction1.getUniProtAccession().getValue());
		assertEquals("CG11299", interaction1.getGeneName());
		assertEquals("EBI-133844", interaction1.getFirstInteractor().getValue());
		assertEquals("EBI-212772", interaction1.getSecondInteractor().getValue() );
		
		Interaction interaction2 = comment.getInteractions().get(1);
		assertEquals(InteractionType.BINARY, interaction2.getType());
		assertEquals(4, interaction2.getNumberOfExperiments());
		assertEquals("O96017", interaction2.getUniProtAccession().getValue());
		assertEquals("CHEK2", interaction2.getGeneName());
		assertEquals("EBI-372428", interaction2.getFirstInteractor().getValue());
		assertEquals("EBI-1180783", interaction2.getSecondInteractor().getValue() );
		
		Interaction interaction3 = comment.getInteractions().get(2);
		assertEquals(InteractionType.XENO, interaction3.getType());
		assertEquals(3, interaction3.getNumberOfExperiments());
		assertEquals("Q6ZWQ9", interaction3.getUniProtAccession().getValue());
		assertEquals("Myl12a", interaction3.getGeneName());
		assertEquals("EBI-372428", interaction3.getFirstInteractor().getValue());
		assertEquals("EBI-8034418", interaction3.getSecondInteractor().getValue() );
	}
}
