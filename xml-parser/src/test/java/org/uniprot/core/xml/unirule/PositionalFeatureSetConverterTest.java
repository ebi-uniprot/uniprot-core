package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.PositionFeatureSet;
import org.uniprot.core.unirule.PositionalFeature;
import org.uniprot.core.unirule.impl.PositionFeatureSetBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureSetType;
import org.uniprot.core.xml.jaxb.unirule.PositionalFeatureType;

public class PositionalFeatureSetConverterTest extends AbstractConverterTest {
    @BeforeAll
    static void setUp() {
        converter = new PositionalFeatureSetConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        PositionFeatureSet uniObj = createSkinnyUniObject();
        PositionalFeatureSetType xmlObj = (PositionalFeatureSetType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNotNull(xmlObj.getPositionalFeature());
        assertEquals(1, xmlObj.getPositionalFeature().size());
        assertNull(xmlObj.getConditionSet());
        assertNull(xmlObj.getAnnotations());
        assertNull(xmlObj.getRuleExceptions());
        assertNull(xmlObj.getTemplate());
        assertNull(xmlObj.getAlignmentSignature());
        assertNull(xmlObj.getTag());
        // convert back to uniObj- test round trip
        PositionFeatureSet updatedUniObj = (PositionFeatureSet) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private PositionFeatureSet createSkinnyUniObject() {
        PositionalFeature pf = PositionalFeatureConverterTest.createSkinnyUniObject();
        PositionFeatureSetBuilder builder = new PositionFeatureSetBuilder(pf);
        return builder.build();
    }

    public static PositionalFeatureSetType createObject() {
        PositionalFeatureSetType positionalFeatureSetType =
                objectCreator.createLoremIpsumObject(PositionalFeatureSetType.class);
        update(positionalFeatureSetType);
        return positionalFeatureSetType;
    }

    public static List<PositionalFeatureSetType> createObjects() {
        PositionalFeatureSetTypeList objects =
                objectCreator.createLoremIpsumObject(PositionalFeatureSetTypeList.class);
        objects.forEach(object -> update(object));
        return objects;
    }

    private static void update(PositionalFeatureSetType positionalFeatureSetType) {
        // fill the list type
        List<PositionalFeatureType> positionalFeatureTypes =
                PositionalFeatureConverterTest.createObjects();
        positionalFeatureSetType.getPositionalFeature().addAll(positionalFeatureTypes);
        positionalFeatureSetType
                .getConditionSet()
                .getCondition()
                .addAll(ConditionConverterTest.createObjects());
        positionalFeatureSetType
                .getAnnotations()
                .getAnnotation()
                .addAll(AnnotationConverterTest.createObjects());
        positionalFeatureSetType
                .getRuleExceptions()
                .getRuleException()
                .addAll(RuleExceptionConverterTest.createObjects());
    }

    public static class PositionalFeatureSetTypeList extends ArrayList<PositionalFeatureSetType> {}
}
