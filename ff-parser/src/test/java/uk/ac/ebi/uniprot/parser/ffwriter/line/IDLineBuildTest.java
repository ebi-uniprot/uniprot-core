package uk.ac.ebi.uniprot.parser.ffwriter.line;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.impl.IDLineBuilder;
import uk.ac.ebi.uniprot.parser.impl.id.IdLineObject;


public class IDLineBuildTest {
	IDLineBuilder builder = new IDLineBuilder();
	@Test
	public void test1() {
			String idLine = "ID   ELNE_HUMAN              Reviewed;          45 AA.";
			IdLineObject idObj = new IdLineObject("ELNE_HUMAN", true, 45);
		
			FFLine ffLine = builder.build(idObj);
			

			String resultString = ffLine.toString();
			System.out.println(resultString);
			System.out.println(idLine);
			assertEquals(idLine, resultString);

		
	}
	@Test
	public void testNew() {

		
			String idLine = "ID   A0A000ACJ5_9GENT        Unreviewed;        45 AA.";
			IdLineObject idObj = new IdLineObject("A0A000ACJ5_9GENT", false, 45);
			
			FFLine ffLine = builder.build(idObj);
			

			String resultString = ffLine.toString();
			System.out.println(resultString);
			System.out.println(idLine);
			assertEquals(idLine, resultString);
	}
}
