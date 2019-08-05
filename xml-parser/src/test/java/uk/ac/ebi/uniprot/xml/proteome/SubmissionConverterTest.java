package uk.ac.ebi.uniprot.xml.proteome;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.builder.SubmissionBuilder;

import uk.ac.ebi.uniprot.xml.jaxb.proteome.ConsortiumType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.NameListType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.PersonType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ReferenceType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.SubmissionType;

class SubmissionConverterTest {
	private ObjectFactory xmlFactory =new ObjectFactory();
	SubmissionConverter converter = new SubmissionConverter();
	ReferenceConverter referenceConverter = new ReferenceConverter();
	@Test
	void testFromXml() {
		ReferenceType reference = xmlFactory.createReferenceType();
		reference.setDate("JUL-2018");
		NameListType nameList = xmlFactory.createNameListType();
		ConsortiumType consortium = xmlFactory.createConsortiumType();
		consortium.setName("Some consortium");
		nameList.getConsortiumOrPerson().add(consortium);
		
		PersonType p1= xmlFactory.createPersonType();
		p1.setName("James");
		nameList.getConsortiumOrPerson().add(p1);
		PersonType p2= xmlFactory.createPersonType();
		p2.setName("James");
		nameList.getConsortiumOrPerson().add(p2);
		
		reference.setAuthorList(nameList);
		SubmissionType xmlSubmission = xmlFactory.createSubmissionType();
		xmlSubmission.setDb("EMBL/GenBank/DDBJ databases");
		xmlSubmission.setTitle("Some titles.");
		reference.setSubmission(xmlSubmission);
		Submission submission = converter.fromXml(reference);
		assertEquals("Some consortium", submission.getAuthoringGroup().get(0));
		assertEquals(2, submission.getAuthors().size());
		assertEquals("Some titles.", submission.getTitle());
		
		assertEquals("James", submission.getAuthors().get(0).getValue());
		assertEquals(SubmissionDatabase.EMBL_GENBANK_DDBJ, submission.getSubmissionDatabase());
		assertEquals("JUL-2018",   submission.getPublicationDate().getValue());
		Citation citation = referenceConverter.fromXml(reference);
		assertEquals(submission, citation);
	}

	@Test
	void testToXml() {
		Submission submission = create();
		ReferenceType reference =converter.toXml(submission);
		ReferenceType refConverted = referenceConverter.toXml(submission);
		assertEquals(reference, refConverted);
		Submission converted = converter.fromXml(reference);
		assertEquals(submission, converted);
	}
	 private Submission create() {
	        SubmissionBuilder builder = new SubmissionBuilder();
	        String date = "JAN-2018";
	        builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
	                .addAuthor("Sulson J.E.")
	                .addAuthor("JWaterston R.")
	                .publicationDate(date)
	                .title("another title");
	        return builder.build();
	    }
}
