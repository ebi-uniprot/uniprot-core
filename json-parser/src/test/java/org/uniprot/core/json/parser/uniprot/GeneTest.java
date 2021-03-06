package org.uniprot.core.json.parser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.*;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.impl.*;

import com.fasterxml.jackson.databind.JsonNode;

/** @author lgonzales */
public class GeneTest {

    @Test
    void testGeneSimple() {
        Gene gene = createSimpleGene();
        ValidateJson.verifyJsonRoundTripParser(gene);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(gene);
        assertNotNull(jsonNode.get("geneName"));
        assertNotNull(jsonNode.get("geneName").get("value"));
        assertEquals("someGene", jsonNode.get("geneName").get("value").asText());
    }

    @Test
    void testGeneComplete() {
        Gene gene = createCompleteGene();
        ValidateJson.verifyJsonRoundTripParser(gene);
        ValidateJson.verifyEmptyFields(gene);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(gene);
        assertNotNull(jsonNode.get("geneName"));
        ValidateJson.validateValueEvidence(
                jsonNode.get("geneName"), "some Gene", "ECO:0000256", "PIRNR", "PIRNR001360");

        assertNotNull(jsonNode.get("synonyms"));
        assertEquals(1, jsonNode.get("synonyms").size());
        ValidateJson.validateValueEvidence(
                jsonNode.get("synonyms").get(0), "some Syn", "ECO:0000256", "PIRNR", "PIRNR001361");

        assertNotNull(jsonNode.get("orderedLocusNames"));
        assertEquals(1, jsonNode.get("orderedLocusNames").size());
        ValidateJson.validateValueEvidence(
                jsonNode.get("orderedLocusNames").get(0),
                "some locus",
                "ECO:0000256",
                "PIRNR",
                "PIRNR001362");

        assertNotNull(jsonNode.get("orfNames"));
        assertEquals(1, jsonNode.get("orfNames").size());
        ValidateJson.validateValueEvidence(
                jsonNode.get("orfNames").get(0), "some orf", "ECO:0000269", "PubMed", "11389730");
    }

    public static Gene createCompleteGene() {
        List<Evidence> geneNameEvidences =
                CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001360");
        GeneName geneName =
                new GeneNameBuilder().value("some Gene").evidencesSet(geneNameEvidences).build();

        List<Evidence> synEvidences =
                CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361");
        GeneNameSynonym synonym =
                new GeneNameSynonymBuilder().value("some Syn").evidencesSet(synEvidences).build();

        List<Evidence> olnNameEvidences =
                CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001362");
        OrderedLocusName olnName =
                new OrderedLocusNameBuilder()
                        .value("some locus")
                        .evidencesSet(olnNameEvidences)
                        .build();

        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000269|PubMed:11389730");
        ORFName orfName = new ORFNameBuilder().value("some orf").evidencesSet(evidences).build();

        return new GeneBuilder()
                .geneName(geneName)
                .synonymsAdd(synonym)
                .orderedLocusNamesAdd(olnName)
                .orfNamesAdd(orfName)
                .build();
    }

    private Gene createSimpleGene() {
        GeneName geneName = new GeneNameBuilder().value("someGene").build();
        return new GeneBuilder().geneName(geneName).build();
    }
}
