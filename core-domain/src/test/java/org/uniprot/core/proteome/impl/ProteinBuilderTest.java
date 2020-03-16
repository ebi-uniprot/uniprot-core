package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.uniprotkb.UniProtkbEntryType;
import org.uniprot.core.uniprotkb.impl.UniProtkbAccessionBuilder;

class ProteinBuilderTest {

    @Test
    void testUniProtAccession() {
        String accession = "P12345";
        Protein protein = new ProteinBuilder().accession(accession).build();

        assertEquals(accession, protein.getAccession().getValue());
        protein =
                new ProteinBuilder()
                        .accession(new UniProtkbAccessionBuilder(accession).build())
                        .build();

        assertEquals(accession, protein.getAccession().getValue());
    }

    @Test
    void testEntryType() {
        UniProtkbEntryType entryType = UniProtkbEntryType.SWISSPROT;
        Protein protein = new ProteinBuilder().entryType(entryType).build();
        assertEquals(entryType, protein.getEntryType());

        entryType = UniProtkbEntryType.TREMBL;
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
                        .entryType(UniProtkbEntryType.SWISSPROT)
                        .geneName(gene)
                        .geneNameType(type)
                        .build();

        ProteinBuilder builder = ProteinBuilder.from(protein);
        Protein newProtein = builder.entryType(UniProtkbEntryType.TREMBL).build();

        assertEquals(protein.getAccession(), newProtein.getAccession());
        assertEquals(protein.getGeneName(), newProtein.getGeneName());
        assertEquals(UniProtkbEntryType.SWISSPROT, protein.getEntryType());
        assertEquals(UniProtkbEntryType.TREMBL, newProtein.getEntryType());
    }
}
