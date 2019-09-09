package org.uniprot.core.citation.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.CitationType;
import org.uniprot.core.citation.Thesis;

import static org.junit.jupiter.api.Assertions.assertEquals;


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
    }

    @Test
    public void testInstitute() {
        ThesisBuilder builder = new ThesisBuilder();
        String institute = "Cambridge University";
        builder.institute(institute);
        Thesis citation = builder.build();
        assertEquals(CitationType.THESIS, citation.getCitationType());
        assertEquals(institute, citation.getInstitute());
    }

    @Test
    public void testAddress() {
        ThesisBuilder builder = new ThesisBuilder();
        String address = "Cambridge";
        builder.address(address);
        Thesis citation = builder.build();
        assertEquals(CitationType.THESIS, citation.getCitationType());
        assertEquals(address, citation.getAddress());
    }
}
