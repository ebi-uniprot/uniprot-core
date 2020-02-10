package org.uniprot.core.json.parser.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.cv.keyword.GeneOntology;
import org.uniprot.cv.keyword.impl.GeneOntologyImpl;

/** @author lgonzales */
class GeneOntologyTest {

    @Test
    void testSimpleGeneOntology() {
        GeneOntology geneOntology = new GeneOntologyImpl(null, null);
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), geneOntology);
    }

    @Test
    void testCompleteGeneOntology() {
        GeneOntology geneOntology = getCompleteGeneOntology();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), geneOntology);
    }

    static GeneOntology getCompleteGeneOntology() {
        return new GeneOntologyImpl("idValue", "termValue");
    }
}
