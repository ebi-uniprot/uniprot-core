package uk.ac.ebi.uniprot.flatfile.parser.converter;

import junit.framework.TestCase;
import org.junit.Test;
import org.uniprot.core.Sequence;

import uk.ac.ebi.uniprot.flatfile.parser.impl.sq.SqLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.sq.SqLineObject;

public class SqLineConverterTest {
	private SqLineConverter converter = new SqLineConverter();
	@Test
	public void test1(){
	
		SqLineObject obj = new SqLineObject();
		obj.crc64 ="B4840739BF7D4121";
		obj.weight = 29735;
		obj.length = 256;
		String sequence = 
				"MAFSAEDVLKEYDRRRRMEALLLSLYYPNDRKLLDYKEWSPPRVQVECPKAPVEWNNPPS" +
				"EKGLIVGHFSGIKYKGEKAQASEVDVNKMCCWVSKFKDAMRRYQGIQTCKIPGKVLSDLD" +
				"AKIKAYNLTVEGVEGFVRYSRVTKQHVAAFLKELRHSKQYENVNLIHYILTDKRVDIQHL" +
				"EKDLVKDFKALVESAHRMRQGHMINVKYILYQLLKKHGHGPDGPDILTVKTGSKGVLYDD" +
				"SFRKIYTDLGWKFTPL";
		obj.sequence =sequence;
		
		Sequence seq = converter.convert(obj);
		TestCase.assertEquals(256, seq.getLength());
		TestCase.assertEquals(29735, seq.getMolWeight());
		TestCase.assertEquals("B4840739BF7D4121", seq.getCrc64());
		TestCase.assertEquals(sequence, seq.getValue());
		
	}
	
}
