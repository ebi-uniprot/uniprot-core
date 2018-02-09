package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.ra.RaLineObject;

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
