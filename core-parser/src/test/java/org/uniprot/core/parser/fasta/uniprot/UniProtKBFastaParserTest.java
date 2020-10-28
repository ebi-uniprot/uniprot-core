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
class UniProtKBFastaParserTest {

    @Test
    void parseFromFasta() {
        String fastaInput =
                ">tr|Q9HI14|Q9HI14_HALSA Isoform of O51971, Transcription initiation factor IID OS=Halobacterium salinarum (strain ATCC 700922 / JCM 11081 / NRC-1) (Halobacterium halobium) OX=64091 GN=tbpA PE=3 SV=1\n"
                        + "MDLEGADYDPEQFPGLVYRLDEPSVVALLFGSGKLVITGGKHPVDAEHAVDTIDSRLEDL\nGLLDGYGDRAK";
        UniProtKBFasta result = UniProtKBFastaParser.fromFasta(fastaInput);
        assertNotNull(result);
        assertEquals(UniProtKBEntryType.TREMBL, result.getEntryType());
        assertNotNull(result.getId());
        assertEquals("Q9HI14", result.getId());
        assertNotNull(result.getUniProtkbId());
        assertEquals("Q9HI14_HALSA", result.getUniProtkbId().getValue());
        assertEquals(
                "Isoform of O51971, Transcription initiation factor IID", result.getProteinName());

        assertNotNull(result.getOrganism());
        assertEquals("tbpA", result.getGeneName());
        assertEquals(ProteinExistence.HOMOLOGY, result.getProteinExistence());
        assertEquals(FlagType.PRECURSOR, result.getFlagType());
        assertEquals(1, result.getSequenceVersion());
        assertNotNull(result.getSequence());
        assertEquals(
                "MDLEGADYDPEQFPGLVYRLDEPSVVALLFGSGKLVITGGKHPVDAEHAVDTIDSRLEDLGLLDGYGDRAK",
                result.getSequence().getValue());
    }

    @Test
    void parseToFasta() {
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
        String fastaValue = UniProtKBFastaParser.toFasta(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">tr|P12345|P12345_PROT"));
        assertTrue(fastaValue.contains(" Sub Name Value (Fragment) "));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=Orf Name Value PE=5 SV=2\n"));
        assertTrue(fastaValue.contains("AAAAAAAAAABBBBBBBBBBAAAAAAAAAA"));
    }

    @Test
    void canParseToFastaWithOlnGene() {
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
                        .proteinExistence(ProteinExistence.PROTEIN_LEVEL)
                        .build();
        String fastaValue = UniProtKBFastaParser.toFasta(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">tr|P12345|P12345_PROT"));
        assertTrue(fastaValue.contains(" Rec Name Value (Fragment) "));
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
        String fastaValue = UniProtKBFastaParser.toFasta(entry);
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
