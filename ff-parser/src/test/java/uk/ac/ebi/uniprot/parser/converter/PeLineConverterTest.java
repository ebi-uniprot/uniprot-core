package uk.ac.ebi.uniprot.parser.converter;

import junit.framework.TestCase;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.parser.impl.pe.PeLineConverter;
import uk.ac.ebi.uniprot.parser.impl.pe.PeLineObject;

public class PeLineConverterTest {
	@Test
	public void test(){
	//	PE   1: Evidence at protein level
		PeLineObject pe = new PeLineObject();
		pe.level =1;
		PeLineConverter converter = new PeLineConverter();
		ProteinExistence peVa =converter.convert(pe);
		TestCase.assertEquals(ProteinExistence.PROTEIN_LEVEL, peVa);
		pe = new PeLineObject();
		pe.level =2;
		converter = new PeLineConverter();
		peVa =converter.convert(pe);
		TestCase.assertEquals(ProteinExistence.TRANSCRIPT_LEVEL, peVa);
		
		pe = new PeLineObject();
		pe.level =3;
		converter = new PeLineConverter();
		peVa =converter.convert(pe);
		TestCase.assertEquals(ProteinExistence.HOMOLOGY, peVa);
		
		pe = new PeLineObject();
		pe.level =4;
		converter = new PeLineConverter();
		peVa =converter.convert(pe);
		TestCase.assertEquals(ProteinExistence.PREDICTED, peVa);
		pe = new PeLineObject();
		
		pe.level =5;
		converter = new PeLineConverter();
		peVa =converter.convert(pe);
		TestCase.assertEquals(ProteinExistence.UNCERTAIN, peVa);
	}
	
}
