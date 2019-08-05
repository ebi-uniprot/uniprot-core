package org.uniprot.core.uniparc.builder;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Location;
import org.uniprot.core.uniparc.InterproGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.builder.InterProGroupBuilder;
import org.uniprot.core.uniparc.builder.SequenceFeatureBuilder;

/**
 *  
 * @author jluo
 * @date: 23 May 2019
 *
*/

class SequenceFeatureBuilderTest {

	@Test
	void testInterProDomain() {
		InterproGroup domain = new InterProGroupBuilder().name("name1")
				.id("id1").build();
		SequenceFeature sf =new SequenceFeatureBuilder().interproGroup(domain)
				.build();
		assertEquals(domain, sf.getInterProDomain());
	}

	@Test
	void testSignatureDbType() {
		InterproGroup domain = new InterProGroupBuilder().name("name1")
				.id("id1").build();
		SequenceFeature sf =new SequenceFeatureBuilder().interproGroup(domain)
				.signatureDbType(SignatureDbType.GENE3D)
				.build();
		assertEquals(domain, sf.getInterProDomain());
		assertEquals(SignatureDbType.GENE3D, sf.getSignatureDbType());
	}
	

	@Test
	void testSignatureDbId() {
		InterproGroup domain = new InterProGroupBuilder().name("name1")
				.id("id1").build();
		SequenceFeature sf =new SequenceFeatureBuilder().interproGroup(domain)
				.signatureDbType(SignatureDbType.HAMAP)
				.signatureDbId("sigId1")
				.build();
		assertEquals(domain, sf.getInterProDomain());
		assertEquals(SignatureDbType.HAMAP, sf.getSignatureDbType());
		assertEquals("sigId1", sf.getSignatureDbId());
	}

	@Test
	void testLocations() {
		List<Location> locations =
				Arrays.asList(new Location(12, 23), new Location(45, 89));
		InterproGroup domain = new InterProGroupBuilder().name("name1")
				.id("id1").build();
		SequenceFeature sf =new SequenceFeatureBuilder().interproGroup(domain)
				.signatureDbType(SignatureDbType.PFAM)
				.signatureDbId("sigId2")
				.locations(locations)
				.build();
		assertEquals(domain, sf.getInterProDomain());
		assertEquals(SignatureDbType.PFAM, sf.getSignatureDbType());
		assertEquals("sigId2", sf.getSignatureDbId());
		assertEquals(locations, sf.getLocations());
	}

	@Test
	void testAddLocation() {
		List<Location> locations =
				Arrays.asList(new Location(12, 23), new Location(45, 89));
		InterproGroup domain = new InterProGroupBuilder().name("name1")
				.id("id1").build();
		SequenceFeature sf =new SequenceFeatureBuilder().interproGroup(domain)
				.signatureDbType(SignatureDbType.PFAM)
				.signatureDbId("sigId2")
				.locations(locations)
				.addLocation(new Location(100, 300))
				.build();
		assertEquals(domain, sf.getInterProDomain());
		assertEquals(SignatureDbType.PFAM, sf.getSignatureDbType());
		assertEquals("sigId2", sf.getSignatureDbId());
		assertEquals(3, sf.getLocations().size());
	}

	@Test
	void testFrom() {
		List<Location> locations =
				Arrays.asList(new Location(12, 23), new Location(45, 89));
		InterproGroup domain = new InterProGroupBuilder().name("name1")
				.id("id1").build();
		SequenceFeature sf =new SequenceFeatureBuilder().interproGroup(domain)
				.signatureDbType(SignatureDbType.PFAM)
				.signatureDbId("sigId2")
				.locations(locations)
				.build();
		SequenceFeature sf2 = new SequenceFeatureBuilder().from(sf).build();
		assertEquals(sf, sf2);
		SequenceFeature sf3 = new SequenceFeatureBuilder().from(sf).signatureDbType(SignatureDbType.PROSITE)
				.build();
		assertEquals(domain, sf3.getInterProDomain());
		assertEquals(SignatureDbType.PROSITE, sf3.getSignatureDbType());
		assertEquals("sigId2", sf3.getSignatureDbId());
		assertEquals(locations, sf3.getLocations());
	}

}

