package uk.ac.ebi.uniprot.json.parser.literature;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.literature.LiteratureMappedReference;
import uk.ac.ebi.uniprot.domain.literature.builder.LiteratureMappedReferenceBuilder;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

/**
 * @author lgonzales
 */
class LiteratureMappedReferenceTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureMappedReference mappedReference = new LiteratureMappedReferenceBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(LiteratureJsonConfig.getInstance().getFullObjectMapper(), mappedReference);
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureMappedReference mappedReference = getCompleteLiteratureMappedReference();
        ValidateJson.verifyJsonRoundTripParser(LiteratureJsonConfig.getInstance().getFullObjectMapper(), mappedReference);
        ValidateJson.verifyEmptyFields(mappedReference);
    }

    static LiteratureMappedReference getCompleteLiteratureMappedReference() {
        return new LiteratureMappedReferenceBuilder()
                .annotation("annotation value")
                .source("source value")
                .sourceId("source Id")
                .addSourceCategory("source category")
                .uniprotAccession("P12345")
                .build();
    }
}
