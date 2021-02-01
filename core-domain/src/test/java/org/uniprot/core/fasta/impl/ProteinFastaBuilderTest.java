package org.uniprot.core.fasta.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.fasta.ProteinFasta;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.impl.SequenceBuilder;

/**
 * @author lgonzales
 * @since 22/10/2020
 */
class ProteinFastaBuilderTest {

    @Test
    void canSetId() {
        String id = "P21802";
        UniProtKBFasta entry = new UniProtKBFastaBuilder().id(id).build();
        assertNotNull(entry.getId());
        assertEquals(id, entry.getId());
    }

    @Test
    void canSetSequence() {
        Sequence sequence = new SequenceBuilder("SEQUENCE VALUE").build();
        UniProtKBFasta entry = new UniProtKBFastaBuilder().sequence(sequence).build();
        assertEquals(sequence, entry.getSequence());
    }

    @Test
    void canSetsequenceString() {
        String sequence = "SEQUENCE VALUE";
        UniProtKBFasta entry = new UniProtKBFastaBuilder().sequence(sequence).build();
        assertNotNull(entry.getSequence());
        assertEquals(sequence, entry.getSequence().getValue());
    }

    @Test
    void fromCompleteProteinFasta() {
        ProteinFasta protein = new ProteinFastaBuilder().id("P21802").sequence("AAAAA").build();
        ProteinFasta proteinFrom = ProteinFastaBuilder.from(protein).build();
        assertEquals(protein, proteinFrom);
        assertTrue(protein.equals(proteinFrom) && proteinFrom.equals(protein));
        assertEquals(protein.hashCode(), proteinFrom.hashCode());
    }
}
