package org.uniprot.core.scorer.uniprotkb.dbx;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.uniprot.core.flatfile.parser.impl.DefaultUniProtParser;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.scorer.uniprotkb.HasScore;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;

/**
 * Created 27/02/19
 *
 * @author Edd
 */
abstract class AbstractDBXTest {
    private static final String DR_TO_REPLACE = "DR_TO_REPLACE";

    abstract HasScore getScored(String lines);

    void testDBXrefScore(String line, double expectedScore) {
        assertEquals(expectedScore, getScored(line).score(), 0.001);
    }

    List<UniProtKBCrossReference> getDBXRefs(String citationLines, String type) {
        UniProtKBEntry entry =
                new DefaultUniProtParser(new SupportingDataMapImpl("", "", "", ""), true)
                        .parse(trEMBLEntry(citationLines));
        return entry.getUniProtCrossReferencesByType(type);
    }

    private static String trEMBLEntry(String citationLines) {
        String entryStr =
                "ID   A0A1I1NX59_9BURK        Unreviewed;       558 AA.\n"
                        + "AC   A0A1I1NX59;\n"
                        + "DT   22-NOV-2017, integrated into UniProtKB/TrEMBL.\n"
                        + "DT   22-NOV-2017, sequence version 1.\n"
                        + "DT   13-FEB-2019, entry version 9.\n"
                        + "DE   SubName: Full=DNA-binding response regulator, OmpR family, contains REC and winged-helix (WHTH) domain {ECO:0000313|EMBL:SFD02281.1};\n"
                        + "GN   ORFNames=SAMN05216204_11479 {ECO:0000313|EMBL:SFD02281.1};\n"
                        + "OS   Massilia yuzhufengensis.\n"
                        + "OC   Bacteria; Proteobacteria; Betaproteobacteria; Burkholderiales;\n"
                        + "OC   Oxalobacteraceae; Massilia.\n"
                        + "OX   NCBI_TaxID=1164594 {ECO:0000313|EMBL:SFD02281.1, ECO:0000313|Proteomes:UP000198639};\n"
                        + "RN   [1]\n"
                        + "RP   PROTEIN SEQUENCE OF 3-20.\n"
                        + "RX   PubMed=9497243;\n"
                        + "RA   Daniele A., Parenti G., D'Addio M., Andria G., Ballabio A., Meroni G.;\n"
                        + "RT   \"Biochemical characterization of arylsulfatase E.\";\n"
                        + "RL   Am. J. Hum. Genet. 62:562-577(1998).\n"
                        + "CC   -----------------------------------------------------------------------\n"
                        + "CC   Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms\n"
                        + "CC   Distributed under the Creative Commons Attribution (CC BY 4.0) License\n"
                        + "CC   -----------------------------------------------------------------------\n"
                        + DR_TO_REPLACE
                        + "\n"
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
                        + "FT                   /evidence=\"ECO:0000256|PROSITE-ProRule:PRU00339\"\n"
                        + "FT   REPEAT          241..274\n"
                        + "FT                   /note=\"TPR\"\n"
                        + "FT                   /evidence=\"ECO:0000256|PROSITE-ProRule:PRU00339\"\n"
                        + "FT   REPEAT          275..308\n"
                        + "FT                   /note=\"TPR\"\n"
                        + "FT                   /evidence=\"ECO:0000256|PROSITE-ProRule:PRU00339\"\n"
                        + "FT   REPEAT          455..488\n"
                        + "FT                   /note=\"TPR\"\n"
                        + "FT                   /evidence=\"ECO:0000256|PROSITE-ProRule:PRU00339\"\n"
                        + "FT   DOMAIN          455..488\n"
                        + "FT                   /note=\"TPR_REGION\"\n"
                        + "FT                   /evidence=\"ECO:0000259|PROSITE:PS50293\"\n"
                        + "FT   MOD_RES         68\n"
                        + "FT                   /note=\"4-aspartylphosphate\"\n"
                        + "FT                   /evidence=\"ECO:0000256|PROSITE-ProRule:PRU00169\"\n"
                        + "SQ   SEQUENCE   558 AA;  62745 MW;  DDA8B194737898EA CRC64;\n"
                        + "     MNAFTTPATD QVDWASKTYL VVDDFVGVRQ LLREALRSLG ARTIDQAASG GEAMGLLNKT\n"
                        + "     RYDVVLCDFN LGEGKNGQQV LEEARMRNLL QPSSVFLMVS AEKSVESVMG AAEHQPDAYL\n"
                        + "     VKPITEGVLL SRLNRVWRKK QVFSLIDQAY VEKDYLRAAR LCDEQVVDNK VHEIDLLRMK\n"
                        + "     ARLMEKSGEP GKARETYERV LAQREYQWAR SGLAKIRMAD GEYEQARQMF QTVIAENRYY\n"
                        + "     IDAYDQLALA YQNLGKHEEA LGILERAAKL SPNSVPRQRN LGQACLKLGN IGMAEKAFRK\n"
                        + "     CISIGEYSIR KTPDAYLGLA RVCGLKNDPK EALQLLIAAQ REFGADHPDL ELRTKITEGL\n"
                        + "     VYHESGDYRR ARKAGDELEA LLQSTSERPD VTTCLEMATL LFAVGCKEAP VDLLCYVIRN\n"
                        + "     NHDNPLLHDD VQKIFEKARM GEEGEGLIRG ARKEAVDLMN QGVLLWKTNK LAEAVEWMRN\n"
                        + "     ARTALPHNAR ILFNSVQILV SHMQQRGYSA ELSEEAHVVL ATVDRLQPGQ QRFAQLMEQL\n"
                        + "     MLLVPKEEPV ALAEAAAV\n"
                        + "//\n";
        return entryStr.replace(DR_TO_REPLACE, citationLines);
    }
}
