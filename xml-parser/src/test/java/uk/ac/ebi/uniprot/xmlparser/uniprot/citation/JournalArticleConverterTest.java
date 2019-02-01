package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.JournalArticle;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CitationType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JournalArticleConverterTest {

    @Test
    void test() {

        JournalArticle citation = create();
        JournalArticleConverter converter = new JournalArticleConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        JournalArticle converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }

    @Test
    void testCitationConverter() {

        JournalArticle citation = create();
        CitationConverter converter = new CitationConverter();
        CitationType xmlCitation = converter.toXml(citation);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlCitation, CitationType.class, "citation"));
        Citation converted = converter.fromXml(xmlCitation);
        assertEquals(citation, converted);
    }


    private JournalArticle create() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        String title = "Some article title";
        String date = "2008";
        String journalName = "Nature";
        builder.journalName(journalName).firstPage("213").lastPage("223").volume("2").title(title)
                .publicationDate(date)
                .addAuthor("Sulson J.E.")
                .addAuthor("JWaterston R.")
                .authoringGroups(Arrays.asList("The C. elegans sequencing consortium"))
                .citationXrefs(Arrays.asList(new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "9851916"),
                                             new DBCrossReferenceImpl<>(CitationXrefType.DOI,
                                                                        "https://doi.org/10.1126/science.282.5396.2012")));
        JournalArticle citation = builder.build();
        return citation;
    }

}
