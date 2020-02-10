package org.uniprot.core.json.parser.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.GeneOntology;
import org.uniprot.core.cv.keyword.impl.GeneOntologyImpl;
import org.uniprot.core.json.parser.ValidateJson;

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
