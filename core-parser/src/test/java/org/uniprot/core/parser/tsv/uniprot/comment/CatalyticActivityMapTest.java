package org.uniprot.core.parser.tsv.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.comment.CatalyticActivityComment;
import org.uniprot.core.uniprot.comment.CommentType;

class CatalyticActivityMapTest {

    @Test
    void testCatalyticActivityMapping() {
        String catalyticActivityLine =
                "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=a ribonucleoside 5'-triphosphate + RNA(n) = diphosphate +\n"
                        + "CC         RNA(n+1); Xref=Rhea:RHEA:21248, Rhea:RHEA-COMP:11128, Rhea:RHEA-\n"
                        + "CC         COMP:11129, ChEBI:CHEBI:33019, ChEBI:CHEBI:61557,\n"
                        + "CC         ChEBI:CHEBI:83400; EC=2.7.7.48; Evidence={ECO:0000255|PROSITE-\n"
                        + "CC         ProRule:PRU00539};";

        UniProtEntry entry =
                CommentTestUtil.createUniProtEntryFromCommentLine(catalyticActivityLine);

        List<CatalyticActivityComment> catalyticActivityComments =
                entry.getCommentsByType(CommentType.CATALYTIC_ACTIVITY);
        assertNotNull(entry);
        CatalyticActivityMap catalyticActivityMap =
                new CatalyticActivityMap(catalyticActivityComments);
        Map<String, String> mappedCatalyticActivity = catalyticActivityMap.attributeValues();
        assertNotNull(mappedCatalyticActivity);
        String value = mappedCatalyticActivity.get("cc_catalytic_activity");
        String expectedValue =
                "CATALYTIC ACTIVITY: Reaction=a ribonucleoside 5'-triphosphate + RNA(n) = "
                        + "diphosphate + RNA(n+1); Xref=Rhea:RHEA:21248, Rhea:RHEA-COMP:11128, Rhea:RHEA-COMP:11129, "
                        + "ChEBI:CHEBI:33019, ChEBI:CHEBI:61557, ChEBI:CHEBI:83400; EC=2.7.7.48; "
                        + "Evidence={ECO:0000255|PROSITE-ProRule:PRU00539};";
        assertEquals(expectedValue, value);
    }

    @Test
    void testMultiplesCatalyticActivityMapping() {
        String catalyticActivityLine =
                "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=a 2'-deoxyribonucleoside 5'-triphosphate + DNA(n) =\n"
                        + "CC         diphosphate + DNA(n+1); Xref=Rhea:RHEA:22508, Rhea:RHEA-\n"
                        + "CC         COMP:11130, Rhea:RHEA-COMP:11131, ChEBI:CHEBI:33019,\n"
                        + "CC         ChEBI:CHEBI:61560, ChEBI:CHEBI:83828; EC=2.7.7.49;\n"
                        + "CC         Evidence={ECO:0000255|PROSITE-ProRule:PRU00405};\n"
                        + "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=a 2'-deoxyribonucleoside 5'-triphosphate + DNA(n) =\n"
                        + "CC         diphosphate + DNA(n+1); Xref=Rhea:RHEA:22508, Rhea:RHEA-\n"
                        + "CC         COMP:11130, Rhea:RHEA-COMP:11131, ChEBI:CHEBI:33019,\n"
                        + "CC         ChEBI:CHEBI:61560, ChEBI:CHEBI:83828; EC=2.7.7.7;\n"
                        + "CC         Evidence={ECO:0000255|PROSITE-ProRule:PRU00405};\n"
                        + "CC   -!- CATALYTIC ACTIVITY:\n"
                        + "CC       Reaction=Endonucleolytic cleavage to 5'-phosphomonoester.;\n"
                        + "CC         EC=3.1.26.4; Evidence={ECO:0000255|PROSITE-ProRule:PRU00408};";

        UniProtEntry entry =
                CommentTestUtil.createUniProtEntryFromCommentLine(catalyticActivityLine);

        List<CatalyticActivityComment> catalyticActivityComments =
                entry.getCommentsByType(CommentType.CATALYTIC_ACTIVITY);
        assertNotNull(entry);
        CatalyticActivityMap catalyticActivityMap =
                new CatalyticActivityMap(catalyticActivityComments);
        Map<String, String> mappedCatalyticActivity = catalyticActivityMap.attributeValues();
        assertNotNull(mappedCatalyticActivity);
        String value = mappedCatalyticActivity.get("cc_catalytic_activity");
        String expectedValue =
                "CATALYTIC ACTIVITY: Reaction=a 2'-deoxyribonucleoside 5'-triphosphate + DNA(n) = "
                        + "diphosphate + DNA(n+1); Xref=Rhea:RHEA:22508, Rhea:RHEA-COMP:11130, Rhea:RHEA-COMP:11131, "
                        + "ChEBI:CHEBI:33019, ChEBI:CHEBI:61560, ChEBI:CHEBI:83828; EC=2.7.7.49; "
                        + "Evidence={ECO:0000255|PROSITE-ProRule:PRU00405}; "
                        + "CATALYTIC ACTIVITY: Reaction=a 2'-deoxyribonucleoside 5'-triphosphate + DNA(n) = diphosphate + DNA(n+1); "
                        + "Xref=Rhea:RHEA:22508, Rhea:RHEA-COMP:11130, Rhea:RHEA-COMP:11131, ChEBI:CHEBI:33019, "
                        + "ChEBI:CHEBI:61560, ChEBI:CHEBI:83828; EC=2.7.7.7; Evidence={ECO:0000255|PROSITE-ProRule:PRU00405}; "
                        + "CATALYTIC ACTIVITY: Reaction=Endonucleolytic cleavage to 5'-phosphomonoester.; "
                        + "EC=3.1.26.4; Evidence={ECO:0000255|PROSITE-ProRule:PRU00408};";
        assertEquals(expectedValue, value);
    }
}
