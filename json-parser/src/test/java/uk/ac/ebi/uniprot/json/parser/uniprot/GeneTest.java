package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.gene.*;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.GeneFactory;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class GeneTest {

    @Test
    public void testGeneSimple() {
        Gene gene = createSimpleGene();
        ValidateJson.verifyJsonRoundTripParser(gene);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(gene);
        assertNotNull(jsonNode.get("geneName"));
        assertNotNull(jsonNode.get("geneName").get("value"));
        assertEquals("someGene",jsonNode.get("geneName").get("value").asText());
    }

    @Test
    public void testGeneComplete() {
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
        GeneName geneName = GeneFactory.INSTANCE.createGeneName("some Gene", geneNameEvidences);

        List<GeneNameSynonym> synonyms = new ArrayList<>();
        List<Evidence> synEvidences = CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001361");
        GeneNameSynonym synonym = GeneFactory.INSTANCE.createGeneNameSynonym("some Syn", synEvidences);
        synonyms.add(synonym);

        List<OrderedLocusName> olnNames = new ArrayList<>();
        List<Evidence> olnNameEvidences = CreateUtils.createEvidenceList("ECO:0000256|PIRNR:PIRNR001362");
        OrderedLocusName olnName = GeneFactory.INSTANCE.createOrderedLocusName("some locus", olnNameEvidences);
        olnNames.add(olnName);

        List<ORFName> orfNames = new ArrayList<>();
        List<Evidence> evidences = CreateUtils.createEvidenceList("ECO:0000269|PubMed:11389730");
        orfNames.add(GeneFactory.INSTANCE.createORFName("some orf", evidences));

        return GeneFactory.INSTANCE.createGene(geneName, synonyms, olnNames, orfNames);
    }

    private Gene createSimpleGene() {
        GeneName geneName = GeneFactory.INSTANCE.createGeneName("someGene", Collections.emptyList());
        return GeneFactory.INSTANCE.createGene(geneName, Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    }

}
