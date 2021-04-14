package org.uniprot.core.json.parser.keyword;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.keyword.KeywordCategory;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.impl.KeywordEntryBuilder;
import org.uniprot.core.impl.StatisticsBuilder;
import org.uniprot.core.json.parser.ValidateJson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    @Test
    void testKeywordCategoryForSimpleObjectMapper() throws JsonProcessingException {
        KeywordEntry keywordEntry = getCompleteKeywordEntry(true);
        keywordEntry =
                KeywordEntryBuilder.from(keywordEntry).category(KeywordCategory.LIGAND).build();

        ObjectMapper mapper = KeywordJsonConfig.getInstance().getSimpleObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(keywordEntry);
        assertNotNull(json);
        assertTrue(json.contains("\"name\" : \"Ligand\""));
        assertTrue(json.contains("\"id\" : \"KW-9993\""));
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
                        .linksAdd("link")
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
