package org.uniprot.core.xml.unirule;

import org.bbottema.loremipsumobjects.typefactories.ClassBasedFactory;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UniRuleEntryConverterTest extends AbstractConverterTest {
    public static class PositionalFeatureSetTypeList extends ArrayList<PositionalFeatureSetType> {

    }

    public static UniRuleType createObject() {
        UniRuleType uniRuleType = objectCreator.createLoremIpsumObject(UniRuleType.class);
        uniRuleType.setMain(MainTypeConverterTest.createObject());
        uniRuleType.setInformation(InformationConverterTest.createObject());
        // fill list types
        List<CaseType> caseTypes = CaseTypeConverterTest.createObjects();
        CasesType casesType = uniRuleObjectFactory.createCasesType();
        casesType.getCase().addAll(caseTypes);
        uniRuleType.setCases(casesType);

        List<SamFeatureSetType> samFeatureSets = SamFeatureSetConverterTest.createObjects();
        uniRuleType.getSamFeatureSet().addAll(samFeatureSets);

            List<PositionalFeatureSetType> positionalFeatureSets = PositionalFeatureSetConverterTest.createObjects();
        uniRuleType.getPositionalFeatureSet().addAll(positionalFeatureSets);
        return uniRuleType;
    }


    public static List<UniRuleType> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject())
                .collect(Collectors.toList());
    }
}
