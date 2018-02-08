package uk.ac.ebi.uniprot.antlr;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import uk.ac.ebi.uniprot.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.parser.impl.oh.OhLineObject;

public class OhLineParserTest {
	@Test
	public void test() {
		String ohLines = "OH   NCBI_TaxID=9598; Pan troglodytes (Chimpanzee).\n";
		UniprotLineParser<OhLineObject> parser = new DefaultUniprotLineParserFactory().createOhLineParser();
		OhLineObject obj = parser.parse(ohLines);
		
		assertEquals(1, obj.hosts.size());
		verify(obj.hosts.get(0), 9598, "Pan troglodytes (Chimpanzee)");


	}
	private void verify(OhLineObject.OhValue oh, int taxId, String hostName) {
		assertEquals(taxId, oh.tax_id);
		assertEquals(hostName, oh.hostname);
	}
	
	@Test
	public void test2() {
		String ohLines = "OH   NCBI_TaxID=3662; Cucurbita moschata (Winter crookneck squash) (Cucurbita pepo var. moschata).\n";
		UniprotLineParser<OhLineObject> parser = new DefaultUniprotLineParserFactory().createOhLineParser();
		OhLineObject obj = parser.parse(ohLines);
		
		assertEquals(1, obj.hosts.size());
		verify(obj.hosts.get(0), 3662, "Cucurbita moschata (Winter crookneck squash) (Cucurbita pepo var. moschata)");


	}
	@Test
	public void test3() {
		String ohLines = "OH   NCBI_TaxID=3662; Cucurbita moschata (Winter crookneck squash) (Cucurbita pepo var. moschata).\n"
		+"OH   NCBI_TaxID=9598; Pan troglodytes (Chimpanzee).\n";
		UniprotLineParser<OhLineObject> parser = new DefaultUniprotLineParserFactory().createOhLineParser();
		OhLineObject obj = parser.parse(ohLines);
		
		assertEquals(2, obj.hosts.size());
		verify(obj.hosts.get(0), 3662, "Cucurbita moschata (Winter crookneck squash) (Cucurbita pepo var. moschata)");
		verify(obj.hosts.get(1), 9598, "Pan troglodytes (Chimpanzee)");


	}
}
