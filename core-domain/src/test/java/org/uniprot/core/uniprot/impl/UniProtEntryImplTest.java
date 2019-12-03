package org.uniprot.core.uniprot.impl;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.builder.UniProtEntryBuilder;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.DiseaseComment;
import org.uniprot.core.uniprot.comment.builder.BPCPCommentBuilder;
import org.uniprot.core.uniprot.comment.builder.DiseaseCommentBuilder;
import org.uniprot.core.uniprot.feature.Feature;
import org.uniprot.core.uniprot.feature.FeatureType;
import org.uniprot.core.uniprot.feature.builder.FeatureBuilder;

import java.util.Arrays;
import java.util.List;

class UniProtEntryImplTest {
  private UniProtEntry minEntry = new UniProtEntryBuilder("acc", "id", UniProtEntryType.SWISSPROT).build();
  private List<Comment> comments = asList(new BPCPCommentBuilder().build(), new DiseaseCommentBuilder().build());
  private List<Feature> features = asList(new FeatureBuilder().type(FeatureType.CHAIN).build(), new FeatureBuilder().type(FeatureType.VARIANT).build());

  @Test
  void needDefaultConstructorForJsonDeserialization() {
    UniProtEntry obj = new UniProtEntryImpl();
    assertNotNull(obj);
  }

  @Test
  void canFilterByCommentType() {
    UniProtEntry entry = UniProtEntryBuilder.fromInstance(minEntry).commentsSet(comments).build();
    List<DiseaseComment> comments = entry.getCommentByType(CommentType.DISEASE);

    assertFalse(comments.isEmpty());
    assertEquals(1, comments.size());
  }

    @Test
    void canFilterByFeatureType() {
        UniProtEntry entry = UniProtEntryBuilder.fromInstance(minEntry).featuresSet(features).build();
        List<Feature> comments = entry.getFeaturesByType(FeatureType.CHAIN);

        assertFalse(comments.isEmpty());
        assertEquals(1, comments.size());
    }
}
