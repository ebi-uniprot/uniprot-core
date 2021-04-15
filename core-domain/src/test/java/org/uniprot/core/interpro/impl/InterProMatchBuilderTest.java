package org.uniprot.core.interpro.impl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.interpro.InterProAc;
import org.uniprot.core.interpro.InterProMatch;
import org.uniprot.core.interpro.MethodType;
import org.uniprot.core.interpro.Status;
import org.uniprot.core.uniprotkb.UniProtKBAccession;
import org.uniprot.core.uniprotkb.impl.UniProtKBAccessionBuilder;

/**
 * @author jluo
 * @date: 9 Apr 2021
 */
class InterProMatchBuilderTest {

    @Test
    void testFrom() {
        Integer fromPos = 23;
        Integer toPos = 29;
        Double score = 36.2;
        MethodType methodType = MethodType.PANTHER;
        InterProAc entryId1 = new InterProAcBuilder("IPR011992").build();
        InterProAc entryId2 = new InterProAcBuilder("IPR011994").build();
        List<InterProAc> interProAcs = List.of(entryId1, entryId2);
        String methodName = "method name";
        Status status = Status.FALSE_POSITIVE;
        String methodAccession = "method acc";
        UniProtKBAccession uniprotKBAccession = new UniProtKBAccessionBuilder("P12345").build();
        InterProMatch obj =
                new InterProMatchBuilder()
                        .fromPos(fromPos)
                        .toPos(toPos)
                        .score(score)
                        .methodType(methodType)
                        .interProAcsSet(interProAcs)
                        .methodName(methodName)
                        .status(status)
                        .methodAccession(methodAccession)
                        .uniprotKBAccession(uniprotKBAccession)
                        .build();

        InterProMatch objFrom = InterProMatchBuilder.from(obj).build();
        assertEquals(obj, objFrom);
    }

    @Test
    void testFromPos() {
        Integer fromPos = 23;
        InterProMatch obj = new InterProMatchBuilder().fromPos(fromPos).build();
        assertEquals(fromPos, obj.getFromPos());
        assertNull(obj.getMethodAccession());
        assertNull(obj.getMethodName());
        assertNull(obj.getMethodType());
        assertNull(obj.getScore());
        assertNull(obj.getStatus());
        assertNull(obj.getToPos());
        assertNull(obj.getUniProtKBAccession());

        assertTrue(obj.getInterProAcs().isEmpty());
    }

    @Test
    void testToPos() {
        Integer toPos = 23;
        InterProMatch obj = new InterProMatchBuilder().toPos(toPos).build();
        assertEquals(toPos, obj.getToPos());
        assertNull(obj.getMethodAccession());
        assertNull(obj.getMethodName());
        assertNull(obj.getMethodType());
        assertNull(obj.getScore());
        assertNull(obj.getStatus());
        assertNull(obj.getFromPos());
        assertNull(obj.getUniProtKBAccession());
        assertTrue(obj.getInterProAcs().isEmpty());
    }

    @Test
    void testScore() {
        Double score = 36.2;
        InterProMatch obj = new InterProMatchBuilder().score(score).build();
        assertEquals(score, obj.getScore());
        assertNull(obj.getMethodAccession());
        assertNull(obj.getMethodName());
        assertNull(obj.getMethodType());
        assertNull(obj.getToPos());
        assertNull(obj.getStatus());
        assertNull(obj.getFromPos());
        assertNull(obj.getUniProtKBAccession());

        assertTrue(obj.getInterProAcs().isEmpty());
    }

    @Test
    void testMethodType() {
        MethodType methodType = MethodType.PANTHER;
        InterProMatch obj = new InterProMatchBuilder().methodType(methodType).build();
        assertEquals(methodType, obj.getMethodType());
        assertNull(obj.getMethodAccession());
        assertNull(obj.getMethodName());
        assertNull(obj.getScore());
        assertNull(obj.getToPos());
        assertNull(obj.getStatus());
        assertNull(obj.getFromPos());
        assertNull(obj.getUniProtKBAccession());

        assertTrue(obj.getInterProAcs().isEmpty());
    }

