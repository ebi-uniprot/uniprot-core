package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.gene.Gene;
import org.uniprot.core.uniprotkb.Keyword;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.impl.AlternativeProductsCommentBuilder;
import org.uniprot.core.uniprotkb.description.ProteinDescription;
import org.uniprot.core.uniprotkb.description.impl.ProteinDescriptionBuilder;
import org.uniprot.core.uniprotkb.impl.GeneBuilder;
import org.uniprot.core.uniprotkb.impl.KeywordBuilder;
import org.uniprot.core.uniprotkb.xdb.UniProtKBCrossReference;
import org.uniprot.core.uniprotkb.xdb.impl.UniProtCrossReferenceBuilder;
import org.uniprot.core.unirule.Annotation;

public class AnnotationImplTest {
    @Test
    void testCreateObjectByNoArgConstructor() {
        Annotation annotation = new AnnotationImpl();
        assertNotNull(annotation);
        assertAll(
                "Fields null",
                () -> assertNull(annotation.getDbReference()),
                () -> assertNull(annotation.getGene()),
                () -> assertNull(annotation.getKeyword()),
                () -> assertNull(annotation.getComment()),
                () -> assertNull(annotation.getProteinDescription()));
    }

    @Test
    void testCreateObject() {
        Comment comment = new AlternativeProductsCommentBuilder().build();
        Keyword keyword = new KeywordBuilder().build();
        Gene gene = new GeneBuilder().build();
        UniProtKBCrossReference dbReference = new UniProtCrossReferenceBuilder().build();
        ProteinDescription proteinDescription = new ProteinDescriptionBuilder().build();
        Annotation annotation =
                new AnnotationImpl(comment, keyword, gene, dbReference, proteinDescription);
        assertNotNull(annotation);
        assertEquals(comment, annotation.getComment());
        assertEquals(keyword, annotation.getKeyword());
        assertEquals(gene, annotation.getGene());
        assertEquals(dbReference, annotation.getDbReference());
    }
}
