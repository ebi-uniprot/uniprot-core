package org.uniprot.core.xml.unirule;

import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.xml.AbstractConverterTest;
import org.uniprot.core.xml.jaxb.unirule.AnnotationType;

import java.util.ArrayList;
import java.util.List;

public class AnnotationConverterTest extends AbstractConverterTest {

    public static class AnnotationTypeList extends ArrayList<AnnotationType> { }

    public static AnnotationType createObject(){
        AnnotationType annotationType = objectCreator.createLoremIpsumObject(AnnotationType.class);
        annotationType.getComment().setType(CommentType.DISEASE.getName());
        return annotationType;
    }


    public static List<AnnotationType> createObjects() {
        return objectCreator.createLoremIpsumObject(AnnotationTypeList.class);
    }
}
