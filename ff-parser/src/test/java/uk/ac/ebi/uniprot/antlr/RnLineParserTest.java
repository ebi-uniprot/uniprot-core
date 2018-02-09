package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.rn.RnLineObject;

public class RnLineParserTest {
	@Test
	public void test() {
		String rnLines = "RN   [2] {ECO:0000313|EMBL:BAG16761.1, ECO:0000269|PubMed:10433554,\n"
				+"RN   ECO:0000303|Ref.6}\n";
		UniprotLineParser<RnLineObject> parser = new DefaultUniprotLineParserFactory().createRnLineParser();
		RnLineObject obj = parser.parse(rnLines);
		assertEquals(2,  obj.number);
		List<String> evidences = Arrays.asList(new String[] {"ECO:0000313|EMBL:BAG16761.1",
			"ECO:0000269|PubMed:10433554", "ECO:0000303|Ref.6"
		});
		assertEquals(evidences,  obj.evidenceInfo.evidences.get(2));
		

	}
}
