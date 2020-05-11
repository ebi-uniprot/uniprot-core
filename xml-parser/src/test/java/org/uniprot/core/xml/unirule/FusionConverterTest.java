package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.impl.FusionBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.FusionType;

public class FusionConverterTest extends AbstractConverterTest {

    @BeforeAll
    static void setUp() {
        converter = new FusionConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        Fusion uniObj = createSkinnyUniObject();
        FusionType xmlObj = (FusionType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertTrue(xmlObj.getCter().isEmpty());
        assertTrue(xmlObj.getNter().isEmpty());
        // convert back to xml- test round trip
        Fusion updatedXmlObj = (Fusion) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedXmlObj);
    }

    public static FusionType createObject() {
        FusionType fusionType = objectCreator.createLoremIpsumObject(FusionType.class);
        // fill list types
        List<String> nter =
                objectCreator.createLoremIpsumObject(MultiValueConverterTest.StringList.class);
        fusionType.getNter().addAll(nter);
        List<String> cter =
                objectCreator.createLoremIpsumObject(MultiValueConverterTest.StringList.class);
        fusionType.getCter().addAll(cter);
        return fusionType;
    }

    public static Fusion createSkinnyUniObject() {
        FusionBuilder builder = new FusionBuilder();
        return builder.build();
    }
}
