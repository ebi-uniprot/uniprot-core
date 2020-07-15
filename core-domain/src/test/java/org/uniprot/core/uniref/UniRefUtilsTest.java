package org.uniprot.core.uniref;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

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
        Set<String> target = new HashSet<>();
        target.add("scientific");
        UniRefUtils.addOrganism("scientific new", target);
        assertEquals(2, target.size());
        assertTrue(target.contains("scientific new"));
    }

    @Test
    void doNotAddOrganismDuplicated() {
        Set<String> target = new HashSet<>();
        target.add("scientific");
        UniRefUtils.addOrganism("scientific", target);
        assertEquals(1, target.size());
        assertTrue(target.contains("scientific"));
    }

    @Test
    void ignoreOrganismWithCommonWhenAfter() {
        Set<String> target = new HashSet<>();
        target.add("scientific");
        target.add("scientific other");
        UniRefUtils.addOrganism("scientific (common)", target);
        assertEquals(2, target.size());
        assertTrue(target.contains("scientific"));
        assertTrue(target.contains("scientific other"));
    }

    @Test
    void cleanOrganismWithBrackets() {
        String cleanedOrganism = UniRefUtils.getOrganismWithoutCommonName("scientific (common)");
        assertNotNull(cleanedOrganism);
        assertEquals("scientific", cleanedOrganism);
    }

    @Test
    void cleanOrganismWithoutBrackets() {
        String organism = "scientific only";
        String cleanedOrganism = UniRefUtils.getOrganismWithoutCommonName(organism);
        assertNotNull(cleanedOrganism);
        assertEquals(organism, cleanedOrganism);
    }
}
