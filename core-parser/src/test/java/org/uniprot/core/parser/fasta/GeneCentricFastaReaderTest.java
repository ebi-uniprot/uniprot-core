package org.uniprot.core.parser.fasta;

import org.junit.jupiter.api.Test;
import org.uniprot.core.genecentric.GeneCentricEntry;
import org.uniprot.core.genecentric.Protein;

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
    }

    @Test
    void parse2(){
        String fastaInput = ">tr|Q9HI29|Q9HI29_HALSA Isoform of O51964, Vng6012h OS=Halobacterium salinarum (strain ATCC 700922 / JCM 11081 / NRC-1) (Halobacterium halobium) OX=64091 GN=VNG_6012H PE=4 SV=1\n" +
                "MSVHLGIGTGMRNNTIGHVYKDWFFYDSDGNLYIQIPGSDKCRKGNGDGTCGHCEDGGQY\n" +
                "NPKTEAGDGRIILIPNSWHNHANEGEEEYFGLKDHVEAYFALDGKNAPDNVRYGKDMIQG\n" +
                "NGVSKGPLNKWVRDVAAKSAIQPQLRMKRLEQAGVPNGNGEDEPKKIKDFGHDDEGNKIP\n" +
                "DMIFHDLRACYCTQLMRNEVPPHKAINKTGHADPDSLKPYVMFAANEIDAKEEQQWY";
        Protein result = GeneCentricFastaReader.parse(fastaInput);
        assertNotNull(result);
    }
}