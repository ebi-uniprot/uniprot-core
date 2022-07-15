package org.uniprot.core.xml.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.impl.LigandBuilder;
import org.uniprot.core.xml.jaxb.uniprot.LigandType;

class LigandConverterTest {
    private LigandConverter converter = new LigandConverter();

    @Test
    void testNameOnly() {
        Ligand ligand = createLigand("Substrate", null, null, null);
        LigandType ligandType = converter.toXml(ligand);
        assertNotNull(ligandType);
        assertEquals("Substrate", ligandType.getName());
        Ligand converted = converter.fromXml(ligandType);
        assertEquals(ligand, converted);
    }

    @Test
    void testNameAndId() {
        Ligand ligand = createLigand("Ca(2+)", "ChEBI:CHEBI:3245", null, null);
        LigandType ligandType = converter.toXml(ligand);
        assertNotNull(ligandType);
        assertEquals("Ca(2+)", ligandType.getName());
        Ligand converted = converter.fromXml(ligandType);
        assertEquals(ligand, converted);
    }

    @Test
    void testFull() {
        Ligand ligand = createLigand("Ca(2+)", "ChEBI:CHEBI:3245", "A1", "Some note");
        LigandType ligandType = converter.toXml(ligand);
        assertNotNull(ligandType);
        assertEquals("Ca(2+)", ligandType.getName());
        Ligand converted = converter.fromXml(ligandType);
        assertEquals(ligand, converted);
    }

    private Ligand createLigand(String name, String id, String label, String note) {
        LigandBuilder builder = new LigandBuilder();
        builder.name(name).id(id).label(label).note(note);

        return builder.build();
    }
}
