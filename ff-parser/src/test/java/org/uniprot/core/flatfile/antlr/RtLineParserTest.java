package org.uniprot.core.flatfile.antlr;

import org.junit.Test;
import org.uniprot.core.flatfile.parser.UniprotLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.rt.RtLineObject;

import static org.junit.Assert.assertEquals;

public class RtLineParserTest {
	@Test
	public void testSimple() {
		String rtLines = "RT   \"A novel adapter protein employs a phosphotyrosine binding domain.\";\n";
		UniprotLineParser<RtLineObject> parser = new DefaultUniprotLineParserFactory().createRtLineParser();
		RtLineObject obj = parser.parse(rtLines);
		assertEquals("A novel adapter protein employs a phosphotyrosine binding domain.", obj.title);
	}
	@Test
	public void testWithDoubleQuotes() {
		String rtLines = "RT   \"A novel adapter \"protein\" employs a phosphotyrosine binding domain.\";\n";
		UniprotLineParser<RtLineObject> parser = new DefaultUniprotLineParserFactory().createRtLineParser();
		RtLineObject obj = parser.parse(rtLines);
		assertEquals("A novel adapter \"protein\" employs a phosphotyrosine binding domain.", obj.title);
	}
	@Test
	public void testMultiLines() {
		String rtLines = "RT   \"New insulin-like proteins with atypical disulfide bond pattern\n"
				+"RT   characterized in Caenorhabditis elegans by comparative sequence\n"
				+"RT   analysis and homology modeling?\";\n"
				;
		UniprotLineParser<RtLineObject> parser = new DefaultUniprotLineParserFactory().createRtLineParser();
		RtLineObject obj = parser.parse(rtLines);
		assertEquals("New insulin-like proteins with atypical disulfide bond pattern"
				+ " characterized in Caenorhabditis elegans by comparative sequence analysis and homology modeling?", obj.title);
	}
	@Test
	public void testWithDot() {
		String rtLines = "RT   \"14-3-3 is phosphorylated by casein kinase I on residue 233.\n"
				+"RT   Phosphorylation at this site in vivo regulates Raf/14-3-3\n"
				+"RT   interaction.\";\n"
				;
		UniprotLineParser<RtLineObject> parser = new DefaultUniprotLineParserFactory().createRtLineParser();
		RtLineObject obj = parser.parse(rtLines);
		assertEquals("14-3-3 is phosphorylated by casein kinase I on residue 233. "
				+ "Phosphorylation at this site in vivo regulates Raf/14-3-3 interaction.", obj.title);
	}
	@Test
	public void testWithDash() {
		String rtLines = "RT   \"Nuclear localization of protein kinase U-alpha is regulated by 14-3-\n"
				+"RT   3.\";\n"
				;
		UniprotLineParser<RtLineObject> parser = new DefaultUniprotLineParserFactory().createRtLineParser();
		RtLineObject obj = parser.parse(rtLines);
		assertEquals("Nuclear localization of protein kinase U-alpha is regulated by 14-3-3."
				, obj.title);
	}
	@Test
	public void testWithSemicolon() {
		String rtLines = "RT   \"The success of acinetobacter species; genetic, metabolic and\n"
				+"RT   virulence attributes.\";\n"
				;
		UniprotLineParser<RtLineObject> parser = new DefaultUniprotLineParserFactory().createRtLineParser();
		RtLineObject obj = parser.parse(rtLines);
		assertEquals("The success of acinetobacter species; genetic, metabolic and virulence attributes."
				, obj.title);
	}
	@Test
	public void testWithSemicolonAndDot() {
		String rtLines = "RT   \"The success of acinetobacter species; genetic. metabolic and\n"
				+"RT   virulence attributes.\";\n"
				;
		UniprotLineParser<RtLineObject> parser = new DefaultUniprotLineParserFactory().createRtLineParser();
		RtLineObject obj = parser.parse(rtLines);
		assertEquals("The success of acinetobacter species; genetic. metabolic and virulence attributes."
				, obj.title);
	}
	@Test
	public void testWithSemicolonAndQuote() {
		String rtLines = "RT   \"The success of \"acinetobacter\" species; genetic. metabolic and\n"
				+"RT   virulence attributes.\";\n"
				;
		UniprotLineParser<RtLineObject> parser = new DefaultUniprotLineParserFactory().createRtLineParser();
		RtLineObject obj = parser.parse(rtLines);
		assertEquals("The success of \"acinetobacter\" species; genetic. metabolic and virulence attributes."
				, obj.title);
	}
	@Test
	public void testWithSemicolonAndQuote2() {
		String rtLines = "RT   \"The success of \"acinetobacter\" species; genetic. metabolic and\n"
				+"RT   virulence \"attributes\".\";\n"
				;
		UniprotLineParser<RtLineObject> parser = new DefaultUniprotLineParserFactory().createRtLineParser();
		RtLineObject obj = parser.parse(rtLines);
		assertEquals("The success of \"acinetobacter\" species; genetic. metabolic and virulence \"attributes\"."
				, obj.title);
	}
	@Test
	public void testWithBracket() {
		String rtLines = "RT   \"[Primary structure of proline tRNA of bacteriophage T5].\";\n"
				;
		UniprotLineParser<RtLineObject> parser = new DefaultUniprotLineParserFactory().createRtLineParser();
		RtLineObject obj = parser.parse(rtLines);
		assertEquals("[Primary structure of proline tRNA of bacteriophage T5]."
				, obj.title);
	}
}
