package org.uniprot.core.parser.tsv.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.parser.tsv.uniparc.UniParcSequenceFeatureMap;
import org.uniprot.core.uniparc.InterproGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.builder.InterProGroupBuilder;
import org.uniprot.core.uniparc.builder.SequenceFeatureBuilder;

/**
 *
 * @author jluo
 * @date: 24 Jun 2019
 *
*/

class UniParcSequenceFeatureMapTest {

	@Test
	void testAttributeValues() {
		List<SequenceFeature> sfs= create();
		UniParcSequenceFeatureMap sfMap = new UniParcSequenceFeatureMap(sfs);
		Map<String, String> result= sfMap.attributeValues();
		assertEquals(14, result.size());
		assertEquals("id1,id2", result.get("InterPro"));
		assertEquals("", result.get("CDD"));
		assertEquals("sigId1,sigId2", result.get("HAMAP"));
		assertEquals("sigId3", result.get("Pfam"));
		assertEquals("", result.get("SMART"));
	}

	@Test
	void testContains() {
		List<String> fields =Arrays.asList( "gene", "upi", "length");
		assertFalse (UniParcSequenceFeatureMap.contains(fields));
		
		fields =Arrays.asList( "gene", "upi", "Gene3D", "InterPro");
		assertTrue (UniParcSequenceFeatureMap.contains(fields));
	}
	@Test
	void testFields() {
		List<String> fields =Arrays.asList("InterPro", "CDD", "Gene3D", "HAMAP",  "PANTHER", "Pfam",
				"PIRSF", "PRINTS", "ProPom", "PROSITE", "SFLD", "SMART", "SUPFAM", "TIGRFAMs");
		assertEquals(UniParcSequenceFeatureMap.FIELDS, fields);
	}
	private List<SequenceFeature> create() {
		InterproGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
		SequenceFeature sf = new SequenceFeatureBuilder().interproGroup(domain).signatureDbType(SignatureDbType.HAMAP)
				.signatureDbId("sigId1").build();
		List<SequenceFeature> sfs = new ArrayList<>();
		sfs.add(sf);

		domain = new InterProGroupBuilder().name("name2").id("id2").build();
		sf = new SequenceFeatureBuilder().interproGroup(domain).signatureDbType(SignatureDbType.HAMAP)
				.signatureDbId("sigId2").build();
		sfs.add(sf);
		domain = new InterProGroupBuilder().name("name2").id("id2").build();
		sf = new SequenceFeatureBuilder().interproGroup(domain).signatureDbType(SignatureDbType.PFAM)
				.signatureDbId("sigId3").build();
		sfs.add(sf);
		return sfs;

	}
}
