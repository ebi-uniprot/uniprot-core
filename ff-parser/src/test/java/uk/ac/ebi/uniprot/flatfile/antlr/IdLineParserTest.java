package uk.ac.ebi.uniprot.flatfile.antlr;

import org.junit.Test;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.id.IdLineObject;

import static org.junit.Assert.assertEquals;

public class IdLineParserTest {
	@Test
	public void test() {
		String idlines = "ID   001R_FRG3G              Reviewed;         256 AA.\n";
		UniprotLineParser<IdLineObject> parser = new DefaultUniprotLineParserFactory().createIdLineParser();
		IdLineObject obj = parser.parse(idlines);
		verify(obj, "001R_FRG3G", true, 256);
	}

	private void verify(IdLineObject obj, String id, boolean reviewed, int seqlen) {
		assertEquals(id, obj.getEntryName());
		assertEquals(reviewed, obj.getReviewed());
		assertEquals(seqlen, obj.getSequenceLength());
	}
	@Test
	public void testIsoform() {
		String idlines = "ID   001R_FRG3G_123           Reviewed;         256 AA.\n";
		UniprotLineParser<IdLineObject> parser = new DefaultUniprotLineParserFactory().createIdLineParser();
		IdLineObject obj = parser.parse(idlines);
		verify(obj, "001R_FRG3G_123", true, 256);
	}

	@Test
	public void test2() {
		String idlines = "ID   001R_FRG3G              Reviewed;         256 AA.\n";
		UniprotLineParser<IdLineObject> parser = new DefaultUniprotLineParserFactory().createIdLineParser();
		IdLineObject obj = parser.parse(idlines);
		verify(obj, "001R_FRG3G", true, 256);
	}

}
