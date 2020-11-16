package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.JournalArticle;
import org.uniprot.core.citation.impl.JournalArticleBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.xml.jaxb.proteome.*;

class JournalArticleConverterTest {
    private final ObjectFactory xmlFactory = new ObjectFactory();
    JournalArticleConverter converter = new JournalArticleConverter();
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
        citationType.setType(org.uniprot.core.citation.CitationType.JOURNAL_ARTICLE.getDisplayName());
        citationType.setDate("2018");
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
        citationType.setFirst("25");
        citationType.setLast("50");
        citationType.setVolume("21");
        citationType.setName("Nature");
        citationType.setTitle("Some Protein related to Variation");
        JournalArticle journalArticle = converter.fromXml(citationType);
        assertEquals("Nature", journalArticle.getJournal().getName());
        assertEquals("50", journalArticle.getLastPage());
        assertEquals("Some Protein related to Variation", journalArticle.getTitle());
        assertEquals("21", journalArticle.getVolume());
        ReferenceType referenceType = xmlFactory.createReferenceType();
        referenceType.setCitation(citationType);
        Citation converted = referenceConverter.fromXml(referenceType);
        assertEquals(journalArticle, converted);
    }

    @Test
    void testToXmlNullCitation() {
        ReferenceType converted = referenceConverter.toXml(null);
        assertNull(converted);
    }

    @Test
    void testToXml() {
        JournalArticle ja = create();
        CitationType reference = converter.toXml(ja);
        ReferenceType refConverted = referenceConverter.toXml(ja);
        assertEquals(reference, refConverted.getCitation());
        JournalArticle converted = converter.fromXml(reference);
        assertEquals(ja, converted);
    }

    private JournalArticle create() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        String title = "Some article title";
        String date = "2008";
        String journalName = "Nature";
        builder.journalName(journalName)
                .firstPage("213")
                .lastPage("223")
                .volume("2")
                .title(title)
                .publicationDate(date)
                .authorsAdd("Sulson J.E.")
                .authorsAdd("JWaterston R.")
                .authoringGroupsSet(Collections.singletonList("The C. elegans sequencing consortium"))
                .citationCrossReferencesAdd(
                        new CrossReferenceBuilder<CitationDatabase>()
                                .database(CitationDatabase.PUBMED)
                                .id("9851916")
                                .build())
                .citationCrossReferencesAdd(
                        new CrossReferenceBuilder<CitationDatabase>()
                                .database(CitationDatabase.DOI)
                                .id("https://doi.org/10.1126/science.282.5396.2012")
                                .build());
        return builder.build();
    }
}
