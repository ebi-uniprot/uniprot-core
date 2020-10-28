package org.uniprot.core.parser.fasta.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.FlagType;

/**
 * @author lgonzales
 * @since 22/10/2020
 */
class UniProtKBFastaParserReaderTest {

    @Test
    void parseEntry() {
        String fastaInput =
                ">tr|Q9HI14|Q9HI14_HALSA Isoform of O51971, Transcription initiation factor IID OS=Halobacterium salinarum OX=64091 GN=tbpA PE=1 SV=3\n"
                        + "MDLEGADYDPEQFPGLVYRLDEPSVVALLFGSGKLVITGGKHPVDAEHAVDTIDSRLEDL\nGLLDGYGDRAK";
        UniProtKBFasta result = UniProtKBFastaParserReader.parse(fastaInput);
        assertNotNull(result);
        assertEquals(UniProtKBEntryType.TREMBL, result.getEntryType());
        assertNotNull(result.getId());
        assertEquals("Q9HI14", result.getId());
        assertNotNull(result.getUniProtkbId());
        assertEquals("Q9HI14_HALSA", result.getUniProtkbId().getValue());
        assertEquals(
                "Isoform of O51971, Transcription initiation factor IID", result.getProteinName());

        assertNotNull(result.getOrganism());
        assertEquals(64091L, result.getOrganism().getTaxonId());
        assertEquals("Halobacterium salinarum", result.getOrganism().getScientificName());

        assertEquals("tbpA", result.getGeneName());
        assertEquals(ProteinExistence.PROTEIN_LEVEL, result.getProteinExistence());
        assertEquals(FlagType.PRECURSOR, result.getFlagType());
        assertEquals(3, result.getSequenceVersion());
        assertNotNull(result.getSequence());
        assertEquals(
                "MDLEGADYDPEQFPGLVYRLDEPSVVALLFGSGKLVITGGKHPVDAEHAVDTIDSRLEDLGLLDGYGDRAK",
                result.getSequence().getValue());
    }

    @Test
    void parseTrembl() {
        String fastaInput =
                ">tr|Q9HI29|Q9HI29_HALSA Isoform of O51964, Vng6012h OS=Halobacterium salinarum (strain ATCC 700922 / JCM 11081 / NRC-1) (Halobacterium halobium) OX=64091 GN=VNG_6012H PE=4 SV=1\n"
                        + "MSVHLGIGTGMRNNTIGHVYKDWFFYDSDGNLYIQIPGSDKCRKGNGDGTCGHCEDGGQY\n"
                        + "NPKTEAGDGRIILIPNSWHNHANEGEEEYFGLKDHVEAYFALDGKNAPDNVRYGKDMIQG\n"
                        + "NGVSKGPLNKWVRDVAAKSAIQPQLRMKRLEQAGVPNGNGEDEPKKIKDFGHDDEGNKIP\n"
                        + "DMIFHDLRACYCTQLMRNEVPPHKAINKTGHADPDSLKPYVMFAANEIDAKEEQQWY";
        UniProtKBFasta result = UniProtKBFastaParserReader.parse(fastaInput);
        assertEquals(UniProtKBEntryType.TREMBL, result.getEntryType());
        assertNotNull(result);
    }

    @Test
    void parseSwissProtFragmentSequence() {
        String fastaInput =
                ">sp|P34935|BIP_PIG Endoplasmic reticulum chaperone BiP (Fragment) OS=Sus scrofa OX=9823 GN=HSPA5 PE=2 SV=2\n"
                        + "DEIVLVGGSTRIPKIQQLVKEFFNGKEPSRGINPDEAVAYGAAVQAGVLSGDQDTGDLVL\n"
                        + "LDVCPLTLGIETVGGVMTKLIPRNTVVPTKKSQIFSTASDNQPTVTIKVYEGERPLTKDN\n"
                        + "HLLGTFDLTGIPPAPRGVPQIEVTFEIDVNGILRVTAEDKGTGNKNKITITNDQNRLTPE\n"
                        + "EIERMVNDAEKFAEEDKKLKERIDTRNELESYAYCLKNQIGDKEKLGGKLSSEDKETMEK\n"
                        + "AVEEKIEWLESHQDADIEDFKA";
        UniProtKBFasta result = UniProtKBFastaParserReader.parse(fastaInput);
        assertEquals(UniProtKBEntryType.SWISSPROT, result.getEntryType());
        assertEquals(FlagType.FRAGMENT, result.getFlagType());
        assertNotNull(result);
    }

    @Test
    void parseWithoutGene() {
        String fastaInput =
                ">tr|Q9HI14|Q9HI14_HALSA Isoform of O51971, Transcription initiation factor IID OS=Halobacterium salinarum OX=64091 PE=5 SV=3\n"
                        + "MDLEGADYDPEQFPGLVYRLDEPSVVALLFGSGKLVITGGKHPVDAEHAVDTIDSRLEDL\nGLLDGYGDRAK";
        UniProtKBFasta result = UniProtKBFastaParserReader.parse(fastaInput);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Q9HI14", result.getId());
        assertNull(result.getGeneName());
        assertEquals(ProteinExistence.UNCERTAIN, result.getProteinExistence());
        assertEquals(
                "MDLEGADYDPEQFPGLVYRLDEPSVVALLFGSGKLVITGGKHPVDAEHAVDTIDSRLEDLGLLDGYGDRAK",
                result.getSequence().getValue());
    }
}
