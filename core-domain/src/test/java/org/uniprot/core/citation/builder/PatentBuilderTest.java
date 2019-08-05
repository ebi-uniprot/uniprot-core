package org.uniprot.core.citation.builder;

import org.junit.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.Patent;
import org.uniprot.core.citation.builder.PatentBuilder;

import static org.junit.Assert.assertEquals;

public class PatentBuilderTest extends AbstractCitationBuilderTest {
    @Test
    public void testBuildAll() {
        PatentBuilder builder = new PatentBuilder();
        String pnumber = "Some Number";
        builder.patentNumber(pnumber);
        this.buildCitationParameters(builder);
        Patent citation = builder.build();
        assertEquals(CitationType.PATENT, citation.getCitationType());
        assertEquals(pnumber, citation.getPatentNumber());
        this.verifyCitation(citation, CitationType.PATENT);
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testPatentNumber() {
        PatentBuilder builder = new PatentBuilder();
        String pnumber = "Some Number";
        builder.patentNumber(pnumber);
        Patent citation = builder.build();
        assertEquals(CitationType.PATENT, citation.getCitationType());
        assertEquals(pnumber, citation.getPatentNumber());
        TestHelper.verifyJson(citation);
    }
}
