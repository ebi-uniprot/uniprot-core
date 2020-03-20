package org.uniprot.core.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.proteome.impl.CanonicalProteinBuilder;
import org.uniprot.core.proteome.impl.ProteinBuilder;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.xml.jaxb.proteome.CanonicalGene;
import org.uniprot.core.xml.jaxb.proteome.EntryType;
import org.uniprot.core.xml.jaxb.proteome.GeneNameType;
import org.uniprot.core.xml.jaxb.proteome.GeneType;
import org.uniprot.core.xml.jaxb.proteome.ObjectFactory;

class CanonicalProteinConverterTest {
    private ObjectFactory xmlFactory = new ObjectFactory();
    CanonicalProteinConverter converter = new CanonicalProteinConverter();

    @Test
    void testFromXml() {
        CanonicalGene cGene = xmlFactory.createCanonicalGene();
        cGene.setGene(createGene1());
        cGene.getRelatedGene().add(createGene2());
        cGene.getRelatedGene().add(createGene3());

        CanonicalProtein cProtein = converter.fromXml(cGene);

        Protein protein = cProtein.getCanonicalProtein();
        assertEquals("P12345", protein.getAccession().getValue());
        assertEquals(UniProtKBEntryType.SWISSPROT, protein.getEntryType());
        assertEquals("gen1", protein.getGeneName());
        assertEquals(org.uniprot.core.proteome.GeneNameType.MOD, protein.getGeneNameType());
        assertEquals(123l, protein.getSequenceLength());
        assertEquals(2, cProtein.getRelatedProteins().size());
    }

    @Test
    void testToXml() {
        Protein protein =
                new ProteinBuilder()
                        .accession("P21312")
                        .entryType(UniProtKBEntryType.TREMBL)
                        .geneName("some gene")
                        .geneNameType(org.uniprot.core.proteome.GeneNameType.ENSEMBL)
                        .sequenceLength(324)
                        .build();

        Protein protein2 =
                new ProteinBuilder()
                        .accession("P21912")
                        .entryType(UniProtKBEntryType.SWISSPROT)
                        .geneName("some gene1")
                        .geneNameType(org.uniprot.core.proteome.GeneNameType.ENSEMBL)
                        .sequenceLength(334)
                        .build();
        Protein protein3 =
                new ProteinBuilder()
                        .accession("P31912")
                        .entryType(UniProtKBEntryType.SWISSPROT)
                        .geneName("some gene3")
                        .geneNameType(org.uniprot.core.proteome.GeneNameType.OLN)
                        .sequenceLength(434)
                        .build();
        CanonicalProteinBuilder builder = new CanonicalProteinBuilder();
        CanonicalProtein cProtein =
                builder.canonicalProtein(protein)
                        .relatedProteinsAdd(protein2)
                        .relatedProteinsAdd(protein3)
                        .build();

        CanonicalGene cGene = converter.toXml(cProtein);
        CanonicalProtein converted = converter.fromXml(cGene);
        assertEquals(cProtein, converted);
    }

    GeneType createGene1() {
        GeneType gene = xmlFactory.createGeneType();
        gene.setAccession("P12345");
        gene.setEntryType(EntryType.SWISS_PROT);
        gene.setGeneName("gen1");
        gene.setGeneNameType(GeneNameType.MOD);
        gene.setLength(123l);
        return gene;
    }

    GeneType createGene2() {
        GeneType gene = xmlFactory.createGeneType();
        gene.setAccession("P32345");
        gene.setEntryType(EntryType.TR_EMBL);
        gene.setGeneName("gen2");
        gene.setGeneNameType(GeneNameType.ENSEMBL);
        gene.setLength(4323l);
        return gene;
    }

    GeneType createGene3() {
        GeneType gene = xmlFactory.createGeneType();
        gene.setAccession("P52345");
        gene.setEntryType(EntryType.SWISS_PROT);
        gene.setGeneName("gen4");
        gene.setGeneNameType(GeneNameType.ENSEMBL);
        gene.setLength(433l);
        return gene;
    }
}
