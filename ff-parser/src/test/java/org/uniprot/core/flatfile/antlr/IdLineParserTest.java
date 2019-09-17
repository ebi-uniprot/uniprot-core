package org.uniprot.core.flatfile.antlr;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.id.IdLineObject;

import static org.junit.jupiter.api.Assertions.assertEquals;


class IdLineParserTest {
	@Test
	void test() {
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
	void testIsoform() {
		String idlines = "ID   001R_FRG3G_123           Reviewed;         256 AA.\n";
		UniprotLineParser<IdLineObject> parser = new DefaultUniprotLineParserFactory().createIdLineParser();
		IdLineObject obj = parser.parse(idlines);
		verify(obj, "001R_FRG3G_123", true, 256);
	}

	@Test
	void test2() {
		String idlines = "ID   001R_FRG3G              Reviewed;         256 AA.\n";
		UniprotLineParser<IdLineObject> parser = new DefaultUniprotLineParserFactory().createIdLineParser();
		IdLineObject obj = parser.parse(idlines);
		verify(obj, "001R_FRG3G", true, 256);
	}

}
