package org.uniprot.core.genecentric.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.fasta.impl.UniProtKBFastaBuilder;
import org.uniprot.core.genecentric.Protein;
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
 * @since 16/10/2020
 */
class ProteinBuilderTest {

    @Test
    void canSetEntryTypeString() {
        UniProtKBEntryType type = UniProtKBEntryType.TREMBL;
        Protein entry = new ProteinBuilder().entryType(type).build();
        assertNotNull(entry.getEntryType());
        assertEquals(type, entry.getEntryType());
    }

    @Test
    void canSetAccessionString() {
        String accession = "P21802";
        Protein entry = new ProteinBuilder().id(accession).build();
        assertNotNull(entry.getId());
        assertEquals(accession, entry.getId());
    }

    @Test
    void canSetUniProtkbIdString() {
        String uniProtkbId = "ID VALUE";
        Protein entry = new ProteinBuilder().uniProtkbId(uniProtkbId).build();
        assertNotNull(entry.getUniProtkbId());
        assertEquals(uniProtkbId, entry.getUniProtkbId().getValue());
    }

    @Test
    void canSetUniProtkbId() {
        UniProtKBId uniProtkbId = new UniProtKBIdBuilder("ID VALUE").build();
        Protein entry = new ProteinBuilder().uniProtkbId(uniProtkbId).build();
        assertEquals(uniProtkbId, entry.getUniProtkbId());
    }

    @Test
    void canSetProteinName() {
        String proteinName = "Protein Name VALUE";
        Protein entry = new ProteinBuilder().proteinName(proteinName).build();
        assertNotNull(entry.getProteinName());
        assertEquals(proteinName, entry.getProteinName());
    }

    @Test
    void canSetOrganism() {
        Organism organism = new OrganismBuilder().taxonId(10L).build();
        Protein entry = new ProteinBuilder().organism(organism).build();
        assertEquals(organism, entry.getOrganism());
    }

    @Test
    void canSetGeneName() {
        String geneName = "Gene Name VALUE";
        Protein entry = new ProteinBuilder().geneName(geneName).build();
        assertNotNull(entry.getGeneName());
        assertEquals(geneName, entry.getGeneName());
    }

    @Test
    void canSetProteinExistence() {
        ProteinExistence proteinExistence = ProteinExistence.PROTEIN_LEVEL;
        Protein entry = new ProteinBuilder().proteinExistence(proteinExistence).build();
        assertNotNull(entry.getProteinExistence());
        assertEquals(proteinExistence, entry.getProteinExistence());
    }

    @Test
    void canSetFlagType() {
        FlagType flagType = FlagType.FRAGMENT;
        Protein entry = new ProteinBuilder().flagType(flagType).build();
        assertNotNull(entry.getFlagType());
        assertEquals(flagType, entry.getFlagType());
    }

    @Test
    void canSetSequence() {
        Sequence sequence = new SequenceBuilder("SEQUENCE VALUE").build();
        Protein entry = new ProteinBuilder().sequence(sequence).build();
        assertEquals(sequence, entry.getSequence());
    }

    @Test
    void canSetsequenceString() {
        String sequence = "SEQUENCE VALUE";
        Protein entry = new ProteinBuilder().sequence(sequence).build();
        assertNotNull(entry.getSequence());
        assertEquals(sequence, entry.getSequence().getValue());
    }

    @Test
    void canSetsequenceVersion() {
        int sequenceVersion = 2;
        Protein entry = new ProteinBuilder().sequenceVersion(sequenceVersion).build();
        assertEquals(sequenceVersion, entry.getSequenceVersion());
    }

    @Test
    void canSetSequenceRange() {
        String seqRange = "20-30";
        Protein entry = new ProteinBuilder().sequenceRange(seqRange).build();
        assertEquals(seqRange, entry.getSequenceRange());
    }

    @Test
    void fromEmptyProtein() {
        Protein protein = new ProteinBuilder().build();
        Protein proteinFrom = ProteinBuilder.from(protein).build();
        assertEquals(protein, proteinFrom);
    }

    @Test
    void fromCompleteProtein() {
        Protein protein = getProtein();
        Protein proteinFrom = ProteinBuilder.from(protein).build();
        assertEquals(protein, proteinFrom);
        assertTrue(protein.equals(proteinFrom) && proteinFrom.equals(protein));
        assertEquals(protein.hashCode(), proteinFrom.hashCode());
    }

    private Protein getProtein() {
        return new ProteinBuilder()
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
                .build();
    }

    @Test
    void fromProteinFastaToProtein() {
        Protein protein = getProtein();
        UniProtKBFasta proteinFasta = UniProtKBFastaBuilder.from(protein).build();

        Protein proteinFrom = ProteinBuilder.from(proteinFasta).build();
        assertTrue(protein.equals(proteinFrom) && proteinFrom.equals(protein));
    }
}
