package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.InteractionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class InteractionConverterTest {

	@Test
	void testBinary() {
		//Q96NT3:GUCD1; NbExp=3; IntAct=EBI-749763, EBI-8293751;
		InteractionBuilder builder = InteractionBuilder.newInstance();
		  Interaction interaction =builder.interactionType(InteractionType.BINARY)
	                .geneName("GUCD1")
	                .numberOfExperiments(3)
	                .firstInteractor(InteractionBuilder.createInteractor("EBI-749763"))
	                .secondInteractor(InteractionBuilder.createInteractor("EBI-8293751"))
	                .uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession("Q96NT3"))
	                .build();
		  InteractionConverter converter = new InteractionConverter();
		  CommentType xmlComment = converter.toXml(interaction);
		  System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		  Interaction converted = converter.fromXml(xmlComment);
		  assertEquals(interaction, converted);
	}
	@Test
	void testSelf() {
		//Self; NbExp=4; IntAct=EBI-749763, EBI-749763;
		InteractionBuilder builder = InteractionBuilder.newInstance();
		  Interaction interaction =builder.interactionType(InteractionType.SELF)

	                .numberOfExperiments(4)
	                .firstInteractor(InteractionBuilder.createInteractor("EBI-749763"))
	                .secondInteractor(InteractionBuilder.createInteractor("EBI-749763"))
	                .build();
		  InteractionConverter converter = new InteractionConverter();
		  CommentType xmlComment = converter.toXml(interaction);
		  System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		  Interaction converted = converter.fromXml(xmlComment);
		  assertEquals(interaction, converted);
	}
	@Test
	void testXENO() {
		//P05845:tnsE (xeno); NbExp=4; IntAct=EBI-542385, EBI-2434514;
		InteractionBuilder builder = InteractionBuilder.newInstance();
		  Interaction interaction =builder.interactionType(InteractionType.XENO)
	                .geneName("tnsE")
	                .numberOfExperiments(4)
	                .firstInteractor(InteractionBuilder.createInteractor("EBI-542385"))
	                .secondInteractor(InteractionBuilder.createInteractor("EBI-2434514"))
	                .uniProtAccession(UniProtFactory.INSTANCE.createUniProtAccession("P05845"))
	                .build();
		  InteractionConverter converter = new InteractionConverter();
		  CommentType xmlComment = converter.toXml(interaction);
		  System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		  Interaction converted = converter.fromXml(xmlComment);
		  assertEquals(interaction, converted);
	}
	
	
}
