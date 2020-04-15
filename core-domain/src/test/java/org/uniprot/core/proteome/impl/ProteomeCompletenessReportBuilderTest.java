package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.proteome.BuscoReport;
import org.uniprot.core.proteome.CPDReport;
import org.uniprot.core.proteome.CPDStatus;
import org.uniprot.core.proteome.ProteomeCompletenessReport;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
class ProteomeCompletenessReportBuilderTest {

    @Test
    void testCPDReport() {
        CPDReport cpdReport = new CPDReportBuilder().confidence(10).build();
        ProteomeCompletenessReport report = new ProteomeCompletenessReportBuilder().cpdReport(cpdReport).build();
        assertEquals(cpdReport, report.getCPDReport());
    }

    @Test
    void testBuscoReport() {
        BuscoReport buscoReport = new BuscoReportBuilder().complete(10).build();
        ProteomeCompletenessReport report = new ProteomeCompletenessReportBuilder().buscoReport(buscoReport).build();
        assertEquals(buscoReport, report.getBuscoReport());
    }

    @Test
    void testFrom() {
        ProteomeCompletenessReport report= ObjectsForTests.createProteomeCompletenessReport();
        ProteomeCompletenessReport newReport = ProteomeCompletenessReportBuilder.from(report).build();
        assertEquals(report, newReport);
    }
}