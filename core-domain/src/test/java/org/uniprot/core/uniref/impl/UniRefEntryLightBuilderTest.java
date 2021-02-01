package org.uniprot.core.uniref.impl;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.Sequence;
import org.uniprot.core.cv.go.GeneOntologyEntry;
import org.uniprot.core.cv.go.impl.GeneOntologyEntryBuilder;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniref.*;

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
    void canSetCommonTaxon() {
        Organism organism =
                new OrganismBuilder().taxonId(9606L).scientificName("scientific").build();
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().commonTaxon(organism).build();
        assertThat(entryLight.getCommonTaxon(), is(organism));
    }

    @Test
    void canSetSeedId() {
        String value = "seedId";
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().seedId(value).build();
        assertThat(entryLight.getSeedId(), is(value));
    }

    @Test
    void testRepresentativeMember() {
        String seq = "MVSWGRFICLVVVTMATLSLARPSFSLVED";
        Sequence sequence = new SequenceBuilder(seq).build();
        UniRefMemberIdType type = UniRefMemberIdType.UNIPARC;
        RepresentativeMember member =
                new RepresentativeMemberBuilder().memberIdType(type).sequence(sequence).build();
        UniRefEntryLight entry = new UniRefEntryLightBuilder().representativeMember(member).build();
        assertEquals(member, entry.getRepresentativeMember());
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
    void canSetOrganisms() {
        Organism organism =
                new OrganismBuilder().taxonId(9606L).scientificName("scientific").build();

        Organism otherOrganism =
                new OrganismBuilder().taxonId(9607L).scientificName("new scientific").build();

        LinkedHashSet<Organism> value = new LinkedHashSet<>(asList(organism, otherOrganism));
        UniRefEntryLight entryLight = new UniRefEntryLightBuilder().organismsSet(value).build();
        assertThat(entryLight.getOrganisms(), is(value));
    }

    @Test
    void canAddOrganisms() {
        Organism organism =
                new OrganismBuilder().taxonId(9606L).scientificName("scientific").build();

        Organism newOrganism =
                new OrganismBuilder().taxonId(9607L).scientificName("new scientific").build();

        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder()
                        .organismsSet(new LinkedHashSet<>(singletonList(organism)));

        entryLightBuilder.organismsAdd(newOrganism);

        assertThat(entryLightBuilder.build().getOrganisms(), contains(organism, newOrganism));
    }

    @Test
    void doNotAddDuplicatedOrganisms() {
        Organism organism =
                new OrganismBuilder().taxonId(9606L).scientificName("scientific").build();

        Organism duplicated =
                new OrganismBuilder().taxonId(9606L).scientificName("scientific").build();
        UniRefEntryLightBuilder entryLightBuilder =
                new UniRefEntryLightBuilder()
                        .organismsSet(new LinkedHashSet<>(singletonList(organism)));

        entryLightBuilder.organismsAdd(duplicated);
        LinkedHashSet<Organism> result = entryLightBuilder.build().getOrganisms();

        assertThat(result.size(), is(1));
        assertThat(result, contains(organism));
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
        Organism organism =
                new OrganismBuilder().taxonId(9606L).scientificName("scientific").build();

        Organism commonTaxon = new OrganismBuilder().taxonId(10116L).scientificName("Rat").build();

        RepresentativeMember representativeMember =
                new RepresentativeMemberBuilder()
                        .memberIdType(UniRefMemberIdType.UNIPROTKB)
                        .sequence(new SequenceBuilder("AAAAA").build())
                        .build();

        UniRefEntryLight entry =
                new UniRefEntryLightBuilder()
                        .id("UniRef50_P12345")
                        .name("Cluster name")
                        .membersAdd("P12345")
                        .organismsAdd(organism)
                        .memberIdTypesAdd(UniRefMemberIdType.UNIPARC)
                        .updated(LocalDate.now())
                        .commonTaxon(commonTaxon)
                        .entryType(UniRefType.UniRef50)
                        .memberCount(5)
                        .representativeMember(representativeMember)
                        .seedId("P12345")
                        .goTermsAdd(new GeneOntologyEntryBuilder().id("GoId").build())
                        .build();
        UniRefEntryLight fromEntry = UniRefEntryLightBuilder.from(entry).build();
        assertThat(entry, is(fromEntry));
        assertThat(entry.equals(fromEntry), is(true));
        assertThat(entry.hashCode(), is(fromEntry.hashCode()));
    }
}
