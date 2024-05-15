package org.uniprot.core.parser.fasta.uniparc;

import org.junit.jupiter.api.Test;
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
import static org.uniprot.core.parser.fasta.uniparc.UniParcFastaParserTestUtils.create;

class UniParcProteomeFastaParserTest {

    @Test
    void toFastaFullSingleValues() {
        UniParcEntry entry = create();
        String fasta = UniParcProteomeFastaParser.toFasta(entry, "UP000005640");
        String expected =
                ">UPI0000083A08|EMBL:CQR81549 UP=UP000005640:Chromosome 1 OX=9606 OS=Homo Sapiens AC=P12345\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaFullMultiValues() {
        String proteomeId = "UP000005640";
        Organism organism = getOrganism(9606L, "Homo Sapiens");
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.SWISSPROT, "P21802", true, organism, null, null, "Protein Name 1", "Gene Name1"));
        xrefs.add(getXref(UniParcDatabase.TREMBL, "P12345", true, organism, null, null, "Protein Name 2", "Gene Name2"));
        xrefs.add(getXref(UniParcDatabase.EMBL_CON, "XP12345", true, organism, proteomeId, "C1"));
        xrefs.add(getXref(UniParcDatabase.EMBL_TPA, "XP54321", true, organism, proteomeId, "C2"));
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry, proteomeId);
        String expected =
                ">UPI0000083A09|EMBL_CON:XP12345|EMBL_TPA:XP54321 UP=UP000005640:C1|C2 OX=9606 OS=Homo Sapiens AC=P21802|P12345\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaWithoutAccessions() {
        String proteomeId = "UP000005640";
        Organism organism = getOrganism(9606L, "Homo Sapiens");
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.SWISSPROT_VARSPLIC, "P21802-1", true, organism));
        xrefs.add(getXref(UniParcDatabase.EMBL_CON, "XP12345", true, organism, proteomeId, "C1"));
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry, proteomeId);
        String expected =
                ">UPI0000083A09|EMBL_CON:XP12345 UP=UP000005640:C1 OX=9606 OS=Homo Sapiens\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaWithoutComponent() {
        String proteomeId = "UP000005640";
        Organism organism = getOrganism(9606L, "Homo Sapiens");
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.EMBL_CON, "XP12345", true, organism, proteomeId, null));
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry, proteomeId);
        String expected =
                ">UPI0000083A09|EMBL_CON:XP12345 UP=UP000005640 OX=9606 OS=Homo Sapiens\n" +
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
        String fasta = UniParcProteomeFastaParser.toFasta(entry, proteomeId);
        String expected =
                ">UPI0000083A09|EMBL_CON:XP12345 UP=UP000005640:C8\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaWithoutSource() {
        String proteomeId = "UP000005640";
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.PDB, "PDB12345", true, null, proteomeId, "C9"));
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry, proteomeId);
        String expected =
                ">UPI0000083A09 UP=UP000005640:C9\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }

    @Test
    void toFastaFilterInactiveSources() {
        String proteomeId = "UP000005640";
        List<UniParcCrossReference> xrefs = new ArrayList<>();
        xrefs.add(getXref(UniParcDatabase.EMBL_CON, "XP12345", true, null, proteomeId, "C5"));
        xrefs.add(getXref(UniParcDatabase.EMBL_CON, "XP54321", false, null, proteomeId, "C3"));
        UniParcEntry entry = new UniParcEntryBuilder()
                .uniParcId(new UniParcIdBuilder("UPI0000083A09").build())
                .uniParcCrossReferencesSet(xrefs)
                .sequence(getSequence())
                .build();
        String fasta = UniParcProteomeFastaParser.toFasta(entry, proteomeId);
        String expected =
                ">UPI0000083A09|EMBL_CON:XP12345 UP=UP000005640:C5\n" +
                        "MSMAMARALATLGRLRYRVSGQLPLLDETAIEVMAGGQFLDGRKAREELGFFSTTALDDT\n" +
                        "LLRAIDWFRDNGYFNA";
        assertEquals(expected, fasta);
    }
}