package org.uniprot.core.json.parser.subcell;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.keyword.impl.GeneOntologyImpl;
import org.uniprot.core.cv.keyword.impl.KeywordImpl;
import org.uniprot.core.cv.subcell.SubcellLocationCategory;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.cv.subcell.impl.SubcellularLocationEntryImpl;
import org.uniprot.core.cv.subcell.impl.SubcellularLocationStatisticsImpl;
import org.uniprot.core.json.parser.ValidateJson;
import org.uniprot.core.json.parser.subcell.SubcellularLocationJsonConfig;

import java.util.Collections;

/**
 * @author lgonzales
 * @since 2019-07-11
 */
class SubcellularLocationEntryTest {

    @Test
    void testSimpleSubcellularLocationEntry() {
        SubcellularLocationEntry subcellularLocationEntry = new SubcellularLocationEntryImpl();
        ValidateJson.verifyJsonRoundTripParser(SubcellularLocationJsonConfig.getInstance().getFullObjectMapper(), subcellularLocationEntry);
    }

    @Test
    void testCompleteSubcellularLocationEntry() {
        SubcellularLocationEntry subcellularLocationEntry = getCompleteSubcellularLocationEntry(true);
        ValidateJson.verifyJsonRoundTripParser(SubcellularLocationJsonConfig.getInstance().getFullObjectMapper(), subcellularLocationEntry);
    }

    private SubcellularLocationEntry getCompleteSubcellularLocationEntry(boolean hasChild) {
        SubcellularLocationEntryImpl entry = new SubcellularLocationEntryImpl();
        entry.setAccession("accession");
        entry.setContent("content");
        entry.setDefinition("definition");
        entry.setGeneOntologies(Collections.singletonList(new GeneOntologyImpl("goId", "goTerm")));
        entry.setId("id");
        entry.setKeyword(new KeywordImpl("keywordId", "keywordAccession"));
        entry.setLinks(Collections.singletonList("link"));
        entry.setNote("note");
        entry.setReferences(Collections.singletonList("synonym"));
        entry.setStatistics(new SubcellularLocationStatisticsImpl(10L, 20L));
        entry.setSynonyms(Collections.singletonList("synonym"));
        entry.setCategory(SubcellLocationCategory.LOCATION);
        if (hasChild) {
            entry.setIsA(Collections.singletonList(getCompleteSubcellularLocationEntry(false)));
            entry.setPartOf(Collections.singletonList(getCompleteSubcellularLocationEntry(false)));
        }
        return entry;
    }
}