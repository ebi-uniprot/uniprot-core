package org.uniprot.core.cv.disease.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.comment.DiseaseDatabase;

public class DiseaseCrossReferenceBuilderTest {
    @Test
    void canAddSingleProperty() {
        String property = "property";
        DiseaseCrossReference reference =
                new DiseaseCrossReferenceBuilder().propertiesAdd(property).build();
        assertFalse(reference.getProperties().isEmpty());
        assertEquals(1, reference.getProperties().size());
        assertEquals(property, reference.getProperties().get(0));
    }

    public static CrossReference<DiseaseDatabase> createObject() {
        CrossReferenceBuilder<DiseaseDatabase> builder = new CrossReferenceBuilder<>();
        String random = UUID.randomUUID().toString();
        String id = "id-" + random;
        builder.id(id).database(DiseaseDatabase.MIM);
        builder.propertiesAdd("prop1" + random, "value1" + random);
        return builder.build();
    }

    public static List<CrossReference<DiseaseDatabase>> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
