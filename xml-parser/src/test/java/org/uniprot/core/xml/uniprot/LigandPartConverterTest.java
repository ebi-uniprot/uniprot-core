package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.impl.LigandPartBuilder;
import org.uniprot.core.xml.jaxb.uniprot.LigandPartType;

class LigandPartConverterTest {
    private LigandPartConverter converter = new LigandPartConverter();

    @Test
    void testNameOnly() {
        LigandPart ligandPart = createLigand("Substrate", null, null, null);
        LigandPartType ligandPartType = converter.toXml(ligandPart);
        assertNotNull(ligandPartType);
        assertEquals("Substrate", ligandPartType.getName());
        LigandPart converted = converter.fromXml(ligandPartType);
        assertEquals(ligandPart, converted);
    }

    @Test
    void testNameAndId() {
        LigandPart ligandPart = createLigand("Ca(2+)", "ChEBI:CHEBI:3245", null, null);
        LigandPartType ligandPartType = converter.toXml(ligandPart);
        assertNotNull(ligandPartType);
        assertEquals("Ca(2+)", ligandPartType.getName());
        LigandPart converted = converter.fromXml(ligandPartType);
        assertEquals(ligandPart, converted);
    }

    @Test
    void testFull() {
        LigandPart ligandPart = createLigand("Ca(2+)", "ChEBI:CHEBI:3245", "A1", "Some note");
        LigandPartType ligandPartType = converter.toXml(ligandPart);
        assertNotNull(ligandPartType);
        assertEquals("Ca(2+)", ligandPartType.getName());
        LigandPart converted = converter.fromXml(ligandPartType);
        assertEquals(ligandPart, converted);
    }

    private LigandPart createLigand(String name, String id, String label, String note) {
        LigandPartBuilder builder = new LigandPartBuilder();
        builder.name(name).id(id).label(label).note(note);

        return builder.build();
    }
}
