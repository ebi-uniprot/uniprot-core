package org.uniprot.core.xml.unirule;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Information;
import org.uniprot.core.unirule.impl.InformationBuilderTest;
import org.uniprot.core.xml.jaxb.unirule.FusionType;
import org.uniprot.core.xml.jaxb.unirule.InformationType;
import org.uniprot.core.xml.jaxb.unirule.MultiValueType;
import org.uniprot.core.xml.jaxb.unirule.ObjectFactory;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    void testFromXmlToObject() {
        Information object = InformationBuilderTest.createObject();
        InformationType xmlObject = converter.toXml(object);
        assertNotNull(xmlObject);
    }

    public static InformationType createObject(int listSize){
        InformationType informationType = getObjectFactory().createInformationType();
        String random = UUID.randomUUID().toString().substring(0, 4);
        String version = "version-" + random;
        String comment = "comment-" + random;
        String oldRuleNum = "oldRuleNum-" + random;
        MultiValueType uniprotId = MultiValueConverterTest.createObject(listSize);
        String dataClass = "dataClass-" + random;
        MultiValueType name = MultiValueConverterTest.createObject(listSize);
        FusionType fusionType = FusionConverterTest.createObject(listSize);
        MultiValueType related = MultiValueConverterTest.createObject(listSize);
        MultiValueType template = MultiValueConverterTest.createObject(listSize);
        MultiValueType duplicate = MultiValueConverterTest.createObject(listSize);
        MultiValueType plasmaid = MultiValueConverterTest.createObject(listSize);
        String internal = "internal-" + random;
        informationType.setVersion(version);
        informationType.setComment(comment);
        informationType.setOldRuleNum(oldRuleNum);
        informationType.setUniprotId(uniprotId);
        informationType.setDataClass(dataClass);
        informationType.setName(name);
        informationType.setFusion(fusionType);
        informationType.setRelated(related);
        informationType.setTemplate(template);
        informationType.setDuplicate(duplicate);
        informationType.setPlasmid(plasmaid);
        informationType.setInternal(internal);
        return informationType;
    }

    public static InformationType createObject(){
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<InformationType> createObjects(int count){
        return IntStream.range(0, count).mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }

    private static ObjectFactory getObjectFactory(){
        if(objectFactory == null){
            objectFactory = new ObjectFactory();
        }
        return objectFactory;
    }

    public static void main(String[] args) {
        System.out.println("Hello World-1");
    }
}
