package uk.ac.ebi.uniprot.domain.citation.builder;

import org.junit.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.Patent;

import static org.junit.Assert.assertEquals;

public class PatentBuilderTest extends AbstractCitationBuilderTest {

    @Test
    public void testBuildAll() {
        PatentBuilder builder = PatentBuilder.newInstance();
        String pnumber = "Some Number";
        builder.patentNumber(pnumber);
        this.builderCitationParamters(builder);
        Patent citation = builder.build();
        assertEquals(CitationType.PATENT, citation.getCitationType());
        assertEquals(pnumber, citation.getPatentNumber());
        this.verifyCitation(citation, CitationType.PATENT);
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testPatentNumber() {
        PatentBuilder builder = PatentBuilder.newInstance();
        String pnumber = "Some Number";
        builder.patentNumber(pnumber);
        Patent citation = builder.build();
        assertEquals(CitationType.PATENT, citation.getCitationType());
        assertEquals(pnumber, citation.getPatentNumber());
        TestHelper.verifyJson(citation);
    }

}
