package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.uniprot.core.interpro.Abstract;
import org.uniprot.core.interpro.InterProAc;
import org.uniprot.core.interpro.InterProEntry;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 * @author jluo
 * @date: 12 Apr 2021
 */
class InterProEntryBuilderTest {

    @Test
    void testBuild() {
        String id = "IPR011992";
        String name = "some name";
        String shortName = "some short name";
        Abstract entryAbstract = new AbstractBuilder("some abstract").build();
        InterProAc entryId = new InterProAcBuilder(id).build();

        UniProtKBAccession entryId1 = new UniProtKBAccessionBuilder("P12345").build();
        UniProtKBAccession entryId2 = new UniProtKBAccessionBuilder("P12346").build();
        Set<UniProtKBAccession> swissAccessions = Set.of(entryId1, entryId2);

        UniProtKBAccession entryId11 = new UniProtKBAccessionBuilder("Q12345").build();
        UniProtKBAccession entryId12 = new UniProtKBAccessionBuilder("Q12346").build();
        Set<UniProtKBAccession> tremblAccessions = Set.of(entryId11, entryId12);

        InterProEntry obj =
                new InterProEntryBuilder()
                        .interProAc(entryId)
                        .checked(true)
                        .name(name)
                        .shortName(shortName)
                        .entryAbstract(entryAbstract)
                        .swissProtAccessionsSet(swissAccessions)
                        .tremblAccessionsSet(tremblAccessions)
                        .build();
        assertEquals(entryId, obj.getInterProAc());
        assertTrue(obj.isChecked());
        assertEquals(name, obj.getName());
        assertEquals(shortName, obj.getShortName());
        assertEquals(entryAbstract, obj.getEntryAbstract());
        assertEquals(swissAccessions, obj.getSwissProtAccessions());
        assertEquals(tremblAccessions, obj.getTremblAccessions());
    }

    @Test
    void testFrom() {
        String id = "IPR011992";
        String name = "some name";
        String shortName = "some short name";
        Abstract entryAbstract = new AbstractBuilder("some abstract").build();
        InterProAc entryId = new InterProAcBuilder(id).build();

        UniProtKBAccession entryId1 = new UniProtKBAccessionBuilder("P12345").build();
        UniProtKBAccession entryId2 = new UniProtKBAccessionBuilder("P12346").build();
        Set<UniProtKBAccession> swissAccessions = Set.of(entryId1, entryId2);

        UniProtKBAccession entryId11 = new UniProtKBAccessionBuilder("Q12345").build();
        UniProtKBAccession entryId12 = new UniProtKBAccessionBuilder("Q12346").build();
        Set<UniProtKBAccession> tremblAccessions = Set.of(entryId11, entryId12);

        InterProEntry obj =
                new InterProEntryBuilder()
                        .interProAc(entryId)
                        .checked(true)
                        .name(name)
                        .shortName(shortName)
                        .entryAbstract(entryAbstract)
                        .swissProtAccessionsSet(swissAccessions)
                        .tremblAccessionsSet(tremblAccessions)
                        .build();

        InterProEntry obj2 = InterProEntryBuilder.from(obj).build();
        assertEquals(obj, obj2);
    }

    @Test
    void testInterProAc() {
        String id = "IPR011992";
        InterProAc entryId = new InterProAcBuilder(id).build();
        InterProEntry obj = new InterProEntryBuilder().interProAc(entryId).build();
        assertEquals(entryId, obj.getInterProAc());
        assertFalse(obj.isChecked());
        assertNull(obj.getEntryAbstract());
        assertNull(obj.getName());
        assertNull(obj.getShortName());
        assertTrue(obj.getSwissProtAccessions().isEmpty());
        assertTrue(obj.getTremblAccessions().isEmpty());
    }

    @Test
    void testChecked() {
        InterProEntry obj = new InterProEntryBuilder().checked(true).build();
        assertTrue(obj.isChecked());
        assertNull(obj.getInterProAc());
        assertNull(obj.getEntryAbstract());
        assertNull(obj.getName());
        assertNull(obj.getShortName());
        assertTrue(obj.getSwissProtAccessions().isEmpty());
        assertTrue(obj.getTremblAccessions().isEmpty());
    }

