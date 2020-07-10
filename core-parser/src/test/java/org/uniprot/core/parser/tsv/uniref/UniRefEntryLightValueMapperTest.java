package org.uniprot.core.parser.tsv.uniref;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.uniprot.core.uniref.UniRefEntryId;
import org.uniprot.core.uniref.UniRefEntryLight;
import org.uniprot.core.uniref.UniRefMemberIdType;
import org.uniprot.core.uniref.UniRefType;
import org.uniprot.core.uniref.impl.UniRefEntryIdBuilder;
import org.uniprot.core.uniref.impl.UniRefEntryLightBuilder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.parser.tsv.uniref.AbstractUniRefEntryMapper.DELIMITER;
import static org.uniprot.core.uniref.UniRefMemberIdType.*;

/**
 * Created 07/07/2020
 *
 * @author Edd
 */
class UniRefEntryLightValueMapperTest {

    private UniRefEntryLightValueMapper mapper;
    private static final String SEQUENCE = "GGGGGGGGGGGGGGGG";
    private static final int MEMBER_COUNT = 5;
    private static final UniRefEntryLight ENTRY = createEntry();
    ;

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
        assertEquals("Homo", entryMap.get("common_taxon"));
        assertEquals("9605", entryMap.get("common_taxonid"));
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
        assertEquals("organism 1; organism 2", organsms);
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

        return new UniRefEntryLightBuilder()
                .id(entryId)
                .updated(created)
                .entryType(type)
                .commonTaxonId(9605L)
                .commonTaxon("Homo")
                .name(name)
                .organismsSet(new HashSet<>(asList("organism 1", "organism 2")))
                .organismIdsSet(new HashSet<>(asList(1L, 2L)))
                .sequence(SEQUENCE)
                .memberCount(MEMBER_COUNT)
                .membersSet(asList("P1", "P2", "P3", "P4", "P5"))
                .memberIdTypesAdd(UNIPROTKB_SWISSPROT)
                .memberIdTypesAdd(UniRefMemberIdType.UNIPARC)
                .build();
    }
}
