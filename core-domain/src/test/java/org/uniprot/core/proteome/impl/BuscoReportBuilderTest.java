package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.proteome.BuscoReport;
import org.uniprot.core.proteome.CanonicalProtein;
import org.uniprot.core.proteome.Protein;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
class BuscoReportBuilderTest {

    @Test
    void testComplete() {
        int complete = 10;
        BuscoReport report = new BuscoReportBuilder().complete(complete).build();
        assertEquals(complete, report.getComplete());
    }

    @Test
    void testCompleteDuplicated() {
        int completeDuplicated = 11;
        BuscoReport report = new BuscoReportBuilder().completeDuplicated(completeDuplicated).build();
        assertEquals(completeDuplicated, report.getCompleteDuplicated());
    }

    @Test
    void testCompleteSingle() {
        int completeSingle = 12;
        BuscoReport report = new BuscoReportBuilder().completeSingle(completeSingle).build();
        assertEquals(completeSingle, report.getCompleteSingle());
    }

    @Test
    void testFragmented() {
        int fragmented = 13;
        BuscoReport report = new BuscoReportBuilder().fragmented(fragmented).build();
        assertEquals(fragmented, report.getFragmented());
    }

    @Test
    void testMissing() {
        int missing = 14;
        BuscoReport report = new BuscoReportBuilder().missing(missing).build();
        assertEquals(missing, report.getMissing());
    }

    @Test
    void testTotal() {
        int total = 15;
        BuscoReport report = new BuscoReportBuilder().total(total).build();
        assertEquals(total, report.getTotal());
    }

    @Test
    void testLineageDb() {
        String lineageDb = "value";
        BuscoReport report = new BuscoReportBuilder().lineageDb(lineageDb).build();
        assertEquals(lineageDb, report.getLineageDb());
    }

    @Test
    void testFrom() {
        BuscoReport report= ObjectsForTests.createBuscoReport();
        BuscoReport newReport = BuscoReportBuilder.from(report).build();
        assertEquals(report, newReport);
    }
}