package org.uniprot.core.scorer.uniprotkb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.Citation;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.flatfile.parser.impl.DefaultUniProtParser;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.UniProtKBReference;

/**
 * @author jieluo
 * @date 19 Jan 2017
 * @time 14:16:09
 */
class NewCitationScoredTest {
    private static final String RP_TO_REPLACE = "RP_TO_REPLACE";

    @Test
    void shouldCitationScore0() {
        List<UniProtKBReference> uniProtKBReferences =
                parseLines(
                        "RP   SUBCELLULAR LOCATION, INTERACTION WITH PKC-3, PHOSPHORYLATION SITES\n"
                                + "RP   SER-17 AND SER-65, AND MUTAGENESIS OF SER-17 AND SER-65.");
        assertEquals(1, uniProtKBReferences.size());
        Citation citation = uniProtKBReferences.get(0).getCitation();
        assertEquals(CitationType.JOURNAL_ARTICLE, citation.getCitationType());
        assertEquals(0.0, new NewCitationScored(citation).score(), 0.001);
    }

    @Test
    void shouldCitation2Score0() {
        List<UniProtKBReference> uniProtKBReferences = parseLines("RP   PROTEIN SEQUENCE OF 3-20.");
        assertEquals(1, uniProtKBReferences.size());
        Citation citation = uniProtKBReferences.get(0).getCitation();
        assertEquals(CitationType.JOURNAL_ARTICLE, citation.getCitationType());
        assertEquals(0.0, new NewCitationScored(citation).score(), 0.001);
    }

    @Test
    void shouldCitation3Score0() {
        List<UniProtKBReference> uniProtKBReferences = parseLines("RP   PROTEIN SEQUENCE OF 3-20.");
        assertEquals(1, uniProtKBReferences.size());
        Citation citation = uniProtKBReferences.get(0).getCitation();
        assertEquals(CitationType.JOURNAL_ARTICLE, citation.getCitationType());
        assertEquals(0.0, new NewCitationScored(citation).score(), 0.001);
    }

    @Test
    void shouldCitation4Score0() {
        List<UniProtKBReference> uniProtKBReferences =
                parseLines(
                        "RP   ACETYLATION [LARGE SCALE ANALYSIS] AT LYS-70 AND LYS-117, AND MASS\n"
                                + "RP   SPECTROMETRY.");
        assertEquals(1, uniProtKBReferences.size());
        Citation citation = uniProtKBReferences.get(0).getCitation();
        assertEquals(CitationType.JOURNAL_ARTICLE, citation.getCitationType());
        assertEquals(0.0, new NewCitationScored(citation).score(), 0.001);
    }

    @Test
    void shouldCitation5Score0() {
        List<UniProtKBReference> uniProtKBReferences =
                parseLines("RP   NUCLEOTIDE SEQUENCE [MRNA].");
        assertEquals(1, uniProtKBReferences.size());
        Citation citation = uniProtKBReferences.get(0).getCitation();
        assertEquals(CitationType.JOURNAL_ARTICLE, citation.getCitationType());
        assertEquals(0.0, new NewCitationScored(citation).score(), 0.001);
    }

    private List<UniProtKBReference> parseLines(String citationLines) {
        UniProtKBEntry entry =
                new DefaultUniProtParser(new SupportingDataMapImpl(), true)
                        .parse(swissProtEntry(citationLines));
        return entry.getReferences();
    }

