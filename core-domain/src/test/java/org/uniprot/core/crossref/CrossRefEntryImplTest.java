package org.uniprot.core.crossref;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrossRefEntryImplTest {

  @Test
  void needDefaultConstructorForJsonDeserialization() {
    CrossRefEntry obj = new CrossRefEntryImpl();
    assertNotNull(obj);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    CrossRefEntry impl = new CrossRefEntryImpl("name", "acc", "abb","pub", "doild", "link", "server", "dburk", "cat",
      5L, 10L);
    CrossRefEntry obj = new CrossRefEntryBuilder().from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}