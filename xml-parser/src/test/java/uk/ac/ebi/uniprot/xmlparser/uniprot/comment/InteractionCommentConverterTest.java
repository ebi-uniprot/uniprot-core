package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;

class InteractionCommentConverterTest {

	@Test
	void test() {
		InteractionBuilder builder = InteractionBuilder.newInstance();
		 Interaction interaction1 =builder.interactionType(InteractionType.SELF)

	                .numberOfExperiments(4)
	                .firstInteractor(InteractionBuilder.createInteractor("EBI-749763"))
	                .secondInteractor(InteractionBuilder.createInteractor("EBI-749763"))
	                .build();
		 
		 InteractionBuilder builder2 = InteractionBuilder.newInstance();
		  Interaction interaction2 =builder2.interactionType(InteractionType.BINARY)
	                .geneName("GUCD1")
	                .numberOfExperiments(3)
	                .firstInteractor(InteractionBuilder.createInteractor("EBI-749763"))
	                .secondInteractor(InteractionBuilder.createInteractor("EBI-8293751"))
	                .uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession("Q96NT3"))
	                .build();
		 List<Interaction> interactions = new ArrayList<>();
		 interactions.add(interaction1);
		 interactions.add(interaction2);
		 InteractionCommentBuilder commentBuilder = InteractionCommentBuilder.newInstance();
		 InteractionComment comment =
				 commentBuilder.interactions(interactions)
				 .build();
		 
		 InteractionCommentConverter converter = new InteractionCommentConverter();
		 List<CommentType> xmlComments = converter.toXml(comment);
		 InteractionComment converted = converter.fromXml(xmlComments);
		 assertEquals(comment, converted);
	}

}
