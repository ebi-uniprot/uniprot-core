package org.uniprot.core.uniref.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefType;

import java.time.LocalDate;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

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
    void canSetHasMemberUniParcIds() {
        boolean value = true;
        UniRefEntryLight entryLight =
                new UniRefEntryLightBuilder().hasMemberUniParcIds(value).build();
        assertThat(entryLight.hasMemberUniParcIDs(), is(value));
    }

    @Test
    void notSettingHasMemberUniParcIdsMeansFalse() {
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().build();
        assertThat(entryLight.hasMemberUniParcIDs(), is(false));
    }

    @Test
    void canSetOrganismIds() {
        List<Long> value = asList(1L, 2L);
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().organismIdsSet(value).build();
        assertThat(entryLight.getOrganismIds(), is(value));
    }

    @Test
    void canAddOrganismIds() {
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder().organismIdsSet(asList(1L, 2L));

        entryLightBuilder.organismIdsAdd(3L);

        assertThat(entryLightBuilder.build().getOrganismIds(), contains(1L, 2L, 3L));
    }

    @Test
    void canSetMemberCount() {
        int value = 100000;
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().memberCount(value).build();
        assertThat(entryLight.getMemberCount(), is(value));
    }
}
