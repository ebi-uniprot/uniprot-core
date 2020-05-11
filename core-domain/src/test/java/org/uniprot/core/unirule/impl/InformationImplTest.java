package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;
import org.uniprot.core.unirule.DataClassType;
import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.Information;

import java.util.Arrays;
import java.util.List;

public class InformationImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        Information information = new InformationImpl();
        assertNotNull(information);
        assertTrue(information.getDuplicates().isEmpty());
        assertTrue(information.getNames().isEmpty());
        assertTrue(information.getPlasmaIds().isEmpty());
        assertTrue(information.getUniProtAccessions().isEmpty());
        assertTrue(information.getUniProtIds().isEmpty());
        assertTrue(information.getRelated().isEmpty());
        assertNull(information.getVersion());
        assertNull(information.getComment());
        assertNull(information.getOldRuleNum());
        assertNull(information.getDataClass());
        assertNull(information.getFusion());
        assertNull(information.getInternal());
    }

    @Test
    void testCreateObject() {
        String version = "version";
        String comment = "sample comment";
        String oldRuleNum = "rule num";
        String uniProtKBId1 = "upkid1";
        String uniProtKBId2 = "upkid2";
        List<String> uniProtIds = Arrays.asList(uniProtKBId1, uniProtKBId2);
        UniProtKBAccession accession1 = new UniProtKBAccessionBuilder("P12345").build();
        UniProtKBAccession accession2 = new UniProtKBAccessionBuilder("Q12345").build();
        List<UniProtKBAccession> accessions = Arrays.asList(accession1, accession2);
        DataClassType dataClass = DataClassType.PROTEIN;
        List<String> names = Arrays.asList("n1", "n2", "n3");
        List<String> related = Arrays.asList("r1", "r2", "r3");
        List<String> duplicates = Arrays.asList("d1", "d2", "d3");
        List<String> plasmaIds = Arrays.asList("p1", "p2", "p3");
        Fusion fusion = new FusionBuilder().build();
        String internal = "sample internal";
        Information information =
                new InformationImpl(
                        version,
                        comment,
                        oldRuleNum,
                        uniProtIds,
                        dataClass,
                        names,
                        fusion,
                        related,
                        accessions,
                        duplicates,
                        plasmaIds,
                        internal);
        assertNotNull(information);
        assertEquals(version, information.getVersion());
        assertEquals(comment, information.getComment());
        assertEquals(oldRuleNum, information.getOldRuleNum());
        assertEquals(uniProtIds, information.getUniProtIds());
        assertEquals(dataClass, information.getDataClass());
        assertEquals(names, information.getNames());
        assertEquals(fusion, information.getFusion());
        assertEquals(related, information.getRelated());
        assertEquals(accessions, information.getUniProtAccessions());
        assertEquals(duplicates, information.getDuplicates());
        assertEquals(plasmaIds, information.getPlasmaIds());
        assertEquals(internal, information.getInternal());
    }
}
