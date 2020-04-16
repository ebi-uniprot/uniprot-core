package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.impl.DiseaseCommentBuilderTest;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilderTest;
import org.uniprot.core.uniprotkb.impl.GeneBuilderTest;
import org.uniprot.core.uniprotkb.impl.KeywordBuilderTest;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtKBCrossReferenceBuilderTest;
import org.uniprot.core.unirule.Annotation;

public class AnnotationBuilderTest {

    public static Annotation createObject() {
        ProteinDescription proteinDescription = ProteinDescriptionBuilderTest.createObject();
        Comment comment = DiseaseCommentBuilderTest.createObject();
        Keyword keyword = KeywordBuilderTest.createObject();
        UniProtKBCrossReference crossReference = UniProtKBCrossReferenceBuilderTest.createObject();
        Gene gene = GeneBuilderTest.createObject();
        AnnotationBuilder builder = new AnnotationBuilder();
        builder.proteinDescription(proteinDescription);
        builder.dbReference(crossReference).gene(gene);
        builder.comment(comment).keyword(keyword);
        Annotation annotation = builder.build();
        assertNotNull(annotation);
        assertEquals(comment, annotation.getComment());
        assertEquals(keyword, annotation.getKeyword());
        assertEquals(crossReference, annotation.getDbReference());
        assertEquals(gene, annotation.getGene());
        assertEquals(proteinDescription, annotation.getProteinDescription());
        return annotation;
    }

    public static List<Annotation> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
