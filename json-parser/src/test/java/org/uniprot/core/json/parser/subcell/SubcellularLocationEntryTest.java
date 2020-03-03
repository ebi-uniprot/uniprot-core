package org.uniprot.core.json.parser.subcell;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.builder.StatisticsBuilder;
import org.uniprot.core.cv.go.builder.GoTermBuilder;
import org.uniprot.core.cv.keyword.builder.KeywordIdBuilder;
import org.uniprot.core.cv.subcell.SubcellLocationCategory;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.cv.subcell.builder.SubcellularLocationEntryBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/**
 * @author lgonzales
 * @since 2019-07-11
 */
class SubcellularLocationEntryTest {

    @Test
    void testSimpleSubcellularLocationEntry() {
        SubcellularLocationEntry subcellularLocationEntry =
                new SubcellularLocationEntryBuilder().build();
        ValidateJson.verifyJsonRoundTripParser(
                SubcellularLocationJsonConfig.getInstance().getFullObjectMapper(),
                subcellularLocationEntry);
    }

    @Test
    void testCompleteSubcellularLocationEntry() {
        SubcellularLocationEntry subcellularLocationEntry =
                getCompleteSubcellularLocationEntry(true);
        ValidateJson.verifyJsonRoundTripParser(
                SubcellularLocationJsonConfig.getInstance().getFullObjectMapper(),
                subcellularLocationEntry);
    }

    private SubcellularLocationEntry getCompleteSubcellularLocationEntry(boolean hasChild) {
        Statistics statistics =
                new StatisticsBuilder().reviewedProteinCount(10).unreviewedProteinCount(20).build();
        SubcellularLocationEntryBuilder entry = new SubcellularLocationEntryBuilder();
        entry.accession("accession");
        entry.content("content");
        entry.definition("definition");
        entry.geneOntologiesAdd(new GoTermBuilder().id("goId").name("goTerm").build());
        entry.id("id");
        entry.keyword(new KeywordIdBuilder().id("keywordId").accession("keywordAccession").build());
        entry.linksAdd("link");
        entry.note("note");
        entry.referencesAdd("synonym");
        entry.statistics(statistics);
        entry.synonymsAdd("synonym");
        entry.category(SubcellLocationCategory.LOCATION);
        if (hasChild) {
            entry.isAAdd(getCompleteSubcellularLocationEntry(false));
            entry.partOfAdd(getCompleteSubcellularLocationEntry(false));
        }
        return entry.build();
    }
}
