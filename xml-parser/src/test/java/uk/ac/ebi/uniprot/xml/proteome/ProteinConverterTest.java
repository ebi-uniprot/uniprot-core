package uk.ac.ebi.uniprot.xml.proteome;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.proteome.builder.ProteinBuilder;
import org.uniprot.core.uniprot.UniProtEntryType;

import uk.ac.ebi.uniprot.xml.jaxb.proteome.EntryType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.GeneNameType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.GeneType;
import uk.ac.ebi.uniprot.xml.jaxb.proteome.ObjectFactory;

class ProteinConverterTest {
	private ObjectFactory xmlFactory =new ObjectFactory();
	ProteinConverter converter = new ProteinConverter();
	@Test
	void testFromXml() {
		GeneType gene = xmlFactory.createGeneType();
		gene.setAccession("P12345");
		gene.setEntryType(EntryType.SWISS_PROT);
		gene.setGeneName("gen1");
		gene.setGeneNameType(GeneNameType.MOD);
		gene.setLength(123l);
		Protein protein = converter.fromXml(gene);
		assertEquals("P12345", protein.getAccession().getValue());
		assertEquals(UniProtEntryType.SWISSPROT, protein.getEntryType());
		assertEquals("gen1", protein.getGeneName());
		assertEquals(org.uniprot.core.proteome.GeneNameType.MOD, protein.getGeneNameType());
		assertEquals(123l, protein.getSequenceLength());
	}

	@Test
	void testToXml() {
		ProteinBuilder builder = ProteinBuilder.newInstance();
		builder.accession("P21312")
		.entryType(UniProtEntryType.TREMBL)
		.geneName("some gene")
		.geneNameType(org.uniprot.core.proteome.GeneNameType.ENSEMBL)
		.sequenceLength(324);
		Protein protein = builder.build();
		GeneType gene = converter.toXml(protein);
		Protein converted = converter.fromXml(gene);
		assertEquals(protein, converted);
	}

}
