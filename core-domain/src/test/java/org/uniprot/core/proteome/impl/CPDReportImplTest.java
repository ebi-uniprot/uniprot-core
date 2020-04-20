package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.proteome.CPDReport;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
class CPDReportImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        CPDReport obj = new CPDReportImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        CPDReport report = ObjectsForTests.createCPDReport();
        CPDReport reportFrom = CPDReportBuilder.from(report).build();
        assertTrue(report.equals(reportFrom) && reportFrom.equals(report));
        assertEquals(report.hashCode(), reportFrom.hashCode());
    }
}
