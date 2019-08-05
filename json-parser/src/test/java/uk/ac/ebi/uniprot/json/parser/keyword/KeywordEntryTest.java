package uk.ac.ebi.uniprot.json.parser.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.impl.KeywordEntryImpl;
import org.uniprot.core.cv.keyword.impl.KeywordStatisticsImpl;

import uk.ac.ebi.uniprot.json.parser.ValidateJson;

import java.util.Collections;

/**
 * @author lgonzales
 */
class KeywordEntryTest {

    @Test
    void testSimpleKeywordEntry() {
        KeywordEntry keywordEntry = new KeywordEntryImpl();
        ValidateJson.verifyJsonRoundTripParser(KeywordJsonConfig.getInstance().getFullObjectMapper(), keywordEntry);
    }

    @Test
    void testCompleteKeywordEntry() {
        KeywordEntry keywordEntry = getCompleteKeywordEntry(true);
        ValidateJson.verifyJsonRoundTripParser(KeywordJsonConfig.getInstance().getFullObjectMapper(), keywordEntry);
    }

    static KeywordEntry getCompleteKeywordEntry(boolean hierarchy) {
        KeywordEntryImpl keywordEntry = new KeywordEntryImpl();
        keywordEntry.setDefinition("Definition value");
        keywordEntry.setKeyword(KeywordTest.getCompleteKeyword());
        keywordEntry.setGeneOntologies(Collections.singletonList(GeneOntologyTest.getCompleteGeneOntology()));
        keywordEntry.setSynonyms(Collections.singletonList("synonym"));
        keywordEntry.setSites(Collections.singletonList("site"));
        keywordEntry.setParents(Collections.singleton(getKeywordEntryParent()));
        keywordEntry.setCategory(KeywordTest.getCompleteKeyword());
        keywordEntry.setStatistics(new KeywordStatisticsImpl(10, 20));

        if (hierarchy) {
            keywordEntry.setChildren(Collections.singletonList(getCompleteKeywordEntry(false)));
        }
        return keywordEntry;
    }

    static KeywordEntry getKeywordEntryParent() {
        KeywordEntryImpl keywordEntry = new KeywordEntryImpl();
        keywordEntry.setKeyword(KeywordTest.getCompleteKeyword());
        return keywordEntry;
    }

}