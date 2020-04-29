package org.uniprot.core.xml.unirule;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.AnnotationType;

public class AnnotationConverterTest extends AbstractConverterTest {

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
