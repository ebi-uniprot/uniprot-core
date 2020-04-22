package org.uniprot.core.xml.unirule;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.impl.FusionBuilder;
import org.uniprot.core.unirule.impl.FusionBuilderTest;
import org.uniprot.core.xml.jaxb.unirule.FusionType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

public class FusionConverterTest {
    private static ObjectFactory objectFactory;
    private static FusionConverter fusionConverter;

    @BeforeAll
    static void setUp() {
        objectFactory = new ObjectFactory();
        fusionConverter = new FusionConverter(objectFactory);
    }

    @Test
    void testObjectToXml() {
        Fusion fusion = FusionBuilderTest.createObject();
        FusionType xmlObject = fusionConverter.toXml(fusion);
        assertNotNull(xmlObject);
        assertEquals(fusion.getNters(), xmlObject.getNter());
        assertEquals(fusion.getCters(), xmlObject.getCter());
    }

    @Test
    void testObjectToXmlWithoutCter(){
        FusionBuilder builder = FusionBuilder.from(FusionBuilderTest.createObject());
        builder.ctersSet(null);
        Fusion fusion = builder.build();
        FusionType xmlObject = fusionConverter.toXml(fusion);
        assertNotNull(xmlObject);
        assertEquals(fusion.getNters(), xmlObject.getNter());
        assertTrue(xmlObject.getCter().isEmpty());
    }

    @Test
    void testObjectToXmlWithoutNter(){
        FusionBuilder builder = FusionBuilder.from(FusionBuilderTest.createObject());
        builder.ntersSet(null);
        Fusion fusion = builder.build();
        FusionType xmlObject = fusionConverter.toXml(fusion);
        assertNotNull(xmlObject);
        assertEquals(fusion.getCters(), xmlObject.getCter());
        assertTrue(xmlObject.getNter().isEmpty());
    }

    @Test
    void testFromXmlToObject(){
        FusionType fusionType = createObject();
        Fusion fusion = fusionConverter.fromXml(fusionType);
        assertNotNull(fusion);
        assertEquals(fusionType.getNter(), fusion.getNters());
        assertEquals(fusionType.getCter(), fusion.getCters());
    }

    @Test
    void testFromEmptyXmlToObject(){
        FusionType fusionType = objectFactory.createFusionType();
        Fusion fusion = fusionConverter.fromXml(fusionType);
        assertNotNull(fusion);
        assertTrue(fusionType.getNter().isEmpty());
        assertTrue(fusionType.getCter().isEmpty());
    }

    @Test
    void testRoundTripXml(){
        FusionType fusionType = createObject();
        // convert xml to object
        Fusion fusion = fusionConverter.fromXml(fusionType);
        assertNotNull(fusion);
        // convert object to xml
        FusionType xmlObject = fusionConverter.toXml(fusion);
        assertNotNull(xmlObject);
        assertEquals(fusionType, xmlObject);
    }

    @Test
    void testRoundTripObject(){
        Fusion fusion = FusionBuilderTest.createObject();
        assertNotNull(fusion);
        // convert object to xml
        FusionType fusionType = fusionConverter.toXml(fusion);
        assertNotNull(fusionType);
        // convert xml to object
        Fusion object = fusionConverter.fromXml(fusionType);
        assertEquals(fusion, object);
    }

    public static FusionType createObject(){
        FusionType fusionType = objectFactory.createFusionType();
        String random = UUID.randomUUID().toString();
        List<String> nter =
                IntStream.range(0, 2)
                        .mapToObj(i -> i + "nter-" + random)
                        .collect(Collectors.toList());
        List<String> cter =
                IntStream.range(0, 2)
                        .mapToObj(i -> i + "cter-" + random)
                        .collect(Collectors.toList());

        fusionType.getNter().addAll(nter);
        fusionType.getCter().addAll(cter);

        return fusionType;
    }

    public static List<FusionType> createObjects(int count){
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
