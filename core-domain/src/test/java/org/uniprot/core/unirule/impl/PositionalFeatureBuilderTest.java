package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Range;
import org.uniprot.core.uniprotkb.feature.Ligand;
import org.uniprot.core.uniprotkb.feature.LigandPart;
import org.uniprot.core.uniprotkb.feature.impl.LigandBuilder;
import org.uniprot.core.uniprotkb.feature.impl.LigandPartBuilder;
import org.uniprot.core.unirule.PositionalFeature;

public class PositionalFeatureBuilderTest {

    @Test
    void testCreateUpdateObject() {
        PositionalFeature pf = createObject();
        assertNotNull(pf);
        PositionalFeatureBuilder builder = PositionalFeatureBuilder.from(pf);
        Range newPos = new Range(0, 1);
        builder.position(newPos);
        PositionalFeature newPf = builder.build();
        assertNotNull(newPf);
        assertEquals(newPos, newPf.getPosition());
    }

    public static PositionalFeature createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        String pattern = "pattern-" + random;
        String type = "type-" + random;
        String value = "value-" + random;
        int start = ThreadLocalRandom.current().nextInt();
        int end = ThreadLocalRandom.current().nextInt(start + 1, Integer.MAX_VALUE);
        Range position = new Range(start, end);
        boolean inGroup = true;

        Ligand ligand =
                new LigandBuilder()
                        .id("CHEBI:12345")
                        .label("label-" + random)
                        .name("name-" + random)
                        .note("note-" + random)
                        .build();
        LigandPart ligandPart =
                new LigandPartBuilder()
                        .id("CHEBI:54321")
                        .label("label-" + random)
                        .name("name-" + random)
                        .note("note-" + random)
                        .build();
        String description = "description-" + random;

        PositionalFeatureBuilder builder = new PositionalFeatureBuilder(position);
        builder.type(type)
                .pattern(pattern)
                .ligand(ligand)
                .ligandPart(ligandPart)
                .description(description);
        builder.inGroup(inGroup);
        PositionalFeature positionFeature = builder.build();
        assertNotNull(positionFeature);
        assertEquals(type, positionFeature.getType());
        assertEquals(pattern, positionFeature.getPattern());
        assertEquals(ligand, positionFeature.getLigand());
        assertEquals(ligandPart, positionFeature.getLigandPart());
        assertEquals(description, positionFeature.getDescription());
        assertEquals(position, positionFeature.getPosition());
        assertEquals(inGroup, positionFeature.isInGroup());
        return positionFeature;
    }

    public static PositionalFeature createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<PositionalFeature> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
