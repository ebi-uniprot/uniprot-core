package org.uniprot.core.parser.tsv.uniprot;

import org.junit.jupiter.api.Test;
import org.uniprot.core.parser.tsv.uniprot.EntryDbXRefMap;
import org.uniprot.core.uniprot.xdb.UniProtDBCrossReference;
import org.uniprot.core.uniprot.xdb.UniProtXDbType;
import org.uniprot.core.uniprot.xdb.builder.UniProtDBCrossReferenceBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntryDbXRefMapTest {


	@Test
	void testGetDataEmpty() {
		EntryDbXRefMap dl = new EntryDbXRefMap(null);
		Map<String, String> result = dl.attributeValues();
		assertTrue(result.isEmpty());

	}

	@Test
	void hasEmbl() {
		List<UniProtDBCrossReference> xrefs = new ArrayList<>();
		xrefs.add(createXref(new UniProtXDbType("EMBL"), "AY189288", "AAO86732.1", "-", "mRNA", null));
		xrefs.add(createXref(new UniProtXDbType("EMBL"), "AK022746", "BAB14220.1", "-", "mRNA", null));
		EntryDbXRefMap dl = new EntryDbXRefMap(xrefs);
		Map<String, String> result = dl.attributeValues();
		assertEquals(1, result.size());
		verify("AY189288;AK022746;", "dr:embl", result);
	}

	@Test
	void hasEmblAndEnsembl() {
		List<UniProtDBCrossReference> xrefs = new ArrayList<>();
		xrefs.add(createXref(new UniProtXDbType("EMBL"), "AY189288", "AAO86732.1", "-", "mRNA", null));
		xrefs.add(createXref(new UniProtXDbType("EMBL"), "AK022746", "BAB14220.1", "-", "mRNA", null));
		// Ensembl; ENST00000330899; ENSP00000369127; ENSG00000086061. [P31689-1]
		// Ensembl; ENST00000439351; ENSP00000414398; ENSG00000090520.
		xrefs.add(createXref(new UniProtXDbType("Ensembl"), "ENST00000330899", "ENSP00000369127", "ENSG00000086061", null,
				"P31689-1"));
		xrefs.add(
				createXref(new UniProtXDbType("Ensembl"), "ENST00000439351", "ENSP00000414398", "ENSG00000090520", null, null));
		EntryDbXRefMap dl = new EntryDbXRefMap(xrefs);
		Map<String, String> result = dl.attributeValues();
		assertEquals(2, result.size());
		verify("AY189288;AK022746;", "dr:embl", result);
		verify("ENST00000330899 [P31689-1];ENST00000439351;", "dr:ensembl", result);
	}

	@Test
	void hasPdbAndSmr() {
		List<UniProtDBCrossReference> xrefs = new ArrayList<>();
		xrefs.add(createXref(new UniProtXDbType("PDB"), "2LO1", "NMR", "-", "A=1-70", null));
		xrefs.add(createXref(new UniProtXDbType("PDB"), "2M6Y", "NMR", "-", "A=1-67", null));
		//PDB; 5TKG; X-ray; 1.20 A; A/B=16-23
		xrefs.add(createXref(new UniProtXDbType("PDB"), "5TKG", "X-ray", "1.20 A", "A/B=16-23", null));
		xrefs.add(createXref(new UniProtXDbType("SMR"), "P31689", "-", null, null, null));
		EntryDbXRefMap dl = new EntryDbXRefMap(xrefs);
		Map<String, String> result = dl.attributeValues();
		assertEquals(3, result.size());
		verify("2LO1;2M6Y;5TKG;", "dr:pdb", result);
		verify("P31689;", "dr:smr", result);
		String pdb3d="NMR spectroscopy (2); X-ray crystallography (1)";
		verify(pdb3d, "3d", result);
	}
	
	@Test
	void hasIntactAndString() {
		List<UniProtDBCrossReference> xrefs = new ArrayList<>();
		xrefs.add(createXref(new UniProtXDbType("IntAct"), "P31689", "97",null, null, null));
		
		xrefs.add(createXref(new UniProtXDbType("STRING"), "9606.ENSP00000369127", "-", null, null, null));
		EntryDbXRefMap dl = new EntryDbXRefMap(xrefs);
		Map<String, String> result = dl.attributeValues();
		assertEquals(2, result.size());
		verify("P31689;", "dr:intact", result);
		verify("9606.ENSP00000369127;", "dr:string", result);
	}

	@Test
	void hasChemblAndSwissLipids() {
		List<UniProtDBCrossReference> xrefs = new ArrayList<>();
		xrefs.add(createXref(new UniProtXDbType("ChEMBL"), "CHEMBL2189122", "-",null, null, null));
		
		xrefs.add(createXref(new UniProtXDbType("SwissLipids"), "SLP:000000475", "-", null, null, null));
		EntryDbXRefMap dl = new EntryDbXRefMap(xrefs);
		Map<String, String> result = dl.attributeValues();
		assertEquals(2, result.size());
		verify("CHEMBL2189122;", "dr:chembl", result);
		verify("SLP:000000475;", "dr:swisslipids", result);
	}
	@Test
	void testBbXrefToString() {
		UniProtDBCrossReference dbxref = createXref(new UniProtXDbType("EMBL"), "AY189288", "AAO86732.1", "-", "mRNA", null);
		String result = EntryDbXRefMap.dbXrefToString(dbxref);
		assertEquals("AY189288", result);
	}
	@Test
	void testProteomeXrefToString() {
		//UP000006548: Chromosome 4
		UniProtDBCrossReference dbxref = createXref(new UniProtXDbType("Proteomes"), "UP000006548", "Chromosome 4", null, null, null);
		String result = EntryDbXRefMap.proteomeXrefToString(dbxref);
		assertEquals("UP000006548: Chromosome 4", result);
	}
	
	private void verify(String expected, String field, Map<String, String> result) {
		String evaluated = result.get(field);
		assertEquals(expected, evaluated);
	}

	private UniProtDBCrossReference createXref(UniProtXDbType type, String id, String desc, String third, String fourth,
			String isoform) {
		return new UniProtDBCrossReferenceBuilder()
				.databaseType(type)
				.isoformId(isoform)
				.id(id)
				.addProperty(type.getAttribute(0), desc)
				.addProperty(type.getAttribute(1), third)
				.addProperty(type.getAttribute(2), fourth)
				.build();
	}
}