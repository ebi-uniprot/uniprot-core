package uk.ac.ebi.uniprot.json.parser.subcell;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.cv.keyword.impl.GeneOntologyImpl;
import uk.ac.ebi.uniprot.cv.keyword.impl.KeywordImpl;
import uk.ac.ebi.uniprot.cv.subcell.SubcellLocationCategory;
import uk.ac.ebi.uniprot.cv.subcell.SubcellularLocationEntry;
import uk.ac.ebi.uniprot.cv.subcell.impl.SubcellularLocationEntryImpl;
import uk.ac.ebi.uniprot.cv.subcell.impl.SubcellularLocationStatisticsImpl;
import uk.ac.ebi.uniprot.json.parser.ValidateJson;

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
