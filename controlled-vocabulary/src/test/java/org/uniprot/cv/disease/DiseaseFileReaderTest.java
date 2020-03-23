package org.uniprot.cv.disease;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.DiseaseEntry;

class DiseaseFileReaderTest {
    private static final DiseaseFileReader reader = new DiseaseFileReader();

    @Test
    void testParseDefaultFile() {
        List<DiseaseEntry> diseases = reader.parse("disease/sample_humdisease.txt");
        assertFalse(diseases.isEmpty());
    }

    @Test
    @DisplayName("Only simple disease with cross references")
    void simpleDiseaseParse() {

        final List<String> input =
                Arrays.asList(
                        "_______________________________",
                        "ID   Roberts syndrome.",
                        "AC   DI-02272",
                        "AR   RBS.",
                        "DE   Rare autosomal recessive disorder characterized",
                        "DR   MIM; 268300; phenotype.",
                        "//");

        final List<DiseaseEntry> retList = reader.parseLines(input);
        assertAll(
                "DiseaseEntry parse result",
                () -> assertNotNull(retList),
                () -> assertEquals(1, retList.size(), "should have one object return"),
                () -> assertEquals("Roberts syndrome", retList.get(0).getId()),
                () -> assertEquals("DI-02272", retList.get(0).getAccession()),
                () -> assertEquals("RBS", retList.get(0).getAcronym()),
                () ->
                        assertEquals(
                                "Rare autosomal recessive disorder characterized",
                                retList.get(0).getDefinition()),
                () ->
                        assertEquals(
                                "MIM",
                                retList.get(0).getCrossReferences().get(0).getDatabaseType()),
                () -> assertEquals("268300", retList.get(0).getCrossReferences().get(0).getId()),
                () ->
                        assertEquals(
                                "phenotype",
                                retList.get(0).getCrossReferences().get(0).getProperties().get(0)),
                () -> assertTrue(retList.get(0).getAlternativeNames().isEmpty()),
                () -> assertTrue(retList.get(0).getKeywords().isEmpty()));
    }

    @Test
    @DisplayName("Should return keywords when parsing disease")
    void parseDiseaseAndCheckKeywords() {

        final List<String> input =
                Arrays.asList(
                        "______________________________",
                        "ID   Unilateral palmoplantar verrucous nevus.",
                        "AC   DI-01111",
                        "AR   UPVN.",
                        "DE   UPVN is characterized by a localized epidermolytic hyperkeratosis in",
                        "DE   parts of the right palm and the right sole, following the lines of",
                        "DE   Blaschko.",
                        "DR   MIM; 144200; phenotype.",
                        "DR   MeSH; D053546.",
                        "KW   KW-1007:Palmoplantar keratoderma.",
                        "//");

        final List<DiseaseEntry> retList = reader.parseLines(input);

        assertAll(
                "DiseaseEntry parse result",
                () -> assertNotNull(retList),
                () -> assertEquals(1, retList.size(), "should have one object return"),
                () ->
                        assertEquals(
                                "Unilateral palmoplantar verrucous nevus", retList.get(0).getId()),
                () -> assertEquals("DI-01111", retList.get(0).getAccession()),
                () -> assertEquals("UPVN", retList.get(0).getAcronym()),
                () ->
                        assertEquals(
                                "MIM",
                                retList.get(0).getCrossReferences().get(0).getDatabaseType()),
                () -> assertEquals("144200", retList.get(0).getCrossReferences().get(0).getId()),
                () ->
                        assertEquals(
                                "phenotype",
                                retList.get(0).getCrossReferences().get(0).getProperties().get(0)),
                () ->
                        Assertions.assertEquals(
                                "Palmoplantar keratoderma",
                                retList.get(0).getKeywords().get(0).getName()),
                () ->
                        Assertions.assertEquals(
                                "KW-1007", retList.get(0).getKeywords().get(0).getId()));
    }

    @Test
    @DisplayName("Should return alternative names when parsing disease")
    void parseDiseaseAndCheckAlternativeNames() {
        final List<String> input =
                Arrays.asList(
                        "_____________________________",
                        "ID   SC phocomelia syndrome.",
                        "AC   DI-02280",
                        "AR   SCPS.",
                        "SY   SC pseudothalidomide syndrome.",
                        "//");
        final List<DiseaseEntry> retList = reader.parseLines(input);

        assertNotNull(retList.get(0).getAlternativeNames());
        assertEquals(1, retList.get(0).getAlternativeNames().size());
        assertEquals("SC pseudothalidomide syndrome", retList.get(0).getAlternativeNames().get(0));

        assertEquals("", retList.get(0).getDefinition());
        assertTrue(retList.get(0).getCrossReferences().isEmpty());
    }

    @Test
    @DisplayName("should return all attributes not null when parsing complete entry")
    void parseLargeDisease() {
        final List<String> input =
                Arrays.asList(
                        "____________________________",
                        "ID   Salt and pepper developmental regression syndrome",
                        "AC   DI-00096",
                        "AR   SPDRS.",
                        "DE   A rare autosomal recessive disorder characterized by infantile onset",
                        "SY   AIES.",
                        "SY   Amish infantile epilepsy syndrome.",
                        "SY   Epilepsy syndrome infantile-onset symptomatic.",
                        "SY   GM3 synthase deficiency.",
                        "SY   Salt and pepper mental retardation syndrome.",
                        "DR   MIM; 609056; phenotype.",
                        "DR   MedGen; C1836824.",
                        "DR   MeSH; D004827.",
                        "KW   KW-0887:Epilepsy.",
                        "KW   KW-0991:Mental retardation.",
                        "//");
        final List<DiseaseEntry> retList = reader.parseLines(input);
        final DiseaseEntry di = retList.get(0);
        assertNotNull(di);
        assertNotNull(di.getId());
        assertNotNull(di.getAccession());
        assertNotNull(di.getAcronym());
        assertNotNull(di.getDefinition());
        assertEquals(5, di.getAlternativeNames().size());
        assertEquals(3, di.getCrossReferences().size());
        assertEquals(2, di.getKeywords().size());
    }
}
