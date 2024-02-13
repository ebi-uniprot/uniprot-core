package org.uniprot.core.fasta.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.genecentric.Protein;
import org.uniprot.core.genecentric.impl.ProteinBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

/**
 * @author lgonzales
 * @since 22/10/2020
 */
class UniProtKBFastaBuilderTest {

    @Test
    void canSetEntryTypeString() {
        UniProtKBEntryType type = UniProtKBEntryType.TREMBL;
        UniProtKBFasta entry = new UniProtKBFastaBuilder().entryType(type).build();
        assertNotNull(entry.getEntryType());
        assertEquals(type, entry.getEntryType());
    }

    @Test
    void canSetId() {
        String id = "P21802";
        UniProtKBFasta entry = new UniProtKBFastaBuilder().id(id).build();
        assertNotNull(entry.getId());
        assertEquals(id, entry.getId());
    }

    @Test
    void canSetUniProtkbIdString() {
        String uniProtkbId = "ID VALUE";
        UniProtKBFasta entry = new UniProtKBFastaBuilder().uniProtkbId(uniProtkbId).build();
        assertNotNull(entry.getUniProtkbId());
        assertEquals(uniProtkbId, entry.getUniProtkbId().getValue());
    }

    @Test
    void canSetUniProtkbId() {
        UniProtKBId uniProtkbId = new UniProtKBIdBuilder("ID VALUE").build();
        UniProtKBFasta entry = new UniProtKBFastaBuilder().uniProtkbId(uniProtkbId).build();
        assertEquals(uniProtkbId, entry.getUniProtkbId());
    }

    @Test
    void canSetProteinName() {
        String proteinName = "Protein Name VALUE";
        UniProtKBFasta entry = new UniProtKBFastaBuilder().proteinName(proteinName).build();
        assertNotNull(entry.getProteinName());
        assertEquals(proteinName, entry.getProteinName());
    }

    @Test
    void canSetOrganism() {
        Organism organism = new OrganismBuilder().taxonId(10L).build();
        UniProtKBFasta entry = new UniProtKBFastaBuilder().organism(organism).build();
        assertEquals(organism, entry.getOrganism());
    }

    @Test
    void canSetGeneName() {
        String geneName = "Gene Name VALUE";
        UniProtKBFasta entry = new UniProtKBFastaBuilder().geneName(geneName).build();
        assertNotNull(entry.getGeneName());
        assertEquals(geneName, entry.getGeneName());
    }

    @Test
    void canSetProteinExistence() {
        ProteinExistence proteinExistence = ProteinExistence.PROTEIN_LEVEL;
        UniProtKBFasta entry =
                new UniProtKBFastaBuilder().proteinExistence(proteinExistence).build();
        assertNotNull(entry.getProteinExistence());
        assertEquals(proteinExistence, entry.getProteinExistence());
    }

    @Test
    void canSetFlagType() {
        FlagType flagType = FlagType.FRAGMENT;
        UniProtKBFasta entry = new UniProtKBFastaBuilder().flagType(flagType).build();
        assertNotNull(entry.getFlagType());
        assertEquals(flagType, entry.getFlagType());
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
    void canSetsequenceVersion() {
        int sequenceVersion = 2;
        UniProtKBFasta entry = new UniProtKBFastaBuilder().sequenceVersion(sequenceVersion).build();
        assertEquals(sequenceVersion, entry.getSequenceVersion());
    }

    @Test
    void fromEmptyUniprotKBFasta() {
        UniProtKBFasta protein = new UniProtKBFastaBuilder().build();
        UniProtKBFasta proteinFrom = UniProtKBFastaBuilder.from(protein).build();
        assertEquals(protein, proteinFrom);
    }

    @Test
    void fromCompleteUniprotKBFasta() {
        UniProtKBFasta proteinFasta = getUniProtKBFasta();
        UniProtKBFasta proteinFastaFrom = UniProtKBFastaBuilder.from(proteinFasta).build();
        assertEquals(proteinFasta, proteinFastaFrom);
        assertTrue(proteinFasta.equals(proteinFastaFrom) && proteinFastaFrom.equals(proteinFasta));
        assertEquals(proteinFasta.hashCode(), proteinFastaFrom.hashCode());
    }

    @Test
    void fromProteinFastaToProtein() {
        UniProtKBFasta proteinFasta = getUniProtKBFasta();
        Protein protein = ProteinBuilder.from(proteinFasta).build();

        UniProtKBFasta proteinFastaFrom = UniProtKBFastaBuilder.from(protein).build();
        assertTrue(proteinFasta.equals(proteinFastaFrom) && proteinFastaFrom.equals(proteinFasta));
    }

    @Test
    void canSetSequenceRange() {
        String sequenceRange = "20-30";
        UniProtKBFasta entry = new UniProtKBFastaBuilder().sequenceRange(sequenceRange).build();
        assertEquals(sequenceRange, entry.getSequenceRange());
    }

    private UniProtKBFasta getUniProtKBFasta() {
        return new UniProtKBFastaBuilder()
                .id("P21802")
                .entryType(UniProtKBEntryType.TREMBL)
                .flagType(FlagType.PRECURSOR)
                .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                .geneName("Gene Name")
                .organism(new OrganismBuilder().taxonId(123L).build())
                .proteinName("protein Name")
                .sequence("AAAAA")
                .sequenceVersion(2)
                .uniProtkbId("P21802_ID")
                .sequenceRange("10-20")
                .build();
    }
}