    private String swissProtEntry(String citationLines) {
        String entryStr =
                "ID   A0A1I1NX59_9BURK        Unreviewed;       558 AA.\n"
                        + "AC   A0A1I1NX59;\n"
                        + "DT   22-NOV-2017, integrated into UniProtKB/TrEMBL.\n"
                        + "DT   22-NOV-2017, sequence version 1.\n"
                        + "DT   11-DEC-2019, entry version 10.\n"
                        + "DE   SubName: Full=DNA-binding response regulator, OmpR family, contains"
                        + " REC and winged-helix (WHTH) domain {ECO:0000313|EMBL:SFD02281.1};\n"
                        + "GN   ORFNames=SAMN05216204_11479 {ECO:0000313|EMBL:SFD02281.1};\n"
                        + "OS   Massilia yuzhufengensis.\n"
                        + "OC   Bacteria; Proteobacteria; Betaproteobacteria; Burkholderiales;\n"
                        + "OC   Oxalobacteraceae; Massilia.\n"
                        + "OX   NCBI_TaxID=1164594 {ECO:0000313|EMBL:SFD02281.1,"
                        + " ECO:0000313|Proteomes:UP000198639};\n"
                        + "RN   [1]\n"
                        + RP_TO_REPLACE
                        + "\n"
                        + "RX   PubMed=9497243;\n"
                        + "RA   Daniele A., Parenti G., D'Addio M., Andria G., Ballabio A., Meroni"
                        + " G.;\n"
                        + "RT   \"Biochemical characterization of arylsulfatase E.\";\n"
                        + "RL   Am. J. Hum. Genet. 62:562-577(1998).\n"
                        + "CC   ---------------------------------------------------------------------------\n"
                        + "CC   Copyrighted by the UniProt Consortium, see"
                        + " https://www.uniprot.org/terms\n"
                        + "CC   Distributed under the Creative Commons Attribution (CC BY 4.0)"
                        + " License\n"
                        + "CC   ---------------------------------------------------------------------------\n"
                        + "DR   EMBL; FOLD01000014; SFD02281.1; -; Genomic_DNA.\n"
                        + "DR   BioCyc; GCF_900112225:BM031_RS13335-MONOMER; -.\n"
                        + "DR   Proteomes; UP000198639; Unassembled WGS sequence.\n"
                        + "DR   GO; GO:0003677; F:DNA binding; IEA:UniProtKB-KW.\n"
                        + "DR   GO; GO:0042802; F:identical protein binding; IEA:InterPro.\n"
                        + "DR   GO; GO:0000160; P:phosphorelay signal transduction system;"
                        + " IEA:InterPro.\n"
                        + "DR   CDD; cd00156; REC; 1.\n"
                        + "DR   Gene3D; 1.25.40.10; -; 1.\n"
                        + "DR   InterPro; IPR011006; CheY-like_superfamily.\n"
                        + "DR   InterPro; IPR001789; Sig_transdc_resp-reg_receiver.\n"
                        + "DR   InterPro; IPR011717; TPR-4.\n"
                        + "DR   InterPro; IPR013026; TPR-contain_dom.\n"
                        + "DR   InterPro; IPR011990; TPR-like_helical_dom_sf.\n"
                        + "DR   InterPro; IPR013105; TPR_2.\n"
                        + "DR   InterPro; IPR019734; TPR_repeat.\n"
                        + "DR   Pfam; PF00072; Response_reg; 1.\n"
                        + "DR   Pfam; PF07719; TPR_2; 1.\n"
                        + "DR   Pfam; PF07721; TPR_4; 1.\n"
                        + "DR   Pfam; PF13181; TPR_8; 1.\n"
                        + "DR   SMART; SM00448; REC; 1.\n"
                        + "DR   SMART; SM00028; TPR; 6.\n"
                        + "DR   SUPFAM; SSF48452; SSF48452; 2.\n"
                        + "DR   SUPFAM; SSF52172; SSF52172; 1.\n"
                        + "DR   PROSITE; PS50110; RESPONSE_REGULATORY; 1.\n"
                        + "DR   PROSITE; PS50005; TPR; 4.\n"
                        + "DR   PROSITE; PS50293; TPR_REGION; 2.\n"
                        + "PE   4: Predicted;\n"
                        + "KW   DNA-binding {ECO:0000313|EMBL:SFD02281.1};\n"
                        + "KW   Phosphoprotein {ECO:0000256|PROSITE-ProRule:PRU00169};\n"
                        + "KW   TPR repeat {ECO:0000256|PROSITE-ProRule:PRU00339}.\n"
                        + "FT   DOMAIN          18..137\n"
                        + "FT                   /note=\"Response regulatory\"\n"
                        + "FT                   /evidence=\"ECO:0000259|PROSITE:PS50110\"\n"
                        + "FT   DOMAIN          207..308\n"
                        + "FT                   /note=\"TPR_REGION\"\n"
                        + "FT                   /evidence=\"ECO:0000259|PROSITE:PS50293\"\n"
                        + "FT   REPEAT          207..240\n"
                        + "FT                   /note=\"TPR\"\n"
                        + "FT                  "
                        + " /evidence=\"ECO:0000256|PROSITE-ProRule:PRU00339\"\n"
                        + "FT   REPEAT          241..274\n"
                        + "FT                   /note=\"TPR\"\n"
                        + "FT                  "
                        + " /evidence=\"ECO:0000256|PROSITE-ProRule:PRU00339\"\n"
                        + "FT   REPEAT          275..308\n"
                        + "FT                   /note=\"TPR\"\n"
                        + "FT                  "
                        + " /evidence=\"ECO:0000256|PROSITE-ProRule:PRU00339\"\n"
                        + "FT   REPEAT          455..488\n"
                        + "FT                   /note=\"TPR\"\n"
                        + "FT                  "
                        + " /evidence=\"ECO:0000256|PROSITE-ProRule:PRU00339\"\n"
                        + "FT   DOMAIN          455..488\n"
                        + "FT                   /note=\"TPR_REGION\"\n"
                        + "FT                   /evidence=\"ECO:0000259|PROSITE:PS50293\"\n"
                        + "FT   MOD_RES         68\n"
                        + "FT                   /note=\"4-aspartylphosphate\"\n"
                        + "FT                  "
                        + " /evidence=\"ECO:0000256|PROSITE-ProRule:PRU00169\"\n"
                        + "SQ   SEQUENCE   558 AA;  62745 MW;  DDA8B194737898EA CRC64;\n"
                        + "     MNAFTTPATD QVDWASKTYL VVDDFVGVRQ LLREALRSLG ARTIDQAASG"
                        + " GEAMGLLNKT\n"
                        + "     RYDVVLCDFN LGEGKNGQQV LEEARMRNLL QPSSVFLMVS AEKSVESVMG"
                        + " AAEHQPDAYL\n"
                        + "     VKPITEGVLL SRLNRVWRKK QVFSLIDQAY VEKDYLRAAR LCDEQVVDNK"
                        + " VHEIDLLRMK\n"
                        + "     ARLMEKSGEP GKARETYERV LAQREYQWAR SGLAKIRMAD GEYEQARQMF"
                        + " QTVIAENRYY\n"
                        + "     IDAYDQLALA YQNLGKHEEA LGILERAAKL SPNSVPRQRN LGQACLKLGN"
                        + " IGMAEKAFRK\n"
                        + "     CISIGEYSIR KTPDAYLGLA RVCGLKNDPK EALQLLIAAQ REFGADHPDL"
                        + " ELRTKITEGL\n"
                        + "     VYHESGDYRR ARKAGDELEA LLQSTSERPD VTTCLEMATL LFAVGCKEAP"
                        + " VDLLCYVIRN\n"
                        + "     NHDNPLLHDD VQKIFEKARM GEEGEGLIRG ARKEAVDLMN QGVLLWKTNK"
                        + " LAEAVEWMRN\n"
                        + "     ARTALPHNAR ILFNSVQILV SHMQQRGYSA ELSEEAHVVL ATVDRLQPGQ"
                        + " QRFAQLMEQL\n"
                        + "     MLLVPKEEPV ALAEAAAV\n"
                        + "//\n";
        return entryStr.replace(RP_TO_REPLACE, citationLines);
    }
}
