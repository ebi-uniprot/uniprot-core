package org.uniprot.core.unirule.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.unirule.Fusion;

public class FusionBuilderTest {
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
