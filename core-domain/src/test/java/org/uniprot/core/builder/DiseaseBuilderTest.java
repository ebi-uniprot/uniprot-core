package org.uniprot.core.builder;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.CrossReference;
import org.uniprot.core.cv.disease.Disease;
import org.uniprot.core.cv.disease.impl.DiseaseImpl;
import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.core.cv.keyword.impl.KeywordImpl;

class DiseaseBuilderTest {

    @Test
    void canCreateWithId() {
        String id = "id";
        Disease disease = new DiseaseBuilder().id(id).build();
        assertNotNull(disease);
        assertEquals(id, disease.getId());
    }

    @Test
    void canCreateWithAccession() {
        String accession = "uniprot";
        Disease disease = new DiseaseBuilder().accession(accession).build();
        assertNotNull(disease);
        assertEquals(accession, disease.getAccession());
    }

    @Test
    void canCreateWithAcronym() {
        String acronym = "acronym";
        Disease disease = new DiseaseBuilder().acronym(acronym).build();
        assertNotNull(disease);
        assertEquals(acronym, disease.getAcronym());
    }

    @Test
    void canCreateWith_definition() {
        String definition = "definition";
        Disease disease = new DiseaseBuilder().definition(definition).build();
        assertNotNull(disease);
        assertEquals(definition, disease.getDefinition());
    }

    @Test
    void canCreateWith_alternativeNamesSingle() {
        String alternativeNames = "alternativeNames";
        Disease disease = new DiseaseBuilder().alternativeNames(alternativeNames).build();
        assertNotNull(disease);
      assertEquals(1, disease.getAlternativeNames().size());
        assertEquals(alternativeNames, disease.getAlternativeNames().get(0));
    }

    @Test
    void canCreateWith_alternativeNames() {
        List<String> alternativeNames = Arrays.asList("alternativeNames", "1", "3");
        Disease disease = new DiseaseBuilder().alternativeNames(alternativeNames).build();
        assertNotNull(disease);
        assertEquals(alternativeNames, disease.getAlternativeNames());
    }

    @Test
    void canCreateWith_reviewedProteinCount() {
        Long reviewedProteinCount = 435L;
        Disease disease = new DiseaseBuilder().reviewedProteinCount(reviewedProteinCount).build();
        assertNotNull(disease);
        assertEquals(reviewedProteinCount, disease.getReviewedProteinCount());
    }

    @Test
    void canCreateWith_unreviewedProteinCount() {
        Long unreviewedProteinCount = -435L;
        Disease disease =
                new DiseaseBuilder().unreviewedProteinCount(unreviewedProteinCount).build();
        assertNotNull(disease);
        assertEquals(unreviewedProteinCount, disease.getUnreviewedProteinCount());
    }

    @Test
    void canCreateWith_keywordsSingle() {
        Keyword keywords = new KeywordImpl("1", "key");
        Disease disease = new DiseaseBuilder().keywords(keywords).build();
        assertNotNull(disease);
      assertEquals(1, disease.getKeywords().size());
        assertEquals(keywords, disease.getKeywords().get(0));
    }

  @Test
  void canCreateWith_keywords() {
    List<Keyword> keywords = Arrays.asList(new KeywordImpl("1", "key"),
      new KeywordImpl("2", "key2"));
    Disease disease = new DiseaseBuilder().keywords(keywords).build();
    assertNotNull(disease);
    assertEquals(keywords, disease.getKeywords());
  }

  @Test
  void canCreateWith_crossReferencesSingle() {
    CrossReference crossReferences = new CrossReference("1", "key");
    Disease disease = new DiseaseBuilder().crossReferences(crossReferences).build();
    assertNotNull(disease);
    assertEquals(1, disease.getCrossReferences().size());
    assertEquals(crossReferences, disease.getCrossReferences().get(0));
  }

  @Test
  void canCreateWith_crossReferences() {
    List<CrossReference> crossReferences = Arrays.asList(new CrossReference("pro", "2"),
      new CrossReference("uni", "1"));
    Disease disease = new DiseaseBuilder().crossReferences(crossReferences).build();
    assertNotNull(disease);
    assertEquals(crossReferences, disease.getCrossReferences());
  }

  @Test
  void canGetInstanceFromStaticMethod() {
    DiseaseBuilder builder = DiseaseBuilder.newInstance();
    assertNotNull(builder);
  }

  @Test
  void builderFrom_constructorImp_shouldCreate_equalObject() {
    Disease impl = new DiseaseImpl("id", "acc", "acr", "def", singletonList("al name"), singletonList(new CrossReference("pro", "2")),
      singletonList(new KeywordImpl("1", "key")), 3L, 6L);
    Disease obj = new DiseaseBuilder().from(impl).build();
    assertTrue(impl.equals(obj) && obj.equals(impl));
    assertEquals(impl.hashCode(), obj.hashCode());
  }
}
