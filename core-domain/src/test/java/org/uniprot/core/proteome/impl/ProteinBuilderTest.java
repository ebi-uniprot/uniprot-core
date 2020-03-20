package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

class ProteinBuilderTest {

    @Test
    void testUniProtAccession() {
        String accession = "P12345";
        Protein protein = new ProteinBuilder().accession(accession).build();

        assertEquals(accession, protein.getAccession().getValue());
        protein =
                new ProteinBuilder()
                        .accession(new UniProtKBAccessionBuilder(accession).build())
                        .build();

        assertEquals(accession, protein.getAccession().getValue());
    }

    @Test
    void testEntryType() {
        UniProtKBEntryType entryType = UniProtKBEntryType.SWISSPROT;
        Protein protein = new ProteinBuilder().entryType(entryType).build();
        assertEquals(entryType, protein.getEntryType());

        entryType = UniProtKBEntryType.TREMBL;
        protein = new ProteinBuilder().entryType(entryType).build();
        assertEquals(entryType, protein.getEntryType());
    }

    @Test
    void testSequenceLength() {
        long leng = 241;

        Protein protein = new ProteinBuilder().sequenceLength(leng).build();
        assertEquals(leng, protein.getSequenceLength());
    }

    @Test
    void testGene() {
        String gene = "some gene Value";
        GeneNameType type = GeneNameType.OLN;

        Protein protein = new ProteinBuilder().geneName(gene).geneNameType(type).build();

        assertEquals(gene, protein.getGeneName());
        assertEquals(type, protein.getGeneNameType());
    }

    @Test
    void testFrom() {
        String accession = "P12345";
        String gene = "some gene Value";
        GeneNameType type = GeneNameType.OLN;

        Protein protein =
                new ProteinBuilder()
                        .accession(accession)
                        .entryType(UniProtKBEntryType.SWISSPROT)
                        .geneName(gene)
                        .geneNameType(type)
                        .build();

        ProteinBuilder builder = ProteinBuilder.from(protein);
        Protein newProtein = builder.entryType(UniProtKBEntryType.TREMBL).build();

        assertEquals(protein.getAccession(), newProtein.getAccession());
        assertEquals(protein.getGeneName(), newProtein.getGeneName());
        assertEquals(UniProtKBEntryType.SWISSPROT, protein.getEntryType());
        assertEquals(UniProtKBEntryType.TREMBL, newProtein.getEntryType());
    }
}
