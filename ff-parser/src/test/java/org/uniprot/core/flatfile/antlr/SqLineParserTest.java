package org.uniprot.core.flatfile.antlr;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.sq.SqLineObject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

class SqLineParserTest {
	@Test
	void test() {
		String osLines = "SQ   SEQUENCE   256 AA;  29735 MW;  B4840739BF7D4121 CRC64;\n"
        +"     MAFSAEDVLK EYDRRRRMEA LLLSLYYPND RKLLDYKEWS PPRVQVECPK APVEWNNPPS\n"
        +"     EKGLIVGHFS GIKYKGEKAQ ASEVDVNKMC CWVSKFKDAM RRYQGIQTCK IPGKVLSDLD\n"
        +"     AKIKAYNLTV EGVEGFVRYS RVTKQHVAAF LKELRHSKQY ENVNLIHYIL TDKRVDIQHL\n"
        +"     EKDLVKDFKA LVESAHRMRQ GHMINVKYIL YQLLKKHGHG PDGPDILTVK TGSKGVLYDD\n"
        +"     SFRKIYTDLG WKFTPL\n"
        ;
		
		
		UniprotLineParser<SqLineObject> parser = new DefaultUniprotLineParserFactory().createSqLineParser();
		SqLineObject obj = parser.parse(osLines);
		verify(obj, 256,  "B4840739BF7D4121",29735,  "MAFSAEDVLKE", "GWKFTPL");
	
	}
	private void verify(SqLineObject obj, int length, String crc64,
			int weight, String start, String end) {
		assertEquals(length, obj.length);
		assertEquals(crc64, obj.crc64);
		assertEquals(weight, obj.weight);
		assertTrue(obj.sequence.startsWith(start));
		assertTrue(obj.sequence.endsWith(end));
	}
	@Test
	void testOneLine() {
		String osLines = "SQ   SEQUENCE   256 AA;  29735 MW;  B4840739BF7D4121 CRC64;\n"
        +"     MAFSAEDVLK WKFTPL\n"
        ;
		
		
		UniprotLineParser<SqLineObject> parser = new DefaultUniprotLineParserFactory().createSqLineParser();
		SqLineObject obj = parser.parse(osLines);
		verify(obj, 256,  "B4840739BF7D4121",29735,  "MAFSAEDVLK", "WKFTPL");
		assertEquals("MAFSAEDVLKWKFTPL", obj.sequence);
	
	}
	@Test
	void test2() {
		String osLines = "SQ   SEQUENCE   162 AA;  18749 MW;  1883589730544714 CRC64;\n"
        +"     MDEGYYSGNL ESVLGYVSDM HTKLASITQL VIAKIETIDN DILNNDIVNF IMCRSNLNNP\n"
        +"     FISFLDTVYT IIDQEIYQNE\n"
        ;
		
		
		UniprotLineParser<SqLineObject> parser = new DefaultUniprotLineParserFactory().createSqLineParser();
		SqLineObject obj = parser.parse(osLines);
		verify(obj, 162,  "1883589730544714",18749,  "MDEGYYSGNL", "IIDQEIYQNE");
		assertEquals("MDEGYYSGNLESVLGYVSDMHTKLASITQLVIAKIETIDNDILNNDIVNFIMCRSNLNNPFISFLDTVYTIIDQEIYQNE", obj.sequence);
	
	}
}

