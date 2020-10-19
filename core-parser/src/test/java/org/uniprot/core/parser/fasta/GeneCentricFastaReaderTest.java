package org.uniprot.core.parser.fasta;

import org.junit.jupiter.api.Test;
import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.FlagType;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 17/10/2020
 */
class GeneCentricFastaReaderTest {

    @Test
    void parse() {
        String fastaInput = ">tr|Q9HI14|Q9HI14_HALSA Isoform of O51971, Transcription initiation factor IID OS=Halobacterium salinarum (strain ATCC 700922 / JCM 11081 / NRC-1) (Halobacterium halobium) OX=64091 GN=tbpA PE=3 SV=1\n" +
                "MDLEGADYDPEQFPGLVYRLDEPSVVALLFGSGKLVITGGKHPVDAEHAVDTIDSRLEDL\nGLLDGYGDRAK";
        Protein result = GeneCentricFastaReader.parse(fastaInput);
        assertNotNull(result);
        assertEquals(UniProtKBEntryType.TREMBL, result.getEntryType());
        assertNotNull(result.getAccession());
        assertEquals("Q9HI14", result.getAccession().getValue());
        assertNotNull(result.getUniProtkbId());
        assertEquals("Q9HI14_HALSA", result.getUniProtkbId().getValue());
        assertEquals("Isoform of O51971, Transcription initiation factor IID", result.getProteinName());

        assertNotNull(result.getOrganism());
        assertEquals("tbpA", result.getGeneName());
        assertEquals(ProteinExistence.HOMOLOGY, result.getProteinExistence());
        assertEquals(FlagType.PRECURSOR, result.getFlagType());
        assertEquals(1, result.getSequenceVersion());
        assertNotNull(result.getSequence());
        assertEquals("MDLEGADYDPEQFPGLVYRLDEPSVVALLFGSGKLVITGGKHPVDAEHAVDTIDSRLEDLGLLDGYGDRAK", result.getSequence().getValue());

    }

    @Test
    void parseTrembl(){
        String fastaInput = ">tr|Q9HI29|Q9HI29_HALSA Isoform of O51964, Vng6012h OS=Halobacterium salinarum (strain ATCC 700922 / JCM 11081 / NRC-1) (Halobacterium halobium) OX=64091 GN=VNG_6012H PE=4 SV=1\n" +
                "MSVHLGIGTGMRNNTIGHVYKDWFFYDSDGNLYIQIPGSDKCRKGNGDGTCGHCEDGGQY\n" +
                "NPKTEAGDGRIILIPNSWHNHANEGEEEYFGLKDHVEAYFALDGKNAPDNVRYGKDMIQG\n" +
                "NGVSKGPLNKWVRDVAAKSAIQPQLRMKRLEQAGVPNGNGEDEPKKIKDFGHDDEGNKIP\n" +
                "DMIFHDLRACYCTQLMRNEVPPHKAINKTGHADPDSLKPYVMFAANEIDAKEEQQWY";
        Protein result = GeneCentricFastaReader.parse(fastaInput);
        assertNotNull(result);
    }

    @Test
    void parseSwissProtFragmentSequence(){
        String fastaInput = ">sp|P34935|BIP_PIG Endoplasmic reticulum chaperone BiP (Fragment) OS=Sus scrofa OX=9823 GN=HSPA5 PE=2 SV=2\n" +
                "DEIVLVGGSTRIPKIQQLVKEFFNGKEPSRGINPDEAVAYGAAVQAGVLSGDQDTGDLVL\n" +
                "LDVCPLTLGIETVGGVMTKLIPRNTVVPTKKSQIFSTASDNQPTVTIKVYEGERPLTKDN\n" +
                "HLLGTFDLTGIPPAPRGVPQIEVTFEIDVNGILRVTAEDKGTGNKNKITITNDQNRLTPE\n" +
                "EIERMVNDAEKFAEEDKKLKERIDTRNELESYAYCLKNQIGDKEKLGGKLSSEDKETMEK\n" +
                "AVEEKIEWLESHQDADIEDFKA";
        Protein result = GeneCentricFastaReader.parse(fastaInput);
        assertNotNull(result);
    }
}