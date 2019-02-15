package uk.ebi.uniprot.scorer.uniprotkb;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;

import java.util.List;

/**
 * 
 * @author jieluo
 * @date 19 Jan 2017
 * @time 14:16:09
 *
 */
public class NewCitationScoredTest {
//    @Test
//    public void shouldCitationScore0() {
//
//        try {
//
//            SwissProtEntryText text = new SwissProtEntryText();
//            text.setRPLine("RP   SUBCELLULAR LOCATION, INTERACTION WITH PKC-3, PHOSPHORYLATION SITES\n" +
//                    "RP   SER-17 AND SER-65, AND MUTAGENESIS OF SER-17 AND SER-65.");
//
//            UniProtEntry entry = UniProtParser.parse(text.getText(), DefaultUniProtFactory.getInstance());
//
//            assertTrue(entry.getCitationsNew(CitationTypeEnum.JOURNAL_ARTICLE).size() == 1);
//            List<JournalArticle> ja = entry.getCitationsNew(CitationTypeEnum.JOURNAL_ARTICLE);
//            NewCitationScored scored = new NewCitationScored(ja.get(0));
//
//            assertEquals(0.0, scored.score(), 0.0001);
//
//        } catch (UniProtParserException e) {
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    public void shouldCitation2Score0() {
//
//        try {
//
//            SwissProtEntryText text = new SwissProtEntryText();
//            text.setRPLine("RP   PROTEIN SEQUENCE OF 3-20.");
//
//            UniProtEntry entry = UniProtParser.parse(text.getText(), DefaultUniProtFactory.getInstance());
//
//            assertTrue(entry.getCitationsNew(CitationTypeEnum.JOURNAL_ARTICLE).size() == 1);
//            List<JournalArticle> ja = entry.getCitationsNew(CitationTypeEnum.JOURNAL_ARTICLE);
//            NewCitationScored scored = new NewCitationScored(ja.get(0));
//            //
//            assertEquals(0.0, scored.score(), 0.001);
//
//        } catch (UniProtParserException e) {
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    public void shouldCitation3Score0() {
//
//        try {
//
//            SwissProtEntryText text = new SwissProtEntryText();
//            text.setRPLine("RP   PROTEIN SEQUENCE OF 3-20.");
//
//            UniProtEntry entry = UniProtParser.parse(text.getText(), DefaultUniProtFactory.getInstance());
//
//            assertTrue(entry.getCitationsNew(CitationTypeEnum.JOURNAL_ARTICLE).size() == 1);
//            List<JournalArticle> ja = entry.getCitationsNew(CitationTypeEnum.JOURNAL_ARTICLE);
//            NewCitationScored scored = new NewCitationScored(ja.get(0));
//            //
//            assertEquals(0.0, scored.score(), 0.001);
//
//        } catch (UniProtParserException e) {
//            fail(e.getMessage());
//        }
//    }
//
//    @Test
//    public void shouldCitation4Score0() {
//
//        try {
//
//            SwissProtEntryText text = new SwissProtEntryText();
//            text.setRPLine("RP   ACETYLATION [LARGE SCALE ANALYSIS] AT LYS-70 AND LYS-117, AND MASS\n" +
//                    "RP   SPECTROMETRY.");
//
//            UniProtEntry entry = UniProtParser.parse(text.getText(), DefaultUniProtFactory.getInstance());
//
//            assertTrue(entry.getCitationsNew(CitationTypeEnum.JOURNAL_ARTICLE).size() == 1);
//            List<JournalArticle> ja = entry.getCitationsNew(CitationTypeEnum.JOURNAL_ARTICLE);
//            NewCitationScored scored = new NewCitationScored(ja.get(0));
//            //
//            assertEquals(0.0, scored.score(), 0.001);
//
//        } catch (UniProtParserException e) {
//            fail(e.getMessage());
//        }
//    }

    @Test
//    public void shouldCitation5Score0() {
//
//        try {
//
//            SwissProtEntryText text = new SwissProtEntryText();
//            text.setRPLine("RP   NUCLEOTIDE SEQUENCE [MRNA].");
//
//            UniProtEntry entry = UniProtParser.parse(text.getText(), DefaultUniProtFactory.getInstance());
//
//            assertTrue(entry.getCitationsNew(CitationTypeEnum.JOURNAL_ARTICLE).size() == 1);
//            List<JournalArticle> ja = entry.getCitationsNew(CitationTypeEnum.JOURNAL_ARTICLE);
//            NewCitationScored scored = new NewCitationScored(ja.get(0));
//            //
//            assertEquals(0.0, scored.score(), 0.001);
//
//        } catch (UniProtParserException e) {
//            fail(e.getMessage());
//        }
//    }

    private List<UniProtReference> parseLines(String lines) {
        // use DefaultUniProtParser and a stub entry but replace RP lines
        return null;
    }

}