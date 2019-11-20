package org.uniprot.core.scorer.uniprotkb;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.DefaultUniProtParser;
import org.uniprot.core.flatfile.parser.impl.SupportingDataMapImpl;
import org.uniprot.core.uniprot.UniProtEntry;

/**
 * Created by IntelliJ IDEA. User: spatient Date: 02/11/11 Time: 14:31 To change this template use
 * File | Settings | File Templates.
 */
class UniProtEntryScorerTest {
    private static final String ENTRY =
            "ID   013L_IIV3               Reviewed;          90 AA.\n" + 
            "AC   Q197E7;\n" + 
            "DT   16-JUN-2009, integrated into UniProtKB/Swiss-Prot.\n" + 
            "DT   11-JUL-2006, sequence version 1.\n" + 
            "DT   11-DEC-2019, entry version 32.\n" + 
            "DE   RecName: Full=Uncharacterized protein IIV3-013L;\n" + 
            "GN   ORFNames=IIV3-013L;\n" + 
            "OS   Invertebrate iridescent virus 3 (IIV-3) (Mosquito iridescent virus).\n" + 
            "OC   Viruses; Iridoviridae; Betairidovirinae; Chloriridovirus.\n" + 
            "OX   NCBI_TaxID=345201;\n" + 
            "OH   NCBI_TaxID=7163; Aedes vexans (Inland floodwater mosquito) (Culex vexans).\n" + 
            "OH   NCBI_TaxID=42431; Culex territans.\n" + 
            "OH   NCBI_TaxID=332058; Culiseta annulata.\n" + 
            "OH   NCBI_TaxID=310513; Ochlerotatus sollicitans (eastern saltmarsh mosquito).\n" + 
            "OH   NCBI_TaxID=329105; Ochlerotatus taeniorhynchus (Black salt marsh mosquito) (Aedes taeniorhynchus).\n" + 
            "OH   NCBI_TaxID=7183; Psorophora ferox.\n" + 
            "RN   [1]\n" + 
            "RP   NUCLEOTIDE SEQUENCE [LARGE SCALE GENOMIC DNA].\n" + 
            "RX   PubMed=16912294; DOI=10.1128/JVI.00464-06;\n" + 
            "RA   Delhon G., Tulman E.R., Afonso C.L., Lu Z., Becnel J.J., Moser B.A.,\n" + 
            "RA   Kutish G.F., Rock D.L.;\n" + 
            "RT   \"Genome of invertebrate iridescent virus type 3 (mosquito iridescent\n" + 
            "RT   virus).\";\n" + 
            "RL   J. Virol. 80:8439-8449(2006).\n" + 
            "CC   -!- SUBCELLULAR LOCATION: Host membrane {ECO:0000305}; Single-pass membrane\n" + 
            "CC       protein {ECO:0000305}.\n" + 
            "CC   ---------------------------------------------------------------------------\n" + 
            "CC   Copyrighted by the UniProt Consortium, see https://www.uniprot.org/terms\n" + 
            "CC   Distributed under the Creative Commons Attribution (CC BY 4.0) License\n" + 
            "CC   ---------------------------------------------------------------------------\n" + 
            "DR   EMBL; DQ643392; ABF82043.1; -; Genomic_DNA.\n" + 
            "DR   RefSeq; YP_654585.1; NC_008187.1.\n" + 
            "DR   SMR; Q197E7; -.\n" + 
            "DR   GeneID; 4156262; -.\n" + 
            "DR   KEGG; vg:4156262; -.\n" + 
            "DR   OrthoDB; 14281at10239; -.\n" + 
            "DR   Proteomes; UP000001358; Genome.\n" + 
            "DR   GO; GO:0033644; C:host cell membrane; IEA:UniProtKB-SubCell.\n" + 
            "DR   GO; GO:0016021; C:integral component of membrane; IEA:UniProtKB-KW.\n" + 
            "PE   4: Predicted;\n" + 
            "KW   Host membrane; Membrane; Reference proteome; Transmembrane;\n" + 
            "KW   Transmembrane helix.\n" + 
            "FT   CHAIN           1..90\n" + 
            "FT                   /note=\"Uncharacterized protein IIV3-013L\"\n" + 
            "FT                   /id=\"PRO_0000377942\"\n" + 
            "FT   TRANSMEM        52..72\n" + 
            "FT                   /note=\"Helical\"\n" + 
            "FT                   /evidence=\"ECO:0000255\"\n" + 
            "SQ   SEQUENCE   90 AA;  10851 MW;  077C22D16315DB07 CRC64;\n" + 
            "     MYYRDQYGNV KYAPEGMGPH HAASSSHHSA QHHHMTKENF SMDDVHSWFE KYKMWFLYAL\n" + 
            "     ILALIFGVFM WWSKYNHDKK RSLNTASIFY\n" + 
            "//\n" ; 


    @Test
    void test1() throws IOException {
        UniProtEntry entry =
                new DefaultUniProtParser(new SupportingDataMapImpl(), true).parse(ENTRY);

        UniProtEntryScorer scored = new UniProtEntryScorer(System.out);
        scored.startUp();
        scored.scoreEntry(entry);
        scored.shutDown();
        assertEquals(1, scored.getTotalScore().getCount());
        assertEquals(14.7, scored.getTotalScore().getSum(), 0.0001);
        assertEquals(14.7, scored.getTotalScore().getMax(), 0.0001);
    }
}
