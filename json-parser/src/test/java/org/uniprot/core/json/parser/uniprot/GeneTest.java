package org.uniprot.core.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.*;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.uniprot.builder.*;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class GeneTest {

    @Test
    void testGeneSimple() {
        Gene gene = createSimpleGene();
        ValidateJson.verifyJsonRoundTripParser(gene);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(gene);
        assertNotNull(jsonNode.get("geneName"));
        assertNotNull(jsonNode.get("geneName").get("value"));
        assertEquals("someGene",jsonNode.get("geneName").get("value").asText());
    }

    @Test
    void testGeneComplete() {
        Gene gene = createCompleteGene();
        ValidateJson.verifyJsonRoundTripParser(gene);
        ValidateJson.verifyEmptyFields(gene);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(gene);
        assertNotNull(jsonNode.get("geneName"));
        ValidateJson.validateValueEvidence(jsonNode.get("geneName"),"some Gene","ECO:0000256","PIRNR","PIRNR001360");

        assertNotNull(jsonNode.get("synonyms"));
        assertEquals(1,jsonNode.get("synonyms").size());
        ValidateJson.validateValueEvidence(jsonNode.get("synonyms").get(0),"some Syn","ECO:0000256","PIRNR","PIRNR001361");

        assertNotNull(jsonNode.get("orderedLocusNames"));
        assertEquals(1,jsonNode.get("orderedLocusNames").size());
        ValidateJson.validateValueEvidence(jsonNode.get("orderedLocusNames").get(0),"some locus","ECO:0000256","PIRNR","PIRNR001362");

        assertNotNull(jsonNode.get("orfNames"));
        assertEquals(1,jsonNode.get("orfNames").size());
        ValidateJson.validateValueEvidence(jsonNode.get("orfNames").get(0),"some orf","ECO:0000269","PubMed","11389730");
    }

    public static Gene createCompleteGene(){
        List<Evidence> geneNameEvidences = CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001360");
        GeneName geneName = new GeneNameBuilder().value("some Gene").evidences(geneNameEvidences).build();

        List<Evidence> synEvidences = CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361");
        GeneNameSynonym synonym = new GeneNameSynonymBuilder().value("some Syn").evidences(synEvidences).build();

        List<Evidence> olnNameEvidences = CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001362");
        OrderedLocusName olnName = new OrderedLocusNameBuilder().value("some locus").evidences(olnNameEvidences).build();

        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000269|PubMed:11389730");
        ORFName orfName = new ORFNameBuilder().value("some orf").evidences(evidences).build();

        return new GeneBuilder()
                .geneName(geneName)
                .addSynonyms(synonym)
                .addOrderedLocusNames(olnName)
                .addOrfNames(orfName)
                .build();
    }

    private Gene createSimpleGene() {
        GeneName geneName = new GeneNameBuilder().value("someGene").build();
        return new GeneBuilder().geneName(geneName).build();
    }

}
