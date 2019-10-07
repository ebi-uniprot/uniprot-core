package org.uniprot.core.uniprot.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.citation.builder.ElectronicArticleBuilder;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;
import org.uniprot.core.uniprot.UniProtReference;
import org.uniprot.core.uniprot.builder.ReferenceCommentBuilder;
import org.uniprot.core.uniprot.builder.UniProtReferenceBuilder;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UniProtReferenceImplTest {

  @Test
  void needDefaultConstructorForJsonDeserialization() {
    UniProtReference obj = new UniProtReferenceImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    UniProtReferenceImpl impl = new UniProtReferenceImpl(new ElectronicArticleBuilder().build(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList());
    UniProtReference obj = new UniProtReferenceBuilder().from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }

  @Test
  void canGetReferenceWithType() {
    List<ReferenceComment> referenceComments = Arrays.asList(
      new ReferenceCommentBuilder().type(ReferenceCommentType.PLASMID).build(),
      new ReferenceCommentBuilder().type(ReferenceCommentType.STRAIN).build()
    );
    UniProtReference reference = new UniProtReferenceBuilder().comments(referenceComments).build();

    List<ReferenceComment> typedReferenceComments = reference.getTypedReferenceComments(ReferenceCommentType.PLASMID);

    assertEquals(1, typedReferenceComments.size());
  }
}