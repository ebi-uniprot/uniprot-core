package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.ProteinType;
import org.uniprot.core.xml.uniprot.EvidencedStringTypeConverterTest;

public class ProteinConverterTest extends AbstractConverterTest {

    public static class AlternativeNameList extends ArrayList<ProteinType.AlternativeName> {}

    public static class ProteinTypeList extends ArrayList<ProteinType> {}

    public static ProteinType createObject() {
        ProteinType proteinType = uniRuleObjectFactory.createProteinType();
        fillList(proteinType);
        return proteinType;
    }

    public static List<ProteinType> createObjects() {
        List<ProteinType> objects = objectCreator.createLoremIpsumObject(ProteinTypeList.class);
        objects.forEach(object -> fillList(object));
        return objects;
    }

    private static ProteinType.AlternativeName createAlternativeName() {
        ProteinType.AlternativeName object =
                uniRuleObjectFactory.createProteinTypeAlternativeName();
        fillList(object);
        return object;
    }

    private static List<ProteinType.AlternativeName> createAlternativeNames() {
        List<ProteinType.AlternativeName> objects =
                objectCreator.createLoremIpsumObject(AlternativeNameList.class);
        objects.forEach(obj -> fillList(obj));
        return objects;
    }

    private static ProteinType.RecommendedName createRecommendedName() {
        ProteinType.RecommendedName object =
                uniRuleObjectFactory.createProteinTypeRecommendedName();
        fillList(object);
        return object;
    }

    private static List<ProteinType.Component> createComponents() {
        int count = ThreadLocalRandom.current().nextInt(1, 5);
        return IntStream.range(0, count)
                .mapToObj(i -> createComponent())
                .collect(Collectors.toList());
    }

    private static List<ProteinType.Domain> createDomains() {
        int count = ThreadLocalRandom.current().nextInt(1, 5);
        return IntStream.range(0, count).mapToObj(i -> createDomain()).collect(Collectors.toList());
    }

    private static ProteinType.Domain createDomain() {
        ProteinType.Domain object = uniRuleObjectFactory.createProteinTypeDomain();
        // fill list and enum types
        object.setRecommendedName(createRecommendedName());
        List<ProteinType.AlternativeName> altNames = createAlternativeNames();
        object.getAlternativeName().addAll(altNames);
        object.setAllergenName(EvidencedStringTypeConverterTest.createObject());
        object.setBiotechName(EvidencedStringTypeConverterTest.createObject());
        object.getCdAntigenName().addAll(EvidencedStringTypeConverterTest.createObjects());
        object.getInnName().addAll(EvidencedStringTypeConverterTest.createObjects());
        object.setFlag(getPreCursorFlag());
        return object;
    }

    private static ProteinType.Component createComponent() {
        ProteinType.Component object = uniRuleObjectFactory.createProteinTypeComponent();
        // fill list and enum types
        object.setRecommendedName(createRecommendedName());
        List<ProteinType.AlternativeName> altNames = createAlternativeNames();
        object.getAlternativeName().addAll(altNames);
        object.setAllergenName(EvidencedStringTypeConverterTest.createObject());
        object.setBiotechName(EvidencedStringTypeConverterTest.createObject());
        object.getCdAntigenName().addAll(EvidencedStringTypeConverterTest.createObjects());
        object.getInnName().addAll(EvidencedStringTypeConverterTest.createObjects());
        object.setFlag(getPreCursorFlag());
        return object;
    }

    private static void fillList(ProteinType.RecommendedName object) {
        object.setFullName(EvidencedStringTypeConverterTest.createObject());
        object.getShortName().addAll(EvidencedStringTypeConverterTest.createObjects());
        object.getEcNumber().addAll(EvidencedStringTypeConverterTest.createObjects());
    }

    private static void fillList(ProteinType proteinType) {
        // fill list and enum types
        proteinType.setRecommendedName(createRecommendedName());
        List<ProteinType.AlternativeName> altNames = createAlternativeNames();
        proteinType.getAlternativeName().addAll(altNames);
        proteinType.setAllergenName(EvidencedStringTypeConverterTest.createObject());
        proteinType.setBiotechName(EvidencedStringTypeConverterTest.createObject());
        proteinType.getCdAntigenName().addAll(EvidencedStringTypeConverterTest.createObjects());
        proteinType.getInnName().addAll(EvidencedStringTypeConverterTest.createObjects());
        // set flag
        proteinType.setFlag(getPreCursorFlag());
        proteinType.getDomain().addAll(createDomains());
        proteinType.getComponent().addAll(createComponents());
    }

    private static void fillList(ProteinType.AlternativeName object) {
        object.setFullName(EvidencedStringTypeConverterTest.createObject());
        object.getShortName().addAll(EvidencedStringTypeConverterTest.createObjects());
        object.getEcNumber().addAll(EvidencedStringTypeConverterTest.createObjects());
    }

    private static ProteinType.Flag getPreCursorFlag() {
        ProteinType.Flag flag = new ProteinType.Flag();
        flag.getValue().add("Precursor");
        return flag;
    }
}
