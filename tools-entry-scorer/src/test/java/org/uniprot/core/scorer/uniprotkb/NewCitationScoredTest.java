package org.uniprot.core.scorer.uniprotkb;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.flatfile.parser.impl.DefaultUniProtParser;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.scorer.uniprotkb.NewCitationScored;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.UniProtReference;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author jieluo
 * @date 19 Jan 2017
 * @time 14:16:09
 */
public class NewCitationScoredTest {
    private static final String RP_TO_REPLACE = "RP_TO_REPLACE";

    @Test
    public void shouldCitationScore0() {
        List<UniProtReference> uniProtReferences = parseLines(
                "RP   SUBCELLULAR LOCATION, INTERACTION WITH PKC-3, PHOSPHORYLATION SITES\n" +
                        "RP   SER-17 AND SER-65, AND MUTAGENESIS OF SER-17 AND SER-65.");
        assertEquals(1, uniProtReferences.size());
        Citation citation = uniProtReferences.get(0).getCitation();
        assertEquals(CitationType.JOURNAL_ARTICLE, citation.getCitationType());
        assertEquals(0.0, new NewCitationScored(citation).score(), 0.001);
    }

    @Test
    public void shouldCitation2Score0() {
        List<UniProtReference> uniProtReferences = parseLines(
                "RP   PROTEIN SEQUENCE OF 3-20.");
        assertEquals(1, uniProtReferences.size());
        Citation citation = uniProtReferences.get(0).getCitation();
        assertEquals(CitationType.JOURNAL_ARTICLE, citation.getCitationType());
        assertEquals(0.0, new NewCitationScored(citation).score(), 0.001);
    }

    @Test
    public void shouldCitation3Score0() {
        List<UniProtReference> uniProtReferences = parseLines(
                "RP   PROTEIN SEQUENCE OF 3-20.");
        assertEquals(1, uniProtReferences.size());
        Citation citation = uniProtReferences.get(0).getCitation();
        assertEquals(CitationType.JOURNAL_ARTICLE, citation.getCitationType());
        assertEquals(0.0, new NewCitationScored(citation).score(), 0.001);
    }

    @Test
    public void shouldCitation4Score0() {
        List<UniProtReference> uniProtReferences = parseLines(
                "RP   ACETYLATION [LARGE SCALE ANALYSIS] AT LYS-70 AND LYS-117, AND MASS\n" +
                        "RP   SPECTROMETRY.");
        assertEquals(1, uniProtReferences.size());
        Citation citation = uniProtReferences.get(0).getCitation();
        assertEquals(CitationType.JOURNAL_ARTICLE, citation.getCitationType());
        assertEquals(0.0, new NewCitationScored(citation).score(), 0.001);
    }

    @Test
    public void shouldCitation5Score0() {
        List<UniProtReference> uniProtReferences = parseLines("RP   NUCLEOTIDE SEQUENCE [MRNA].");
        assertEquals(1, uniProtReferences.size());
        Citation citation = uniProtReferences.get(0).getCitation();
        assertEquals(CitationType.JOURNAL_ARTICLE, citation.getCitationType());
        assertEquals(0.0, new NewCitationScored(citation).score(), 0.001);
    }

    private List<UniProtReference> parseLines(String citationLines) {
        UniProtEntry entry = new DefaultUniProtParser(new SupportingDataMapImpl(), true)
                .parse(swissProtEntry(citationLines));
        return entry.getReferences();
    }

