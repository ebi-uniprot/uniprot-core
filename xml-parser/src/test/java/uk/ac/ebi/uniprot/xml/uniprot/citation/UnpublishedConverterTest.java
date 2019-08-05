package uk.ac.ebi.uniprot.xml.uniprot.citation;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.Unpublished;
import org.uniprot.core.citation.builder.UnpublishedBuilder;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.citation.CitationConverter;
import uk.ac.ebi.uniprot.xml.uniprot.citation.UnpublishedConverter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UnpublishedConverterTest {

    @Test
    void test() {
        Unpublished citation = create();
        UnpublishedConverter converter = new UnpublishedConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Unpublished converted = converter.fromXml(xmlCitation);
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

    private Unpublished create() {
        UnpublishedBuilder builder = new UnpublishedBuilder();
        String title = "Some title";
        builder.title(title).publicationDate("MAY-2005");
        return builder.build();
    }
}
