package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.Patent;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PatentBuilderTest extends AbstractCitationBuilderTest {

    @Test
    public void testBuildAll() {
        PatentBuilder builder = PatentBuilder.newInstance();
        String pnumber ="Some Number";
        builder.patentNumber(pnumber);
        this.builderCitationParamters(builder);
        Patent citation = builder.build();
        assertEquals(CitationType.PATENT, citation.getCitationType());
        assertEquals(pnumber, citation.getPatentNumber());
        this.verifyCitation(citation, CitationType.PATENT);
    }

    @Test
    public void testPatentNumber() {
        PatentBuilder builder = PatentBuilder.newInstance();
        String pnumber ="Some Number";
        builder.patentNumber(pnumber);
        Patent citation = builder.build();
        assertEquals(CitationType.PATENT, citation.getCitationType());
        assertEquals(pnumber, citation.getPatentNumber());
    }

}
