package org.uniprot.core.uniprotkb.description.impl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.uniprotkb.description.Name;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.impl.EvidenceBuilderTest;

// TODO: lgonzales
class NameBuilderTest {
    public static Name createObject() {
        String random = UUID.randomUUID().toString();
        String value = "Value - " + random;
        NameBuilder builder = new NameBuilder();
        builder.value(value);
        List<Evidence> evidences = EvidenceBuilderTest.createObjects(3);
        builder.evidencesSet(evidences);
        return builder.build();
    }

    public static List<Name> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
