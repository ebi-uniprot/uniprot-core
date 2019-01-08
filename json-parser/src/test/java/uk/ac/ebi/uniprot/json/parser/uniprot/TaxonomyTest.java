package uk.ac.ebi.uniprot.json.parser.uniprot;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import uk.ac.ebi.uniprot.domain.taxonomy.Organism;
import uk.ac.ebi.uniprot.domain.taxonomy.OrganismName;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtTaxonId;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.TaxonomyFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtTaxonIdImpl;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
/**
 *
 * @author lgonzales
 */
public class TaxonomyTest {

    @Test
    public void testUniProtTaxonIdSimple() {
        UniProtTaxonId taxonId = new UniProtTaxonIdImpl(9606, Collections.emptyList());
        ValidateJson.verifyJsonRoundTripParser(taxonId);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(taxonId);
        assertEquals(9606,jsonNode.get("taxonId").asInt());
    }

    @Test
    public void testUniProtTaxonIdComplete() {
        UniProtTaxonId taxonId = getUniProtTaxonId();
        ValidateJson.verifyJsonRoundTripParser(taxonId);
        ValidateJson.verifyEmptyFields(taxonId);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(taxonId);
        assertEquals(9606,jsonNode.get("taxonId").asInt());
        assertEquals(1,jsonNode.get("evidences").size());

        JsonNode evidence = jsonNode.get("evidences").get(0);
        ValidateJson.validateEvidence(evidence,"ECO:0000256","PIRNR","PIRNR001363");
    }

    @Test
    public void testOrganismNameSimple(){
        OrganismName organismName = TaxonomyFactory.INSTANCE.createOrganismName("scientific name");
        ValidateJson.verifyJsonRoundTripParser(organismName);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(organismName);
        assertEquals("scientific name",jsonNode.get("scientificName").asText());
    }

    @Test
    public void testOrganismNameComplete() {
        OrganismName organismName = getOrganismName();
        ValidateJson.verifyJsonRoundTripParser(organismName);
        ValidateJson.verifyEmptyFields(organismName);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(organismName);
        assertEquals("scientific name",jsonNode.get("scientificName").asText());
        assertEquals("common name",jsonNode.get("commonName").asText());
        jsonNode = jsonNode.get("synonyms");
        assertEquals(2,jsonNode.size());
        assertEquals("synonyms 1",jsonNode.get(0).asText());
    }

    @Test
    public void testOrganismSimple() {
        OrganismName organismName = TaxonomyFactory.INSTANCE.createOrganismName("scientific name");
        Organism organism = TaxonomyFactory.INSTANCE.createOrganism(organismName,9606L);
        ValidateJson.verifyJsonRoundTripParser(organism);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(organism);
        assertEquals(9606,jsonNode.get("taxonId").asInt());
        assertNotNull(jsonNode.get("name"));
        assertNotNull(jsonNode.get("name").get("scientificName"));
        assertEquals("scientific name",jsonNode.get("name").get("scientificName").asText());
        assertEquals(9606,jsonNode.get("taxonId").asInt());
    }

    @Test
    public void testOrganismComplete() {
        Organism organism = getOrganism();
        ValidateJson.verifyJsonRoundTripParser(organism);
        ValidateJson.verifyEmptyFields(organism);

        JsonNode jsonNode = ValidateJson.getJsonNodeFromSerializeOnlyMapper(organism);
        assertEquals(9606,jsonNode.get("taxonId").asInt());
        jsonNode = jsonNode.get("name");
        assertNotNull(jsonNode);
        assertEquals("scientific name",jsonNode.get("scientificName").asText());
        assertEquals("common name",jsonNode.get("commonName").asText());
        jsonNode = jsonNode.get("synonyms");
        assertEquals(2,jsonNode.size());
        assertEquals("synonyms 1",jsonNode.get(0).asText());

    }

    public static UniProtTaxonId getUniProtTaxonId() {
        List<Evidence> evidences = Collections.singletonList(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001363"));
        return new UniProtTaxonIdImpl(9606, evidences);
    }

    public static OrganismName getOrganismName() {
        List<String> synonyms = Arrays.asList("synonyms 1", "synonyms 2");
        return TaxonomyFactory.INSTANCE.createOrganismName("scientific name", "common name", synonyms);
    }

    public static Organism getOrganism() {
        OrganismName organismName = getOrganismName();
        return TaxonomyFactory.INSTANCE.createOrganism(organismName,9606L);
    }
}