    @Test
    void testName() {
        String name = "some name";
        InterProEntry obj = new InterProEntryBuilder().name(name).build();
        assertEquals(name, obj.getName());
        assertFalse(obj.isChecked());
        assertNull(obj.getEntryAbstract());
        assertNull(obj.getInterProAc());
        assertNull(obj.getShortName());
        assertTrue(obj.getSwissProtAccessions().isEmpty());
        assertTrue(obj.getTremblAccessions().isEmpty());
    }

    @Test
    void testShortName() {
        String shortName = "some short name";
        InterProEntry obj = new InterProEntryBuilder().shortName(shortName).build();
        assertEquals(shortName, obj.getShortName());
        assertFalse(obj.isChecked());
        assertNull(obj.getEntryAbstract());
        assertNull(obj.getInterProAc());
        assertNull(obj.getName());
        assertTrue(obj.getSwissProtAccessions().isEmpty());
        assertTrue(obj.getTremblAccessions().isEmpty());
    }

    @Test
    void testEntryAbstract() {
        Abstract entryAbstract = new AbstractBuilder("some abstract").build();
        InterProEntry obj = new InterProEntryBuilder().entryAbstract(entryAbstract).build();
        assertEquals(entryAbstract, obj.getEntryAbstract());
        assertFalse(obj.isChecked());
        assertNull(obj.getShortName());
        assertNull(obj.getInterProAc());
        assertNull(obj.getName());
        assertTrue(obj.getSwissProtAccessions().isEmpty());
        assertTrue(obj.getTremblAccessions().isEmpty());
    }

    @Test
    void testSwissProtAccessionsSet() {
        UniProtKBAccession entryId1 = new UniProtKBAccessionBuilder("P12345").build();
        UniProtKBAccession entryId2 = new UniProtKBAccessionBuilder("P12346").build();
        Set<UniProtKBAccession> swissAccessions = Set.of(entryId1, entryId2);
        InterProEntry obj =
                new InterProEntryBuilder().swissProtAccessionsSet(swissAccessions).build();
        assertEquals(swissAccessions, obj.getSwissProtAccessions());
        assertFalse(obj.isChecked());
        assertNull(obj.getShortName());
        assertNull(obj.getInterProAc());
        assertNull(obj.getName());
        assertNull(obj.getEntryAbstract());
        assertTrue(obj.getTremblAccessions().isEmpty());
    }

    @Test
    void testSwissProtAccessionsAdd() {
        UniProtKBAccession entryId1 = new UniProtKBAccessionBuilder("P12345").build();

        InterProEntry obj = new InterProEntryBuilder().swissProtAccessionsAdd(entryId1).build();
        assertEquals(Set.of(entryId1), obj.getSwissProtAccessions());
        assertFalse(obj.isChecked());
        assertNull(obj.getShortName());
        assertNull(obj.getInterProAc());
        assertNull(obj.getName());
        assertNull(obj.getEntryAbstract());
        assertTrue(obj.getTremblAccessions().isEmpty());
    }

    @Test
    void testTremblAccessionsSet() {
        UniProtKBAccession entryId1 = new UniProtKBAccessionBuilder("P12345").build();
        UniProtKBAccession entryId2 = new UniProtKBAccessionBuilder("P12346").build();
        Set<UniProtKBAccession> tremblAccessions = Set.of(entryId1, entryId2);
        InterProEntry obj =
                new InterProEntryBuilder().tremblAccessionsSet(tremblAccessions).build();
        assertEquals(tremblAccessions, obj.getTremblAccessions());
        assertFalse(obj.isChecked());
        assertNull(obj.getShortName());
        assertNull(obj.getInterProAc());
        assertNull(obj.getName());
        assertNull(obj.getEntryAbstract());
        assertTrue(obj.getSwissProtAccessions().isEmpty());
    }

    @Test
    void testTremblAccessionsAdd() {
        UniProtKBAccession entryId1 = new UniProtKBAccessionBuilder("P12345").build();

        InterProEntry obj = new InterProEntryBuilder().tremblAccessionsAdd(entryId1).build();
        assertEquals(Set.of(entryId1), obj.getTremblAccessions());
        assertFalse(obj.isChecked());
        assertNull(obj.getShortName());
        assertNull(obj.getInterProAc());
        assertNull(obj.getName());
        assertNull(obj.getEntryAbstract());
        assertTrue(obj.getSwissProtAccessions().isEmpty());
    }
}
