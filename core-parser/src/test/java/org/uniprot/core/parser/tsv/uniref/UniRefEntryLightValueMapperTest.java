package org.uniprot.core.parser.tsv.uniref;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.parser.tsv.uniref.AbstractUniRefEntryMapper.DELIMITER;
import static org.uniprot.core.uniref.UniRefMemberIdType.*;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;
import org.uniprot.core.uniref.*;
import org.uniprot.core.uniref.impl.RepresentativeMemberBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryIdBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryLightBuilder;

/**
 * Created 07/07/2020
 *
 * @author Edd
 */
class UniRefEntryLightValueMapperTest {

    private UniRefEntryLightValueMapper mapper;
    private static final String SEQUENCE = "GGGGGGGGGGGGGGGG";
    private static final int MEMBER_COUNT = 5;
    private static final UniRefEntryLight ENTRY = createEntry();;

    @BeforeEach
    void setUp() {
        mapper = new UniRefEntryLightValueMapper();
    }

    @Test
    void mappedFieldsAreCorrect() {
        Map<String, String> entryMap =
                mapper.mapEntity(ENTRY, AbstractUniRefEntryMapper.UNIREF_FIELDS);

        assertEquals("UniRef50_P03923", entryMap.get("id"));
        assertEquals("Cluster: AMP-binding enzyme family protein", entryMap.get("name"));
        assertEquals("organism 1", entryMap.get("common_taxon"));
        assertEquals("1", entryMap.get("common_taxonid"));
        assertEquals(Integer.toString(MEMBER_COUNT), entryMap.get("count"));
        assertEquals("2018-06-21", entryMap.get("created"));
        assertEquals(Integer.toString(SEQUENCE.length()), entryMap.get("length"));
        assertEquals(SEQUENCE, entryMap.get("sequence"));
        assertEquals("1.0", entryMap.get("identity"));
        assertEquals(
                UNIPROTKB_SWISSPROT.getName() + DELIMITER + UNIPARC.getName(),
                entryMap.get("types"));
    }

    @Test
    void testGetOrganism() {
        String organsms = mapper.getOrganisms(ENTRY);
        assertEquals("organism 1; organism 2 (common)", organsms);
    }

    @Test
    void testGetOrganismIDs() {
        String organsmTaxID = mapper.getOrganismTaxId(ENTRY);
        assertEquals("1; 2", organsmTaxID);
    }

    @Test
    void testGetMembers() {
        String members = mapper.getMembers(ENTRY);
        assertEquals("P1; P2; P3; P4; P5", members);
    }

    @Test
    void expectOnlySwissProtType() {
        Set<UniRefMemberIdType> typeSet =
                Stream.of(UNIPROTKB_SWISSPROT).collect(Collectors.toSet());
        String typeString = mapper.getTypes(typeSet);
        assertThat(typeString, is(UNIPROTKB_SWISSPROT.getName()));
    }

    @Test
    void expectOnlyTrEMBLType() {
        Set<UniRefMemberIdType> typeSet = Stream.of(UNIPROTKB_TREMBL).collect(Collectors.toSet());
        String typeString = mapper.getTypes(typeSet);
        assertThat(typeString, is(UNIPROTKB_TREMBL.getName()));
    }

    @Test
    void expectBothSwissProtAndTrEMBLType() {
        Set<UniRefMemberIdType> typeSet =
                Stream.of(UNIPROTKB_SWISSPROT, UNIPROTKB_TREMBL).collect(Collectors.toSet());
        String typeString = mapper.getTypes(typeSet);
        assertThat(
                typeString,
                is(UNIPROTKB_SWISSPROT.getName() + DELIMITER + UNIPROTKB_TREMBL.getName()));
    }

    @Test
    void expectSwissProtAndUniParcType() {
        Set<UniRefMemberIdType> typeSet =
                Stream.of(UNIPROTKB_SWISSPROT, UNIPARC).collect(Collectors.toSet());
        String typeString = mapper.getTypes(typeSet);
        assertThat(typeString, is(UNIPROTKB_SWISSPROT.getName() + DELIMITER + UNIPARC.getName()));
    }

    @Test
    void expectTrEMBLAndUniParcType() {
        Set<UniRefMemberIdType> typeSet =
                Stream.of(UNIPROTKB_TREMBL, UNIPARC).collect(Collectors.toSet());
        String typeString = mapper.getTypes(typeSet);
        assertThat(typeString, is(UNIPROTKB_TREMBL.getName() + DELIMITER + UNIPARC.getName()));
    }

    @Test
    void expectSwissProtTrEMBLAndUniParcType() {
        Set<UniRefMemberIdType> typeSet =
                Stream.of(UNIPROTKB_SWISSPROT, UNIPROTKB_TREMBL, UNIPARC)
                        .collect(Collectors.toSet());
        String typeString = mapper.getTypes(typeSet);
        assertThat(
                typeString,
                is(
                        UNIPROTKB_SWISSPROT.getName()
                                + DELIMITER
                                + UNIPROTKB_TREMBL.getName()
                                + DELIMITER
                                + UNIPARC.getName()));
    }

    @Test
    void expectUniParcType() {
        Set<UniRefMemberIdType> typeSet = Stream.of(UNIPARC).collect(Collectors.toSet());
        String typeString = mapper.getTypes(typeSet);
        assertThat(typeString, is(UNIPARC.getName()));
    }

    private static UniRefEntryLight createEntry() {

        String id = "UniRef50_P03923";
        UniRefType type = UniRefType.UniRef100;
        String name = "Cluster: AMP-binding enzyme family protein";
        UniRefEntryId entryId = new UniRefEntryIdBuilder(id).build();
        LocalDate created = LocalDate.of(2018, 6, 21);

        Organism organism = new OrganismBuilder().taxonId(1L).scientificName("organism 1").build();

        Organism organismWithCommon =
                new OrganismBuilder()
                        .taxonId(2L)
                        .scientificName("organism 2")
                        .commonName("common")
                        .build();

        RepresentativeMember representativeMember = new RepresentativeMemberBuilder()
                .sequence(new SequenceBuilder(SEQUENCE).build())
                .build();
        return new UniRefEntryLightBuilder()
                .id(entryId)
                .updated(created)
                .entryType(type)
                .commonTaxon(organism)
                .name(name)
                .organismsAdd(organism)
                .organismsAdd(organismWithCommon)
                .representativeMember(representativeMember)
                .memberCount(MEMBER_COUNT)
                .membersSet(asList("P1", "P2", "P3", "P4", "P5"))
                .memberIdTypesAdd(UNIPROTKB_SWISSPROT)
                .memberIdTypesAdd(UniRefMemberIdType.UNIPARC)
                .build();
    }
}
