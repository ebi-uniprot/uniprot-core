package org.uniprot.core.flatfile.parser.converter;

import junit.framework.TestCase;
import org.junit.Test;
import org.uniprot.core.flatfile.parser.impl.ox.OxLineConverter;
import org.uniprot.core.flatfile.parser.impl.ox.OxLineObject;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.taxonomy.Organism;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class OxLineConverterTest {
	@Test
	public void test(){
	//	OX   NCBI_TaxID=9606;
		OxLineObject osO = new OxLineObject();
		osO.taxonomy_id =9606;
		OxLineConverter converter = new OxLineConverter();
		Organism taxId =converter.convert(osO);
		TestCase.assertEquals(9606, taxId.getTaxonId());	
	}
	@Test
	public void testEvidence(){
	//	OX   NCBI_TaxID=9606{EI1,EI2};
		OxLineObject osO = new OxLineObject();
		osO.taxonomy_id =9606;
		List<String> evIds = new ArrayList<String>();
		evIds.add("ECO:0000313|Ensembl:ENSTGUP00000005391");
		evIds.add("ECO:0000313|Ensembl:ENSTGUP00000005392");
		osO.evidenceInfo.evidences.put(osO.taxonomy_id, evIds);
		
		
		OxLineConverter converter = new OxLineConverter();
		Organism taxId =converter.convert(osO);
		TestCase.assertEquals(9606, taxId.getTaxonId());	
		List<Evidence> eviIds = taxId.getEvidences();
		assertEquals(2, eviIds.size());
		Evidence eviId = eviIds.get(0);
		Evidence eviId2 = eviIds.get(1);
		assertEquals("ECO:0000313|Ensembl:ENSTGUP00000005391", eviId.getValue());
		assertEquals("ECO:0000313|Ensembl:ENSTGUP00000005392", eviId2.getValue());
	}
}