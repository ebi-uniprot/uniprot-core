package uk.ac.ebi.uniprot.parser.ffwriter.line.rlines;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefType;
import uk.ac.ebi.uniprot.domain.citation.CitationXrefs;
import uk.ac.ebi.uniprot.domain.citation.builder.AbstractCitationBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.parser.impl.rx.RXLineBuilder;



public class RXLineBuilderTest {
	RXLineBuilder builder =new RXLineBuilder();
	@Test
	public void testPubmed(){
		CitationXrefs xrefs = buildCitationXref("15165820", null, null);
		List<String> lines = builder.buildLine(xrefs, true, true);
		assertEquals(1, lines.size());
		String expected = "RX   PubMed=15165820;";
		assertEquals(expected, lines.get(0));
	}
	@Test
	public void testDOI(){
		CitationXrefs xrefs = buildCitationXref(null, "10.1016/j.virol.2004.02.019",  null);
		List<String> lines = builder.buildLine(xrefs, true, true);
		assertEquals(1, lines.size());
		String expected = "RX   DOI=10.1016/j.virol.2004.02.019;";
		assertEquals(expected, lines.get(0));
	}
	
	@Test
	public void testAgricola(){
		CitationXrefs xrefs = buildCitationXref(null, null, "asfsadgdasgdagd");
		List<String> lines = builder.buildLine(xrefs, true, true);
		assertEquals(1, lines.size());
		String expected = "RX   AGRICOLA=asfsadgdasgdagd;";
		assertEquals(expected, lines.get(0));
	}
	
	@Test
	public void testPubmedDOI(){
		CitationXrefs xrefs = buildCitationXref("15165820", "10.1016/j.virol.2004.02.019",  null);
		List<String> lines = builder.buildLine(xrefs, true, true);
		assertEquals(1, lines.size());
		String expected = "RX   PubMed=15165820; DOI=10.1016/j.virol.2004.02.019;";
		assertEquals(expected, lines.get(0));
	}
	
	@Test
	public void testPubmedAgricola(){
		CitationXrefs xrefs = buildCitationXref("15165820", null, "asfsadgdasgdagd");
		List<String> lines = builder.buildLine(xrefs, true, true);
		assertEquals(1, lines.size());
		String expected = "RX   PubMed=15165820; AGRICOLA=asfsadgdasgdagd;";
		assertEquals(expected, lines.get(0));
	}
	
	@Test
	public void testAll(){
		CitationXrefs xrefs = buildCitationXref("15165820", "10.1016/j.virol.2004.02.019", "asfsadgdasgdagd");
		List<String> lines = builder.buildLine(xrefs, true, true);
		assertEquals(2, lines.size());
		String expected = "RX   DOI=10.1016/j.virol.2004.02.019;";
		assertEquals(expected, lines.get(1));
	}
	@Test
	public void test2(){
		CitationXrefs xrefs = buildCitationXref("15165820", "10.1016/j.virol.2004.02.019", "asfsadgdasgdagd");
		List<String> lines = builder.buildLine(xrefs, false, true);
		assertEquals(1, lines.size());
		String expected = "PubMed=15165820; AGRICOLA=asfsadgdasgdagd; DOI=10.1016/j.virol.2004.02.019;";
		assertEquals(expected, lines.get(0));
	}
	private CitationXrefs buildCitationXref(String pubmed, String doi, String agricolaId ){

		List<DBCrossReference<CitationXrefType>> xrefs = new ArrayList<>();
		if(pubmed !=null)
			xrefs.add(UniProtFactory.INSTANCE.createDBCrossReference(CitationXrefType.PUBMED, pubmed));
		if(doi !=null)
		xrefs.add(UniProtFactory.INSTANCE.createDBCrossReference(CitationXrefType.DOI, doi));
		if(agricolaId !=null)
			xrefs.add(UniProtFactory.INSTANCE.createDBCrossReference(CitationXrefType.AGRICOLA, agricolaId));
		return AbstractCitationBuilder.createCitationXrefs(xrefs);
		
	}
}
