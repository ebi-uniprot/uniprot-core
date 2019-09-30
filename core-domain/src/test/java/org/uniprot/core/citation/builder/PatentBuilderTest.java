package org.uniprot.core.citation.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.Patent;

class PatentBuilderTest extends AbstractCitationBuilderTest {
    @Test
    void testBuildAll() {
        PatentBuilder builder = new PatentBuilder();
        String pnumber = "Some Number";
        builder.patentNumber(pnumber);
        this.buildCitationParameters(builder);
        Patent citation = builder.build();
        assertEquals(CitationType.PATENT, citation.getCitationType());
        assertEquals(pnumber, citation.getPatentNumber());
        this.verifyCitation(citation, CitationType.PATENT);
    }

    @Test
    void testPatentNumber() {
        PatentBuilder builder = new PatentBuilder();
        String pnumber = "Some Number";
        builder.patentNumber(pnumber);
        Patent citation = builder.build();
        assertEquals(CitationType.PATENT, citation.getCitationType());
        assertEquals(pnumber, citation.getPatentNumber());
    }
}
