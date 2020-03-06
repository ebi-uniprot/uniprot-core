package org.uniprot.core.uniprot.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.DiseaseComment;
import org.uniprot.core.uniprot.comment.impl.BPCPCommentBuilder;
import org.uniprot.core.uniprot.comment.impl.DiseaseCommentBuilder;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.uniprot.feature.impl.FeatureBuilder;

class UniProtEntryImplTest {
    private UniProtEntry minEntry =
            new UniProtEntryBuilder("acc", "id", UniProtEntryType.SWISSPROT).build();
    private List<Comment> comments =
            asList(new BPCPCommentBuilder().build(), new DiseaseCommentBuilder().build());
    private List<Feature> features =
            asList(
                    new FeatureBuilder().type(FeatureType.CHAIN).build(),
                    new FeatureBuilder().type(FeatureType.VARIANT).build());

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        UniProtEntry obj = new UniProtEntryImpl();
        assertNotNull(obj);
    }

    @Test
    void canFilterByCommentType() {
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).commentsSet(comments).build();
        List<DiseaseComment> comments = entry.getCommentsByType(CommentType.DISEASE);

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
    }

    @Test
    void canFilterByFeatureType() {
        UniProtEntry entry = UniProtEntryBuilder.from(minEntry).featuresSet(features).build();
        List<Feature> comments = entry.getFeaturesByType(FeatureType.CHAIN);

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
    }
}
