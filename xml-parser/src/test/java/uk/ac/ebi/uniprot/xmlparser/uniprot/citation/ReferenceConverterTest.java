package uk.ac.ebi.uniprot.xmlparser.uniprot.citation;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.citation.*;
import uk.ac.ebi.uniprot.domain.citation.builder.BookBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.JournalArticleBuilder;
import uk.ac.ebi.uniprot.domain.citation.builder.SubmissionBuilder;
import uk.ac.ebi.uniprot.domain.impl.DBCrossReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.builder.ReferenceCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtReferenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidenceCode;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.builder.EvidenceBuilder;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.ReferenceType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

class ReferenceConverterTest {

    @Test
    void testSubmission() {
        Submission submission = createSubmission();

        List<String> referencePositions = Arrays.asList("Some position");
        List<Evidence> evidences = createEvidences();
        List<ReferenceComment> refComments = new ArrayList<>();
        refComments.add(
                createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
        refComments.add(
                createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));
        UniProtReference uniReference = createUniProtReference(submission,
                                                               referencePositions, refComments, Collections
                                                                       .emptyList());
        ReferenceConverter converter = new ReferenceConverter(new EvidenceIndexMapper());
        ReferenceType xmlReference = converter.toXml(uniReference);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlReference, ReferenceType.class, "reference"));
        UniProtReference converted = converter.fromXml(xmlReference);
        assertEquals(uniReference, converted);

    }

    private UniProtReference createUniProtReference(Citation citation, List<String> referencePositions, List<ReferenceComment> refComments, List<Evidence> evidences) {
        return new UniProtReferenceBuilder()
                .comments(refComments)
                .citation(citation)
                .positions(referencePositions)
                .evidences(evidences)
                .build();
    }

    private ReferenceComment createReferenceComment(ReferenceCommentType commentType, String value, List<Evidence> evidences) {
        return new ReferenceCommentBuilder()
                .type(commentType)
                .value(value)
                .evidences(evidences)
                .build();
    }

    @Test
    void testJournalArticle() {
        JournalArticle citation = createJournalArticle();

        List<String> referencePositions = Arrays.asList("Some position");
        List<Evidence> evidences = createEvidences();
        List<ReferenceComment> refComments = new ArrayList<>();
        refComments.add(
                createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
        refComments.add(
                createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));
        UniProtReference uniReference = createUniProtReference(citation,
                                                               referencePositions, refComments, evidences);
        ReferenceConverter converter = new ReferenceConverter(new EvidenceIndexMapper());
        ReferenceType xmlReference = converter.toXml(uniReference);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlReference, ReferenceType.class, "reference"));
        UniProtReference converted = converter.fromXml(xmlReference);
        assertEquals(uniReference, converted);

    }

    @Test
    void testBook() {
        Book citation = createBook();

        List<String> referencePositions = Arrays.asList("Some position");
        List<Evidence> evidences = createEvidences();
        List<ReferenceComment> refComments = new ArrayList<>();
//		refComments.add(
//				createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
//		refComments.add(
//				createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));
        UniProtReference uniReference = createUniProtReference(citation,
                                                               referencePositions, refComments, evidences);
        ReferenceConverter converter = new ReferenceConverter(new EvidenceIndexMapper());
        ReferenceType xmlReference = converter.toXml(uniReference);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlReference, ReferenceType.class, "reference"));
        UniProtReference converted = converter.fromXml(xmlReference);
        assertEquals(uniReference, converted);

    }

    private Submission createSubmission() {
        SubmissionBuilder builder = new SubmissionBuilder();
        String title = "Protein research";
        String date = "JAN-2018";
        builder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ)
                .title(title)
                .addAuthor("Sulson J.E.")
                .addAuthor("JWaterston R.")
                .publicationDate(date);
        return builder.build();
    }

    private JournalArticle createJournalArticle() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        String title = "Some article title";
        String date = "2008";
        String journalName = "Nature";
        builder.journalName(journalName).firstPage("213").lastPage("223").volume("2")
                .title(title)
                .addAuthor("Sulson J.E.")
                .addAuthor("JWaterston R.")
                .publicationDate(date)
                .authoringGroups(Arrays.asList("The C. elegans sequencing consortium"))
                .citationXrefs(Arrays.asList(new DBCrossReferenceImpl<>(CitationXrefType.PUBMED, "9851916"),
                                             new DBCrossReferenceImpl<>(CitationXrefType.DOI,
                                                                        "https://doi.org/10.1126/science.282.5396.2012")));
        JournalArticle citation = builder.build();
        return citation;
    }

    private Book createBook() {
        BookBuilder builder = new BookBuilder();
        String bookName = "Some book name";
        String title = "Some article title";
        String date = "2009";
        builder.bookName(bookName)
                .addEditor("David")
                .addEditor("Charlie")
                .firstPage("234").lastPage("324C").volume("3").publisher("London Press").address("London").title(title)
                .publicationDate(date);
        Book book = builder.build();
        return book;
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(new EvidenceBuilder()
                              .databaseId("PRU10028")
                              .databaseName("PROSITE-ProRule")
                              .evidenceCode(EvidenceCode.ECO_0000255)
                              .build());
        evidences.add(new EvidenceBuilder()
                              .databaseId("PIRNR001361")
                              .databaseName("PIRNR")
                              .evidenceCode(EvidenceCode.ECO_0000256)
                              .build());
        return evidences;
    }
}
