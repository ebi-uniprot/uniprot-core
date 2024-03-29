package org.uniprot.core.parser.fasta.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.fasta.impl.UniProtKBFastaBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

/**
 * @author lgonzales
 * @since 22/10/2020
 */
class UniProtKBFastaParserWriterTest {

    @Test
    void canWriteFastaStringWithFragmentSequence() {
        UniProtKBFasta entry =
                new UniProtKBFastaBuilder()
                        .id("P12345")
                        .uniProtkbId("P12345_PROT")
                        .entryType(UniProtKBEntryType.TREMBL)
                        .sequence(
                                new SequenceBuilder(
                                                "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBB")
                                        .build())
                        .geneName("Gene Name Value")
                        .proteinName("Protein Name Value")
                        .organism(
                                new OrganismBuilder()
                                        .taxonId(9606L)
                                        .scientificName("Organism Name Value")
                                        .build())
                        .sequenceVersion(2)
                        .proteinExistence(ProteinExistence.UNCERTAIN)
                        .flagType(FlagType.FRAGMENT)
                        .build();
        String fastaValue = UniProtKBFastaParserWriter.toString(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">tr|P12345|P12345_PROT"));
        assertTrue(fastaValue.contains(" Protein Name Value (Fragment) "));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=Gene Name Value PE=5 SV=2\n"));
        assertTrue(
                fastaValue.contains(
                        "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBB\nAAAAAAAAAABBBBBBBBBB"));
    }

    @Test
    void canWriteFastaString() {
        UniProtKBFasta entry =
                new UniProtKBFastaBuilder()
                        .id("P21802")
                        .uniProtkbId("P21802_HUMAN")
                        .entryType(UniProtKBEntryType.SWISSPROT)
                        .sequence(
                                new SequenceBuilder(
                                                "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBB")
                                        .build())
                        .geneName("Gene Name Value")
                        .proteinName("Protein Name Value")
                        .organism(
                                new OrganismBuilder()
                                        .taxonId(9606L)
                                        .scientificName("Organism Name Value")
                                        .build())
                        .sequenceVersion(2)
                        .proteinExistence(ProteinExistence.PREDICTED)
                        .flagType(FlagType.PRECURSOR)
                        .build();
        String fastaValue = UniProtKBFastaParserWriter.toString(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">sp|P21802|P21802_HUMAN"));
        assertTrue(fastaValue.contains(" Protein Name Value"));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=Gene Name Value PE=4 SV=2\n"));
        assertTrue(
                fastaValue.contains(
                        "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBB\nAAAAAAAAAABBBBBBBBBB"));
    }

    @Test
    void canWriteFastaStringForIsoformEntry() {
        UniProtKBFasta entry =
                new UniProtKBFastaBuilder()
                        .id("P21802-1")
                        .uniProtkbId("P21802_HUMAN")
                        .entryType(UniProtKBEntryType.SWISSPROT)
                        .sequence(
                                new SequenceBuilder(
                                                "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBB")
                                        .build())
                        .geneName("Gene Name Value")
                        .proteinName("Protein Name Value")
                        .organism(
                                new OrganismBuilder()
                                        .taxonId(9606L)
                                        .scientificName("Organism Name Value")
                                        .build())
                        .sequenceVersion(2)
                        .proteinExistence(ProteinExistence.PREDICTED)
                        .flagType(FlagType.PRECURSOR)
                        .build();
        String fastaValue = UniProtKBFastaParserWriter.toString(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">sp|P21802-1|P21802_HUMAN"));
        assertTrue(fastaValue.contains(" Protein Name Value"));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=Gene Name Value\n"));
        assertFalse(fastaValue.contains("PE=4"));
        assertFalse(fastaValue.contains("SV=2"));
        assertTrue(
                fastaValue.contains(
                        "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBB\nAAAAAAAAAABBBBBBBBBB"));
    }

    @Test
    void canWriteFastaStringWithSubsequence() {
        String seqRange = "10-15";
        UniProtKBFasta entry =
                new UniProtKBFastaBuilder()
                        .id("P21802")
                        .uniProtkbId("P12345_PROT")
                        .entryType(UniProtKBEntryType.SWISSPROT)
                        .sequence(
                                new SequenceBuilder(
                                        "ABCDE")
                                        .build())
                        .geneName("Gene Name Value")
                        .proteinName("Protein Name Value")
                        .organism(
                                new OrganismBuilder()
                                        .taxonId(9606L)
                                        .scientificName("Organism Name Value")
                                        .build())
                        .sequenceVersion(2)
                        .proteinExistence(ProteinExistence.UNCERTAIN)
                        .flagType(FlagType.FRAGMENT)
                        .sequenceRange(seqRange)
                        .build();
        String fastaValue = UniProtKBFastaParserWriter.toString(entry);
        assertNotNull(fastaValue);
        assertEquals(">sp|P21802|10-15\nABCDE", fastaValue);
    }
}
