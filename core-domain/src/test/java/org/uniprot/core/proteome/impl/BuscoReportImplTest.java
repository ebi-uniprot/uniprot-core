package org.uniprot.core.proteome.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.proteome.BuscoReport;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
class BuscoReportImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        BuscoReport obj = new BuscoReportImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        BuscoReport report = ObjectsForTests.createBuscoReport();
        BuscoReport reportFrom = BuscoReportBuilder.from(report).build();
        assertTrue(report.equals(reportFrom) && reportFrom.equals(report));
        assertEquals(report.hashCode(), reportFrom.hashCode());
    }

    @Test
    void testCalculateSummary() {
        BuscoReport report =
                new BuscoReportBuilder()
                        .complete(3775)
                        .completeSingle(1639)
                        .completeDuplicated(2136)
                        .fragmented(64)
                        .missing(111)
                        .total(3950)
                        .build();
        assertNotNull(report.calculateSummary());
        assertEquals("C:95.6%[S:41.5%,D:54.1%],F:1.6%,M:2.8%,n:3950", report.calculateSummary());
    }
}
