package org.uniprot.core.proteome.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.GeneNameType;
import org.uniprot.core.proteome.Protein;
import org.uniprot.core.proteome.builder.ProteinBuilder;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;

class ProteinBuilderTest {

	@Test
	void testUniProtAccession() {
		String accession ="P12345";
		Protein protein = ProteinBuilder.newInstance().accession(accession).build();
		
		assertEquals(accession, protein.getAccession().getValue());
		protein = ProteinBuilder.newInstance().accession(new UniProtAccessionBuilder(accession).build()).build();
		
		assertEquals(accession, protein.getAccession().getValue());
	}
	@Test
	void testEntryType() {
		UniProtEntryType entryType = UniProtEntryType.SWISSPROT;
		Protein protein = ProteinBuilder.newInstance().entryType(entryType).build();
		assertEquals(entryType, protein.getEntryType());
		
		entryType = UniProtEntryType.TREMBL;
		 protein = ProteinBuilder.newInstance().entryType(entryType).build();
		assertEquals(entryType, protein.getEntryType());
	}
	
	@Test
	void testSequenceLength() {
		long leng = 241;
		
		Protein protein = ProteinBuilder.newInstance().sequenceLength(leng).build();
		assertEquals(leng, protein.getSequenceLength());
	}

	@Test
	void testGene() {
		String gene  ="some gene Value";
		GeneNameType type = GeneNameType.OLN;
		
		Protein protein = ProteinBuilder.newInstance().geneName(gene).geneNameType(type).build();
		
		assertEquals(gene, protein.getGeneName());
		assertEquals(type, protein.getGeneNameType());
	}
	
	@Test
	void testFrom() {
		String accession ="P12345";
		String gene  ="some gene Value";
		GeneNameType type = GeneNameType.OLN;
		
		Protein protein = ProteinBuilder.newInstance()
				.accession(accession)
				.entryType(UniProtEntryType.SWISSPROT)
				.geneName(gene).geneNameType(type).build();
		
		ProteinBuilder builder =ProteinBuilder.newInstance().from(protein);
		Protein newProtein = builder.entryType(UniProtEntryType.TREMBL).build();
		
		assertEquals(protein.getAccession(), newProtein.getAccession());
		assertEquals(protein.getGeneName(), newProtein.getGeneName());
		assertEquals(UniProtEntryType.SWISSPROT, protein.getEntryType());
		assertEquals(UniProtEntryType.TREMBL, newProtein.getEntryType());
	}
}
