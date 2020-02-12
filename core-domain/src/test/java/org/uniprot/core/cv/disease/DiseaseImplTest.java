package org.uniprot.core.cv.disease;

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
import org.uniprot.core.cv.disease.impl.DiseaseCrossReferenceImpl;
import org.uniprot.core.cv.disease.impl.DiseaseEntryImpl;
import org.uniprot.core.cv.keyword.Keyword;
import org.uniprot.core.cv.keyword.impl.KeywordImpl;

public class DiseaseImplTest {
    private String uuid;
    private String id;
    private String acc;
    private String acronym;
    private String def;
    private List<String> altNames;
    private List<DiseaseCrossReference> xrefs;
    private List<Keyword> kws;
    private Long rc;
    private Long urc;

    @BeforeEach
    void setUp() {
        this.uuid = UUID.randomUUID().toString();
        this.id = "id-" + this.uuid;
        this.acc = "acc" + this.uuid;
        this.acronym = "acronym" + this.uuid;
        this.def = "def" + this.uuid;
        this.altNames =
                IntStream.range(0, 5)
                        .mapToObj(i -> "an-" + i + this.uuid)
                        .collect(Collectors.toList());
        this.xrefs = IntStream.range(0, 5).mapToObj(i -> getXRef(i)).collect(Collectors.toList());
        this.kws = IntStream.range(0, 5).mapToObj(i -> getKeyword(i)).collect(Collectors.toList());
        this.rc = ThreadLocalRandom.current().nextLong(10000);
        this.urc = ThreadLocalRandom.current().nextLong(10000);
    }

    @Test
    void testCreateDisease() {
        DiseaseEntry disease = createDisease();

        Assertions.assertEquals(id, disease.getId());
        Assertions.assertEquals(acc, disease.getAccession());
        Assertions.assertEquals(def, disease.getDefinition());
        Assertions.assertIterableEquals(altNames, disease.getAlternativeNames());
        Assertions.assertIterableEquals(xrefs, disease.getCrossReferences());
        Assertions.assertIterableEquals(kws, disease.getKeywords());
        Assertions.assertEquals(rc, disease.getReviewedProteinCount());
        Assertions.assertEquals(urc, disease.getUnreviewedProteinCount());
    }

    @Test
    void testEquals() {
        DiseaseEntry d1 = createDisease();
        DiseaseEntry d2 = createDisease();
        Assertions.assertEquals(d1, d2);
        // their hash should also be same
        Assertions.assertEquals(d1.hashCode(), d2.hashCode());
    }

    @Test
    void testNotEquals() {
        DiseaseEntry d1 = createDisease();
        DiseaseEntry d2 = createDisease();
        DiseaseEntryImpl d21 = (DiseaseEntryImpl) d2;
        d21.setKeywords(Collections.emptyList());
        Assertions.assertNotEquals(d1, d2);
    }

    private DiseaseEntry createDisease() {
        return new DiseaseEntryImpl(
                this.id,
                this.acc,
                this.acronym,
                this.def,
                this.altNames,
                this.xrefs,
                this.kws,
                this.rc,
                this.urc);
    }

    private Keyword getKeyword(int i) {
        return new KeywordImpl("id" + i + this.uuid, "acc" + i + this.uuid);
    }

    private DiseaseCrossReference getXRef(int randomiser) {

        List<String> props = Arrays.asList("prop1", "prop2", "prop3");
        String id = "XREF-123" + randomiser + this.uuid;
        String databaseType = "SAMPLE_TYPE" + randomiser + this.uuid;
        return new DiseaseCrossReferenceImpl(databaseType, id, props);
    }
}
