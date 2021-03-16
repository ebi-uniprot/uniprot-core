package org.uniprot.core.json.parser.publication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import org.uniprot.core.publication.*;
import org.uniprot.core.publication.impl.*;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.ReferenceCommentBuilder;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/** @author sahmad Created 18/12/2020 */
class MappedPublicationsJsonConfigTest {
    @Test
    void testSimpleMappedPublications() {
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        MappedPublications mappedPubs = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                MappedPublicationsJsonConfig.getInstance().getFullObjectMapper(), mappedPubs);
    }

    @Test
    void testCompleteMappedPublications() {
        MappedPublications mappedPublications = getCompleteMappedPublications();
        ValidateJson.verifyJsonRoundTripParser(
                MappedPublicationsJsonConfig.getInstance().getFullObjectMapper(),
                mappedPublications);
        ValidateJson.verifyEmptyFields(mappedPublications);
    }

    @Test
    void testPrettyPrintedMappedPublicationsIgnoresUniProtKBAccession()
            throws JsonProcessingException {
        MappedPublications mappedPublications = getCompleteMappedPublications();
        ObjectMapper mapper = MappedPublicationsJsonConfig.getInstance().getSimpleObjectMapper();
        String json =
                mapper.writerWithDefaultPrettyPrinter().writeValueAsString(mappedPublications);

        assertThat(json, containsString("uniProtKBMappedReference"));
        assertThat(json, not(containsString("uniProtKBAccession")));
    }

    static MappedPublications getCompleteMappedPublications() {
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        builder.uniProtKBMappedReference(getCompleteUniProtKBMappedReference());
        builder.computationalMappedReferencesAdd(getCompleteComputationallyMappedReference());
        builder.communityMappedReferencesAdd(getCompleteCommunityMappedReference());
        return builder.build();
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
        citationBuilder
                .title("book title")
                .publicationDate(new PublicationDateBuilder("12-12-20").build());
        citationBuilder.citationCrossReferencesAdd(xref);
        Submission citation = citationBuilder.build();
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
        MappedSource mappedSource = new MappedSourceBuilder().name("src").id("srcId").build();
        String pubMedId = "12345";
        Set<String> cats = new HashSet<>();
        cats.add("cat1");
        cats.add("cat2");
        List<ReferenceComment> comments = new ArrayList<>();
        comments.add(
                new ReferenceCommentBuilder()
                        .type(ReferenceCommentType.PLASMID)
                        .value("val1")
                        .evidencesAdd(ObjectsForTests.createEvidence())
                        .build());
        comments.add(
                new ReferenceCommentBuilder()
                        .type(ReferenceCommentType.STRAIN)
                        .value("val2")
                        .evidencesAdd(ObjectsForTests.createEvidence())
                        .build());

        UniProtKBMappedReferenceBuilder builder = new UniProtKBMappedReferenceBuilder();
        builder.uniProtKBAccession(accession).source(mappedSource);
        builder.pubMedId(pubMedId).sourceCategoriesSet(cats);
        builder.referenceCommentsSet(comments);
        builder.referencePositionsAdd("1");
        builder.referenceNumber(123);
        builder.citation(citation);
        return builder.build();
    }

    static CommunityMappedReference getCompleteCommunityMappedReference() {
        String protOrGene = "protOrGene";
        String function = "function";
        String disease = "disease";
        String comment = "comment";
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
        MappedSource mappedSource = new MappedSourceBuilder().name("src").id("srcId").build();
        String pubMedId = "12345";
        Set<String> cats = new HashSet<>();
        cats.add("cat1");
        cats.add("cat2");

        CommunityAnnotationBuilder communityAnnotationBuilder = new CommunityAnnotationBuilder();
        communityAnnotationBuilder
                .comment(comment)
                .proteinOrGene(protOrGene)
                .function(function)
                .disease(disease);
        CommunityMappedReferenceBuilder builder = new CommunityMappedReferenceBuilder();
        builder.communityAnnotation(communityAnnotationBuilder.build());
        builder.uniProtKBAccession(accession).source(mappedSource);
        builder.pubMedId(pubMedId).sourceCategoriesSet(cats);
        return builder.build();
    }

    static ComputationallyMappedReference getCompleteComputationallyMappedReference() {
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
        MappedSource mappedSource = new MappedSourceBuilder().name("src").id("srcId").build();
        String pubMedId = "12345";
        Set<String> cats = new HashSet<>();
        cats.add("cat1");
        cats.add("cat2");
        ComputationallyMappedReferenceBuilder builder = new ComputationallyMappedReferenceBuilder();
        builder.annotation("annotation");
        builder.uniProtKBAccession(accession).source(mappedSource);
        builder.pubMedId(pubMedId).sourceCategoriesSet(cats);
        return builder.build();
    }
}
