package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.impl.SubmissionBuilder;
import org.uniprot.core.xml.jaxb.proteome.*;

class SubmissionConverterTest {
    private ObjectFactory xmlFactory = new ObjectFactory();
    SubmissionConverter converter = new SubmissionConverter();
    ReferenceConverter referenceConverter = new ReferenceConverter();

    @Test
    void fromXmlEmptyReference() {
        ReferenceConverter converter = new ReferenceConverter();
        ObjectFactory xmlFactory = new ObjectFactory();

        ReferenceType referenceType = xmlFactory.createReferenceType();
        Citation result = converter.fromXml(referenceType);
        assertNull(result);
    }

    @Test
    void testFromXml() {
        CitationType citationType = xmlFactory.createCitationType();
        citationType.setType(org.uniprot.core.citation.CitationType.SUBMISSION.getDisplayName());
        citationType.setDate("JUL-2018");
        NameListType nameList = xmlFactory.createNameListType();
        ConsortiumType consortium = xmlFactory.createConsortiumType();
        consortium.setName("Some consortium");
        nameList.getConsortiumOrPerson().add(consortium);

        PersonType p1 = xmlFactory.createPersonType();
        p1.setName("James");
        nameList.getConsortiumOrPerson().add(p1);
        PersonType p2 = xmlFactory.createPersonType();
        p2.setName("James");
        nameList.getConsortiumOrPerson().add(p2);

        citationType.setAuthorList(nameList);
        citationType.setDb("EMBL/GenBank/DDBJ databases");
        citationType.setTitle("Some titles.");
        Submission submission = converter.fromXml(citationType);
        assertEquals("Some consortium", submission.getAuthoringGroups().get(0));
        assertEquals(2, submission.getAuthors().size());
        assertEquals("Some titles.", submission.getTitle());

        assertEquals("James", submission.getAuthors().get(0).getValue());
        assertEquals(SubmissionDatabase.EMBL_GENBANK_DDBJ, submission.getSubmissionDatabase());
        assertEquals("JUL-2018", submission.getPublicationDate().getValue());
        ReferenceType referenceType = xmlFactory.createReferenceType();
        referenceType.setCitation(citationType);
        Citation citation = referenceConverter.fromXml(referenceType);
        assertEquals(submission, citation);
    }

    @Test
    void testToXmlNullCitation() {
        ReferenceType converted = referenceConverter.toXml(null);
        assertNull(converted);
    }

    @Test
    void testToXml() {
        Submission submission = create();
        CitationType reference = converter.toXml(submission);
        ReferenceType refConverted = referenceConverter.toXml(submission);
        assertEquals(reference, refConverted.getCitation());
        Submission converted = converter.fromXml(reference);
        assertEquals(submission, converted);
    }

    private Submission create() {
        SubmissionBuilder builder = new SubmissionBuilder();
        String date = "JAN-2018";
        builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
                .authorsAdd("Sulson J.E.")
                .authorsAdd("JWaterston R.")
                .publicationDate(date)
                .title("another title");
        return builder.build();
    }
}
