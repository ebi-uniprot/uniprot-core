package org.uniprot.core.json.parser.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/** @author lgonzales */
class KeywordTest {

    @Test
    void testSimpleKeyword() {
        KeywordId keyword = new KeywordIdBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), keyword);
    }

    @Test
    void testCompleteKeyword() {
        KeywordId keyword = getCompleteKeyword();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), keyword);
        ValidateJson.verifyEmptyFields(keyword);
    }

    static KeywordId getCompleteKeyword() {
        return new KeywordIdBuilder().id("idValue").accession("accessionValue").build();
    }
}
