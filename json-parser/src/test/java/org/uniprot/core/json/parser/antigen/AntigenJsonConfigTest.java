package org.uniprot.core.json.parser.antigen;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.uniprot.core.CrossReference;
import org.uniprot.core.PositionModifier;
import org.uniprot.core.antigen.AntigenDatabase;
import org.uniprot.core.antigen.AntigenEntry;
import org.uniprot.core.antigen.AntigenFeature;
import org.uniprot.core.antigen.AntigenFeatureType;
import org.uniprot.core.antigen.impl.AntigenEntryBuilder;
import org.uniprot.core.antigen.impl.AntigenFeatureBuilder;
import org.uniprot.core.uniprotkb.feature.AlternativeSequence;
import org.uniprot.core.feature.FeatureLocation;
import org.uniprot.core.uniprotkb.feature.impl.AlternativeSequenceBuilder;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.OrganimsTest;
import org.uniprot.core.json.parser.uniprot.SequenceTest;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 12/05/2020
 */
class AntigenJsonConfigTest {

    private static final Logger logger = LoggerFactory.getLogger(AntigenJsonConfigTest.class);

    @Test
    void testSimpleAntigenEntry() {
        AntigenEntryBuilder builder = new AntigenEntryBuilder();

        AntigenEntry antigenEntry = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                AntigenJsonConfig.getInstance().getFullObjectMapper(), antigenEntry);
    }

    @Test
    void testCompleteAntigenEntry() {
        AntigenEntry antigenEntry = getCompleteAntigenEntry();
        ValidateJson.verifyJsonRoundTripParser(
                AntigenJsonConfig.getInstance().getFullObjectMapper(), antigenEntry);
        ValidateJson.verifyEmptyFields(antigenEntry);
    }

    @Test
    void testCompleteAntigenSimpleMapper() {
        AntigenEntry antigenEntry = getCompleteAntigenEntry();
        ValidateJson.verifyEmptyFields(antigenEntry);
        try {
            ObjectMapper mapper = AntigenJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(antigenEntry);
            logger.debug(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    private AntigenEntry getCompleteAntigenEntry() {
        return new AntigenEntryBuilder()
                .primaryAccession("P21802")
                .uniProtkbId("P21802_HUMAN")
                .sequence(SequenceTest.getSequence())
                .organism(OrganimsTest.getOrganism())
                .featuresAdd(getCompleteAntigenFeature())
                .sequence(new SequenceBuilder("AAAAAAAAAA").build())
                .build();
    }

    private AntigenFeature getCompleteAntigenFeature() {
        FeatureLocation location = new FeatureLocation(
                "AAAAA", 2, 8, PositionModifier.OUTSIDE, PositionModifier.OUTSIDE);

        CrossReference<AntigenDatabase> crossReference = new CrossReferenceBuilder<AntigenDatabase>()
                .database(AntigenDatabase.ENSEMBL)
                .id("ENST00000346997")
                .propertiesAdd("GeneId", "ENSP00000263451")
                .propertiesAdd("ProteinId", "ENSG00000066468")
                .build();

        Evidence evidence = new EvidenceBuilder()
                .databaseName("HPA")
                .databaseId("HPA12345")
                .evidenceCode(EvidenceCode.ECO_0000255)
                .build();

        return new AntigenFeatureBuilder()
                .type(AntigenFeatureType.ANTIGEN)
                .description("Description value")
                .featureCrossReference(crossReference)
                .location(location)
                .evidencesAdd(evidence)
                .matchScore(98)
                .antigenSequence("AAAAA")
                .build();
    }

}