    private String swissProtEntry(String citationLines) {
        String entryStr = "ID   A0A1I1NX59_9BURK        Reviewed;       558 AA.\n" +
                "AC   A0A1I1NX59;\n" +
                "DT   22-NOV-2017, integrated into UniProtKB/TrEMBL.\n" +
                "DT   22-NOV-2017, sequence version 1.\n" +
                "DT   13-FEB-2019, entry version 9.\n" +
                "DE   SubName: Full=DNA-binding response regulator, OmpR family, contains REC and winged-helix (WHTH) domain {ECO:0000313|EMBL:SFD02281.1};\n" +
                "GN   ORFNames=SAMN05216204_11479 {ECO:0000313|EMBL:SFD02281.1};\n" +
                "OS   Massilia yuzhufengensis.\n" +
                "OC   Bacteria; Proteobacteria; Betaproteobacteria; Burkholderiales;\n" +
                "OC   Oxalobacteraceae; Massilia.\n" +
                "OX   NCBI_TaxID=1164594 {ECO:0000313|EMBL:SFD02281.1, ECO:0000313|Proteomes:UP000198639};\n" +
                "RN   [1]\n" +
                RP_TO_REPLACE + "\n" +
                "RX   PubMed=9497243;\n" +
                "RA   Daniele A., Parenti G., D'Addio M., Andria G., Ballabio A., Meroni G.;\n" +
                "RT   \"Biochemical characterization of arylsulfatase E.\";\n" +
                "RL   Am. J. Hum. Genet. 62:562-577(1998).\n" +
                "CC   -----------------------------------------------------------------------\n" +
                "CC   Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms\n" +
                "CC   Distributed under the Creative Commons Attribution (CC BY 4.0) License\n" +
                "CC   -----------------------------------------------------------------------\n" +
                "DR   EMBL; FOLD01000014; SFD02281.1; -; Genomic_DNA.\n" +
                "DR   BioCyc; GCF_900112225:BM031_RS13335-MONOMER; -.\n" +
                "PE   4: Predicted;\n" +
                "KW   Complete proteome {ECO:0000313|Proteomes:UP000198639};\n" +
                "KW   TPR repeat {ECO:0000256|PROSITE-ProRule:PRU00339}.\n" +
                "FT   DOMAIN       18    137       Response regulatory.\n" +
                "FT                                {ECO:0000259|PROSITE:PS50110}.\n" +
                "FT   REPEAT      207    240       TPR. {ECO:0000256|PROSITE-\n" +
                "FT                                ProRule:PRU00339}.\n" +
                "FT   MOD_RES      68     68       4-aspartylphosphate.\n" +
                "FT                                {ECO:0000256|PROSITE-ProRule:PRU00169}.\n" +
                "SQ   SEQUENCE   558 AA;  62745 MW;  DDA8B194737898EA CRC64;\n" +
                "     MNAFTTPATD QVDWASKTYL VVDDFVGVRQ LLREALRSLG ARTIDQAASG GEAMGLLNKT\n" +
                "     RYDVVLCDFN LGEGKNGQQV LEEARMRNLL QPSSVFLMVS AEKSVESVMG AAEHQPDAYL\n" +
                "     VKPITEGVLL SRLNRVWRKK QVFSLIDQAY VEKDYLRAAR LCDEQVVDNK VHEIDLLRMK\n" +
                "     ARLMEKSGEP GKARETYERV LAQREYQWAR SGLAKIRMAD GEYEQARQMF QTVIAENRYY\n" +
                "     IDAYDQLALA YQNLGKHEEA LGILERAAKL SPNSVPRQRN LGQACLKLGN IGMAEKAFRK\n" +
                "     CISIGEYSIR KTPDAYLGLA RVCGLKNDPK EALQLLIAAQ REFGADHPDL ELRTKITEGL\n" +
                "     VYHESGDYRR ARKAGDELEA LLQSTSERPD VTTCLEMATL LFAVGCKEAP VDLLCYVIRN\n" +
                "     NHDNPLLHDD VQKIFEKARM GEEGEGLIRG ARKEAVDLMN QGVLLWKTNK LAEAVEWMRN\n" +
                "     ARTALPHNAR ILFNSVQILV SHMQQRGYSA ELSEEAHVVL ATVDRLQPGQ QRFAQLMEQL\n" +
                "     MLLVPKEEPV ALAEAAAV\n" +
                "//\n";
        return entryStr.replace(RP_TO_REPLACE, citationLines);
    }
}