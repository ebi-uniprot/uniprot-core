package org.uniprot.core.cv.disease.builder;

import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.builder.KeywordIdBuilder;

class DiseaseEntryBuilderTest {

    @Test
    void canCreateWithId() {
        String id = "id";
        DiseaseEntry disease = new DiseaseEntryBuilder().id(id).build();
        assertNotNull(disease);
        assertEquals(id, disease.getId());
    }

    @Test
    void canCreateWithAccession() {
        String accession = "uniprot";
        DiseaseEntry disease = new DiseaseEntryBuilder().accession(accession).build();
        assertNotNull(disease);
        assertEquals(accession, disease.getAccession());
    }

    @Test
    void canCreateWithAcronym() {
        String acronym = "acronym";
        DiseaseEntry disease = new DiseaseEntryBuilder().acronym(acronym).build();
        assertNotNull(disease);
        assertEquals(acronym, disease.getAcronym());
    }

    @Test
    void canCreateWith_definition() {
        String definition = "definition";
        DiseaseEntry disease = new DiseaseEntryBuilder().definition(definition).build();
        assertNotNull(disease);
        assertEquals(definition, disease.getDefinition());
    }

    @Test
    void canCreateWith_alternativeNamesSingle() {
        String alternativeNames = "alternativeNames";
        DiseaseEntry disease =
                new DiseaseEntryBuilder().alternativeNamesAdd(alternativeNames).build();
        assertNotNull(disease);
        assertEquals(1, disease.getAlternativeNames().size());
        assertEquals(alternativeNames, disease.getAlternativeNames().get(0));
    }

    @Test
    void canCreateWith_alternativeNames() {
        List<String> alternativeNames = Arrays.asList("alternativeNames", "1", "3");
        DiseaseEntry disease =
                new DiseaseEntryBuilder().alternativeNamesSet(alternativeNames).build();
        assertNotNull(disease);
        assertEquals(alternativeNames, disease.getAlternativeNames());
    }

    @Test
    void canCreateWith_reviewedProteinCount() {
        Long reviewedProteinCount = 435L;
        DiseaseEntry disease =
                new DiseaseEntryBuilder().reviewedProteinCount(reviewedProteinCount).build();
        assertNotNull(disease);
        assertEquals(reviewedProteinCount, disease.getReviewedProteinCount());
    }

    @Test
    void canCreateWith_unreviewedProteinCount() {
        Long unreviewedProteinCount = -435L;
        DiseaseEntry disease =
                new DiseaseEntryBuilder().unreviewedProteinCount(unreviewedProteinCount).build();
        assertNotNull(disease);
        assertEquals(unreviewedProteinCount, disease.getUnreviewedProteinCount());
    }

    @Test
    void canCreateWith_keywordsSingle() {
        KeywordId keywords = kw("1", "key");
        DiseaseEntry disease = new DiseaseEntryBuilder().keywordsAdd(keywords).build();
        assertNotNull(disease);
        assertEquals(1, disease.getKeywords().size());
        assertEquals(keywords, disease.getKeywords().get(0));
    }

    @Test
    void canCreateWith_keywords() {
        List<KeywordId> keywords = Arrays.asList(kw("1", "key"), kw("2", "key2"));
        DiseaseEntry disease = new DiseaseEntryBuilder().keywordsSet(keywords).build();
        assertNotNull(disease);
        assertEquals(keywords, disease.getKeywords());
    }

    @Test
    void canCreateWith_crossReferencesSingle() {
        DiseaseCrossReference crossReferences =
                new DiseaseCrossReferenceBuilder().databaseType("1").id("key").build();
        DiseaseEntry disease =
                new DiseaseEntryBuilder().crossReferencesAdd(crossReferences).build();
        assertNotNull(disease);
        assertEquals(1, disease.getCrossReferences().size());
        assertEquals(crossReferences, disease.getCrossReferences().get(0));
    }

    @Test
    void canCreateWith_crossReferences() {
        List<DiseaseCrossReference> crossReferences =
                Arrays.asList(
                        new DiseaseCrossReferenceBuilder().databaseType("pro").id("2").build(),
                        new DiseaseCrossReferenceBuilder().databaseType("uni").id("1").build());
        DiseaseEntry disease =
                new DiseaseEntryBuilder().crossReferencesSet(crossReferences).build();
        assertNotNull(disease);
        assertEquals(crossReferences, disease.getCrossReferences());
    }

    @Test
    void canGetInstanceFromStaticMethod() {
        DiseaseEntryBuilder builder = new DiseaseEntryBuilder();
        assertNotNull(builder);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        DiseaseEntry impl =
                new DiseaseEntryImpl(
                        "id",
                        "acc",
                        "acr",
                        "def",
                        singletonList("al name"),
                        singletonList(
                                new DiseaseCrossReferenceBuilder()
                                        .databaseType("pro")
                                        .id("2")
                                        .build()),
                        singletonList(kw("1", "key")),
                        3L,
                        6L);
        DiseaseEntry obj = DiseaseEntryBuilder.from(impl).build();
        assertTrue(impl.equals(obj) && obj.equals(impl));
        assertEquals(impl.hashCode(), obj.hashCode());
    }

    private KeywordId kw(String id, String accession) {
        return new KeywordIdBuilder().id(id).accession(accession).build();
    }
}
