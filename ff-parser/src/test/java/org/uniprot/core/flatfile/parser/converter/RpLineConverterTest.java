package org.uniprot.core.flatfile.parser.converter;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.impl.rp.RpLineConverter;
import org.uniprot.core.flatfile.parser.impl.rp.RpLineObject;

import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class RpLineConverterTest {
	private final RpLineConverter converter = new RpLineConverter();
	@Test
	public void test1(){
		// "RP   NUCLEOTIDE SEQUENCE [MRNA].\n";
		RpLineObject rp =new RpLineObject();
		rp.scopes.add("NUCLEOTIDE SEQUENCE [MRNA]");
		 List<String> css =converter.convert(rp);
		 assertEquals(1, css.size());
		 String cs = css.get(0);
		 assertEquals("NUCLEOTIDE SEQUENCE [MRNA]", cs);
	}
	
	@Test
	public void test2(){
		// """RP   NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C), FUNCTION, INTERACTION
        //RP   WITH PKC-3, SUBCELLULAR LOCATION, TISSUE SPECIFICITY, DEVELOPMENTAL
        //RP   STAGE, AND MUTAGENESIS OF PHE-175 AND PHE-221.
		RpLineObject rp =new RpLineObject();
		rp.scopes.add("NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C)");
		rp.scopes.add("FUNCTION");
		rp.scopes.add("INTERACTION WITH PKC-3");
		rp.scopes.add("SUBCELLULAR LOCATION");
		rp.scopes.add("TISSUE SPECIFICITY");
		rp.scopes.add("DEVELOPMENTAL STAGE");
		rp.scopes.add("MUTAGENESIS OF PHE-175 AND PHE-221");

		 List<String> css =converter.convert(rp);
		 assertEquals(7, css.size());

		 assertEquals("NUCLEOTIDE SEQUENCE [MRNA] (ISOFORMS A AND C)", css.get(0));
		 assertEquals("FUNCTION", css.get(1));
		 assertEquals("INTERACTION WITH PKC-3", css.get(2));
		 assertEquals("SUBCELLULAR LOCATION", css.get(3));
		 assertEquals("TISSUE SPECIFICITY", css.get(4));
		 assertEquals("DEVELOPMENTAL STAGE", css.get(5));
		 assertEquals("MUTAGENESIS OF PHE-175 AND PHE-221", css.get(6));
	}
}
