package org.uniprot.core.proteome.impl;

import org.junit.jupiter.api.Test;
import org.uniprot.core.ObjectsForTests;
import org.uniprot.core.proteome.ProteomeCompletenessReport;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author lgonzales
 * @since 14/04/2020
 */
class ProteomeCompletenessReportImplTest {

    @Test
    void needDefaultConstructorForJsonDeserialization() {
        ProteomeCompletenessReport obj = new ProteomeCompletenessReportImpl();
        assertNotNull(obj);
    }

    @Test
    void builderFrom_constructorImp_shouldCreate_equalObject() {
        ProteomeCompletenessReport report= ObjectsForTests.createProteomeCompletenessReport();
        ProteomeCompletenessReport reportFrom = ProteomeCompletenessReportBuilder.from(report).build();
        assertTrue(report.equals(reportFrom) && reportFrom.equals(report));
        assertEquals(report.hashCode(), reportFrom.hashCode());
    }
}