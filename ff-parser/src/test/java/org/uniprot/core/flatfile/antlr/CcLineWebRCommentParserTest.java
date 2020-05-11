package org.uniprot.core.flatfile.antlr;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.UniprotKBLineParser;
import org.uniprot.core.flatfile.parser.impl.DefaultUniprotKBLineParserFactory;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineFormater;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.*;

class CcLineWebRCommentParserTest {
    @Test
    void test1() {
        String lines =
                "CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=CD40L defect database;\n"
                        + "CC       URL=\"http://bioinf.uta.fi/CD40Lbase/\";\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);

        assertTrue(cc.getObject() instanceof WebResource);
        WebResource wr = (WebResource) cc.getObject();
        assertEquals("CD40Lbase", wr.getName());
        assertEquals("CD40L defect database", wr.getNote());
        assertEquals("http://bioinf.uta.fi/CD40Lbase/", wr.getUrl());
    }

    @Test
    void test2() {
        String lines =
                "CC   -!- WEB RESOURCE: Name=Functional Glycomics Gateway - GTase;\n"
                        + "CC       Note=Beta1,4-N-acetylgalactosaminyltransferase III.;\n"
                        + "CC      "
                        + " URL=\"http://www.functionalglycomics.org/glycomics/search/jsp/landing.jsp?query=gt_mou_507\";\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);
        assertTrue(cc.getObject() instanceof WebResource);
        WebResource wr = (WebResource) cc.getObject();
        assertEquals("Functional Glycomics Gateway - GTase", wr.getName());
        assertEquals("Beta1,4-N-acetylgalactosaminyltransferase III.", wr.getNote());
        assertEquals(
                "http://www.functionalglycomics.org/glycomics/search/jsp/landing.jsp?query=gt_mou_507",
                wr.getUrl());
    }

    @Test
    void test3() {
        String lines =
                "CC   -!- WEB RESOURCE: Name=GeneReviews;\n"
                        + "CC       URL=\"http://www.genetests.org/query?gene=RP1\";\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);

        assertTrue(cc.getObject() instanceof WebResource);
        WebResource wr = (WebResource) cc.getObject();
        assertEquals("GeneReviews", wr.getName());
        assertEquals("http://www.genetests.org/query?gene=RP1", wr.getUrl());
    }

    @Test
    void testNoHdeader() {
        String ccLineString =
                "WEB RESOURCE: Name=GeneReviews;\n"
                        + "URL=\"http://www.genetests.org/query?gene=RP1\";\n";
        UniprotKBLineParser<CcLineObject> parser =
                new DefaultUniprotKBLineParserFactory().createCcLineParser();
        CcLineFormater formater = new CcLineFormater();
        String lines = formater.format(ccLineString);
        CcLineObject obj = parser.parse(lines);
        assertEquals(1, obj.getCcs().size());
        CC cc = obj.getCcs().get(0);

        assertTrue(cc.getObject() instanceof WebResource);
        WebResource wr = (WebResource) cc.getObject();
        assertEquals("GeneReviews", wr.getName());
        assertEquals("http://www.genetests.org/query?gene=RP1", wr.getUrl());
    }
}
