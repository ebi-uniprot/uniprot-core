package org.uniprot.core.json.parser.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordEntryKeyword;
import org.uniprot.core.cv.keyword.builder.KeywordEntryKeywordBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/** @author lgonzales */
class KeywordTest {

    @Test
    void testSimpleKeyword() {
        KeywordEntryKeyword keyword = new KeywordEntryKeywordBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), keyword);
    }

    @Test
    void testCompleteKeyword() {
        KeywordEntryKeyword keyword = getCompleteKeyword();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), keyword);
        ValidateJson.verifyEmptyFields(keyword);
    }

    static KeywordEntryKeyword getCompleteKeyword() {
        return new KeywordEntryKeywordBuilder().id("idValue").accession("accessionValue").build();
    }
}
