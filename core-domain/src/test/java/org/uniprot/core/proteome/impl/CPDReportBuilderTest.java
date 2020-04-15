package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.proteome.BuscoReport;
import org.uniprot.core.proteome.CPDReport;
import org.uniprot.core.proteome.CPDStatus;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
class CPDReportBuilderTest {

    @Test
    void testAverageCdss() {
        int averageCdss = 10;
        CPDReport report = new CPDReportBuilder().averageCdss(averageCdss).build();
        assertEquals(averageCdss, report.getAverageCdss());
    }

    @Test
    void testStdCdss() {
        double stdCdss = 10d;
        CPDReport report = new CPDReportBuilder().stdCdss(stdCdss).build();
        assertEquals(stdCdss, report.getStdCdss());
    }

    @Test
    void testConfidence() {
        int confidence = 10;
        CPDReport report = new CPDReportBuilder().confidence(confidence).build();
        assertEquals(confidence, report.getConfidence());
    }

    @Test
    void testProteomeCount() {
        int proteomeCount = 10;
        CPDReport report = new CPDReportBuilder().proteomeCount(proteomeCount).build();
        assertEquals(proteomeCount, report.getProteomeCount());
    }

    @Test
    void testStatus() {
        CPDStatus status = CPDStatus.STANDARD;
        CPDReport report = new CPDReportBuilder().status(status).build();
        assertEquals(status, report.getStatus());
    }

    @Test
    void testFrom() {
        CPDReport report= ObjectsForTests.createCPDReport();
        CPDReport newReport = CPDReportBuilder.from(report).build();
        assertEquals(report, newReport);
    }
}