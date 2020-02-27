package org.uniprot.cv.keyword;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.keyword.KeywordEntry;
import org.uniprot.core.cv.keyword.KeywordId;

class KeywordFileReaderTest {

    private final KeywordFileReader parser = new KeywordFileReader();

    @Disabled
    @Test
    void testParseDefaultFile() {
        List<KeywordEntry> keywords = parser.parse(KeywordCache.FTP_LOCATION);
        assertFalse(keywords.isEmpty());
    }

    @Test
    @DisplayName("Only single category parsing")
    void testCategoryParse() {

        final List<String> input =
                Arrays.asList(
                        "_______________________________",
                        "IC   Domain.",
                        "AC   KW-9994",
                        "DE   Keywords assigned to proteins because they have at least one specimen",
                        "DE   of a specific domain.",
                        "//");

        final List<KeywordEntry> retList = parser.parseLines(input);
        assertAll(
                "Category parse result",
                () -> assertNotNull(retList),
                () -> assertEquals(1, retList.size(), "should have one object return"),
                () -> assertEquals("Domain", retList.get(0).getKeyword().getName()),
                () -> assertEquals("KW-9994", retList.get(0).getKeyword().getId()),
                () ->
                        assertEquals(
                                "Keywords assigned to proteins because they have at least one specimen of a specific domain.",
                                retList.get(0).getDefinition()),
                () -> assertNotNull(retList.get(0).getParents()),
                () -> assertTrue(retList.get(0).getParents().isEmpty()));
    }

    @Test
    @DisplayName("Only single keyword parsing without hierarchy and category attached")
    void testKeywordParse() {

        final List<String> input =
                Arrays.asList(
                        "_______________________________",
                        "ID   2Fe-2S.",
                        "AC   KW-0001",
                        "DE   Protein which contains at least one 2Fe-2S iron-sulfur cluster: 2 iron",
                        "DE   atoms complexed to 2 inorganic sulfides and 4 sulfur atoms of",
                        "DE   cysteines from the protein.",
                        "SY   [2Fe-2S] cluster; [Fe2S2] cluster; 2 iron, 2 sulfur cluster binding;",
                        "SY   Di-mu-sulfido-diiron; Fe2/S2 (inorganic) cluster; Fe2S2.",
                        "GO   GO:0051537; 2 iron, 2 sulfur cluster binding",
                        "HI   Ligand: Iron; Iron-sulfur; 2Fe-2S.",
                        "HI   Ligand: Metal-binding; Iron-sulfur; 2Fe-2S.",
                        "HI   Ligand: Metal-binding; 2Fe-2S.",
                        "CA   Ligand.",
                        "//");

        final List<KeywordEntry> retList = parser.parseLines(input);

        assertAll(
                "Keyword parse result",
                () -> assertNotNull(retList.get(0).getSynonyms()),
                () ->
                        assertEquals(
                                6,
                                retList.get(0).getSynonyms().size(),
                                "should have one synonyms return"),
                () ->
                        assertEquals(
                                "Fe2S2",
                                retList.get(0).getSynonyms().get(5),
                                "should be without dot"),
                () -> assertNotNull(retList.get(0).getGeneOntologies()),
                () -> assertEquals(1, retList.get(0).getGeneOntologies().size()),
                () -> assertEquals("GO:0051537", retList.get(0).getGeneOntologies().get(0).getId()),
                () ->
                        assertEquals(
                                "2 iron, 2 sulfur cluster binding",
                                retList.get(0).getGeneOntologies().get(0).getName()));
    }

    @Test
    @DisplayName("Category and Relationship test")
    void relationShipTest() {
        final List<String> input =
                Arrays.asList(
                        "_______________________________",
                        "ID   2Fe-2S.",
                        "AC   KW-0001",
                        "HI   Ligand: Iron; Iron-sulfur; 2Fe-2S.",
                        "HI   Ligand: Metal-binding; Iron-sulfur; 2Fe-2S.",
                        "HI   Ligand: Metal-binding; 2Fe-2S.",
                        "CA   Ligand.",
                        "//",
                        "IC   Ligand.",
                        "AC   KW-9993",
                        "//",
                        "ID   Iron-sulfur.",
                        "AC   KW-0411",
                        "//",
                        "ID   Metal-binding.",
                        "AC   KW-0479",
                        "//");
        final List<KeywordEntry> retList = parser.parseLines(input);
        WrongKeywordEntry wrongKeywordEntry = new WrongKeywordEntry();
        final KeywordEntry kw =
                retList.stream()
                        .filter(k -> k.getKeyword().getName().equals("2Fe-2S"))
                        .findFirst()
                        .orElse(wrongKeywordEntry);
        assertNotEquals(kw, wrongKeywordEntry);

        assertNotNull(kw.getCategory());
        assertEquals("KW-9993", kw.getCategory().getId());

        assertNotNull(kw.getChildren());
        assertEquals(2, kw.getChildren().size());

        assertNotNull(kw.getSites());
        assertTrue(kw.getSites().isEmpty());
    }

    private static class WrongKeywordEntry implements KeywordEntry {
        @Override
        public KeywordId getKeyword() {
            return null;
        }

        @Override
        public String getDefinition() {
            return null;
        }

        @Override
        public List<String> getSynonyms() {
            return null;
        }

        @Override
        public List<GeneOntologyEntry> getGeneOntologies() {
            return null;
        }

        @Override
        public Set<KeywordEntry> getParents() {
            return null;
        }

        @Override
        public List<String> getSites() {
            return null;
        }

        @Override
        public KeywordId getCategory() {
            return null;
        }

        @Override
        public List<KeywordEntry> getChildren() {
            return null;
        }

        @Override
        public String getAccession() {
            return getKeyword().getId();
        }

        @Override
        public Statistics getStatistics() {
            return null;
        }
    }

    @Test
    @DisplayName("No Category and Relationship test on keyword")
    void noRelationTest() {
        final List<String> input =
                Arrays.asList(
                        "_______________________________",
                        "ID   Tungsten.",
                        "AC   KW-0826",
                        "HI   Ligand: Tungsten.",
                        "WW   http://www.webelements.com/tungsten/",
                        "CA   Ligand.",
                        "//");
        final List<KeywordEntry> retList = parser.parseLines(input);
        final KeywordEntry kw = retList.get(0);
        assertNotNull(kw);

        assertTrue(kw.getParents().isEmpty());
        assertNull(kw.getCategory());

        assertNotNull(kw.getSites());
        assertFalse(kw.getSites().isEmpty());
        assertEquals("http://www.webelements.com/tungsten/", kw.getSites().get(0));
    }
}
