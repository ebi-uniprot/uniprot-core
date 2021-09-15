package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.*;
import org.uniprot.core.unirule.impl.UniRuleEntryBuilder;
import org.uniprot.core.unirule.impl.UniRuleIdBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.*;

public class UniRuleEntryConverterTest extends AbstractConverterTest {

    @BeforeAll
    static void setUp() {
        converter = new UniRuleEntryConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        UniRuleEntry uniObj = createSkinnyUniObject();
        UniRuleType xmlObj = (UniRuleType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(xmlObj.getInformation());
        assertNotNull(xmlObj.getMain());
        assertNotNull(xmlObj.getId());
        assertNull(xmlObj.getCases());
        assertTrue(xmlObj.getSamFeatureSet().isEmpty());
        assertTrue(xmlObj.getPositionalFeatureSet().isEmpty());
        assertNull(xmlObj.getCreated());
        assertNull(xmlObj.getModified());
        // convert back to uniObj- test round trip
        UniRuleEntry updatedUniObj = (UniRuleEntry) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private UniRuleEntry createSkinnyUniObject() {
        // UniRuleId uniRuleId, Information information, Rule mainRule)
        Information info = InformationConverterTest.createSkinnyUniObject();
        Rule rule = MainTypeConverterTest.createSkinnyUniObject();
        UniRuleId uniRuleid = new UniRuleIdBuilder("uid").build();
        UniRuleEntryBuilder builder = new UniRuleEntryBuilder(uniRuleid, info, rule);
        return builder.build();
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

        List<PositionalFeatureSetType> positionalFeatureSets =
                PositionalFeatureSetConverterTest.createObjects();
        uniRuleType.getPositionalFeatureSet().addAll(positionalFeatureSets);
        return uniRuleType;
    }

    public static List<UniRuleType> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
