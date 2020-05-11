package org.uniprot.core.xml.unirule;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.unirule.Annotation;
import org.uniprot.core.unirule.impl.AnnotationBuilder;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.AnnotationType;

public class AnnotationConverterTest extends AbstractConverterTest {

    @BeforeAll
    static void setUp() {
        converter = new AnnotationConverter();
    }

    @Test
    void testConvertSkinnyUniObj() {
        Annotation uniObj = createSkinnyUniObject();
        AnnotationType xmlObj = (AnnotationType) converter.toXml(uniObj);
        assertNotNull(xmlObj);
        assertNull(xmlObj.getComment());
        assertNull(xmlObj.getKeyword());
        assertNull(xmlObj.getGene());
        assertNull(xmlObj.getProtein());
        assertNull(xmlObj.getDbReference());
        // convert back to uniObj- test round trip
        Annotation updatedUniObj = (Annotation) converter.fromXml(xmlObj);
        assertEquals(uniObj, updatedUniObj);
    }

    private Annotation createSkinnyUniObject() {
        AnnotationBuilder builder = new AnnotationBuilder();
        return builder.build();
    }

    public static class AnnotationTypeList extends ArrayList<AnnotationType> {}

    public static AnnotationType createObject() {
        AnnotationType annotationType = objectCreator.createLoremIpsumObject(AnnotationType.class);
        update(annotationType);
        return annotationType;
    }

    public static List<AnnotationType> createObjects() {
        AnnotationTypeList annotationTypes =
                objectCreator.createLoremIpsumObject(AnnotationTypeList.class);
        annotationTypes.forEach(annotationType -> update(annotationType));
        return annotationTypes;
    }

    private static void update(AnnotationType annotationType) {
        annotationType.setProtein(ProteinConverterTest.createObject());
        // update comment type
        annotationType.getComment().setType(CommentType.DISEASE.getName());
        // // update type to MIM
        annotationType.getComment().getDisease().getDbReference().setType("MIM");
    }
}
