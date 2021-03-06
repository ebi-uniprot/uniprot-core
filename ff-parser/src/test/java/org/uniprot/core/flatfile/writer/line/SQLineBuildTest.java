package org.uniprot.core.flatfile.writer.line;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.flatfile.parser.impl.sq.SQLineBuilder;
import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.impl.SequenceBuilder;

class SQLineBuildTest {
    @Test
    void test() {
        String sqLine =
                "SQ   SEQUENCE   504 AA;  54443 MW;  F994A9048C816394 CRC64;\n"
                        + "     MTPTLAALLC LGLSLGPRTH VQAGPFPKPT LWAEPGSVIS WGSPVTIWCQ GSLEAQEYRL\n"
                        + "     DKEGSPEPWD RNNPLEPKNK ARFSIPSITE HHAGRYRCHY YSSAGWSEPS DPLELVMTGA\n"
                        + "     YSKPTLSALP SPVVASGGNM TLQCGSQKGY HHFVLMKEGE HQLPRTLDSQ QLHSGGFQAL\n"
                        + "     FPVGPVNPSH RWRFTCYYYY MNTPRVWSHP SDPLEILPSG VSRKPSLLTL QGPVLAPGQS\n"
                        + "     LTLQCGSDVG YDRFVLYKEG ERDFLQRPGQ QPQAGLSQAN FTLGPVSPSH GGQYRCYGAH\n"
                        + "     NLSSEWSAPS DPLNILMAGQ IYDTVSLSAQ PGPTVASGEN VTLLCQSWWQ FDTFLLTKEG\n"
                        + "     AAHPPLRLRS MYGAHKYQAE FPMSPVTSAH AGTYRCYGSY SSNPHLLSFP SEPLELMVSG\n"
                        + "     HSGGSSLPPT GPPSTPGESL RPRGERGLPQ GSLSLPKDPT PLPSRTGLCP RGSEAGLVKS\n"
                        + "     GGFKAERDVG AQPGGGAGLM WGAR";
        SQLineBuilder builder = new SQLineBuilder();
        String seq =
                "MTPTLAALLCLGLSLGPRTHVQAGPFPKPTLWAEPGSVISWGSPVTIWCQGSLEAQEYRLDKEGSPEPWDRNNPLEPKNKARFSIPSIT"
                        + "EHHAGRYRCHYYSSAGWSEPSDPLELVMTGAYSKPTLSALPSPVVASGGNMTLQCGSQKGYHHFVLMKEGEHQLPRTLDSQQLHSGGFQA"
                        + "LFPVGPVNPSHRWRFTCYYYYMNTPRVWSHPSDPLEILPSGVSRKPSLLTLQGPVLAPGQSLTLQCGSDVGYDRFVLYKEGERDFLQRPG"
                        + "QQPQAGLSQANFTLGPVSPSHGGQYRCYGAHNLSSEWSAPSDPLNILMAGQIYDTVSLSAQPGPTVASGENVTLLCQSWWQFDTFLLTKEGA"
                        + "AHPPLRLRSMYGAHKYQAEFPMSPVTSAHAGTYRCYGSYSSNPHLLSFPSEPLELMVSGHSGGSSLPPTGPPSTPGESLRPRGERGLPQGSLS"
                        + "LPKDPTPLPSRTGLCPRGSEAGLVKSGGFKAERDVGAQPGGGAGLMWGAR";
        Sequence sequence = new SequenceBuilder(seq).build();
        FFLine ffLine = builder.build(sequence);
        String resultString = ffLine.toString();
        assertEquals(sqLine, resultString);
    }
}
