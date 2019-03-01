package uk.ac.ebi.uniprot.cv.impl;


import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.cv.keyword.GeneOntology;
import uk.ac.ebi.uniprot.cv.keyword.Keyword;
import uk.ac.ebi.uniprot.cv.keyword.KeywordCache;
import uk.ac.ebi.uniprot.cv.keyword.KeywordDetail;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class KeywordFileReaderTest {

    private final KeywordFileReader parser = new KeywordFileReader();

    @Disabled
    @Test
    void testParseDefaultFile() {
        List<KeywordDetail> keywords = parser.parse(KeywordCache.FTP_LOCATION);
        assertFalse(keywords.isEmpty());
    }

    @Test
    @DisplayName("Only single category parsing")
    void testCategoryParse() {

        final List<String> input =
                Arrays.asList("_______________________________",
                              "IC   Domain.",
                              "AC   KW-9994",
                              "DE   Keywords assigned to proteins because they have at least one specimen",
                              "DE   of a specific domain.",
                              "//");

        final List<KeywordDetail> retList = parser.parseLines(input);
        assertAll("Category parse result",
                  () -> assertNotNull(retList),
                  () -> assertEquals(1, retList.size(), "should have one object return"),
                  () -> assertEquals("Domain", retList.get(0).getKeyword().getId()),
                  () -> assertEquals("KW-9994", retList.get(0).getKeyword().getAccession()),
                  () -> assertEquals(
                          "Keywords assigned to proteins because they have at least one specimen of a specific domain.",
                          retList.get(0).getDefinition()),
                  () -> assertNull(retList.get(0).getHierarchy())
        );
    }

    @Test
    @DisplayName("Only single keyword parsing without hierarchy and category attached")
    void testKeywordParse() {

        final List<String> input =
                Arrays.asList("_______________________________",
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

        final List<KeywordDetail> retList = parser.parseLines(input);

        assertAll("Keyword parse result",
                  () -> assertNotNull(retList.get(0).getSynonyms()),
                  () -> assertEquals(6, retList.get(0).getSynonyms().size(), "should have one synonyms return"),
                  () -> assertEquals("Fe2S2", retList.get(0).getSynonyms().get(5), "should be without dot"),
                  () -> assertNotNull(retList.get(0).getGeneOntologies()),
                  () -> assertEquals(1, retList.get(0).getGeneOntologies().size()),
                  () -> assertEquals("GO:0051537", retList.get(0).getGeneOntologies().get(0).getGoId()),
                  () -> assertEquals("2 iron, 2 sulfur cluster binding",
                                     retList.get(0).getGeneOntologies().get(0).getGoTerm())
        );

    }

    @Test
    @DisplayName("Category and Relationship test")
    void relationShipTest() {
        final List<String> input =
                Arrays.asList("_______________________________",
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
        final List<KeywordDetail> retList = parser.parseLines(input);
        WrongKeywordDetail wrongKeywordDetail = new WrongKeywordDetail();
        final KeywordDetail kw = retList.stream().filter(k -> k.getKeyword().getId().equals("2Fe-2S")).findFirst()
                .orElse(wrongKeywordDetail);
        assertNotEquals(kw, wrongKeywordDetail);

        assertNotNull(kw.getCategory());
        assertEquals("KW-9993", kw.getCategory().getAccession());

        assertNotNull(kw.getHierarchy());
        assertEquals(2, kw.getHierarchy().size());

        assertNull(kw.getSites());
    }

    private static class WrongKeywordDetail implements KeywordDetail {
        @Override
        public Keyword getKeyword() {
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
        public List<GeneOntology> getGeneOntologies() {
            return null;
        }

        @Override
        public List<Keyword> getHierarchy() {
            return null;
        }

        @Override
        public List<String> getSites() {
            return null;
        }

        @Override
        public Keyword getCategory() {
            return null;
        }
    }

    @Test
    @DisplayName("No Category and Relationship test on keyword")
    void noRelationTest() {
        final List<String> input =
                Arrays.asList("_______________________________",
                              "ID   Tungsten.",
                              "AC   KW-0826",
                              "HI   Ligand: Tungsten.",
                              "WW   http://www.webelements.com/tungsten/",
                              "CA   Ligand.",
                              "//");
        final List<KeywordDetail> retList = parser.parseLines(input);
        final KeywordDetail kw = retList.get(0);
        assertNotNull(kw);

        assertTrue(kw.getHierarchy().isEmpty());
        assertNull(kw.getCategory());

        assertNotNull(kw.getSites());
        assertFalse(kw.getSites().isEmpty());
        assertEquals("http://www.webelements.com/tungsten/", kw.getSites().get(0));
    }

}
