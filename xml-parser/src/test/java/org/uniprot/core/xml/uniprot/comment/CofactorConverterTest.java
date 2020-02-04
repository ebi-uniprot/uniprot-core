package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.DBCrossReference;
import org.uniprot.core.builder.DBCrossReferenceBuilder;
import org.uniprot.core.uniprot.comment.Cofactor;
import org.uniprot.core.uniprot.comment.CofactorReferenceType;
import org.uniprot.core.uniprot.comment.builder.CofactorBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.CofactorType;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.ObjectFactory;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class CofactorConverterTest {
    CofactorConverter converter;

    @BeforeEach
    void setUp() {

        EvidenceIndexMapper evidenceReferenceHandler = new EvidenceIndexMapper();

        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060646");
        Evidence evidence3 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        Evidence evidence4 = parseEvidenceLine("ECO:0000269|PubMed:9060648");
        Map<Evidence, Integer> evIdMap = new HashMap<>();
        evIdMap.put(evidence1, 1);
        evIdMap.put(evidence2, 2);
        evIdMap.put(evidence3, 3);
        evIdMap.put(evidence4, 4);
        evidenceReferenceHandler.reset(evIdMap);

        this.converter = new CofactorConverter(evidenceReferenceHandler);
    }

    @Test
    void testToXml() {
        // CC Name=Zn(2+); Xref=ChEBI:CHEBI:29105;
        // CC Evidence={ECO:0000269|PubMed:9060645, ECO:0000269|PubMed:9060647};

        DBCrossReference<CofactorReferenceType> reference =
                new DBCrossReferenceBuilder<CofactorReferenceType>()
                        .databaseType(CofactorReferenceType.CHEBI)
                        .id("CHEBI:29105")
                        .build();

        Evidence evidence1 = parseEvidenceLine("ECO:0000269|PubMed:9060645");
        Evidence evidence2 = parseEvidenceLine("ECO:0000269|PubMed:9060647");
        List<Evidence> evids = new ArrayList<>();
        evids.add(evidence1);
        evids.add(evidence2);

        Cofactor cofactor =
                new CofactorBuilder()
                        .reference(reference)
                        .evidencesSet(evids)
                        .name("Zn(2+)")
                        .build();
        CofactorType cofactorType = converter.toXml(cofactor);
        assertEquals("Zn(2+)", cofactorType.getName());
        assertEquals("CHEBI:29105", cofactorType.getDbReference().getId());
        assertEquals("ChEBI", cofactorType.getDbReference().getType());
        List<Integer> evs = cofactorType.getEvidence();
        assertEquals(2, evs.size());
        assertEquals(1, evs.get(0).intValue());
        assertEquals(3, evs.get(1).intValue());
        System.out.println(
                UniProtXmlTestHelper.toXmlString(cofactorType, CofactorType.class, "cofactor"));

        Cofactor converted = converter.fromXml(cofactorType);
        assertEquals(cofactor, converted);
    }

    @Test
    void testFromXml() {
        ObjectFactory objectFactory = new ObjectFactory();
        CofactorType cofactorType = objectFactory.createCofactorType();
        cofactorType.setName("Zn(2+)");
        DbReferenceType dbref = objectFactory.createDbReferenceType();
        dbref.setId("CHEBI:29105");
        dbref.setType("ChEBI");
        cofactorType.setDbReference(dbref);
        cofactorType.getEvidence().add(2);
        cofactorType.getEvidence().add(3);
        Cofactor cofactor = converter.fromXml(cofactorType);
        assertEquals("Zn(2+)", cofactor.getName());
        assertEquals("CHEBI:29105", cofactor.getCofactorReference().getId());
        assertEquals(
                CofactorReferenceType.CHEBI, cofactor.getCofactorReference().getDatabaseType());
        List<Evidence> evids = cofactor.getEvidences();
        assertEquals(2, evids.size());
        assertEquals("ECO:0000269|PubMed:9060646", evids.get(0).getValue());
        assertEquals("ECO:0000269|PubMed:9060647", evids.get(1).getValue());
    }
}
