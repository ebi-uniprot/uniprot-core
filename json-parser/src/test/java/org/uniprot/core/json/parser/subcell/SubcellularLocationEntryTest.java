package org.uniprot.core.json.parser.subcell;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.impl.GoTermBuilder;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.cv.subcell.SubcellLocationCategory;
import org.uniprot.core.cv.subcell.SubcellularLocationEntry;
import org.uniprot.core.cv.subcell.impl.SubcellularLocationEntryBuilder;
import org.uniprot.core.impl.StatisticsBuilder;
import org.uniprot.core.json.parser.ValidateJson;

/**
 * @author lgonzales
 * @since 2019-07-11
 */
public class SubcellularLocationEntryTest {

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

    public static SubcellularLocationEntry getCompleteSubcellularLocationEntry(boolean hasChild) {
        Statistics statistics =
                new StatisticsBuilder().reviewedProteinCount(10).unreviewedProteinCount(20).build();
        SubcellularLocationEntryBuilder entry = new SubcellularLocationEntryBuilder();
        entry.id("accession");
        entry.content("content");
        entry.definition("definition");
        entry.geneOntologiesAdd(new GoTermBuilder().id("goId").name("goTerm").build());
        entry.name("id");
        entry.keyword(new KeywordIdBuilder().name("keywordId").id("keywordAccession").build());
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
