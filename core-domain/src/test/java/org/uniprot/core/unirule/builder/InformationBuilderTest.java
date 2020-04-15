package org.uniprot.core.unirule.builder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.UniProtKBId;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.uniprotkb.impl.UniProtKBIdBuilderTest;
import org.uniprot.core.unirule.DataClassType;
import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.Information;

public class InformationBuilderTest {
    public static Information createObject() {
        String random = UUID.randomUUID().toString();
        String version = "version-" + random;
        String comment = "comment-" + random;
        String oldRuleNum = "oldRuleNum-" + random;
        String internal = "internal-" + random;
        List<UniProtKBId> uniProtIds = UniProtKBIdBuilderTest.createObjects(5);
        int rIndex = ThreadLocalRandom.current().nextInt(0, DataClassType.values().length);
        DataClassType dataClass = DataClassType.values()[rIndex];
        List<String> names =
                IntStream.range(0, 5)
                        .mapToObj(i -> i + "-name-" + random)
                        .collect(Collectors.toList());
        Fusion fusion = FusionBuilderTest.createObject();
        List<String> related =
                IntStream.range(0, 3)
                        .mapToObj(i -> i + "-related-" + random)
                        .collect(Collectors.toList());
        List<String> duplicates =
                IntStream.range(0, 5)
                        .mapToObj(i -> i + "-duplicates-" + random)
                        .collect(Collectors.toList());
        List<String> plasmaIds =
                IntStream.range(0, 7)
                        .mapToObj(i -> i + "-plasmaIds-" + random)
                        .collect(Collectors.toList());
        List<UniProtKBAccession> uniProtAccessions = UniProtKBAccessionBuilderTest.createObjects(3);

        InformationBuilder builder = new InformationBuilder();
        builder.version(version).comment(comment).oldRuleNum(oldRuleNum);
        builder.uniProtIds(uniProtIds).dataClass(dataClass).namesSet(names);
        builder.fusion(fusion).uniProtAccessionsSet(uniProtAccessions).relatedSet(related);
        builder.duplicatesSet(duplicates).plasmaIdsSet(plasmaIds).internal(internal);
        Information information = builder.build();
        // verify
        assertNotNull(builder);
        assertEquals(version, information.getVersion());
        assertEquals(comment, information.getComment());
        assertEquals(oldRuleNum, information.getOldRuleNum());
        assertEquals(uniProtIds, information.getUniProtIds());
        assertEquals(dataClass, information.getDataClass());
        assertEquals(names, information.getNames());
        assertEquals(fusion, information.getFusion());
        assertEquals(related, information.getRelated());
        assertEquals(uniProtAccessions, information.getUniProtAccessions());
        assertEquals(duplicates, information.getDuplicates());
        assertEquals(plasmaIds, information.getPlasmaIds());
        assertEquals(internal, information.getInternal());

        return information;
    }

    public static List<Information> createObjects(int count) {
        return IntStream.range(0, count).mapToObj(i -> createObject()).collect(Collectors.toList());
    }
}
