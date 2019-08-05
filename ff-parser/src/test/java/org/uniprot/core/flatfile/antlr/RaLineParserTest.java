package org.uniprot.core.flatfile.antlr;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.ra.RaLineObject;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RaLineParserTest {
	@Test
	public void test() {
		String raLines = "RA   Tan W.G., Barkman T.J., Gregory Chinchar V., Essani K.;\n";
		UniprotLineParser<RaLineObject> parser = new DefaultUniprotLineParserFactory().createRaLineParser();
		RaLineObject obj = parser.parse(raLines);
		verify(obj, Arrays.asList(new String[] {"Tan W.G.","Barkman T.J.","Gregory Chinchar V.", 
				"Essani K."}));

	
	}
	private void verify(RaLineObject obj, List<String> authors) {
		assertEquals(authors, obj.authors);
	}
	@Test
	public void test2() {
		String raLines = "RA   Galinier A., Perriere G., Duclos B.;\n";
		UniprotLineParser<RaLineObject> parser = new DefaultUniprotLineParserFactory().createRaLineParser();
		RaLineObject obj = parser.parse(raLines);
		verify(obj, Arrays.asList(new String[] {"Galinier A.","Perriere G.","Duclos B."
				}));

	
	}
	@Test
	public void test3() {
		String raLines = "RA   Galinier A. B.;\n";
		UniprotLineParser<RaLineObject> parser = new DefaultUniprotLineParserFactory().createRaLineParser();
		RaLineObject obj = parser.parse(raLines);
		verify(obj, Arrays.asList(new String[] {"Galinier A. B."
				}));
	}
	@Test
	public void test4() {
		String raLines = "RA   Galinier A., Bleicher F., Nasoff M.S., Baker H.V. II, Wolf R.E. Jr.,\n"
		+"RA   Cozzone A.J., Cortay J.-C.;\n"
				;
		UniprotLineParser<RaLineObject> parser = new DefaultUniprotLineParserFactory().createRaLineParser();
		RaLineObject obj = parser.parse(raLines);
		verify(obj, Arrays.asList(new String[] {"Galinier A.","Bleicher F.","Nasoff M.S.", 
				"Baker H.V. II", "Wolf R.E. Jr.", "Cozzone A.J.","Cortay J.-C." }));

	
	}
}
