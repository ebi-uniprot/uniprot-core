package org.uniprot.core.uniprotkb.description.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.uniprotkb.description.EC;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilderTest;

// TODO: lgonzales
public class ECBuilderTest {

    public static EC createObject() {
        ECBuilder builder = new ECBuilder();
        String random = UUID.randomUUID().toString();
        String value = "value-" + random;
        builder.value(value);
        List<Evidence> evidences = EvidenceBuilderTest.createObjects(5);
        builder.evidencesSet(evidences);
        return builder.build();
    }

    public static List<EC> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
