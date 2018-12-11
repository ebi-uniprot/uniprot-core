package uk.ac.ebi.uniprot.domain.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InteractionCommentImplTest {

	@Test
	void testInteractionCommentImpl() {
		List<Interaction> interactions = new ArrayList<>();
		 InteractionBuilder builder = InteractionBuilder.newInstance();
	        Interaction interaction =builder.interactionType(InteractionType.BINARY)
	                .geneName("gn22")
	                .numberOfExperiments(3)
	                .firstInteractor(InteractionBuilder.createInteractor("first1"))
	                .secondInteractor(InteractionBuilder.createInteractor("first2"))
	                .uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession("P12345"))
	                .build();
	        interactions.add(interaction);
	        Interaction interaction2 = new InteractionImpl(	InteractionType.SELF, 
	        		null, "some gene name", 12, 
	        		InteractionBuilder.createInteractor("first 1"),
	        		InteractionBuilder.createInteractor("second 2"));
	       interactions.add(interaction2);
	       
	       InteractionComment comment =new  InteractionCommentImpl(interactions);
	       assertEquals(interactions, comment.getInteractions());
	       assertEquals(CommentType.INTERACTION, comment.getCommentType());
	       TestHelper.verifyJson(comment);
	        
	}

}
