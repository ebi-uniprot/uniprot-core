package uk.ac.ebi.uniprot.domain.uniprot.xdb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

class UniProtXDbTypesTest {

	@Test
	void testGetAllTypes() {
		List<UniProtXDbType> types = UniProtXDbTypes.INSTANCE.getAllDBXRefTypes();
		
		assertFalse(types.isEmpty());
		System.out.println(types.size());
		
	}
	@Test
	void testEmblType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("EMBL");
		assertTrue(opType.isPresent());
		assertEquals("EMBL", opType.get().getName());
		assertEquals(DatabaseCategory.SEQUENCE_DATABASES, opType.get().getCategory());
		assertEquals("https://www.ebi.ac.uk/ena/data/view/%value", opType.get().getUriLink());
		assertEquals(3, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "ProteinId", "protein sequence ID", 
				"https://www.ebi.ac.uk/ena/data/view/%value");
		verifyAttribute(opType.get().getAttributes().get(1), "Status", "status", 
				null);
		verifyAttribute(opType.get().getAttributes().get(2), "MoleculeType", "molecule type", 
				null);
	}
	
	@Test
	void testPDBType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("PDB");
		assertTrue(opType.isPresent());
		assertEquals("PDB", opType.get().getName());
		assertEquals("PDB", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.D3_STRUCTURE_DATABASES, opType.get().getCategory());
		assertEquals("https://www.ebi.ac.uk/pdbe/entry/pdb/%value", opType.get().getUriLink());
		assertEquals(3, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "Method", "method", 
				null);
		verifyAttribute(opType.get().getAttributes().get(1), "Resolution", "resolution", 
				null);
		verifyAttribute(opType.get().getAttributes().get(2), "Chains", "chains", 
				null);
	}
	
	@Test
	void testComplexPortalType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("ComplexPortal");
		assertTrue(opType.isPresent());
		assertEquals("ComplexPortal", opType.get().getName());
		assertEquals("ComplexPortal", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.PROTEIN_PROTEIN_INTERACTION_DATABASES, opType.get().getCategory());
		assertEquals("https://www.ebi.ac.uk/complexportal/complex/%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "EntryName", "entry name", 
				null);
	}

	
	@Test
	void testChEMBLType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("ChEMBL");
		assertTrue(opType.isPresent());
		assertEquals("ChEMBL", opType.get().getName());
		assertEquals("ChEMBL", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.CHEMISTRY, opType.get().getCategory());
		assertEquals("https://www.ebi.ac.uk/chembldb/target/inspect/%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "Description", "description", 
				null);		
		
	}
	
	@Test
	void testMoonDBType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("MoonDB");
		assertTrue(opType.isPresent());
		assertEquals("MoonDB", opType.get().getName());
		assertEquals("MoonDB", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.PROTEIN_FAMILY_GROUP_DATABASES, opType.get().getCategory());
		assertEquals("http://moondb.hb.univ-amu.fr/protein/%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "Type", "type", 
				null);
	}
	
	@Test
	void testiPTMnetType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("iPTMnet");
		assertTrue(opType.isPresent());
		assertEquals("iPTMnet", opType.get().getName());
		assertEquals("iPTMnet", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.PTM_DATABASES, opType.get().getCategory());
		assertEquals("https://research.bioinformatics.udel.edu/iptmnet/entry/%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "Description", "description", 
				null);		
	}
	
	@Test
	void testdbSNPType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("dbSNP");
		assertTrue(opType.isPresent());
		assertEquals("dbSNP", opType.get().getName());
		assertEquals("dbSNP", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.POLYMORPHISM_AND_MUTATION_DATABASES, opType.get().getCategory());
		assertEquals("https://www.ncbi.nlm.nih.gov/snp/%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "Description", "description", 
				null);		
	}
	
	@Test
	void testSwiss2dpageType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("SWISS-2DPAGE");
		assertTrue(opType.isPresent());
		assertEquals("SWISS-2DPAGE", opType.get().getName());
		assertEquals("SWISS-2DPAGE", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.D2_GEL_DATABASES, opType.get().getCategory());
		assertEquals("https://world-2dpage.expasy.org/swiss-2dpage/protein/ac=%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "Description", "description", 
				null);		
	}
	
	@Test
	void testMaxQBType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("MaxQB");
		assertTrue(opType.isPresent());
		assertEquals("MaxQB", opType.get().getName());
		assertEquals("MaxQB", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.PROTEOMIC_DATABASES, opType.get().getCategory());
		assertEquals("http://maxqb.biochem.mpg.de/mxdb/protein/show/%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "Description", "description", 
				null);		
	}
	
	@Test
	void testDNASUType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("DNASU");
		assertTrue(opType.isPresent());
		assertEquals("DNASU", opType.get().getName());
		assertEquals("DNASU", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.PROTOCOLS_AND_MATERIALS_DATABASES, opType.get().getCategory());
		assertEquals("https://dnasu.org/DNASU/AdvancedSearchOptions.do?geneName=%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "Description", "description", 
				null);		
	}
	
	@Test
	void testEnsemblType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("Ensembl");
		assertTrue(opType.isPresent());
		assertEquals("Ensembl", opType.get().getName());
		assertEquals("Ensembl", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.GENOME_ANNOTATION_DATABASES, opType.get().getCategory());
		assertEquals("https://www.ensembl.org/id/%value", opType.get().getUriLink());
		assertEquals(2, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "ProteinId", "protein sequence ID", 
				"https://www.ensembl.org/id/%value");
		verifyAttribute(opType.get().getAttributes().get(1), "GeneId", "gene ID", 
				"https://www.ensembl.org/id/%value");
		
	}
	@Test
	void testVGNCType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("VGNC");
		assertTrue(opType.isPresent());
		assertEquals("VGNC", opType.get().getName());
		assertEquals("VGNC", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.ORGANISM_SPECIFIC_DATABASES, opType.get().getCategory());
		assertEquals("https://vertebrate.genenames.org/data/gene-symbol-report/#!/vgnc_id/%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "GeneName", "gene designation", 
				null);		
	}
	
	@Test
	void testeggNOGType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("eggNOG");
		assertTrue(opType.isPresent());
		assertEquals("eggNOG", opType.get().getName());
		assertEquals("eggNOG", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.PHYLOGENOMIC_DATABASES, opType.get().getCategory());
		assertEquals("http://eggnogdb.embl.de/#/app/results?seqid=%acc&target_nogs=%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "ToxonomicScope", "taxonomic scope", 
				null);		
	}
	
	@Test
	void testReactomeType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("Reactome");
		assertTrue(opType.isPresent());
		assertEquals("Reactome", opType.get().getName());
		assertEquals("Reactome", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.ENZYME_AND_PATHWAY_DATABASES, opType.get().getCategory());
		assertEquals("https://www.reactome.org/PathwayBrowser/#%value&FLG=%acc", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "PathwayName", "pathway name", 
				null);		
	}
	
	@Test
	void testChiTaRSType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("ChiTaRS");
		assertTrue(opType.isPresent());
		assertEquals("ChiTaRS", opType.get().getName());
		assertEquals("ChiTaRS", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.OTHER, opType.get().getCategory());
		assertEquals("http://chitars.md.biu.ac.il/bin/search.pl?searchtype=gene_name&searchstr=%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "OrganismName", "organism name", 
				null);		
	}
	
	@Test
	void testExpressionAtlasType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("ExpressionAtlas");
		assertTrue(opType.isPresent());
		assertEquals("ExpressionAtlas", opType.get().getName());
		assertEquals("ExpressionAtlas", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.GENE_EXPRESSION_DATABASES, opType.get().getCategory());
		assertEquals("https://www.ebi.ac.uk/gxa/query?geneQuery=%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "ExpressionPatterns", "expression patterns", 
				null);		
	}
	
	
	@Test
	void testPIRSFType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("PIRSF");
		assertTrue(opType.isPresent());
		assertEquals("PIRSF", opType.get().getName());
		assertEquals("PIRSF", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.FAMILY_AND_DOMAIN_DATABASES, opType.get().getCategory());
		assertEquals("https://pir.georgetown.edu/cgi-bin/ipcSF?id=%value", opType.get().getUriLink());
		assertEquals(2, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "EntryName", "entry name", 
				null);		
		verifyAttribute(opType.get().getAttributes().get(1), "MatchStatus", "match status", 
				null);		
	}
	
	@Test
	void testGOType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("GO");
		assertTrue(opType.isPresent());
		assertEquals("GO", opType.get().getName());
		assertEquals("GO", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.GENE_ONTOLOGY_DATABASES, opType.get().getCategory());
		assertEquals("https://prosite.expasy.org/doc/%value", opType.get().getUriLink());
		assertEquals(3, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "GoTerm", "term", 
				null);		
		verifyAttribute(opType.get().getAttributes().get(1), "GoEvidenceType", "evidence", 
				null);		
		verifyAttribute(opType.get().getAttributes().get(2), "GoEvidenceSource", "source", 
				null);		
	}
	
	@Test
	void testProteomesType() {
		Optional<UniProtXDbType> opType = UniProtXDbTypes.INSTANCE.getType("Proteomes");
		assertTrue(opType.isPresent());
		assertEquals("Proteomes", opType.get().getName());
		assertEquals("Proteomes", opType.get().getDisplayName());
		assertEquals(DatabaseCategory.PROTEOMES_DATABASES, opType.get().getCategory());
		assertEquals("https://www.uniprot.org/proteomes/%value", opType.get().getUriLink());
		assertEquals(1, opType.get().getAttributes().size());
		verifyAttribute(opType.get().getAttributes().get(0), "Component", "component", 
				null);		
	}
	
	
	void verifyAttribute(DBXRefTypeAttribute attr, String name, String xmlTag, String link) {
		assertEquals(name, attr.getName());
		assertEquals(xmlTag, attr.getXmlTag());
		assertEquals(link, attr.getUriLink());
		
	}
}
