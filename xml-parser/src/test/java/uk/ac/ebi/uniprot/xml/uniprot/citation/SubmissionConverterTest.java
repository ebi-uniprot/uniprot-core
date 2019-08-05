package uk.ac.ebi.uniprot.xml.uniprot.citation;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.builder.SubmissionBuilder;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.citation.CitationConverter;
import uk.ac.ebi.uniprot.xml.uniprot.citation.SubmissionConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubmissionConverterTest {

    @Test
    void test() {
        Submission citation = create();
        SubmissionConverter converter = new SubmissionConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Submission converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    @Test
    void testCitationConverter() {
        Citation citation = create();
        CitationConverter converter = new CitationConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Citation converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    private Submission create() {
        SubmissionBuilder builder = new SubmissionBuilder();
        String title = "Protein research";
        String date = "JAN-2018";
        builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ).title(title)
                .addAuthor("Sulson J.E.")
                .addAuthor("JWaterston R.")
                .publicationDate(date);
        return builder.build();
    }
}
