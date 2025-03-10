package org.uniprot.core.parser.fasta.uniparc;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Property;
import org.uniprot.core.uniparc.UniParcCrossReference;
import org.uniprot.core.uniparc.UniParcDatabase;
import org.uniprot.core.uniparc.UniParcEntry;
import org.uniprot.core.uniparc.impl.UniParcEntryBuilder;
import org.uniprot.core.uniparc.impl.UniParcIdBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.parser.fasta.uniparc.UniParcFastaParserTestUtils.*;

class UniParcProteomeFastaParserTest {

    private static final Property source1 = new Property(UniParcCrossReference.PROPERTY_SOURCES, "ABC01416:UP000005640:Chromosome 1");
    private static final UniParcCrossReference xrefSource1 = getXref(UniParcDatabase.EMBL_CON, "ABC01416", true,  null, "UP000005640", "Chromosome 1");
    private static final Property source2 = new Property(UniParcCrossReference.PROPERTY_SOURCES, "ABC01417:UP000005640:Chromosome 2");
    private static final UniParcCrossReference xrefSource2 = getXref(UniParcDatabase.REFSEQ, "ABC01417", true,  null, "UP000005640", "Chromosome 2");
    private static final Organism humanOrganism = getOrganism(9606L, "Homo Sapiens");

    @Test
    void toFastaWithoutCrossReferencesReturnJustIdAndSequence() {
        UniParcEntry entry = new UniParcEntryBuilder().uniParcId("UPI0000083A08")
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry);
        String expected = ">UPI0000083A08\n" +
                "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaFullSingleValues() {
        Organism organism = getOrganism(9606L, "Homo Sapiens");
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.SWISSPROT, "P21802", true, organism, null, null, "Protein Name 1", "Gene Name1", source1 ));
        xrefs.add(xrefSource1);
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry);
        String expected =
                ">UPI0000083A09 Protein Name 1 OS=Homo Sapiens OX=9606 GN=Gene Name1 AC=P21802 SS=EMBL_CON:ABC01416 PC=UP000005640:Chromosome 1\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaFullMultiValues() {
        Organism organism = getOrganism(9606L, "Homo Sapiens");
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.SWISSPROT, "P21802", true, organism, null, null, "Protein Name 1", "Gene Name1", source1 ));
        xrefs.add(xrefSource1);
        xrefs.add(getXref(UniParcDatabase.TREMBL, "P12345", true, organism, null, null, "Protein Name 2", "Gene Name2", source2));
        xrefs.add(xrefSource2);
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry);
        String expected =
                ">UPI0000083A09 Protein Name 1|Protein Name 2 OS=Homo Sapiens OX=9606 GN=Gene Name1|Gene Name2 AC=P21802|P12345 SS=EMBL_CON:ABC01416|RefSeq:ABC01417 PC=UP000005640:Chromosome 1|Chromosome 2\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaFullAccessionActiveOnly() {
        Organism organism = getOrganism(9606L, "Homo Sapiens");
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.SWISSPROT, "P21802", true, organism, null, null, "Protein Name 1", "Gene Name1", source1 ));
        xrefs.add(xrefSource1);
        xrefs.add(getXref(UniParcDatabase.TREMBL, "P12345", false, organism, null, null, "Protein Name 2", "Gene Name2", source2));
        xrefs.add(xrefSource2);
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry);
        String expected =
                ">UPI0000083A09 Protein Name 1 OS=Homo Sapiens OX=9606 GN=Gene Name1 AC=P21802 SS=EMBL_CON:ABC01416 PC=UP000005640:Chromosome 1\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaWithoutAccessions() {
        String proteomeId = "UP000005640";
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.EMBL_CON, "XP12345", true, humanOrganism, proteomeId, "C1"));
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry);
        String expected =
                ">UPI0000083A09 OS=Homo Sapiens OX=9606 SS=EMBL_CON:XP12345 PC=UP000005640:C1\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaWithoutComponent() {
        String proteomeId = "UP000005640";
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.EMBL_CON, "XP12345", true, humanOrganism, proteomeId, null));
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry);
        String expected =
                ">UPI0000083A09 OS=Homo Sapiens OX=9606 SS=EMBL_CON:XP12345 PC=UP000005640\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaWithoutOrganism() {
        String proteomeId = "UP000005640";
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.EMBL_CON, "XP12345", true, null, proteomeId, "C8"));
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry);
        String expected =
                ">UPI0000083A09 SS=EMBL_CON:XP12345 PC=UP000005640:C8\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaActiveOnlySources() {
        String proteomeId = "UP000005640";
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.EMBL_CON, "XP12345", true, humanOrganism, proteomeId, "C5"));
        xrefs.add(getXref(UniParcDatabase.EMBL_CON, "XP54321", false, null, proteomeId, "C3"));
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry);
        String expected =
                ">UPI0000083A09 OS=Homo Sapiens OX=9606 SS=EMBL_CON:XP12345 PC=UP000005640:C5\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }
}