package org.uniprot.core.flatfile.antlr;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.rn.RnLineObject;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
