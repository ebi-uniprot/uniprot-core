package org.uniprot.core.xml.uniprot.citation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.*;
import org.uniprot.core.citation.impl.BookBuilder;
import org.uniprot.core.citation.impl.JournalArticleBuilder;
import org.uniprot.core.citation.impl.SubmissionBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.UniProtKBReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;
import org.uniprot.core.uniprotkb.impl.ReferenceCommentBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBReferenceBuilder;
import org.uniprot.core.xml.jaxb.uniprot.ReferenceType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class ReferenceConverterTest {

    @Test
    void testSubmission() {
        Submission submission = createSubmission();

        List<String> referencePositions = Arrays.asList("Some position");
        List<Evidence> evidences = createEvidences();
        List<ReferenceComment> refComments = new ArrayList<>();
        refComments.add(createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
        refComments.add(createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));
        UniProtKBReference uniReference =
                createUniProtReference(
                        submission, referencePositions, refComments, Collections.emptyList());
        ReferenceConverter converter = new ReferenceConverter(new EvidenceIndexMapper());
        ReferenceType xmlReference = converter.toXml(uniReference);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlReference, ReferenceType.class, "reference"));
        UniProtKBReference converted = converter.fromXml(xmlReference);
        assertEquals(uniReference, converted);
    }

    private UniProtKBReference createUniProtReference(
            Citation citation,
            List<String> referencePositions,
            List<ReferenceComment> refComments,
            List<Evidence> evidences) {
        return new UniProtKBReferenceBuilder()
                .referenceCommentsSet(refComments)
                .citation(citation)
                .referencePositionsSet(referencePositions)
                .evidencesSet(evidences)
                .build();
    }

    private ReferenceComment createReferenceComment(
            ReferenceCommentType commentType, String value, List<Evidence> evidences) {
        return new ReferenceCommentBuilder()
                .type(commentType)
                .value(value)
                .evidencesSet(evidences)
                .build();
    }

    @Test
    void testJournalArticle() {
        JournalArticle citation = createJournalArticle();

        List<String> referencePositions = Arrays.asList("Some position");
        List<Evidence> evidences = createEvidences();
        List<ReferenceComment> refComments = new ArrayList<>();
        refComments.add(createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
        refComments.add(createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));
        UniProtKBReference uniReference =
                createUniProtReference(citation, referencePositions, refComments, evidences);
        ReferenceConverter converter = new ReferenceConverter(new EvidenceIndexMapper());
        ReferenceType xmlReference = converter.toXml(uniReference);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlReference, ReferenceType.class, "reference"));
        UniProtKBReference converted = converter.fromXml(xmlReference);
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
        UniProtKBReference uniReference =
                createUniProtReference(citation, referencePositions, refComments, evidences);
        ReferenceConverter converter = new ReferenceConverter(new EvidenceIndexMapper());
        ReferenceType xmlReference = converter.toXml(uniReference);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlReference, ReferenceType.class, "reference"));
        UniProtKBReference converted = converter.fromXml(xmlReference);
        assertEquals(uniReference, converted);
    }

    private Submission createSubmission() {
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

    private JournalArticle createJournalArticle() {
        JournalArticleBuilder builder = new JournalArticleBuilder();
        String title = "Some article title";
        String date = "2008";
        String journalName = "Nature";
        builder.journalName(journalName)
                .firstPage("213")
                .lastPage("223")
                .volume("2")
                .title(title)
                .authorsAdd("Sulson J.E.")
                .authorsAdd("JWaterston R.")
                .publicationDate(date)
                .authoringGroupsSet(Arrays.asList("The C. elegans sequencing consortium"))
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
        JournalArticle citation = builder.build();
        return citation;
    }

    private Book createBook() {
        BookBuilder builder = new BookBuilder();
        String bookName = "Some book name";
        String title = "Some article title";
        String date = "2009";
        builder.bookName(bookName)
                .editorsAdd("David")
                .editorsAdd("Charlie")
                .firstPage("234")
                .lastPage("324C")
                .volume("3")
                .publisher("London Press")
                .address("London")
                .title(title)
                .publicationDate(date);
        Book book = builder.build();
        return book;
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(
                new EvidenceBuilder()
                        .databaseId("PRU10028")
                        .databaseName("PROSITE-ProRule")
                        .evidenceCode(EvidenceCode.ECO_0000255)
                        .build());
        evidences.add(
                new EvidenceBuilder()
                        .databaseId("PIRNR001361")
                        .databaseName("PIRNR")
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .build());
        return evidences;
    }
}
