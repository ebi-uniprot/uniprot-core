package uk.ac.ebi.uniprot.domain.citation.builder;

import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.citation.CitationType;
import uk.ac.ebi.uniprot.domain.citation.Thesis;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ThesisBuilderTest  extends AbstractCitationBuilderTest{

    @Test
    public void testBuildAll() {
        ThesisBuilder builder = ThesisBuilder.newInstance();
        String institute ="Cambridge University";
        String address ="Cambridge";
        builder.institute(institute);
        builder.address(address);
        this.builderCitationParamters(builder);
        Thesis citation = builder.build();
        assertEquals(institute, citation.getInstitute());
        assertEquals(address, citation.getAddress());
        this.verifyCitation(citation, CitationType.THESIS);
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testInstitute() {
        ThesisBuilder builder = ThesisBuilder.newInstance();
        String institute ="Cambridge University";
        builder.institute(institute);
        Thesis citation = builder.build();
        assertEquals(CitationType.THESIS, citation.getCitationType());
        assertEquals(institute, citation.getInstitute());
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testAddress() {
        ThesisBuilder builder = ThesisBuilder.newInstance();
        String address ="Cambridge";
        builder.address(address);
        Thesis citation = builder.build();
        assertEquals(CitationType.THESIS, citation.getCitationType());
        assertEquals(address, citation.getAddress());
        TestHelper.verifyJson(citation);
    }

}
