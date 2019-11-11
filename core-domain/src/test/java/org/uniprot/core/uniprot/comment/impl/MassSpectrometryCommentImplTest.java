package org.uniprot.core.uniprot.comment.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprot.comment.builder.MassSpectrometryCommentBuilder;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.uniprot.core.ObjectsForTests.createEvidences;

class MassSpectrometryCommentImplTest {
  @Test
  void needDefaultConstructorForJsonDeserialization() {
    MassSpectrometryComment obj = new MassSpectrometryCommentImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    MassSpectrometryComment impl = new MassSpectrometryCommentImpl(MassSpectrometryMethod.ELECTROSPRAY, 0.1F,0.2F,"note",
      Collections.emptyList(), createEvidences());
    MassSpectrometryComment obj = new MassSpectrometryCommentBuilder().from(impl).build();

    assertTrue(impl.hasMolWeight());
    assertTrue(impl.hasMolWeightError());
    assertTrue(impl.hasNote());
    assertTrue(impl.hasMethod());

    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}