package org.uniprot.core.json.parser.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordGeneOntology;
import org.uniprot.core.cv.keyword.builder.KeywordGeneOntologyBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/** @author lgonzales */
class KeywordGeneOntologyTest {

    @Test
    void testSimpleGeneOntology() {
        KeywordGeneOntology geneOntology = new KeywordGeneOntologyBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), geneOntology);
    }

    @Test
    void testCompleteGeneOntology() {
        KeywordGeneOntology geneOntology = getCompleteGeneOntology();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), geneOntology);
    }

    static KeywordGeneOntology getCompleteGeneOntology() {
        return new KeywordGeneOntologyBuilder().id("idValue").term("termValue").build();
    }
}