    @Test
    void testInterProAcsSet() {
        InterProAc entryId1 = new InterProAcBuilder("IPR011992").build();
        InterProAc entryId2 = new InterProAcBuilder("IPR011994").build();
        List<InterProAc> interProAcs = List.of(entryId1, entryId2);
        InterProMatch obj = new InterProMatchBuilder().interProAcsSet(interProAcs).build();
        assertNull(obj.getMethodName());
        assertNull(obj.getMethodAccession());
        assertNull(obj.getMethodType());
        assertNull(obj.getScore());
        assertNull(obj.getToPos());
        assertNull(obj.getStatus());
        assertNull(obj.getFromPos());
        assertNull(obj.getUniProtKBAccession());

        assertEquals(interProAcs, obj.getInterProAcs());
    }

    @Test
    void testInterProAcsAdd() {
        InterProAc entryId1 = new InterProAcBuilder("IPR011992").build();

        InterProMatch obj = new InterProMatchBuilder().interProAcsAdd(entryId1).build();
        assertNull(obj.getMethodName());
        assertNull(obj.getMethodAccession());
        assertNull(obj.getMethodType());
        assertNull(obj.getScore());
        assertNull(obj.getToPos());
        assertNull(obj.getStatus());
        assertNull(obj.getFromPos());
        assertNull(obj.getUniProtKBAccession());

        assertEquals(List.of(entryId1), obj.getInterProAcs());
    }

    @Test
    void testMethodName() {
        String methodName = "method name";
        InterProMatch obj = new InterProMatchBuilder().methodName(methodName).build();
        assertEquals(methodName, obj.getMethodName());
        assertNull(obj.getMethodAccession());
        assertNull(obj.getMethodType());
        assertNull(obj.getScore());
        assertNull(obj.getToPos());
        assertNull(obj.getStatus());
        assertNull(obj.getFromPos());
        assertNull(obj.getUniProtKBAccession());

        assertTrue(obj.getInterProAcs().isEmpty());
    }

    @Test
    void testStatus() {
        Status status = Status.FALSE_POSITIVE;
        InterProMatch obj = new InterProMatchBuilder().status(status).build();
        assertEquals(status, obj.getStatus());
        assertNull(obj.getMethodAccession());
        assertNull(obj.getMethodType());
        assertNull(obj.getScore());
        assertNull(obj.getToPos());
        assertNull(obj.getMethodName());
        assertNull(obj.getFromPos());
        assertNull(obj.getUniProtKBAccession());

        assertTrue(obj.getInterProAcs().isEmpty());
    }

    @Test
    void testMethodAccession() {
        String methodAccession = "method acc";
        InterProMatch obj = new InterProMatchBuilder().methodAccession(methodAccession).build();
        assertEquals(methodAccession, obj.getMethodAccession());
        assertNull(obj.getMethodName());
        assertNull(obj.getMethodType());
        assertNull(obj.getScore());
        assertNull(obj.getToPos());
        assertNull(obj.getStatus());
        assertNull(obj.getFromPos());
        assertNull(obj.getUniProtKBAccession());

        assertTrue(obj.getInterProAcs().isEmpty());
    }

    @Test
    void testUniprotKBAccession() {
        UniProtKBAccession uniprotKBAccession = new UniProtKBAccessionBuilder("P12345").build();
        InterProMatch obj =
                new InterProMatchBuilder().uniprotKBAccession(uniprotKBAccession).build();
        assertEquals(uniprotKBAccession, obj.getUniProtKBAccession());
        assertNull(obj.getMethodName());
        assertNull(obj.getMethodType());
        assertNull(obj.getScore());
        assertNull(obj.getToPos());
        assertNull(obj.getStatus());
        assertNull(obj.getFromPos());
        assertNull(obj.getMethodAccession());

        assertTrue(obj.getInterProAcs().isEmpty());
    }
}
