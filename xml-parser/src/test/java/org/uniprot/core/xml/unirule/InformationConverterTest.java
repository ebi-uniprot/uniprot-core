package org.uniprot.core.xml.unirule;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Information;
import org.uniprot.core.unirule.impl.InformationBuilderTest;
import org.uniprot.core.xml.jaxb.unirule.InformationType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class InformationConverterTest {
    private static ObjectFactory objectFactory;
    private static InformationConverter converter;


    @BeforeAll
    static void setUp() {
        objectFactory = new ObjectFactory();
        converter = new InformationConverter(objectFactory);
    }

    @Test
    void testObjectToXml() {
        Information object = InformationBuilderTest.createObject();
        InformationType xmlObject = converter.toXml(object);
        assertNotNull(xmlObject);
    }

    @Test
    void testFromXmlToObject(){
        Information object = InformationBuilderTest.createObject();
        InformationType xmlObject = converter.toXml(object);
        assertNotNull(xmlObject);
    }
}
