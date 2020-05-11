package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Fusion;

public class FusionBuilderTest {

    @Test
    void testCreateObjectUpdateNtersList() {
        Fusion fusion = createObject();
        String name = "extra1";
        FusionBuilder builder = FusionBuilder.from(fusion);
        // add another  value
        builder.ntersAdd(name);
        Fusion updatedFusion = builder.build();

        assertNotNull(updatedFusion);
        assertEquals(fusion.getCters(), updatedFusion.getCters());
        assertEquals(fusion.getNters().size() + 1, updatedFusion.getNters().size());
    }

    @Test
    void testCreateObjectUpdateCtersList() {
        Fusion fusion = createObject();
        String name = "extra2";
        FusionBuilder builder = FusionBuilder.from(fusion);
        // add another  value
        builder.ctersAdd(name);
        Fusion updatedFusion = builder.build();

        assertNotNull(updatedFusion);
        assertEquals(fusion.getNters(), updatedFusion.getNters());
        assertEquals(fusion.getCters().size() + 1, updatedFusion.getCters().size());
    }

    @Test
    void testCreateObjectWithOneNter() {
        FusionBuilder builder = new FusionBuilder();
        String nter = "sample nter";
        builder.ntersAdd(nter);
        Fusion fusion = builder.build();
        assertNotNull(fusion);
        assertEquals(Arrays.asList(nter), fusion.getNters());
    }

    @Test
    void testCreateObjectWithOneCter() {
        FusionBuilder builder = new FusionBuilder();
        String cter = "sample cter";
        builder.ctersAdd(cter);
        Fusion fusion = builder.build();
        assertNotNull(fusion);
        assertEquals(Arrays.asList(cter), fusion.getCters());
    }

    public static Fusion createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        List<String> nters =
                IntStream.range(0, listSize)
                        .mapToObj(i -> i + "nter-" + random)
                        .collect(Collectors.toList());
        List<String> cters =
                IntStream.range(0, listSize)
                        .mapToObj(i -> i + "cter-" + random)
                        .collect(Collectors.toList());
        FusionBuilder builder = new FusionBuilder();
        builder.ntersSet(nters);
        builder.ctersSet(cters);
        Fusion fusion = builder.build();
        assertNotNull(fusion);
        assertEquals(nters, fusion.getNters());
        assertEquals(cters, fusion.getCters());
        return fusion;
    }

    public static Fusion createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<Fusion> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
