package org.uniprot.core.unirule.impl;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MultiValueBuilderTest {

    public static List<String> createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        return IntStream.range(0, listSize)
                .mapToObj(i -> i + "val-" + random)
                .collect(Collectors.toList());
    }

    public static List<String> createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<List<String>> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
