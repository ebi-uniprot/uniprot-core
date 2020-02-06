package org.uniprot.core.json.parser.literature;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.literature.builder.LiteratureMappedReferenceBuilder;

/** @author lgonzales */
class LiteratureMappedReferenceTest {

    @Test
    void testSimpleLiteratureEntry() {
        LiteratureMappedReference mappedReference = new LiteratureMappedReferenceBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), mappedReference);
    }

    @Test
    void testCompleteLiteratureEntry() {
        LiteratureMappedReference mappedReference = getCompleteLiteratureMappedReference();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), mappedReference);
        ValidateJson.verifyEmptyFields(mappedReference);
    }

    static LiteratureMappedReference getCompleteLiteratureMappedReference() {
        return new LiteratureMappedReferenceBuilder()
                .annotation("annotation value")
                .source("source value")
                .sourceId("source Id")
                .sourceCategoriesAdd("source category")
                .uniprotAccession("P12345")
                .build();
    }
}
