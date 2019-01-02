package uk.ac.ebi.uniprot.xmlparser.uniprot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.domain.Range;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.FeatureFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.domain.uniprot.feature.AlternativeSequence;
import uk.ac.ebi.uniprot.domain.uniprot.feature.Feature;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureId;
import uk.ac.ebi.uniprot.domain.uniprot.feature.FeatureType;

class FeatureConverterTest {

	@Test
	void testVariant() {

		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		FeatureConverter converter = new FeatureConverter(evRefMapper);
		AlternativeSequence altSeq = FeatureFactory.INSTANCE.createAlternativeSequence("T", Arrays.asList("I"));
		String description = "in CSTNU; reduces catalytic activity and affinity for pyridoxal phosphate; dbSNP:rs28941785";
		String description2 = "In CSTNU; reduces catalytic activity and affinity for pyridoxal phosphate; dbSNP:rs28941785.";
		String ftid = "VAR_015450";

		Feature feature = createFeature(FeatureType.VARIANT, 67, 67, description, ftid, altSeq);
		uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj,
				uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType.class, "feature"));
		verify(xmlObj, 67, 67, description2, ftid, "T", Arrays.asList(1, 2));
		Feature converted = converter.fromXml(xmlObj);
		assertEquals(feature, converted);

		// FT VARIANT 67 67 T -> I (in CSTNU; reduces catalytic
		// FT activity and affinity for pyridoxal
		// FT phosphate; dbSNP:rs28941785).
		// FT /FTId=VAR_015450.
	}

	@Test
	void testVariantNoEvidence() {

		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		FeatureConverter converter = new FeatureConverter(evRefMapper);
		AlternativeSequence altSeq = FeatureFactory.INSTANCE.createAlternativeSequence("T", Arrays.asList("I"));
		String description = "in CSTNU; reduces catalytic activity and affinity for pyridoxal phosphate; dbSNP:rs28941785";
		String ftid = "VAR_015450";
		String description2 = "In CSTNU; reduces catalytic activity and affinity for pyridoxal phosphate; dbSNP:rs28941785.";
		Feature feature =FeatureFactory.INSTANCE.createFeature(FeatureType.VARIANT, new Range(67, 67), description,
				FeatureFactory.INSTANCE.createFeatureId(ftid), altSeq, Collections.emptyList());
				

		uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj,
				uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType.class, "feature"));
		verify(xmlObj, 67, 67, description2, ftid, "T", Collections.emptyList());
		Feature converted = converter.fromXml(xmlObj);
		assertEquals(feature, converted);

		// FT VARIANT 67 67 T -> I (in CSTNU; reduces catalytic
		// FT activity and affinity for pyridoxal
		// FT phosphate; dbSNP:rs28941785).
		// FT /FTId=VAR_015450.
	}
	
	private void verify(uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType xmlObj,
			int start, int end, String description,  String ftid, String original,
			
			List<Integer> evidences
			) {
		assertEquals(evidences, xmlObj.getEvidence());
		assertEquals(description, xmlObj.getDescription());
		assertEquals(ftid, xmlObj.getId());
		if(start == end) {
			assertTrue(xmlObj.getLocation().getPosition() !=null);
			assertEquals(start, xmlObj.getLocation().getPosition().getPosition().intValue());
		}else {
			assertEquals(start, xmlObj.getLocation().getBegin().getPosition().intValue());
			assertEquals(end, xmlObj.getLocation().getEnd().getPosition().intValue());
		}
		assertEquals(original, xmlObj.getOriginal());
	}
	

	@Test
	void testVarSeq() {
		// FT VAR_SEQ 153 196 Missing (in isoform 2).
		// FT {ECO:0000303|PubMed:1339280}.
		// FT /FTId=VSP_006306.
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		FeatureConverter converter = new FeatureConverter(evRefMapper);
		AlternativeSequence altSeq = null;
		String description = "in isoform 2";
		String description2 = "In isoform 2.";
		String ftid = "VSP_006306";

		Feature feature = createFeature(FeatureType.VAR_SEQ, 153, 196, description, ftid, altSeq);
		uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj,
				uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType.class, "feature"));
		verify(xmlObj, 153, 196, description2, ftid, null, Arrays.asList(1, 2));
		Feature converted = converter.fromXml(xmlObj);
		assertEquals(feature, converted);
		
	}
	
	@Test
	void testHelix() {
	//	FT   HELIX        18     24       {ECO:0000244|PDB:3COG}.
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		FeatureConverter converter = new FeatureConverter(evRefMapper);
		AlternativeSequence altSeq = null;
		String description = "";
		String ftid = null;

		Feature feature = createFeature(FeatureType.HELIX, 18, 24, description, ftid, altSeq);
		uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj,
				uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType.class, "feature"));
		verify(xmlObj, 18, 24, null, ftid, null, Arrays.asList(1, 2));
		Feature converted = converter.fromXml(xmlObj);
		assertEquals(feature, converted);
	}
	
	@Test
	void testDOMAIN() {
	//		FT   DOMAIN      109    322       Adrift-type SAM-dependent 2'-O-MTase.
	//	FT                                {ECO:0000255|PROSITE-ProRule:PRU00946}.
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		FeatureConverter converter = new FeatureConverter(evRefMapper);
		AlternativeSequence altSeq = null;
		String description = "Adrift-type SAM-dependent 2'-O-MTase";
		String ftid = null;

		Feature feature = createFeature(FeatureType.DOMAIN, 109, 322, description, ftid, altSeq);
		uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj,
				uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType.class, "feature"));
		verify(xmlObj, 109, 322, description, ftid, null, Arrays.asList(1, 2));
		Feature converted = converter.fromXml(xmlObj);
		assertEquals(feature, converted);
	} 

	@Test
	void testCHAIN() {
	//	FT   CHAIN         1    405       Cystathionine gamma-lyase.
	//	FT                                /FTId=PRO_0000114749.
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		FeatureConverter converter = new FeatureConverter(evRefMapper);
		AlternativeSequence altSeq = null;
		String description = "Cystathionine gamma-lyase";
		String ftid = "PRO_0000114749";

		Feature feature = createFeature(FeatureType.CHAIN, 1, 405, description, ftid, altSeq);
		uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj,
				uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType.class, "feature"));
		verify(xmlObj, 1, 405, description, ftid, null, Arrays.asList(1, 2));
		Feature converted = converter.fromXml(xmlObj);
		assertEquals(feature, converted);
	}
	@Test
	void testConflict() {
	//	FT   CONFLICT    658    658       C -> S (in Ref. 2; AAI71654).
	//	FT                                {ECO:0000305}.
		
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		FeatureConverter converter = new FeatureConverter(evRefMapper);
		AlternativeSequence altSeq = FeatureFactory.INSTANCE.createAlternativeSequence("C", Arrays.asList("S"));
		String description = "in Ref. 2; AAI71654";
		String description2 = "In Ref. 2; AAI71654.";
		String ftid = "";

		Feature feature = createFeature(FeatureType.CONFLICT, 658, 658, description, ftid, altSeq);
		uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj,
				uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType.class, "feature"));
		verify(xmlObj, 658, 658, description2, null, "C", Arrays.asList(1, 2));
		Feature converted = converter.fromXml(xmlObj);
		assertEquals(feature, converted);
	}
	@Test
	void testMUTAGEN() {
	//FT   MUTAGEN     188    188       G->R: In hot2-1; reduced tolerance to
	//	FT                                abiotic stresses such as salt, drought
	//	FT                                and heat. {ECO:0000269|PubMed:17156413}.
		
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		FeatureConverter converter = new FeatureConverter(evRefMapper);
		AlternativeSequence altSeq = FeatureFactory.INSTANCE.createAlternativeSequence("G", Arrays.asList("R", "S"));
		String description = "In hot2-1; reduced tolerance to abiotic stresses such as salt, drought and heat";
		String description2 = "In hot2-1; reduced tolerance to abiotic stresses such as salt, drought and heat.";
		String ftid = "";

		Feature feature = createFeature(FeatureType.MUTAGEN, 188, 188, description, ftid, altSeq);
		uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj,
				uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType.class, "feature"));
		verify(xmlObj, 188, 188, description2, null, "G", Arrays.asList(1, 2));
		Feature converted = converter.fromXml(xmlObj);
		assertEquals(feature, converted);
	}
	
	@Test
	void testVARSEQ() {
	//	FT   VAR_SEQ    1158   1202       GSFLTKKQDQAARKIMRFLRRCRHRMRELKQNQELEGLPQP
	//	FT                                GLAT -> LLSHQEAGPGSPEDHEIPAALPTQDEGTEAEPG
	//	FT                                AGRASPAGTGHMTWPPPFSPPWGRLVQS (in isoform
	//	FT                                6). {ECO:0000303|PubMed:15489334}.
	//	FT                                /FTId=VSP_046059.
		EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
		FeatureConverter converter = new FeatureConverter(evRefMapper);
		AlternativeSequence altSeq = FeatureFactory.INSTANCE.
				createAlternativeSequence("GSFLTKKQDQAARKIMRFLRRCRHRMRELKQNQELEGLPQPGLAT",
						Arrays.asList("AGRASPAGTGHMTWPPPFSPPWGRLVQS"));
		String description = "in isoform 6";
		String description2 = "In isoform 6.";
		String ftid = "VSP_046059";

		Feature feature = createFeature(FeatureType.VAR_SEQ, 1158, 1202, description, ftid, altSeq);
		uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType xmlObj = converter.toXml(feature);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlObj,
				uk.ac.ebi.uniprot.xml.jaxb.uniprot.FeatureType.class, "feature"));
		verify(xmlObj, 1158, 1202, description2, ftid, "GSFLTKKQDQAARKIMRFLRRCRHRMRELKQNQELEGLPQPGLAT", Arrays.asList(1, 2));
		Feature converted = converter.fromXml(xmlObj);
		assertEquals(feature, converted);
	}
	
	
	private Feature createFeature(FeatureType type, int start, int end, String description, String ftid,
			AlternativeSequence altSeq) {
		List<Evidence> evidences = Arrays.asList(new Evidence[] { createEvidence("ECO:0000256|PIRNR:PIRNR001461"),
				createEvidence("ECO:0000269|PubMed:11389730") });
		
		FeatureId featureId = null;
		if(!Strings.isNullOrEmpty(ftid)) {
			featureId = FeatureFactory.INSTANCE.createFeatureId(ftid);
		}
		return FeatureFactory.INSTANCE.createFeature(type, new Range(start, end), description,
				featureId, altSeq, evidences);
	}

	private Evidence createEvidence(String evidenceStr) {
		return UniProtFactory.INSTANCE.createEvidence(evidenceStr);
	}
}
