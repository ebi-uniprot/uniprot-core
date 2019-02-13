package uk.ac.ebi.uniprot.flatfile.parser.converter;


import org.junit.Test;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ac.AcLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ac.AcLineObject;
import uk.ac.ebi.uniprot.flatfile.parser.impl.ac.UniProtAcLineObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class AcLineConverterTest {
	private AcLineConverter converter = new AcLineConverter();
	@Test
	public void testConverter() throws Exception{
		//val ac_one_line_moreacc = "AC   Q6GZX4; Q6GZX5; Q6GZX6;\n"
		AcLineObject acObj = new AcLineObject();
		acObj.primaryAcc ="Q6GZX4";
		acObj.secondaryAcc.add("Q6GZX5");
		acObj.secondaryAcc.add("Q6GZX6");
		
		UniProtAcLineObject uniObj = converter.convert(acObj);
		assertEquals("Q6GZX4", uniObj.getPrimaryAccession().getValue());
		assertEquals(2, uniObj.getSecondAccessions().size());
		testSecondAccIn("Q6GZX5", uniObj);
		testSecondAccIn("Q6GZX6", uniObj);
		
	}
	@Test
	public void testConverter2() throws Exception{
		//val ac_one_line_moreacc = "AC   Q6GZX4; Q6GZX5; Q6GZX6;\n"
		AcLineObject acObj = new AcLineObject();
		acObj.primaryAcc ="Q6GZX4";
		
		UniProtAcLineObject uniObj = converter.convert(acObj);
		assertEquals("Q6GZX4", uniObj.getPrimaryAccession().getValue());
		assertEquals(0, uniObj.getSecondAccessions().size());
	}
	private void testSecondAccIn(String val, UniProtAcLineObject uniObj){
		boolean found =false;
		for(UniProtAccession scObj: uniObj.getSecondAccessions()){
			if(val.equals(scObj.getValue())){
				found =true;
				break;
			}
		}
		assertTrue(found);
	}
}
