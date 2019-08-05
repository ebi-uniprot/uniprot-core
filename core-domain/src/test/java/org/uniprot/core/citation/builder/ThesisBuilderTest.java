package org.uniprot.core.citation.builder;

import org.junit.Test;
import org.uniprot.core.TestHelper;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.Thesis;
import org.uniprot.core.citation.builder.ThesisBuilder;

import static org.junit.Assert.assertEquals;

public class ThesisBuilderTest extends AbstractCitationBuilderTest {
    @Test
    public void testBuildAll() {
        ThesisBuilder builder = new ThesisBuilder();
        String institute = "Cambridge University";
        String address = "Cambridge";
        builder.institute(institute);
        builder.address(address);
        this.buildCitationParameters(builder);
        Thesis citation = builder.build();
        assertEquals(institute, citation.getInstitute());
        assertEquals(address, citation.getAddress());
        this.verifyCitation(citation, CitationType.THESIS);
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testInstitute() {
        ThesisBuilder builder = new ThesisBuilder();
        String institute = "Cambridge University";
        builder.institute(institute);
        Thesis citation = builder.build();
        assertEquals(CitationType.THESIS, citation.getCitationType());
        assertEquals(institute, citation.getInstitute());
        TestHelper.verifyJson(citation);
    }

    @Test
    public void testAddress() {
        ThesisBuilder builder = new ThesisBuilder();
        String address = "Cambridge";
        builder.address(address);
        Thesis citation = builder.build();
        assertEquals(CitationType.THESIS, citation.getCitationType());
        assertEquals(address, citation.getAddress());
        TestHelper.verifyJson(citation);
    }
}
