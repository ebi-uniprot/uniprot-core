package org.uniprot.core.json.parser.literature;

import org.junit.jupiter.api.Test;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.literature.LiteratureStoreEntry;
import org.uniprot.core.literature.builder.LiteratureStoreEntryBuilder;

/**
 * @author lgonzales
 * @since 2019-12-16
 */
class LiteratureStoreEntryTest {

    @Test
    void testSimpleLiteratureStoreEntry() {
        LiteratureStoreEntryBuilder builder = new LiteratureStoreEntryBuilder();

        LiteratureStoreEntry literatureStoreEntry = builder.build();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureStoreEntry);
    }

    @Test
    void testCompleteLiteratureStoreEntry() {
        LiteratureStoreEntry literatureStoreEntry = getCompleteLiteratureStoreEntry();
        ValidateJson.verifyJsonRoundTripParser(
                LiteratureJsonConfig.getInstance().getFullObjectMapper(), literatureStoreEntry);
        ValidateJson.verifyEmptyFields(literatureStoreEntry);
    }

    LiteratureStoreEntry getCompleteLiteratureStoreEntry() {
        return new LiteratureStoreEntryBuilder()
                .literatureEntry(LiteratureEntryTest.getCompleteLiteratureEntry())
                .addLiteratureMappedReference(
                        LiteratureMappedReferenceTest.getCompleteLiteratureMappedReference())
                .build();
    }
}
