package org.uniprot.core.uniref.impl;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.uniprot.core.uniref.impl.UniRefEntryLightImpl.NAME_PREFIX;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;

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
    void canSetRepresentativeId() {
        String value = "id";
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().representativeId(value).build();
        assertThat(entryLight.getRepresentativeId(), is(value));
    }

    @Test
    void canSetSeedId() {
        String value = "seedId";
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().seedId(value).build();
        assertThat(entryLight.getSeedId(), is(value));
    }

    @Test
    void settingNameSetsNameAndProteinName() {
        String value = NAME_PREFIX + "name";
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().name(value).build();
        assertThat(entryLight.getName(), is(value));
        assertThat(
                entryLight.getRepresentativeProteinName(),
                is(value.substring(NAME_PREFIX.length())));
    }

    @Test
    void settingSequenceSetsSequenceAndSequenceLength() {
        String value = "AAAA";
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().sequence(value).build();
        assertThat(entryLight.getSequence(), is(value));
        assertThat(entryLight.getSequenceLength(), is(value.length()));
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
        LinkedHashSet<Long> value = new LinkedHashSet<>(asList(1L, 2L));
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().organismIdsSet(value).build();
        assertThat(entryLight.getOrganismIds(), is(value));
    }

    @Test
    void canAddOrganismIds() {
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder().organismIdsSet(new LinkedHashSet<>(asList(1L, 2L)));

        entryLightBuilder.organismIdsAdd(3L);
        entryLightBuilder.organismIdsAdd(4L);
        entryLightBuilder.organismIdsAdd(5L);

        assertThat(entryLightBuilder.build().getOrganismIds(), contains(1L, 2L, 3L, 4L, 5L));
    }

    @Test
    void canSetOrganisms() {
        LinkedHashSet<String> value = new LinkedHashSet<>(asList("1", "2"));
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().organismsSet(value).build();
        assertThat(entryLight.getOrganisms(), is(value));
    }

    @Test
    void canAddOrganisms() {
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder().organismsSet(new LinkedHashSet<>(asList("1", "2")));

        entryLightBuilder.organismsAdd("3");
        entryLightBuilder.organismsAdd("4");
        entryLightBuilder.organismsAdd("5");

        assertThat(entryLightBuilder.build().getOrganisms(), contains("1", "2", "3", "4", "5"));
    }

    @Test
    void doNotAddDuplicatedOrganisms() {
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder()
                        .organismsSet(new LinkedHashSet<>(asList("1 (common)", "2")));

        entryLightBuilder.organismsAdd("1");
        entryLightBuilder.organismsAdd("2 (common)");
        entryLightBuilder.organismsAdd("3");

        assertThat(entryLightBuilder.build().getOrganisms(), contains("1 (common)", "2", "3"));
    }

    @Test
    void canSetMemberCount() {
        int value = 100000;
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().memberCount(value).build();
        assertThat(entryLight.getMemberCount(), is(value));
    }

    @Test
    void canSetOrganismCount() {
        int value = 100000;
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().organismCount(value).build();
        assertThat(entryLight.getOrganismCount(), is(value));
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

    @Test
    void canSetGoTerms() {
        GeneOntologyEntry entry1 = new GeneOntologyEntryBuilder().id("id1").build();
        GeneOntologyEntry entry2 = new GeneOntologyEntryBuilder().id("id2").build();
        List<GeneOntologyEntry> value = asList(entry1, entry2);
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().goTermsSet(value).build();
        assertThat(entryLight.getGoTerms(), is(value));
    }

    @Test
    void canAddGoTerms() {
        GeneOntologyEntry entry1 = new GeneOntologyEntryBuilder().id("id1").build();
        GeneOntologyEntry entry2 = new GeneOntologyEntryBuilder().id("id2").build();
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder().goTermsSet(asList(entry1, entry2));

        GeneOntologyEntry entry3 = new GeneOntologyEntryBuilder().id("id3").build();
        entryLightBuilder.goTermsAdd(entry3);

        assertThat(entryLightBuilder.build().getGoTerms(), contains(entry1, entry2, entry3));
    }

    @Test
    void testFrom() {
        UniRefEntryLight entry =
                new UniRefEntryLightBuilder()
                        .id("UniRef50_P12345")
                        .name("Cluster name")
                        .membersAdd("P12345")
                        .organismsAdd("Human")
                        .organismIdsAdd(9606L)
                        .memberIdTypesAdd(UniRefMemberIdType.UNIPARC)
                        .sequence("AAAAA")
                        .updated(LocalDate.now())
                        .commonTaxon("Rat")
                        .commonTaxonId(10116L)
                        .entryType(UniRefType.UniRef50)
                        .memberCount(5)
                        .representativeId("P12345")
                        .seedId("P12345")
                        .goTermsAdd(new GeneOntologyEntryBuilder().id("GoId").build())
                        .build();
        UniRefEntryLight fromEntry = UniRefEntryLightBuilder.from(entry).build();
        assertThat(entry, is(fromEntry));
        assertThat(entry.equals(fromEntry), is(true));
        assertThat(entry.hashCode(), is(fromEntry.hashCode()));
    }
}
