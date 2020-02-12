package org.uniprot.core.json.parser.keyword;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.builder.StatisticsBuilder;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.impl.KeywordEntryImpl;
import org.uniprot.core.json.parser.ValidateJson;

/** @author lgonzales */
class KeywordEntryTest {

    @Test
    void testSimpleKeywordEntry() {
        KeywordEntry keywordEntry = new KeywordEntryImpl();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), keywordEntry);
    }

    @Test
    void testCompleteKeywordEntry() {
        KeywordEntry keywordEntry = getCompleteKeywordEntry(true);
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), keywordEntry);
    }

    static KeywordEntry getCompleteKeywordEntry(boolean hierarchy) {
        Statistics statistics =
                new StatisticsBuilder().reviewedProteinCount(10).unreviewedProteinCount(20).build();
        KeywordEntryImpl keywordEntry = new KeywordEntryImpl();
        keywordEntry.setDefinition("Definition value");
        keywordEntry.setKeyword(KeywordTest.getCompleteKeyword());
        keywordEntry.setGeneOntologies(
                Collections.singletonList(GeneOntologyTest.getCompleteGeneOntology()));
        keywordEntry.setSynonyms(Collections.singletonList("synonym"));
        keywordEntry.setSites(Collections.singletonList("site"));
        keywordEntry.setParents(Collections.singleton(getKeywordEntryParent()));
        keywordEntry.setCategory(KeywordTest.getCompleteKeyword());
        keywordEntry.setStatistics(statistics);

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
