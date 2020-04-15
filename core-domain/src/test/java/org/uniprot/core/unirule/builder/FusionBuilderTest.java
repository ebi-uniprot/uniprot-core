package org.uniprot.core.unirule.builder;

import org.junit.jupiter.api.Test;
import org.uniprot.core.unirule.Fusion;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FusionBuilderTest {

    @Test
    void testCreateObjectUpdateNtersList(){
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
    void testCreateObjectUpdateCtersList(){
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

    public static Fusion createObject() {
        String random = UUID.randomUUID().toString();
        List<String> nters =
                IntStream.range(0, 3)
                        .mapToObj(i -> i + "nter-" + random)
                        .collect(Collectors.toList());
        List<String> cters =
                IntStream.range(0, 5)
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

    public static List<Fusion> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
