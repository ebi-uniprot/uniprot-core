package org.uniprot.core.uniprotkb.description.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.evidence.Evidence;

// TODO: lgonzales
public class ECBuilderTest {

    public static EC createObject(int listSize) {
        ECBuilder builder = new ECBuilder();
        String random = UUID.randomUUID().toString();
        String value = "value-" + random;
        builder.value(value);
        List<Evidence> evidences = new ArrayList<>();
        builder.evidencesSet(evidences);
        return builder.build();
    }

    public static EC createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<EC> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
