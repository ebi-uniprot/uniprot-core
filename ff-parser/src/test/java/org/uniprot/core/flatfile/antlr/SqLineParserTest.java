package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.sq.SqLineObject;

class SqLineParserTest {
    @Test
    void test() {
        String osLines =
                "SQ   SEQUENCE   256 AA;  29735 MW;  B4840739BF7D4121 CRC64;\n"
                        + "     MAFSAEDVLK EYDRRRRMEA LLLSLYYPND RKLLDYKEWS PPRVQVECPK APVEWNNPPS\n"
                        + "     EKGLIVGHFS GIKYKGEKAQ ASEVDVNKMC CWVSKFKDAM RRYQGIQTCK IPGKVLSDLD\n"
                        + "     AKIKAYNLTV EGVEGFVRYS RVTKQHVAAF LKELRHSKQY ENVNLIHYIL TDKRVDIQHL\n"
                        + "     EKDLVKDFKA LVESAHRMRQ GHMINVKYIL YQLLKKHGHG PDGPDILTVK TGSKGVLYDD\n"
                        + "     SFRKIYTDLG WKFTPL\n";

        UniprotKBLineParser<SqLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createSqLineParser();
        SqLineObject obj = parser.parse(osLines);
        verify(obj, 256, "B4840739BF7D4121", 29735, "MAFSAEDVLKE", "GWKFTPL");
    }

    private void verify(
            SqLineObject obj, int length, String crc64, int weight, String start, String end) {
        assertEquals(length, obj.length);
        assertEquals(crc64, obj.crc64);
        assertEquals(weight, obj.weight);
        assertTrue(obj.sequence.startsWith(start));
        assertTrue(obj.sequence.endsWith(end));
    }

    @Test
    void testOneLine() {
        String osLines =
                "SQ   SEQUENCE   256 AA;  29735 MW;  B4840739BF7D4121 CRC64;\n"
                        + "     MAFSAEDVLK WKFTPL\n";

        UniprotKBLineParser<SqLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createSqLineParser();
        SqLineObject obj = parser.parse(osLines);
        verify(obj, 256, "B4840739BF7D4121", 29735, "MAFSAEDVLK", "WKFTPL");
        assertEquals("MAFSAEDVLKWKFTPL", obj.sequence);
    }

    @Test
    void test2() {
        String osLines =
                "SQ   SEQUENCE   162 AA;  18749 MW;  1883589730544714 CRC64;\n"
                        + "     MDEGYYSGNL ESVLGYVSDM HTKLASITQL VIAKIETIDN DILNNDIVNF IMCRSNLNNP\n"
                        + "     FISFLDTVYT IIDQEIYQNE\n";

        UniprotKBLineParser<SqLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createSqLineParser();
        SqLineObject obj = parser.parse(osLines);
        verify(obj, 162, "1883589730544714", 18749, "MDEGYYSGNL", "IIDQEIYQNE");
        assertEquals(
                "MDEGYYSGNLESVLGYVSDMHTKLASITQLVIAKIETIDNDILNNDIVNFIMCRSNLNNPFISFLDTVYTIIDQEIYQNE",
                obj.sequence);
    }
}
