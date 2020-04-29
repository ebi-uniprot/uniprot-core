 package org.uniprot.core.xml.unirule;

 import org.uniprot.core.xml.AbstractConverterTest;
 import org.uniprot.core.xml.jaxb.unirule.AnnotationType;
 import org.uniprot.core.xml.jaxb.unirule.AnnotationsType;
 import org.uniprot.core.xml.jaxb.unirule.SamFeatureSetType;

 import java.util.ArrayList;
 import java.util.List;

 public class SamFeatureSetConverterTest extends AbstractConverterTest {


     public static SamFeatureSetType createObject(){
         SamFeatureSetType samFeatureSetType = objectCreator.createLoremIpsumObject(SamFeatureSetType.class);
         List<AnnotationType> annotationTypes = AnnotationConverterTest.createObjects();
         AnnotationsType annotationsType = uniRuleObjectFactory.createAnnotationsType();
         annotationsType.getAnnotation().addAll(annotationTypes);
         samFeatureSetType.setAnnotations(annotationsType);
         return samFeatureSetType;
     }

     public static List<SamFeatureSetType> createObjects() {
         return objectCreator.createLoremIpsumObject(SamFeatureSetTypeList.class);
     }

     public static class SamFeatureSetTypeList extends ArrayList<SamFeatureSetType> {}
 }
