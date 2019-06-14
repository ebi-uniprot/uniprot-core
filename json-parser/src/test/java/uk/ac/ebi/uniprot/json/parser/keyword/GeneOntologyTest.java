package uk.ac.ebi.uniprot.json.parser.keyword;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.impl.GeneOntologyImpl;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

/**
 * @author lgonzales
 */
class GeneOntologyTest {

    @Test
    void testSimpleGeneOntology() {
        GeneOntology geneOntology = new GeneOntologyImpl(null, null);
        ValidateJson.verifyJsonRoundTripParser(KeywordJsonConfig.getInstance().getFullObjectMapper(), geneOntology);
    }

    @Test
    void testCompleteGeneOntology() {
        GeneOntology geneOntology = getCompleteGeneOntology();
        ValidateJson.verifyJsonRoundTripParser(KeywordJsonConfig.getInstance().getFullObjectMapper(), geneOntology);
    }

    static GeneOntology getCompleteGeneOntology() {
        return new GeneOntologyImpl("idValue", "termValue");
    }
}
