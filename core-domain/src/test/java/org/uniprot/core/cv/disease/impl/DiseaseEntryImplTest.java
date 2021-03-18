package org.uniprot.core.cv.disease.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.Statistics;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.cv.disease.DiseaseEntry;
import org.uniprot.core.cv.keyword.KeywordId;
import org.uniprot.core.cv.keyword.impl.KeywordIdBuilder;
import org.uniprot.core.impl.StatisticsBuilder;

class DiseaseEntryImplTest {
    private String uuid;
    private String name;
    private String id;
    private String acronym;
    private String def;
    private List<String> altNames;
    private List<DiseaseCrossReference> xrefs;
    private List<KeywordId> kws;
    private Statistics stat;

    @BeforeEach
    void setUp() {
        this.uuid = UUID.randomUUID().toString();
        this.name = "name-" + this.uuid;
        this.id = "id" + this.uuid;
        this.acronym = "acronym" + this.uuid;
        this.def = "def" + this.uuid;
        this.altNames =
                IntStream.range(0, 5)
                        .mapToObj(i -> "an-" + i + this.uuid)
                        .collect(Collectors.toList());
        this.xrefs = IntStream.range(0, 5).mapToObj(i -> getXRef(i)).collect(Collectors.toList());
        this.kws = IntStream.range(0, 5).mapToObj(i -> getKeyword(i)).collect(Collectors.toList());
        this.stat = new StatisticsBuilder()
                .reviewedProteinCount(10000)
                .unreviewedProteinCount(10000)
                .build();
    }

    @Test
    void testCreateDisease() {
        DiseaseEntry disease = createDisease();

        assertEquals(name, disease.getName());
        assertEquals(id, disease.getId());
        assertEquals(def, disease.getDefinition());
        Assertions.assertIterableEquals(altNames, disease.getAlternativeNames());
        Assertions.assertIterableEquals(xrefs, disease.getCrossReferences());
        Assertions.assertIterableEquals(kws, disease.getKeywords());
        assertEquals(stat, disease.getStatistics());
    }

    @Test
    void testEquals() {
        DiseaseEntry d1 = createDisease();
        DiseaseEntry d2 = createDisease();
        assertEquals(d1, d2);
        // their hash should also be same
        assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void testNotEquals() {
        DiseaseEntry d1 = createDisease();
        DiseaseEntry d2 = DiseaseEntryBuilder.from(d1).keywordsSet(Collections.emptyList()).build();
        assertNotEquals(d1, d2);
    }

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        DiseaseEntry obj = new DiseaseEntryImpl();
        assertNotNull(obj);
    }

    private DiseaseEntry createDisease() {
        return new DiseaseEntryImpl(
                this.name,
                this.id,
                this.acronym,
                this.def,
                this.altNames,
                this.xrefs,
                this.kws,
                this.stat);
    }

    private KeywordId getKeyword(int i) {
        return new KeywordIdBuilder().name("name" + i + this.uuid).id("id" + i + this.uuid).build();
    }

    private DiseaseCrossReference getXRef(int randomiser) {

        List<String> props = Arrays.asList("prop1", "prop2", "prop3");
        String id = "XREF-123" + randomiser + this.uuid;
        String databaseType = "SAMPLE_TYPE" + randomiser + this.uuid;
        return new DiseaseCrossReferenceBuilder()
                .databaseType(databaseType)
                .id(id)
                .propertiesSet(props)
                .build();
    }
}
