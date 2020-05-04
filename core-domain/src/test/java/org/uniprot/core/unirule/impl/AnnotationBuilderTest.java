package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.impl.FreeTextCommentBuilderTest;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilderTest;
import org.uniprot.core.uniprotkb.impl.GeneBuilderTest;
import org.uniprot.core.uniprotkb.impl.KeywordBuilderTest;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtKBCrossReferenceBuilderTest;
import org.uniprot.core.unirule.Annotation;

public class AnnotationBuilderTest {

    public static Annotation createObject(int listSize) {
        ProteinDescription proteinDescription =
                ProteinDescriptionBuilderTest.createObject(listSize);
        // remove submissionsName because UniRule's ProteinType doesnt have this field
        proteinDescription =
                ProteinDescriptionBuilder.from(proteinDescription)
                        .submissionNamesSet(new ArrayList<>())
                        .build();
        Comment comment = FreeTextCommentBuilderTest.createObject(listSize);
        Keyword keyword = KeywordBuilderTest.createObject(listSize);
        UniProtKBCrossReference crossReference =
                UniProtKBCrossReferenceBuilderTest.createObject(listSize);
        Gene gene = GeneBuilderTest.createObject(listSize);
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

    public static Annotation createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<Annotation> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
