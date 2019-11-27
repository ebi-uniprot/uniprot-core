package org.uniprot.core.literature.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.literature.LiteratureMappedReference;
import org.uniprot.core.literature.builder.LiteratureMappedReferenceBuilder;
import org.uniprot.core.uniprot.impl.UniProtAccessionImpl;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class LiteratureMappedReferenceImplTest {

  @Test
  void needDefaultConstructorForJsonDeserialization() {
    LiteratureMappedReference obj = new LiteratureMappedReferenceImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    LiteratureMappedReference impl = new LiteratureMappedReferenceImpl(new UniProtAccessionImpl("acc"), "sou", "sid",
      Collections.singletonList("sou cat"), "anno");
    LiteratureMappedReference obj = new LiteratureMappedReferenceBuilder().from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}