package org.uniprot.core.flatfile.parser.converter;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.pe.PeLineConverter;
import org.uniprot.core.flatfile.parser.impl.pe.PeLineObject;
import org.uniprot.core.uniprot.ProteinExistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PeLineConverterTest {
	@Test
	public void test(){
	//	PE   1: Evidence at protein level
		PeLineObject pe = new PeLineObject();
		pe.level =1;
		PeLineConverter converter = new PeLineConverter();
		ProteinExistence peVa =converter.convert(pe);
		assertEquals(ProteinExistence.PROTEIN_LEVEL, peVa);
		pe = new PeLineObject();
		pe.level =2;
		converter = new PeLineConverter();
		peVa =converter.convert(pe);
		assertEquals(ProteinExistence.TRANSCRIPT_LEVEL, peVa);
		
		pe = new PeLineObject();
		pe.level =3;
		converter = new PeLineConverter();
		peVa =converter.convert(pe);
		assertEquals(ProteinExistence.HOMOLOGY, peVa);
		
		pe = new PeLineObject();
		pe.level =4;
		converter = new PeLineConverter();
		peVa =converter.convert(pe);
		assertEquals(ProteinExistence.PREDICTED, peVa);
		pe = new PeLineObject();
		
		pe.level =5;
		converter = new PeLineConverter();
		peVa =converter.convert(pe);
		assertEquals(ProteinExistence.UNCERTAIN, peVa);
	}
}
