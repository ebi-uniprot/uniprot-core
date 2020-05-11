package org.uniprot.core.unirule.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilderTest;
import org.uniprot.core.unirule.DataClassType;
import org.uniprot.core.unirule.Fusion;
import org.uniprot.core.unirule.Information;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class InformationBuilderTest {

    @Test
    void testCreateObjectUpdateUniProtIdsList() {
        Information information = createObject();
        // add couple of uniProtIds
        InformationBuilder builder = InformationBuilder.from(information);
        String up1 = "upkbid1";
        String up2 = "upkibid2";
        builder.uniProtIdsAdd(up1).uniProtIdsAdd(up2);
        Information updatedInformation = builder.build();
        assertNotNull(updatedInformation);
        assertEquals(
                information.getUniProtIds().size() + 2, updatedInformation.getUniProtIds().size());
    }

    @Test
    void testCreateObjectUpdateUniProtAccessionsList() {
        Information information = createObject();
        // add couple of accession
        InformationBuilder builder = InformationBuilder.from(information);
        UniProtKBAccession acc1 = UniProtKBAccessionBuilderTest.createObject();
        UniProtKBAccession acc2 = UniProtKBAccessionBuilderTest.createObject();
        builder.uniProtAccessionsAdd(acc1).uniProtAccessionsAdd(acc2);
        Information updatedInformation = builder.build();
        assertNotNull(updatedInformation);
        assertEquals(
                information.getUniProtAccessions().size() + 2,
                updatedInformation.getUniProtAccessions().size());
    }

    @Test
    void testCreateObjectUpdateRelatedList() {
        Information information = createObject();
        // add a related
        InformationBuilder builder = InformationBuilder.from(information);
        String rel1 = "new related";
        builder.relatedAdd(rel1);
        Information updatedInformation = builder.build();
        assertNotNull(updatedInformation);
        assertEquals(information.getRelated().size() + 1, updatedInformation.getRelated().size());
    }

    @Test
    void testCreateObjectUpdateDuplicatesList() {
        Information information = createObject();
        // add in duplicates list
        InformationBuilder builder = InformationBuilder.from(information);
        String dup = "new duplicate";
        builder.duplicatesAdd(dup);
        Information updatedInformation = builder.build();
        assertNotNull(updatedInformation);
        assertEquals(
                information.getDuplicates().size() + 1, updatedInformation.getDuplicates().size());
    }

    @Test
    void testCreateObjectUpdatePlasmaIdsList() {
        Information information = createObject();
        // add couple of uniProtIds
        InformationBuilder builder = InformationBuilder.from(information);
        String p1 = "plasma1";
        String p2 = "plasma2";
        builder.plasmaIdsAdd(p1).plasmaIdsAdd(p2);
        Information updatedInformation = builder.build();
        assertNotNull(updatedInformation);
        assertEquals(
                information.getPlasmaIds().size() + 2, updatedInformation.getPlasmaIds().size());
    }

    @Test
    void testAddOneUniProtKBId() {
        InformationBuilder builder = new InformationBuilder("version");
        String uniProtKBId = "upkbid1";
        builder.uniProtIdsAdd(uniProtKBId);
        Information information = builder.build();
        assertNotNull(information);
        assertEquals(Arrays.asList(uniProtKBId), information.getUniProtIds());
    }

    @Test
    void testAddOneName() {
        InformationBuilder builder = new InformationBuilder("version");
        String name = "sample name";
        builder.namesAdd(name);
        Information information = builder.build();
        assertNotNull(information);
        assertEquals(Arrays.asList(name), information.getNames());
    }

    @Test
    void testAddOneUniProtKBAccession() {
        InformationBuilder builder = new InformationBuilder("version");
        UniProtKBAccession uniProtKBAccession = UniProtKBAccessionBuilderTest.createObject();
        builder.uniProtAccessionsAdd(uniProtKBAccession);
        Information information = builder.build();
        assertNotNull(information);
        assertEquals(Arrays.asList(uniProtKBAccession), information.getUniProtAccessions());
    }

    @Test
    void testAddOneRelated() {
        InformationBuilder builder = new InformationBuilder("version");
        String related = "sample related";
        builder.relatedAdd(related);
        Information information = builder.build();
        assertNotNull(information);
        assertEquals(Arrays.asList(related), information.getRelated());
    }

    @Test
    void testAddOneDuplicates() {
        InformationBuilder builder = new InformationBuilder("version");
        String duplicateItem = "sample dup item";
        builder.duplicatesAdd(duplicateItem);
        Information information = builder.build();
        assertNotNull(information);
        assertEquals(Arrays.asList(duplicateItem), information.getDuplicates());
    }

    @Test
    void testAddOnePlasmaIds() {
        InformationBuilder builder = new InformationBuilder("version");
        String plasmaId = "sample pId";
        builder.plasmaIdsAdd(plasmaId);
        Information information = builder.build();
        assertNotNull(information);
        assertEquals(Arrays.asList(plasmaId), information.getPlasmaIds());
    }

    public static Information createObject(int listSize) {
        String random = UUID.randomUUID().toString();
        String version = "version-" + random;
        String comment = "comment-" + random;
        String oldRuleNum = "oldRuleNum-" + random;
        String internal = "internal-" + random;
        List<String> uniProtIds =
                IntStream.range(0, listSize)
                        .mapToObj(i -> i + "upi" + random)
                        .collect(Collectors.toList());
        int rIndex = ThreadLocalRandom.current().nextInt(0, DataClassType.values().length);
        DataClassType dataClass = DataClassType.values()[rIndex];
        List<String> names =
                IntStream.range(0, listSize)
                        .mapToObj(i -> i + "-name-" + random)
                        .collect(Collectors.toList());
        Fusion fusion = FusionBuilderTest.createObject(listSize);
        List<String> related =
                IntStream.range(0, listSize)
                        .mapToObj(i -> i + "-related-" + random)
                        .collect(Collectors.toList());
        List<String> duplicates =
                IntStream.range(0, listSize)
                        .mapToObj(i -> i + "-duplicates-" + random)
                        .collect(Collectors.toList());
        List<String> plasmaIds =
                IntStream.range(0, listSize)
                        .mapToObj(i -> i + "-plasmaIds-" + random)
                        .collect(Collectors.toList());
        List<UniProtKBAccession> uniProtAccessions =
                UniProtKBAccessionBuilderTest.createObjects(listSize);

        InformationBuilder builder = new InformationBuilder(version);
        builder.comment(comment).oldRuleNum(oldRuleNum);
        builder.uniProtIdsSet(uniProtIds).dataClass(dataClass).namesSet(names);
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

    public static Information createObject() {
        int listSize = ThreadLocalRandom.current().nextInt(1, 5);
        return createObject(listSize);
    }

    public static List<Information> createObjects(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createObject(count))
                .collect(Collectors.toList());
    }
}
