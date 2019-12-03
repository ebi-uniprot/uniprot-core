package org.uniprot.core.proteome.builder;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.proteome.RedundantProteome;

class RedundantProteomeBuilderTest {

    @Test
    void test() {
        String id = "UP000004340";
        RedundantProteome rproteome =
                RedundantProteomeBuilder.newInstance()
                        .proteomeId(new ProteomeIdBuilder(id).build())
                        .similarity(0.98f)
                        .build();

        assertEquals(id, rproteome.getId().getValue());
        assertEquals(0.98f, rproteome.getSimilarity().doubleValue(), Double.MIN_VALUE);
    }

    @Test
    void canAddIdFromString() {
        RedundantProteome proteome = new RedundantProteomeBuilder().proteomeId("id").build();
        assertNotNull(proteome.getId());
    }
}
