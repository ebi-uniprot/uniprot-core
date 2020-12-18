package org.uniprot.core.json.parser.publication;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.publication.MappedPublications;
import org.uniprot.core.publication.impl.MappedPublicationsBuilder;

import static org.uniprot.core.json.parser.publication.CommunityMappedReferenceTest.getCompleteCommunityMappedReference;
import static org.uniprot.core.json.parser.publication.ComputationallyMappedReferenceTest.getCompleteComputationallyMappedReference;
import static org.uniprot.core.json.parser.publication.UniProtKBMappedReferenceTest.getCompleteUniProtKBMappedReference;

/**
 * @author sahmad
 * @created 18/12/2020
 */
public class MappedPublicationsJsonConfigTest {
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
                MappedPublicationsJsonConfig.getInstance().getFullObjectMapper(), mappedPublications);
        ValidateJson.verifyEmptyFields(mappedPublications);
    }

    static MappedPublications getCompleteMappedPublications() {
        MappedPublicationsBuilder builder = new MappedPublicationsBuilder();
        builder.unreviewedMappedReference(getCompleteUniProtKBMappedReference());
        builder.reviewedMappedReference(getCompleteUniProtKBMappedReference());
        builder.computationalMappedReferencesAdd(getCompleteComputationallyMappedReference());
        builder.communityMappedReferencesAdd(getCompleteCommunityMappedReference());
        return builder.build();
    }
}
