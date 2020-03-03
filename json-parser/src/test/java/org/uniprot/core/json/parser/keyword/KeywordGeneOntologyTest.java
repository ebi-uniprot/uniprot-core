package org.uniprot.core.json.parser.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GoTerm;
import org.uniprot.core.cv.go.builder.GeneOntologyEntryBuilder;
import org.uniprot.core.cv.go.builder.GoTermBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/** @author lgonzales */
class KeywordGeneOntologyTest {

    @Test
    void testSimpleGeneOntology() {
        GoTerm geneOntology = new GeneOntologyEntryBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), geneOntology);
    }

    @Test
    void testCompleteGeneOntology() {
        GoTerm geneOntology = getCompleteGeneOntology();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), geneOntology);
    }

    static GoTerm getCompleteGeneOntology() {
        return new GoTermBuilder().id("idValue").name("termValue").build();
    }
}
