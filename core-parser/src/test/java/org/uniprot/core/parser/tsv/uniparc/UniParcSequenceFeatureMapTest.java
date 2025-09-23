package org.uniprot.core.parser.tsv.uniparc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniparc.InterProGroup;
import org.uniprot.core.uniparc.SequenceFeature;
import org.uniprot.core.uniparc.SignatureDbType;
import org.uniprot.core.uniparc.impl.InterProGroupBuilder;
import org.uniprot.core.uniparc.impl.SequenceFeatureBuilder;

/**
 * @author jluo
 * @date: 24 Jun 2019
 */
class UniParcSequenceFeatureMapTest {

    @Test
    void testAttributeValues() {
        List<SequenceFeature> sfs = create();
        UniParcSequenceFeatureMap sfMap = new UniParcSequenceFeatureMap(sfs);
        Map<String, String> result = sfMap.attributeValues();
        assertEquals(14, result.size());
        assertEquals("id1,id2", result.get("InterPro"));
        assertEquals("", result.get("CDD"));
        assertEquals("sigId1,sigId2", result.get("HAMAP"));
        assertEquals("sigId3", result.get("Pfam"));
        assertEquals("", result.get("SMART"));
    }

    @Test
    void testContains() {
        List<String> fields = Arrays.asList("gene", "upi", "length");
        assertFalse(UniParcSequenceFeatureMap.contains(fields));

        fields = Arrays.asList("gene", "upi", "Gene3D", "InterPro");
        assertTrue(UniParcSequenceFeatureMap.contains(fields));
    }

    @Test
    void testFields() {
        List<String> fields =
                Arrays.asList(
                        "InterPro",
                        "CDD",
                        "Gene3D",
                        "HAMAP",
                        "PANTHER",
                        "Pfam",
                        "PIRSF",
                        "PRINTS",
                        "PROSITE",
                        "SFLD",
                        "SMART",
                        "SUPFAM",
                        "NCBIfam",
                        "FunFam",
                        "PIRSR");
        assertEquals(UniParcSequenceFeatureMap.FIELDS, fields);
    }

    @Test
    void testAttributeValuesWithMissingInterProDomain() {
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .signatureDbType(SignatureDbType.PFAM)
                        .signatureDbId("sigId1")
                        .build();
        List<SequenceFeature> sfs = List.of(sf);
        UniParcSequenceFeatureMap sfMap = new UniParcSequenceFeatureMap(sfs);
        Map<String, String> result = sfMap.attributeValues();
        assertEquals(14, result.size());
        assertEquals("", result.get("InterPro"));
        assertEquals("sigId1", result.get("Pfam"));
    }

    private List<SequenceFeature> create() {
        InterProGroup domain = new InterProGroupBuilder().name("name1").id("id1").build();
        SequenceFeature sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.HAMAP)
                        .signatureDbId("sigId1")
                        .build();
        List<SequenceFeature> sfs = new ArrayList<>();
        sfs.add(sf);

        domain = new InterProGroupBuilder().name("name2").id("id2").build();
        sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.HAMAP)
                        .signatureDbId("sigId2")
                        .build();
        sfs.add(sf);
        domain = new InterProGroupBuilder().name("name2").id("id2").build();
        sf =
                new SequenceFeatureBuilder()
                        .interproGroup(domain)
                        .signatureDbType(SignatureDbType.PFAM)
                        .signatureDbId("sigId3")
                        .build();
        sfs.add(sf);
        return sfs;
    }
}
