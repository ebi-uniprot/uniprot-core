package org.uniprot.core.uniref.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;

/**
 * Created 29/06/2020
 *
 * @author Edd
 */
class UniRefEntryLightBuilderTest {
    @Test
    void canSetId() {
        String value = "id";
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().id(value).build();
        assertThat(entryLight.getId().getValue(), is(value));
    }

    @Test
    void canSetName() {
        String value = "name";
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().name(value).build();
        assertThat(entryLight.getName(), is(value));
    }

    @Test
    void canSetUpdated() {
        LocalDate value = LocalDate.now();
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().updated(value).build();
        assertThat(entryLight.getUpdated(), is(value));
    }

    @Test
    void canSetEntryType() {
        UniRefType value = UniRefType.UniRef50;
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().entryType(value).build();
        assertThat(entryLight.getEntryType(), is(value));
    }

    @Test
    void canSetCommonTaxonId() {
        long value = 1L;
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().commonTaxonId(value).build();
        assertThat(entryLight.getCommonTaxonId(), is(value));
    }

    @Test
    void canSetCommonTaxon() {
        String value = "taxon";
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().commonTaxon(value).build();
        assertThat(entryLight.getCommonTaxon(), is(value));
    }

    @Test
    void canSetRepresentativeSequence() {
        String value = "AAAA";
        UniRefEntryLight entryLight =
                new UniRefEntryLightBuilder().representativeSequence(value).build();
        assertThat(entryLight.getRepresentativeSequence(), is(value));
    }

    @Test
    void canSetMembers() {
        List<String> value = asList("id1", "id2");
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().membersSet(value).build();
        assertThat(entryLight.getMembers(), is(value));
    }

    @Test
    void canAddMembers() {
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder().membersSet(asList("id1", "id2"));

        entryLightBuilder.membersAdd("id3");

        assertThat(entryLightBuilder.build().getMembers(), contains("id1", "id2", "id3"));
    }

    @Test
    void canSetOrganismIds() {
        Set<Long> value = new HashSet<>(asList(1L, 2L));
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().organismIdsSet(value).build();
        assertThat(entryLight.getOrganismIds(), is(value));
    }

    @Test
    void canAddOrganismIds() {
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder().organismIdsSet(new HashSet<>(asList(1L, 2L)));

        entryLightBuilder.organismIdsAdd(3L);
        entryLightBuilder.organismIdsAdd(4L);
        entryLightBuilder.organismIdsAdd(5L);

        assertThat(entryLightBuilder.build().getOrganismIds(), contains(1L, 2L, 3L, 4L, 5L));
    }

    @Test
    void canSetOrganisms() {
        Set<String> value = new HashSet<>(asList("1", "2"));
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().organismsSet(value).build();
        assertThat(entryLight.getOrganisms(), is(value));
    }

    @Test
    void canAddOrganisms() {
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder().organismsSet(new HashSet<>(asList("1", "2")));

        entryLightBuilder.organismsAdd("3");
        entryLightBuilder.organismsAdd("4");
        entryLightBuilder.organismsAdd("5");

        assertThat(entryLightBuilder.build().getOrganisms(), contains("1", "2", "3", "4", "5"));
    }

    @Test
    void doNotAddDuplicatedOrganisms() {
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder().organismsSet(new HashSet<>(asList("1 (common)", "2")));

        entryLightBuilder.organismsAdd("1");
        entryLightBuilder.organismsAdd("2 (common)");
        entryLightBuilder.organismsAdd("3");

        assertThat(entryLightBuilder.build().getOrganisms(), containsInAnyOrder("1 (common)", "2 (common)", "3"));
    }

    @Test
    void canSetMemberCount() {
        int value = 100000;
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().memberCount(value).build();
        assertThat(entryLight.getMemberCount(), is(value));
    }

    @Test
    void canSetMemberIdTypes() {
        Set<UniRefMemberIdType> value =
                new HashSet<>(
                        asList(
                                UniRefMemberIdType.UNIPROTKB_SWISSPROT,
                                UniRefMemberIdType.UNIPROTKB_TREMBL));
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().memberIdTypesSet(value).build();
        assertThat(entryLight.getMemberIdTypes(), is(value));
    }

    @Test
    void canAddMemberIdTypes() {
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder()
                        .memberIdTypesSet(
                                new HashSet<>(
                                        asList(
                                                UniRefMemberIdType.UNIPROTKB_SWISSPROT,
                                                UniRefMemberIdType.UNIPROTKB_TREMBL)));

        entryLightBuilder.memberIdTypesAdd(UniRefMemberIdType.UNIPARC);
        entryLightBuilder.memberIdTypesAdd(UniRefMemberIdType.UNIPARC);

        assertThat(
                entryLightBuilder.build().getMemberIdTypes(),
                containsInAnyOrder(
                        UniRefMemberIdType.UNIPROTKB_SWISSPROT,
                        UniRefMemberIdType.UNIPROTKB_TREMBL,
                        UniRefMemberIdType.UNIPARC));
    }
}
