package org.uniprot.core.json.parser.keyword;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.impl.KeywordEntryBuilder;
import org.uniprot.core.impl.StatisticsBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/** @author lgonzales */
public class KeywordEntryTest {

    @Test
    void testSimpleKeywordEntry() {
        KeywordEntry keywordEntry = new KeywordEntryBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), keywordEntry);
    }

    @Test
    void testCompleteKeywordEntry() {
        KeywordEntry keywordEntry = getCompleteKeywordEntry(true);
        ValidateJson.verifyJsonRoundTripParser(
                KeywordJsonConfig.getInstance().getFullObjectMapper(), keywordEntry);
    }

    public static KeywordEntry getCompleteKeywordEntry(boolean hierarchy) {
        Statistics statistics =
                new StatisticsBuilder().reviewedProteinCount(10).unreviewedProteinCount(20).build();
        KeywordEntry keywordEntry =
                new KeywordEntryBuilder()
                        .definition("Definition value")
                        .keyword(KeywordTest.getCompleteKeyword())
                        .geneOntologiesAdd(KeywordGeneOntologyTest.getCompleteGeneOntology())
                        .synonymsAdd("synonym")
                        .sitesAdd("site")
                        .parentsAdd(getKeywordEntryParent())
                        .category(KeywordTest.getCompleteKeyword())
                        .statistics(statistics)
                        .build();

        if (hierarchy) {
            keywordEntry =
                    KeywordEntryBuilder.from(keywordEntry)
                            .childrenAdd(getCompleteKeywordEntry(false))
                            .build();
        }
        return keywordEntry;
    }

    static KeywordEntry getKeywordEntryParent() {
        return new KeywordEntryBuilder().keyword(KeywordTest.getCompleteKeyword()).build();
    }
}
