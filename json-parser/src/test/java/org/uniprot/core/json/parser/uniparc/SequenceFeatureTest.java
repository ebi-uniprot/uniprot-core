package org.uniprot.core.json.parser.uniparc;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniparc.InterProGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.impl.InterProGroupBuilder;
import org.uniprot.core.uniparc.impl.SequenceFeatureBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.uniprot.core.uniparc.impl.SequenceFeatureLocationBuilder;

/**
 * @author jluo
 * @date: 24 May 2019
 */
class SequenceFeatureTest {
    @Test
    void testInterProGroup() {
        InterProGroup domain =
                new InterProGroupBuilder().id("IPR007123").name("Gelsolin-like domain").build();

        ValidateJson.verifyJsonRoundTripParser(
                UniParcJsonConfig.getInstance().getFullObjectMapper(), domain);

        try {
            ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(domain);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @Test
    void testSequenceFeature() {
        SequenceFeatureBuilder builder = new SequenceFeatureBuilder();
        builder.signatureDbType(SignatureDbType.PFAM)
                .signatureDbId("PF00626")
                .locationsAdd(new SequenceFeatureLocationBuilder().range(81, 163).alignment("81M").build())
                .locationsAdd(new SequenceFeatureLocationBuilder().range(202, 267).alignment("202M").build())
                .locationsAdd(new SequenceFeatureLocationBuilder().range(330, 398).alignment("81M").build())
                .locationsAdd(new SequenceFeatureLocationBuilder().range(586, 653).alignment("586M").build())
                .locationsAdd(new SequenceFeatureLocationBuilder().range(692, 766).alignment("81M").build())
                .interproGroup(
                        new InterProGroupBuilder()
                                .id("IPR007123")
                                .name("Gelsolin-like domain")
                                .build());
        SequenceFeature sf = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                UniParcJsonConfig.getInstance().getFullObjectMapper(), sf);

        try {
            ObjectMapper mapper = UniParcJsonConfig.getInstance().getSimpleObjectMapper();
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(sf);
            System.out.println(json);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
