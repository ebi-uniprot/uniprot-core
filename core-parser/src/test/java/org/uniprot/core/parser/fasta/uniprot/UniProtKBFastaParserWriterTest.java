package org.uniprot.core.parser.fasta.uniprot;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.fasta.impl.UniProtKBFastaBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.description.impl.NameBuilder;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.uniprotkb.description.impl.ProteinRecNameBuilder;
import org.uniprot.core.uniprotkb.description.impl.ProteinSubNameBuilder;
import org.uniprot.core.uniprotkb.impl.*;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

/**
 * @author lgonzales
 * @since 22/10/2020
 */
class UniProtKBFastaParserWriterTest {

    @Test
    void canParseEntry() {
        UniProtKBEntry entry =
                new UniProtKBEntryBuilder("P12345", "P12345_PROT", UniProtKBEntryType.SWISSPROT)
                        .sequence(
                                new SequenceBuilder(
                                                "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBB")
                                        .build())
                        .genesAdd(
                                new GeneBuilder()
                                        .geneName(
                                                new GeneNameBuilder()
                                                        .value("Gene Name Value")
                                                        .build())
                                        .build())
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .recommendedName(
                                                new ProteinRecNameBuilder()
                                                        .fullName(
                                                                new NameBuilder()
                                                                        .value("Rec Name Value")
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(
                                new OrganismBuilder()
                                        .taxonId(9606L)
                                        .scientificName("Organism Name Value")
                                        .build())
                        .entryAudit(new EntryAuditBuilder().sequenceVersion(2).build())
                        .proteinExistence(ProteinExistence.PREDICTED)
                        .build();
        String fastaValue = UniProtKBFastaParserWriter.parse(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">sp|P12345|P12345_PROT"));
        assertTrue(fastaValue.contains(" Rec Name Value "));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=Gene Name Value PE=4 SV=2\n"));
        assertTrue(
                fastaValue.contains(
                        "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBB\nAAAAAAAAAABBBBBBBBBB"));
    }

    @Test
    void canParseFragmentSequence() {
        UniProtKBEntry entry =
                new UniProtKBEntryBuilder("P12345", "P12345_PROT", UniProtKBEntryType.TREMBL)
                        .sequence(new SequenceBuilder("AAAAAAAAAABBBBBBBBBBAAAAAAAAAA").build())
                        .genesAdd(
                                new GeneBuilder()
                                        .orfNamesAdd(
                                                new ORFNameBuilder()
                                                        .value("Orf Name Value")
                                                        .build())
                                        .build())
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .flag(FlagType.FRAGMENT)
                                        .submissionNamesAdd(
                                                new ProteinSubNameBuilder()
                                                        .fullName(
                                                                new NameBuilder()
                                                                        .value("Sub Name Value")
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(
                                new OrganismBuilder()
                                        .taxonId(9606L)
                                        .scientificName("Organism Name Value")
                                        .build())
                        .entryAudit(new EntryAuditBuilder().sequenceVersion(2).build())
                        .proteinExistence(ProteinExistence.UNCERTAIN)
                        .build();
        String fastaValue = UniProtKBFastaParserWriter.parse(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">tr|P12345|P12345_PROT"));
        assertTrue(fastaValue.contains(" Sub Name Value (Fragment) "));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=Orf Name Value PE=5 SV=2\n"));
        assertTrue(fastaValue.contains("AAAAAAAAAABBBBBBBBBBAAAAAAAAAA"));
    }

    @Test
    void canParseWithOlnGene() {
        UniProtKBEntry entry =
                new UniProtKBEntryBuilder("P12345", "P12345_PROT", UniProtKBEntryType.TREMBL)
                        .sequence(new SequenceBuilder("AAAAAAAAAABBBBBBBBBBAAAAAAAAAA").build())
                        .genesAdd(
                                new GeneBuilder()
                                        .orderedLocusNamesAdd(
                                                new OrderedLocusNameBuilder()
                                                        .value("Oln Name Value")
                                                        .build())
                                        .build())
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .flag(FlagType.FRAGMENTS_PRECURSOR)
                                        .submissionNamesAdd(
                                                new ProteinSubNameBuilder()
                                                        .fullName(
                                                                new NameBuilder()
                                                                        .value("Sub Name Value")
                                                                        .build())
                                                        .build())
                                        .build())
                        .organism(
                                new OrganismBuilder()
                                        .taxonId(9606L)
                                        .scientificName("Organism Name Value")
                                        .build())
                        .entryAudit(new EntryAuditBuilder().sequenceVersion(2).build())
                        .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                        .build();
        String fastaValue = UniProtKBFastaParserWriter.parse(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">tr|P12345|P12345_PROT"));
        assertTrue(fastaValue.contains(" Sub Name Value (Fragment) "));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=Oln Name Value PE=1 SV=2\n"));
        assertTrue(fastaValue.contains("AAAAAAAAAABBBBBBBBBBAAAAAAAAAA"));
    }

    @Test
    void canParseWithUniProtKBFastaEntry() {
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
                        .flagType(FlagType.FRAGMENT)
                        .build();
        String fastaValue = UniProtKBFastaParserWriter.parse(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">sp|P21802|P21802_HUMAN"));
        assertTrue(fastaValue.contains(" Protein Name Value (Fragment) "));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=Gene Name Value PE=4 SV=2\n"));
        assertTrue(
                fastaValue.contains(
                        "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBB\nAAAAAAAAAABBBBBBBBBB"));
    }
}
