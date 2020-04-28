// package org.uniprot.core.xml.unirule;
//
// import org.uniprot.core.xml.AbstractConverterTest;
// import org.uniprot.core.xml.jaxb.uniprot.CommentType;
// import org.uniprot.core.xml.jaxb.uniprot.DbReferenceType;
// import org.uniprot.core.xml.jaxb.uniprot.GeneType;
// import org.uniprot.core.xml.jaxb.uniprot.KeywordType;
// import org.uniprot.core.xml.jaxb.unirule.AnnotationType;
// import org.uniprot.core.xml.jaxb.unirule.ProteinType;
//
// import java.util.List;
// import java.util.UUID;
// import java.util.concurrent.ThreadLocalRandom;
// import java.util.stream.Collectors;
// import java.util.stream.IntStream;
//
// public class AnnotationConverterTest extends AbstractConverterTest {
//
//    private static AnnotationConverter annotationConverter;
//
//    static {
//        annotationConverter = new AnnotationConverter(objectFactory);
//    }
//
//    public static AnnotationType createObject(int listSize) {
//        AnnotationType annotationType = objectFactory.createAnnotationType();
//        CommentType commentType = null;
//        annotationType.setComment(commentType);
//        KeywordType keywordType = null;
//        annotationType.setKeyword(keywordType);
//        GeneType geneType = null;
//        annotationType.setGene(geneType);
//        ProteinType proteinType = null;
//        annotationType.setProtein(proteinType);
//        DbReferenceType dbReferenceType = null;
//        annotationType.setDbReference(dbReferenceType);
//
//        return annotationType;
//    }
//
//    public static AnnotationType createObject() {
//        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
//        return createObject(listSize);
//    }
//
//    public static List<AnnotationType> createObjects(int count) {
//        return IntStream.range(0, count)
//                .mapToObj(i -> createObject(count))
//                .collect(Collectors.toList());
//    }
// }
