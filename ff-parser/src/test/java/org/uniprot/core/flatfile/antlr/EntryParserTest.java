package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.entry.EntryObject;

class EntryParserTest {
    @Test
    void test() {
        String entryLines =
                "ID   001R_FRG3G              Reviewed;         256 AA.\n"
                        + "AC   Q6GZX4;\n"
                        + "DT   28-JUN-2011, integrated into UniProtKB/Swiss-Prot.\n"
                        + "DT   19-JUL-2004, sequence version 1.\n"
                        + "DT   18-APR-2012, entry version 24.\n"
                        + "DE   RecName: Full=Putative transcription factor 001R;\n"
                        + "GN   ORFNames=FV3-001R;\n"
                        + "OS   Frog virus 3 (isolate Goorha) (FV-3).\n"
                        + "OC   Viruses; dsDNA viruses, no RNA stage; Iridoviridae; Ranavirus.\n"
                        + "OX   NCBI_TaxID=654924;\n"
                        + "OH   NCBI_TaxID=8295; Ambystoma (mole salamanders).\n"
                        + "OH   NCBI_TaxID=30343; Hyla versicolor (chameleon treefrog).\n"
                        + "OH   NCBI_TaxID=8316; Notophthalmus viridescens (Eastern newt) (Triturus viridescens).\n"
                        + "OH   NCBI_TaxID=8404; Rana pipiens (Northern leopard frog).\n"
                        + "OH   NCBI_TaxID=45438; Rana sylvatica (Wood frog).\n"
                        + "RN   [1]\n"
                        + "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n"
                        + "RX   PubMed=15165820; DOI=10.1016/j.virol.2004.02.019;\n"
                        + "RA   Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K.;\n"
                        + "RT   \"Comparative genomic analyses of frog virus 3, type species of the\n"
                        + "RT   genus Ranavirus (family Iridoviridae).\";\n"
                        + "RL   Virology 323:70-84(2004).\n"
                        + "DR   EMBL; AY548484; AAT09660.1; -; Genomic_DNA.\n"
                        + "DR   RefSeq; YP_031579.1; NC_005946.1.\n"
                        + "DR   ProteinModelPortal; Q6GZX4; -.\n"
                        + "DR   GeneID; 2947773; -.\n"
                        + "DR   ProtClustDB; CLSP2511514; -.\n"
                        + "DR   GO; GO:0006355; P:regulation of transcription, DNA-dependent; IEA:UniProtKB-KW.\n"
                        + "DR   GO; GO:0046782; P:regulation of viral transcription; IEA:InterPro.\n"
                        + "DR   GO; GO:0006351; P:transcription, DNA-dependent; IEA:UniProtKB-KW.\n"
                        + "DR   InterPro; IPR007031; Poxvirus_VLTF3.\n"
                        + "DR   Pfam; PF04947; Pox_VLTF3; 1.\n"
                        + "PE   4: Predicted;\n"
                        + "KW   Activator; Complete proteome; Reference proteome; Transcription;\n"
                        + "KW   Transcription regulation.\n"                     
                        + "FT   TRANSMEM        116..142\n" + 
                          "FT                   /note=\"Helical\"\n" + 
                          "FT                   /evidence=\"ECO:0000256|SAM:Phobius\"\n" + 
                          "FT   TRANSMEM        154..177\n" + 
                          "FT                   /note=\"Helical\"\n" + 
                          "FT                   /evidence=\"ECO:0000256|SAM:Phobius\"\n"
                        + "SQ   SEQUENCE   256 AA;  29735 MW;  B4840739BF7D4121 CRC64;\n"
                        + "     MAFSAEDVLK EYDRRRRMEA LLLSLYYPND RKLLDYKEWS PPRVQVECPK APVEWNNPPS\n"
                        + "     EKGLIVGHFS GIKYKGEKAQ ASEVDVNKMC CWVSKFKDAM RRYQGIQTCK IPGKVLSDLD\n"
                        + "     AKIKAYNLTV EGVEGFVRYS RVTKQHVAAF LKELRHSKQY ENVNLIHYIL TDKRVDIQHL\n"
                        + "     EKDLVKDFKA LVESAHRMRQ GHMINVKYIL YQLLKKHGHG PDGPDILTVK TGSKGVLYDD\n"
                        + "     SFRKIYTDLG WKFTPL\n"
                        + "//\n";
        UniprotLineParser<EntryObject> parser =
                new DefaultUniprotLineParserFactory().createEntryParser();
        EntryObject obj = parser.parse(entryLines);
        assertNotNull(obj);
        assertEquals("Q6GZX4", obj.ac.primaryAcc);
    }
}
