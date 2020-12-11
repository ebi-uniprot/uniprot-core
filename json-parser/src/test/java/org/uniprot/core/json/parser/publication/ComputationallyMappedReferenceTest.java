package org.uniprot.core.json.parser.publication;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.publication.ComputationallyMappedReference;
import org.uniprot.core.publication.MappedSource;
import org.uniprot.core.publication.impl.ComputationallyMappedReferenceBuilder;
import org.uniprot.core.publication.impl.MappedSourceBuilder;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * @author sahmad
 */
class ComputationallyMappedReferenceTest {

    @Test
    void testSimpleCommunityMappedReference() {
        ComputationallyMappedReferenceBuilder builder = new ComputationallyMappedReferenceBuilder();
        ComputationallyMappedReference computationallyMappedReference = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                ComputationallyMappedReferenceJsonConfig.getInstance().getFullObjectMapper(), computationallyMappedReference);
    }

    @Test
    void testCompleteCommunityMappedReference() {
        ComputationallyMappedReference computationallyMappedReference = getCompleteComputationallyMappedReference();
        ValidateJson.verifyJsonRoundTripParser(
                ComputationallyMappedReferenceJsonConfig.getInstance().getFullObjectMapper(), computationallyMappedReference);
        ValidateJson.verifyEmptyFields(computationallyMappedReference);
    }

    static ComputationallyMappedReference getCompleteComputationallyMappedReference() {
        UniProtKBAccession accession = new UniProtKBAccessionBuilder("P12345").build();
        MappedSource mappedSource = new MappedSourceBuilder().source("src").sourceId("srcId").build();
        String pubMedId = "12345";
        Set<String> cats = new HashSet<>();
        cats.add("cat1");cats.add("cat2");
        ComputationallyMappedReferenceBuilder builder = new ComputationallyMappedReferenceBuilder();
        builder.annotation("annotation");
        builder.uniProtKBAccession(accession).source(mappedSource);
        builder.pubMedId(pubMedId).sourceCategoriesSet(cats);
        return builder.build();
    }
}
