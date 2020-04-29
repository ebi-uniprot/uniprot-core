package org.uniprot.core.xml.unirule;

import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
import org.uniprot.core.xml.jaxb.unirule.AnnotationType;

import java.util.ArrayList;
import java.util.List;

public class AnnotationConverterTest extends AbstractConverterTest {

    public static class AnnotationTypeList extends ArrayList<AnnotationType> { }

    public static AnnotationType createObject(){
        AnnotationType annotationType = objectCreator.createLoremIpsumObject(AnnotationType.class);
        updateEnums(annotationType);
        return annotationType;
    }

    public static List<AnnotationType> createObjects() {
        AnnotationTypeList annotationTypes = objectCreator.createLoremIpsumObject(AnnotationTypeList.class);
        annotationTypes.forEach(annotationType -> updateEnums(annotationType));
        return annotationTypes;
    }

    private static void updateEnums(AnnotationType annotationType) {
        // update comment type
        annotationType.getComment().setType(CommentType.DISEASE.getName());
        // // update type to MIM
        annotationType.getComment().getDisease().getDbReference().setType("MIM");
    }


}
