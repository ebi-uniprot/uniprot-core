package org.uniprot.core.uniparc.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.InterProGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SequenceFeatureLocation;
import org.uniprot.core.uniparc.SignatureDbType;

/**
 * @author jluo
 * @date: 23 May 2019
 */
class SequenceFeatureBuilderTest {

    @Test
    void testInterProDomain() {
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf = new SequenceFeatureBuilder().interproGroup(domain).build();
        assertEquals(domain, sf.getInterProDomain());
    }

    @Test
    void testSignatureDbType() {
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.GENE3D)
                        .build();
        assertEquals(domain, sf.getInterProDomain());
        assertEquals(SignatureDbType.GENE3D, sf.getSignatureDbType());
    }

    @Test
    void testSignatureDbId() {
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.HAMAP)
                        .signatureDbId("sigId1")
                        .build();
        assertEquals(domain, sf.getInterProDomain());
        assertEquals(SignatureDbType.HAMAP, sf.getSignatureDbType());
        assertEquals("sigId1", sf.getSignatureDbId());
    }

    @Test
    void testLocations() {
        List<SequenceFeatureLocation> locations = Arrays.asList(new SequenceFeatureLocationBuilder().range(12, 23).alignment("55M").build(), new SequenceFeatureLocationBuilder().range(45, 89).build());
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.PFAM)
                        .signatureDbId("sigId2")
                        .locationsSet(locations)
                        .build();
        assertEquals(domain, sf.getInterProDomain());
        assertEquals(SignatureDbType.PFAM, sf.getSignatureDbType());
        assertEquals("sigId2", sf.getSignatureDbId());
        assertEquals(locations, sf.getLocations());
    }

    @Test
    void testAddLocation() {
        List<SequenceFeatureLocation> locations = Arrays.asList(new SequenceFeatureLocationBuilder().range(12, 23).alignment("55M").build(), new SequenceFeatureLocationBuilder().range(45, 89).build());
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.PFAM)
                        .signatureDbId("sigId2")
                        .locationsSet(locations)
                        .locationsAdd(new SequenceFeatureLocationBuilder().range(100, 300).build())
                        .build();
        assertEquals(domain, sf.getInterProDomain());
        assertEquals(SignatureDbType.PFAM, sf.getSignatureDbType());
        assertEquals("sigId2", sf.getSignatureDbId());
        assertEquals(3, sf.getLocations().size());
    }

    @Test
    void testFrom() {
        List<SequenceFeatureLocation> locations = Arrays.asList(new SequenceFeatureLocationBuilder().range(12, 23).alignment("55M").build(), new SequenceFeatureLocationBuilder().range(45, 89).build());
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.PFAM)
                        .signatureDbId("sigId2")
                        .locationsSet(locations)
                        .build();
        SequenceFeature sf2 = SequenceFeatureBuilder.from(sf).build();
        assertEquals(sf, sf2);
        SequenceFeature sf3 =
                SequenceFeatureBuilder.from(sf).signatureDbType(SignatureDbType.PROSITE).build();
        assertEquals(domain, sf3.getInterProDomain());
        assertEquals(SignatureDbType.PROSITE, sf3.getSignatureDbType());
        assertEquals("sigId2", sf3.getSignatureDbId());
        assertEquals(locations, sf3.getLocations());
    }
}
