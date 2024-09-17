package org.uniprot.core.json.parser.publication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
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
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
        MappedSource mappedSource = new MappedSourceBuilder().name("src").id("srcId").build();
        String citationId = "12345";
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
        builder.citationId(citationId).sourceCategoriesSet(cats);
        builder.referenceCommentsSet(comments);
        builder.referencePositionsAdd("1");
        builder.referenceNumber(123);
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
                .disease(disease)
                .submissionDate(LocalDate.of(2024,9,16));
        CommunityMappedReferenceBuilder builder = new CommunityMappedReferenceBuilder();
        builder.communityAnnotation(communityAnnotationBuilder.build());
        builder.uniProtKBAccession(accession).source(mappedSource);
        builder.citationId(pubMedId).sourceCategoriesSet(cats);
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
        builder.citationId(pubMedId).sourceCategoriesSet(cats);
        return builder.build();
    }
}
