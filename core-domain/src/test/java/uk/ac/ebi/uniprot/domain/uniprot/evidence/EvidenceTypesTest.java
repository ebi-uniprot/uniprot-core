package uk.ac.ebi.uniprot.domain.uniprot.evidence;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EvidenceTypesTest {

    @Test
    void testEvidenceTypes() {
        assertNotNull(EvidenceTypes.INSTANCE);
    }

    @Test
    void testGetAllTypes() {
        List<EvidenceTypeDetail> types = EvidenceTypes.INSTANCE.getAllTypes();
        assertEquals(46, types.size());
    }

    @Test
    void testGetTypeEMBL() {
        EvidenceTypeDetail opType = EvidenceTypes.INSTANCE.getType("EMBL");
        verify(opType, "EMBL", "EMBL", "https://www.ebi.ac.uk/ena/data/view/%value");
    }

    @Test
    void testGetTypeHamapRule() {
        EvidenceTypeDetail opType = EvidenceTypes.INSTANCE.getType("HAMAP-Rule");
        verify(opType, "HAMAP-Rule", "HAMAP-Rule", "https://www.uniprot.org/unirule/%value");
    }

    @Test
    void testGetTypePubMed() {
        EvidenceTypeDetail opType = EvidenceTypes.INSTANCE.getType("PubMed");
        verify(opType, "PubMed", "PubMed", "https://www.uniprot.org/citations/%value");
    }


    @Test
    void testGetTypeReference() {
        EvidenceTypeDetail opType = EvidenceTypes.INSTANCE.getType("Reference");
        verify(opType, "Reference", "Reference", "");
    }

    @Test
    void testGetNoType() {
        assertThrows(IllegalArgumentException.class,
                     () -> {
                         EvidenceTypes.INSTANCE.getType("NoType");
                     });


    }


    private void verify(EvidenceTypeDetail type, String name, String displayName, String url) {
        assertEquals(name, type.getName());
        assertEquals(displayName, type.getDisplayName());
        assertEquals(url, type.getUriLink());
    }

}
