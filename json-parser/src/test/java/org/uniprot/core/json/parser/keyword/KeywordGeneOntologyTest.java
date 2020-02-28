package org.uniprot.core.json.parser.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.builder.GeneOntologyEntryBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/** @author lgonzales */
class KeywordGeneOntologyTest {

    @Test
    void testSimpleGeneOntology() {
        GeneOntologyEntry geneOntology = new GeneOntologyEntryBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), geneOntology);
    }

    @Test
    void testCompleteGeneOntology() {
        GeneOntologyEntry geneOntology = getCompleteGeneOntology();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), geneOntology);
    }

    static GeneOntologyEntry getCompleteGeneOntology() {
        return new GeneOntologyEntryBuilder().id("idValue").name("termValue").build();
    }
}
