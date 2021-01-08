package org.uniprot.core.json.parser.publication;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.citation.CitationDatabase;
import org.uniprot.core.citation.Submission;
import org.uniprot.core.citation.SubmissionDatabase;
import org.uniprot.core.citation.impl.AuthorBuilder;
import org.uniprot.core.citation.impl.PublicationDateBuilder;
import org.uniprot.core.citation.impl.SubmissionBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.publication.UniProtKBMappedReference;
import org.uniprot.core.publication.impl.MappedSourceBuilder;
import org.uniprot.core.publication.impl.UniProtKBMappedReferenceBuilder;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.ReferenceCommentBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author sahmad
 */
class UniProtKBMappedReferenceTest {

    @Test
    void testSimpleUniProtKBMappedReference() {
        UniProtKBMappedReferenceBuilder builder = new UniProtKBMappedReferenceBuilder();
        UniProtKBMappedReference mappedReference = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                UniProtKBMappedReferenceJsonConfig.getInstance().getFullObjectMapper(), mappedReference);
    }

    @Test
    void testCompleteUniProtMappedReference() {
        UniProtKBMappedReference mappedReference = getCompleteUniProtKBMappedReference();
        ValidateJson.verifyJsonRoundTripParser(
                UniProtKBMappedReferenceJsonConfig.getInstance().getFullObjectMapper(), mappedReference);
        ValidateJson.verifyEmptyFields(mappedReference);
    }

    static UniProtKBMappedReference getCompleteUniProtKBMappedReference() {
        SubmissionBuilder citationBuilder = new SubmissionBuilder();
        citationBuilder.submittedToDatabase(SubmissionDatabase.EMBL_GENBANK_DDBJ);
        citationBuilder.authoringGroupsAdd("sample author");
        citationBuilder.authorsAdd(new AuthorBuilder("auth name").build());
        CrossReference<CitationDatabase> xref =
                new CrossReferenceBuilder<CitationDatabase>()
                        .database(CitationDatabase.AGRICOLA)
                        .id("id2")
                        .build();
        citationBuilder.title("book title").publicationDate(new PublicationDateBuilder("12-12-20").build());
        citationBuilder.citationCrossReferencesAdd(xref);
        Submission citation = citationBuilder.build();
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
        MappedSource mappedSource = new MappedSourceBuilder().name("src").id("srcId").build();
        String pubMedId = "12345";
        Set<String> cats = new HashSet<>();
        cats.add("cat1");cats.add("cat2");
        List<ReferenceComment> comments = new ArrayList<>();
        comments.add(new ReferenceCommentBuilder().type(ReferenceCommentType.PLASMID).value("val1")
                .evidencesAdd(ObjectsForTests.createEvidence()).build());
        comments.add(new ReferenceCommentBuilder().type(ReferenceCommentType.STRAIN).value("val2")
                .evidencesAdd(ObjectsForTests.createEvidence()).build());

        UniProtKBMappedReferenceBuilder builder = new UniProtKBMappedReferenceBuilder();
        builder.uniProtKBAccession(accession).source(mappedSource);
        builder.pubMedId(pubMedId).sourceCategoriesSet(cats);
        builder.referenceCommentsSet(comments);
        builder.referencePositionsAdd("1");
        builder.referenceNumber(123);
        builder.citation(citation);
        return builder.build();
    }
}
