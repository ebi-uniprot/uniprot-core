package uk.ac.ebi.uniprot.flatfile.antlr;

import org.junit.Test;
import uk.ac.ebi.uniprot.flatfile.parser.UniprotLineParser;
import uk.ac.ebi.uniprot.flatfile.parser.impl.DefaultUniprotLineParserFactory;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineFormater;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineObject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CcLineWebRCommentParserTest {
	@Test
	public void test1() {
		String lines = "CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=CD40L defect database;\n"
				+"CC       URL=\"http://bioinf.uta.fi/CD40Lbase/\";\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		
		assertTrue(cc.object instanceof CcLineObject.WebResource);
		CcLineObject.WebResource wr = (CcLineObject.WebResource) cc.object;
		assertEquals("CD40Lbase", wr.name);
		assertEquals("CD40L defect database", wr.note);
		assertEquals("http://bioinf.uta.fi/CD40Lbase/", wr.url);

		
	}
	
	@Test
	public void test2() {
		String lines = "CC   -!- WEB RESOURCE: Name=Functional Glycomics Gateway - GTase;\n"
				+"CC       Note=Beta1,4-N-acetylgalactosaminyltransferase III.;\n"
				+"CC       URL=\"http://www.functionalglycomics.org/glycomics/search/jsp/landing.jsp?query=gt_mou_507\";\n"
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);	
		assertTrue(cc.object instanceof CcLineObject.WebResource);
		CcLineObject.WebResource wr = (CcLineObject.WebResource) cc.object;
		assertEquals("Functional Glycomics Gateway - GTase", wr.name);
		assertEquals("Beta1,4-N-acetylgalactosaminyltransferase III.", wr.note);
		assertEquals("http://www.functionalglycomics.org/glycomics/search/jsp/landing.jsp?query=gt_mou_507", wr.url);

		
	}

	@Test
	public void test3() {
		String lines = "CC   -!- WEB RESOURCE: Name=GeneReviews;\n"
				+"CC       URL=\"http://www.genetests.org/query?gene=RP1\";\n"
		
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		
		assertTrue(cc.object instanceof CcLineObject.WebResource);
		CcLineObject.WebResource wr = (CcLineObject.WebResource) cc.object;
		assertEquals("GeneReviews", wr.name);
		assertEquals("http://www.genetests.org/query?gene=RP1", wr.url);

		
	}

	@Test
	public void testNoHdeader() {
		String ccLineString = "WEB RESOURCE: Name=GeneReviews;\n"
				+"URL=\"http://www.genetests.org/query?gene=RP1\";\n"
		
				;
		UniprotLineParser<CcLineObject> parser = new DefaultUniprotLineParserFactory().createCcLineParser();
		CcLineFormater formater  =new CcLineFormater();
		String lines = formater.format(ccLineString);
		CcLineObject obj = parser.parse(lines);
		assertEquals(1, obj.ccs.size());
		CcLineObject.CC cc = obj.ccs.get(0);
		
		assertTrue(cc.object instanceof CcLineObject.WebResource);
		CcLineObject.WebResource wr = (CcLineObject.WebResource) cc.object;
		assertEquals("GeneReviews", wr.name);
		assertEquals("http://www.genetests.org/query?gene=RP1", wr.url);

		
	}
}
