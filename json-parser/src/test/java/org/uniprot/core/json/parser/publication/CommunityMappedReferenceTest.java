package org.uniprot.core.json.parser.publication;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.publication.CommunityMappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.publication.impl.CommunityAnnotationBuilder;
import org.uniprot.core.publication.impl.CommunityMappedReferenceBuilder;
import org.uniprot.core.publication.impl.MappedSourceBuilder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sahmad
 */
class CommunityMappedReferenceTest {

    @Test
    void testSimpleCommunityMappedReference() {
        CommunityMappedReferenceBuilder builder = new CommunityMappedReferenceBuilder();
        CommunityMappedReference communityMappedReference = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                CommunityMappedReferenceJsonConfig.getInstance().getFullObjectMapper(), communityMappedReference);
    }

    @Test
    void testCompleteCommunityMappedReference() {
        CommunityMappedReference communityMappedReference = getCompleteCommunityMappedReference();
        ValidateJson.verifyJsonRoundTripParser(
                CommunityMappedReferenceJsonConfig.getInstance().getFullObjectMapper(), communityMappedReference);
        ValidateJson.verifyEmptyFields(communityMappedReference);
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
        cats.add("cat1");cats.add("cat2");

        CommunityAnnotationBuilder communityAnnotationBuilder = new CommunityAnnotationBuilder();
        communityAnnotationBuilder.comment(comment).proteinOrGene(protOrGene).function(function).disease(disease);
        CommunityMappedReferenceBuilder builder = new CommunityMappedReferenceBuilder();
        builder.communityAnnotation(communityAnnotationBuilder.build());
        builder.uniProtKBAccession(accession).source(mappedSource);
        builder.pubMedId(pubMedId).sourceCategoriesSet(cats);
        return builder.build();
    }
}
