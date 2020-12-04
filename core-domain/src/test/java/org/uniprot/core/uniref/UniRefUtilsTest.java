package org.uniprot.core.uniref;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.taxonomy.Organism;
import org.uniprot.core.uniprotkb.taxonomy.impl.OrganismBuilder;

/**
 * @author lgonzales
 * @since 08/07/2020
 */
class UniRefUtilsTest {

    @Test
    void getsSwissProtIdType() {
        UniRefMemberIdType type = UniRefUtils.getUniProtKBIdType("FGFR2_HUMAN", "P21802");
        assertThat(type, is(UniRefMemberIdType.UNIPROTKB_SWISSPROT));
    }

    @Test
    void getsTrEMBLIdType() {
        UniRefMemberIdType type = UniRefUtils.getUniProtKBIdType("A0A510U5A0_9GAMM", "A0A510U5A0");
        assertThat(type, is(UniRefMemberIdType.UNIPROTKB_TREMBL));
    }

    @Test
    void addOrganismNewOrganism() {
        Organism organism = new OrganismBuilder()
                .taxonId(9606L)
                .scientificName("scientific")
                .build();

        Organism newOrganism = new OrganismBuilder()
                .taxonId(9607L)
                .scientificName("new scientific")
                .build();

        Set<Organism> target = new HashSet<>();
        target.add(organism);

        UniRefUtils.addOrganism(newOrganism, target);
        assertEquals(2, target.size());
        assertTrue(target.contains(organism));
        assertTrue(target.contains(newOrganism));
    }

    @Test
    void doNotAddOrganismDuplicated() {
        Organism organism = new OrganismBuilder()
                .taxonId(9606L)
                .scientificName("scientific")
                .build();
        Set<Organism> target = new HashSet<>();
        target.add(organism);
        UniRefUtils.addOrganism(organism, target);
        assertEquals(1, target.size());
        assertTrue(target.contains(organism));
    }

    @Test
    void ignoreOrganismWithCommonWhenAfter() {
        Organism organism = new OrganismBuilder()
                .taxonId(9606L)
                .scientificName("scientific")
                .build();

        Organism organismWithCommon = new OrganismBuilder()
                .taxonId(9606L)
                .scientificName("scientific")
                .commonName("common")
                .build();
        Set<Organism> target = new HashSet<>();
        target.add(organism);
        UniRefUtils.addOrganism(organismWithCommon, target);
        assertEquals(1, target.size());
        assertTrue(target.contains(organism));
        assertFalse(target.contains(organismWithCommon));
    }

    @Test
    void canGetOrganismScientificName() {
        String scientific = UniRefUtils.getOrganismScientificName("scientific (common)");
        assertNotNull(scientific);
        assertEquals("scientific", scientific);
    }

    @Test
    void canGetOrganismScientificNameWithoutCommon() {
        String organism = "scientific only";
        String scientific = UniRefUtils.getOrganismScientificName(organism);
        assertNotNull(scientific);
        assertEquals(organism, scientific);
    }

    @Test
    void canGetOrganismCommonName() {
        String commonName = UniRefUtils.getOrganismCommonName("scientific (common)");
        assertNotNull(commonName);
        assertEquals("common", commonName);
    }

    @Test
    void canGetOrganismCommonNameWithoutCommon() {
        String organism = "scientific only";
        String scientific = UniRefUtils.getOrganismCommonName(organism);
        assertNotNull(scientific);
        assertEquals("", scientific);
    }
}
