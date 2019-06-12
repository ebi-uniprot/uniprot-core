package uk.ac.ebi.uniprot.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.proteome.CanonicalProtein;
import uk.ac.ebi.uniprot.domain.proteome.Protein;
import uk.ac.ebi.uniprot.domain.proteome.builder.CanonicalProteinBuilder;
import uk.ac.ebi.uniprot.domain.proteome.builder.ProteinBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.CanonicalGene;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.EntryType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.GeneNameType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.GeneType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;

class CanonicalProteinConverterTest {
	private ObjectFactory xmlFactory =new ObjectFactory();
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
		assertEquals(UniProtEntryType.SWISSPROT, protein.getEntryType());
		assertEquals("gen1", protein.getGeneName());
		assertEquals(uk.ac.ebi.uniprot.domain.proteome.GeneNameType.MOD, protein.getGeneNameType());
		assertEquals(123l, protein.getSequenceLength());
		assertEquals(2, cProtein.getRelatedProteins().size());
	}

	@Test
	void testToXml() {
		Protein protein = ProteinBuilder.newInstance()
		.accession("P21312")
		.entryType(UniProtEntryType.TREMBL)
		.geneName("some gene")
		.geneNameType(uk.ac.ebi.uniprot.domain.proteome.GeneNameType.ENSEMBL)
		.sequenceLength(324)
		.build();
		
		Protein protein2 = ProteinBuilder.newInstance()
				.accession("P21912")
				.entryType(UniProtEntryType.SWISSPROT)
				.geneName("some gene1")
				.geneNameType(uk.ac.ebi.uniprot.domain.proteome.GeneNameType.ENSEMBL)
				.sequenceLength(334)
				.build();
		Protein protein3 = ProteinBuilder.newInstance()
				.accession("P31912")
				.entryType(UniProtEntryType.SWISSPROT)
				.geneName("some gene3")
				.geneNameType(uk.ac.ebi.uniprot.domain.proteome.GeneNameType.OLN)
				.sequenceLength(434)
				.build();
		CanonicalProteinBuilder builder = CanonicalProteinBuilder.newInstance();
		CanonicalProtein cProtein =builder.canonicalProtein(protein)
		.addRelatedProtein(protein2)
		.addRelatedProtein(protein3)
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
