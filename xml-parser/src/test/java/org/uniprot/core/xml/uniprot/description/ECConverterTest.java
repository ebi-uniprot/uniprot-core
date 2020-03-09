package org.uniprot.core.xml.uniprot.description;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.description.EC;
import org.uniprot.core.uniprot.description.impl.ECBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceCode;
import org.uniprot.core.uniprot.evidence.impl.EvidenceBuilder;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.uniprot.EvidencedStringType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class ECConverterTest {

    @Test
    void test() {
        String ec = "4.6.1.2";
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("Ensembl")
                        .databaseId("ENSP0001324")
                        .evidenceCode(EvidenceCode.ECO_0000313)
                        .build());
        evidences.add(
                new EvidenceBuilder()
                        .databaseName("PIRNR")
                        .databaseName("PIRNR001361")
                        .evidenceCode(EvidenceCode.ECO_0000256)
                        .build());

        EC ecObj = new ECBuilder().value(ec).evidencesSet(evidences).build();
        EvidenceIndexMapper evRefMapper = new EvidenceIndexMapper();
        ECConverter converter = new ECConverter(evRefMapper);
        EvidencedStringType xmlObj = converter.toXml(ecObj);
        assertEquals(ec, xmlObj.getValue());
        assertEquals(Arrays.asList(1, 2), xmlObj.getEvidence());
        EC converted = converter.fromXml(xmlObj);
        assertEquals(ecObj, converted);
        DbReferenceType dbReference = converter.toXmlDbReference(ecObj);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(
                        dbReference, DbReferenceType.class, "dbReference"));
    }
}
