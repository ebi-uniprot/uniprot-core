package org.uniprot.utils.uniprotkb;

import static org.junit.jupiter.api.Assertions.*;

import java.util.EnumSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.uniprot.core.cv.go.GoEvidenceType;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode;
import org.uniprot.core.uniprotkb.evidence.EvidenceCode.Category;

/**
 * @author jluo
 * @date: 28 Jun 2021
 */
class GoEvidenceUtilsTest {

    @Test
    void testGoEvidenceType2EvidenceCodeIDA() {
        GoEvidenceType type = GoEvidenceType.IDA;
        Optional<EvidenceCode> opCode = GoEvidenceUtils.goEvidenceType2EvidenceCode(type);
        assertTrue(opCode.isPresent());
        assertEquals(EvidenceCode.ECO_0000314, opCode.get());
        assertEquals(
                EnumSet.of(Category.EXPERIMENTAL, Category.MANUAL), opCode.get().getCategories());
    }

    @Test
    void testGoEvidenceType2EvidenceCodeTAS() {
        GoEvidenceType type = GoEvidenceType.TAS;
        Optional<EvidenceCode> opCode = GoEvidenceUtils.goEvidenceType2EvidenceCode(type);
        assertTrue(opCode.isPresent());
        assertEquals(EvidenceCode.ECO_0000304, opCode.get());
        assertEquals(EnumSet.of(Category.MANUAL), opCode.get().getCategories());
    }
}
