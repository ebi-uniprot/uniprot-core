package org.uniprot.core.cv.keyword;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class KeywordCategoryTest {

  @ParameterizedTest
  @EnumSource(KeywordCategory.class)
  void nameAndDisplayNameAreSame(KeywordCategory category){
    assertSame(category.getName(), category.toDisplayName());
  }

  @ParameterizedTest
  @EnumSource(KeywordCategory.class)
  void nameShouldNotBeNull(KeywordCategory category){
    assertNotNull(category.getName());
  }

  @ParameterizedTest
  @ValueSource(strings = {"BIOLOGICAL PROCESS","diseaseEntry", "ptm"})
  void caseWillIgnoreWhileConvertingFromNameString(String val){
    assertNotEquals(KeywordCategory.UNKNOWN, KeywordCategory.fromValue(val));
  }

  @Test
  void nullNameWillConsiderUnknown(){
    assertEquals(KeywordCategory.UNKNOWN, KeywordCategory.fromValue(null));
  }
}