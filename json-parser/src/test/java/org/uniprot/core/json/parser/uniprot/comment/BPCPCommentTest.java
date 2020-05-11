package org.uniprot.core.json.parser.uniprot.comment;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.JsonNode;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.uniprot.CreateUtils;
import org.uniprot.core.uniprotkb.comment.*;
import org.uniprot.core.uniprotkb.comment.impl.*;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidencedValue;

import java.util.List;

/** @author lgonzales */
public class BPCPCommentTest {

    @Test
    void testBPCPCommentSimple() {

        BPCPComment comment = new BPCPCommentBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("BIOPHYSICOCHEMICAL PROPERTIES", jsonNode.get("commentType").asText());
    }

    @Test
    void testBPCPCommentComplete() {
        BPCPComment comment = getBpcpComment();

        ValidateJson.verifyJsonRoundTripParser(comment);
        ValidateJson.verifyEmptyFields(comment);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(comment);
        assertNotNull(jsonNode.get("commentType"));
        assertEquals("BIOPHYSICOCHEMICAL PROPERTIES", jsonNode.get("commentType").asText());

        assertNotNull(jsonNode.get("molecule"));
        assertEquals("Isoform 3", jsonNode.get("molecule").asText());

        assertNotNull(jsonNode.get("absorption"));
        JsonNode absorption = jsonNode.get("absorption");
        assertNotNull(absorption.get("max"));
        assertEquals(10, absorption.get("max").asInt());
        assertNotNull(absorption.get("approximate"));
        assertTrue(absorption.get("approximate").asBoolean());
        assertNotNull(absorption.get("note"));
        assertNotNull(absorption.get("note").get("texts"));
        assertEquals(1, absorption.get("note").get("texts").size());
        JsonNode valueEvidence = absorption.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(
                valueEvidence, "value1", "ECO:0000255", "PROSITE-ProRule", "PRU10028");
        assertNotNull(absorption.get("evidences"));
        assertEquals(1, absorption.get("evidences").size());
        ValidateJson.validateEvidence(
                absorption.get("evidences").get(0), "ECO:0000255", "PROSITE-ProRule", "PRU10028");

        assertNotNull(jsonNode.get("kineticParameters"));
        JsonNode kineticParameters = jsonNode.get("kineticParameters");
        assertNotNull(kineticParameters.get("maximumVelocities"));
        assertEquals(1, kineticParameters.get("maximumVelocities").size());

        JsonNode maximumVelocities = kineticParameters.get("maximumVelocities").get(0);
        assertNotNull(maximumVelocities.get("velocity"));
        assertEquals(1.0d, maximumVelocities.get("velocity").asDouble(), 0.0000001d);
        assertNotNull(maximumVelocities.get("unit"));
        assertEquals("unit1", maximumVelocities.get("unit").asText());
        assertNotNull(maximumVelocities.get("enzyme"));
        assertEquals("enzyme1", maximumVelocities.get("enzyme").asText());
        assertNotNull(maximumVelocities.get("evidences"));
        assertEquals(1, maximumVelocities.get("evidences").size());
        ValidateJson.validateEvidence(
                maximumVelocities.get("evidences").get(0),
                "ECO:0000255",
                "PROSITE-ProRule",
                "PRU10028");

        JsonNode michaelisConstants = kineticParameters.get("michaelisConstants").get(0);
        assertNotNull(michaelisConstants.get("constant"));
        assertEquals(2.0999999046325684, michaelisConstants.get("constant").asDouble(), 0.0000001d);
        assertNotNull(michaelisConstants.get("unit"));
        assertEquals("uM", michaelisConstants.get("unit").asText());
        assertNotNull(michaelisConstants.get("substrate"));
        assertEquals("sub1", michaelisConstants.get("substrate").asText());
        assertNotNull(michaelisConstants.get("evidences"));
        assertEquals(1, michaelisConstants.get("evidences").size());
        ValidateJson.validateEvidence(
                michaelisConstants.get("evidences").get(0),
                "ECO:0000255",
                "PROSITE-ProRule",
                "PRU10028");

        assertNotNull(kineticParameters.get("note"));
        assertNotNull(kineticParameters.get("note").get("texts"));
        assertEquals(1, kineticParameters.get("note").get("texts").size());
        JsonNode kinectEvidence = kineticParameters.get("note").get("texts").get(0);
        ValidateJson.validateValueEvidence(
                kinectEvidence, "value1", "ECO:0000255", "PROSITE-ProRule", "PRU10028");

        assertNotNull(jsonNode.get("phDependence"));
        JsonNode phDependence = jsonNode.get("phDependence");
        assertNotNull(phDependence.get("texts"));
        assertEquals(1, phDependence.get("texts").size());
        JsonNode phDependenceEvidence = phDependence.get("texts").get(0);
        ValidateJson.validateValueEvidence(
                phDependenceEvidence, "value1", "ECO:0000255", "PROSITE-ProRule", "PRU10028");

        assertNotNull(jsonNode.get("redoxPotential"));
        JsonNode redoxPotential = jsonNode.get("redoxPotential");
        assertNotNull(redoxPotential.get("texts"));
        assertEquals(1, redoxPotential.get("texts").size());
        JsonNode redoxPotentialEvidence = redoxPotential.get("texts").get(0);
        ValidateJson.validateValueEvidence(
                redoxPotentialEvidence, "value1", "ECO:0000255", "PROSITE-ProRule", "PRU10028");

        assertNotNull(jsonNode.get("temperatureDependence"));
        JsonNode temperatureDependence = jsonNode.get("temperatureDependence");
        assertNotNull(temperatureDependence.get("texts"));
        assertEquals(1, temperatureDependence.get("texts").size());
        JsonNode temperatureDependenceEvidence = temperatureDependence.get("texts").get(0);
        ValidateJson.validateValueEvidence(
                temperatureDependenceEvidence,
                "value1",
                "ECO:0000255",
                "PROSITE-ProRule",
                "PRU10028");
    }

    public static BPCPComment getBpcpComment() {
        List<Evidence> evidences =
                CreateUtils.createEvidenceList("ECO:0000255|PROSITE-ProRule:PRU10028");
        List<EvidencedValue> texts =
                CreateUtils.createEvidencedValueList(
                        "value1", "ECO:0000255|PROSITE-ProRule:PRU10028");
        Note note = new NoteBuilder(texts).build();
        Absorption absorption =
                new AbsorptionBuilder()
                        .max(10)
                        .approximate(true)
                        .note(note)
                        .evidencesSet(evidences)
                        .build();
        MaximumVelocity velocity =
                new MaximumVelocityBuilder()
                        .velocity(1.0F)
                        .unit("unit1")
                        .enzyme("enzyme1")
                        .evidencesSet(evidences)
                        .build();
        MichaelisConstant mConstant =
                new MichaelisConstantBuilder()
                        .constant(2.1F)
                        .unit(MichaelisConstantUnit.MICRO_MOL)
                        .substrate("sub1")
                        .evidencesSet(evidences)
                        .build();
        KineticParameters kp =
                new KineticParametersBuilder()
                        .maximumVelocitiesAdd(velocity)
                        .michaelisConstantsAdd(mConstant)
                        .note(note)
                        .build();
        return new BPCPCommentBuilder()
                .molecule("Isoform 3")
                .absorption(absorption)
                .kineticParameters(kp)
                .phDependence(new PhDependenceBuilder(texts).build())
                .redoxPotential(new RedoxPotentialBuilder(texts).build())
                .temperatureDependence(new TemperatureDependenceBuilder(texts).build())
                .build();
    }
}
