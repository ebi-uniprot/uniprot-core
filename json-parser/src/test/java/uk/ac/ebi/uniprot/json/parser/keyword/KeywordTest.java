package uk.ac.ebi.uniprot.json.parser.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.core.cv.keyword.impl.KeywordImpl;

import uk.ac.ebi.uniprot.json.parser.ValidateJson;

/**
 * @author lgonzales
 */
class KeywordTest {

    @Test
    void testSimpleKeyword() {
        Keyword keyword = new KeywordImpl(null, null);
        ValidateJson.verifyJsonRoundTripParser(KeywordJsonConfig.getInstance().getFullObjectMapper(), keyword);
    }

    @Test
    void testCompleteKeyword() {
        Keyword keyword = getCompleteKeyword();
        ValidateJson.verifyJsonRoundTripParser(KeywordJsonConfig.getInstance().getFullObjectMapper(), keyword);
        ValidateJson.verifyEmptyFields(keyword);
    }

    static Keyword getCompleteKeyword() {
        return new KeywordImpl("idValue", "accessionValue");
    }
}
