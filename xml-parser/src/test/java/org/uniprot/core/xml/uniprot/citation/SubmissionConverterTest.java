package org.uniprot.core.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.impl.SubmissionBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CitationType;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

@Slf4j
class SubmissionConverterTest {

    @Test
    void test() {
        Submission citation = create();
        SubmissionConverter converter = new SubmissionConverter();
        CitationType xmlCitation = converter.toXml(citation);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Submission converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    @Test
    void testCitationConverter() {
        Citation citation = create();
        CitationConverter converter = new CitationConverter();
        CitationType xmlCitation = converter.toXml(citation);
        log.debug(
                UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Citation converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    private Submission create() {
        SubmissionBuilder builder = new SubmissionBuilder();
        String title = "Protein research";
        String date = "JAN-2018";
        builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
                .title(title)
                .authorsAdd("Sulson J.E.")
                .authorsAdd("JWaterston R.")
                .publicationDate(date);
        return builder.build();
    }
}
