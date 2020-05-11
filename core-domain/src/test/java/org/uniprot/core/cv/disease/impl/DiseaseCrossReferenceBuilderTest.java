package org.uniprot.core.cv.disease.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;
import org.uniprot.core.CrossReference;
import org.uniprot.core.cv.disease.DiseaseCrossReference;
import org.uniprot.core.impl.CrossReferenceBuilder;
import org.uniprot.core.uniprotkb.comment.DiseaseDatabase;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    public static CrossReference<DiseaseDatabase> createObject(int listSize) {
        CrossReferenceBuilder<DiseaseDatabase> builder = new CrossReferenceBuilder<>();
        String random = UUID.randomUUID().toString();
        String id = "id-" + random;
        builder.id(id).database(DiseaseDatabase.MIM);
        builder.propertiesAdd("prop1" + random, "value1" + random);
        return builder.build();
    }

    public static CrossReference<DiseaseDatabase> createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<CrossReference<DiseaseDatabase>> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
