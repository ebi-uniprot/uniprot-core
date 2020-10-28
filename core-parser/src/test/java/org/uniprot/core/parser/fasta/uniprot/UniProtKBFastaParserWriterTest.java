package org.uniprot.core.parser.fasta.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.fasta.impl.UniProtKBFastaBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.impl.*;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

/**
 * @author lgonzales
 * @since 22/10/2020
 */
class UniProtKBFastaParserWriterTest {

    @Test
    void canParseEntryWithFragmentSequence() {
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
    void canParseEntry() {
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
}
