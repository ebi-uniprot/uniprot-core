package org.uniprot.core.flatfile.writer.line.cc;

import org.junit.Test;
import org.uniprot.core.uniprot.comment.WebResourceComment;
import org.uniprot.core.uniprot.comment.builder.WebResourceCommentBuilder;

public class CCWebResourceBuildTest extends CCBuildTestAbstr {
    @Test
    public void testWebResources() throws Exception {
        String ccLine = ("CC   -!- WEB RESOURCE: Name=Worthington enzyme manual;\n" +
                "CC       URL=\"http://www.worthington-biochem.com/STDH/\";");

        String ccLineString = ("WEB RESOURCE: Name=Worthington enzyme manual;\n" +
                "URL=\"http://www.worthington-biochem.com/STDH/\";");
        doTest(ccLine);
        WebResourceCommentBuilder builder = new WebResourceCommentBuilder();
        builder.resourceName("Worthington enzyme manual")
        .resourceUrl("http://www.worthington-biochem.com/STDH/");
        
        WebResourceComment comment = builder.build();

        doTest(ccLine, comment);
        doTestString(ccLineString, comment);
    }

    @Test
    public void testDATABASE1() {
        String ccLine = ("CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=European CD40L defect database\n" +
                "CC       (mutation db);\n" +
                "CC       URL=\"http://www.expasy.org/cd40lbase\";");
        doTest(ccLine);
    }

    @Test
    public void testDATABASE2() {
        String ccLine = ("CC   -!- WEB RESOURCE: Name=Alzheimer Research Forum; Note=APP mutations;\n" +
                "CC       URL=\"http://www.alzforum.org/res/com/mut/app/default.asp\";");
        doTest(ccLine);
    }
}
