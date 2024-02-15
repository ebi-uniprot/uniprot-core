package org.uniprot.core.parser.fasta.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.fasta.UniProtKBFasta;
import org.uniprot.core.fasta.impl.UniProtKBFastaBuilder;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.ProteinExistence;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.description.FlagType;
import org.uniprot.core.uniprotkb.description.impl.NameBuilder;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.uniprotkb.description.impl.ProteinNameBuilder;
import org.uniprot.core.uniprotkb.description.impl.ProteinSubNameBuilder;
import org.uniprot.core.uniprotkb.impl.*;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        UniProtKBFasta result = UniProtKBFastaParser.fromFastaString(fastaInput);
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
        String fastaValue = UniProtKBFastaParser.toFastaString(entry);
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
                                                new ProteinNameBuilder()
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
        String fastaValue = UniProtKBFastaParser.toFastaString(entry);
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
        String fastaValue = UniProtKBFastaParser.toFastaString(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">sp|P21802|P21802_HUMAN"));
        assertTrue(fastaValue.contains(" Protein Name Value (Fragment) "));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=Gene Name Value PE=4 SV=2\n"));
        assertTrue(
                fastaValue.contains(
                        "AAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBBAAAAAAAAAABBBBBBBBBB\nAAAAAAAAAABBBBBBBBBB"));
    }

    @Test
    void canParseToFastaWithMultipleGeneNames() {
        List<Gene> genes = new ArrayList<>();
        genes.add(
                new GeneBuilder().geneName(new GeneNameBuilder().value("Gene 1").build()).build());
        genes.add(
                new GeneBuilder().geneName(new GeneNameBuilder().value("Gene 2").build()).build());
        UniProtKBEntry entry = getEntryWithGene(genes);
        String fastaValue = UniProtKBFastaParser.toFastaString(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">tr|P12345|P12345_PROT"));
        assertTrue(fastaValue.contains(" Rec Name Value (Fragment) "));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=Gene 1 PE=1 SV=2\n"));
        assertTrue(fastaValue.contains("AAAAAAAAAABBBBBBBBBBAAAAAAAAAA"));
    }

    @Test
    void canParseToFastaWithMultipleOlnGeneNames() {
        List<Gene> genes = new ArrayList<>();
        genes.add(
                new GeneBuilder()
                        .orderedLocusNamesAdd(new OrderedLocusNameBuilder().value("OLN 1").build())
                        .build());
        genes.add(
                new GeneBuilder()
                        .orderedLocusNamesAdd(new OrderedLocusNameBuilder().value("OLN 2").build())
                        .build());
        UniProtKBEntry entry = getEntryWithGene(genes);
        String fastaValue = UniProtKBFastaParser.toFastaString(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">tr|P12345|P12345_PROT"));
        assertTrue(fastaValue.contains(" Rec Name Value (Fragment) "));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=OLN 1 PE=1 SV=2\n"));
        assertTrue(fastaValue.contains("AAAAAAAAAABBBBBBBBBBAAAAAAAAAA"));
    }

    @Test
    void canParseToFastaWithMultipleOrfGeneNames() {
        List<Gene> genes = new ArrayList<>();
        genes.add(
                new GeneBuilder().orfNamesAdd(new ORFNameBuilder().value("ORF 1").build()).build());
        genes.add(
                new GeneBuilder().orfNamesAdd(new ORFNameBuilder().value("ORF 2").build()).build());
        UniProtKBEntry entry = getEntryWithGene(genes);
        String fastaValue = UniProtKBFastaParser.toFastaString(entry);
        assertNotNull(fastaValue);
        assertTrue(fastaValue.contains(">tr|P12345|P12345_PROT"));
        assertTrue(fastaValue.contains(" Rec Name Value (Fragment) "));
        assertTrue(fastaValue.contains(" OS=Organism Name Value OX=9606 "));
        assertTrue(fastaValue.contains("GN=ORF 1 PE=1 SV=2\n"));
        assertTrue(fastaValue.contains("AAAAAAAAAABBBBBBBBBBAAAAAAAAAA"));
    }

    @Test
    void testToUniProtKBFasta(){
        List<Gene> genes = List.of(new GeneBuilder().orfNamesAdd(new ORFNameBuilder().value("ORF 1").build()).build());
        UniProtKBEntry entry = getEntryWithGene(genes);
        UniProtKBFasta result = UniProtKBFastaParser.toUniProtKBFasta(entry);
        assertNotNull(result);
        assertEquals(entry.getPrimaryAccession().getValue(), result.getId());
        assertEquals(entry.getEntryType(), result.getEntryType());
        assertEquals(entry.getUniProtkbId(), result.getUniProtkbId());
        assertEquals("Rec Name Value", result.getProteinName());
        assertEquals("ORF 1", result.getGeneName());
        assertEquals(entry.getOrganism(), result.getOrganism());
        assertEquals("FRAGMENTS_PRECURSOR", result.getFlagType().name());
        assertEquals(entry.getProteinExistence(), result.getProteinExistence());
        assertEquals(entry.getEntryAudit().getSequenceVersion(), result.getSequenceVersion());
        assertEquals(entry.getSequence(), result.getSequence());
        assertNull(result.getSequenceRange());
    }

    @Test
    void testToUniProtKBFastaWithSubsequenceRange(){
        List<Gene> genes = List.of(new GeneBuilder().orfNamesAdd(new ORFNameBuilder().value("ORF 1").build()).build());
        UniProtKBEntry entry = getEntryWithGene(genes);
        String sequenceRange = "10-20";
        UniProtKBFasta result = UniProtKBFastaParser.toUniProtKBFasta(entry, sequenceRange);
        assertNotNull(result);
        assertEquals(entry.getPrimaryAccession().getValue(), result.getId());
        assertEquals(entry.getEntryType(), result.getEntryType());
        assertEquals(entry.getUniProtkbId(), result.getUniProtkbId());
        assertEquals("Rec Name Value", result.getProteinName());
        assertEquals("ORF 1", result.getGeneName());
        assertEquals(entry.getOrganism(), result.getOrganism());
        assertEquals("FRAGMENTS_PRECURSOR", result.getFlagType().name());
        assertEquals(entry.getProteinExistence(), result.getProteinExistence());
        assertEquals(entry.getEntryAudit().getSequenceVersion(), result.getSequenceVersion());
        assertEquals(entry.getSequence().getValue().substring(9, 20), result.getSequence().getValue());
        assertEquals(sequenceRange, result.getSequenceRange());
    }

    @Test
    void testToUniProtKBFastaWithSubsequenceOutOfRange(){
        List<Gene> genes = List.of(new GeneBuilder().orfNamesAdd(new ORFNameBuilder().value("ORF 1").build()).build());
        UniProtKBEntry entry = getEntryWithGene(genes);
        String sequenceRange = "40-50";
        UniProtKBFasta result = UniProtKBFastaParser.toUniProtKBFasta(entry, sequenceRange);
        assertNotNull(result);
        assertEquals(entry.getPrimaryAccession().getValue(), result.getId());
        assertTrue(result.getSequence().getValue().isEmpty());
        assertEquals(sequenceRange, result.getSequenceRange());
    }

    @Test
    void testToUniProtKBFastaWithSubsequenceOutOfRangeEnd(){
        List<Gene> genes = List.of(new GeneBuilder().orfNamesAdd(new ORFNameBuilder().value("ORF 1").build()).build());
        UniProtKBEntry entry = getEntryWithGene(genes);
        String sequenceRange = "20-100";
        UniProtKBFasta result = UniProtKBFastaParser.toUniProtKBFasta(entry, sequenceRange);
        assertNotNull(result);
        assertEquals(entry.getPrimaryAccession().getValue(), result.getId());
        assertEquals(entry.getSequence().getValue().substring(19), result.getSequence().getValue());
        assertEquals(sequenceRange, result.getSequenceRange());
    }

    private UniProtKBEntry getEntryWithGene(List<Gene> genes) {
        UniProtKBEntry entry =
                new UniProtKBEntryBuilder("P12345", "P12345_PROT", UniProtKBEntryType.TREMBL)
                        .sequence(new SequenceBuilder("AAAAAAAAAABBBBBBBBBBAAAAAAAAAA").build())
                        .genesSet(genes)
                        .proteinDescription(
                                new ProteinDescriptionBuilder()
                                        .flag(FlagType.FRAGMENTS_PRECURSOR)
                                        .recommendedName(
                                                new ProteinNameBuilder()
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
        return entry;
    }

}
