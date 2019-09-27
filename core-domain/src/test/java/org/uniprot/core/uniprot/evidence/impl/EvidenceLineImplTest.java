package org.uniprot.core.uniprot.evidence.impl;

import java.time.LocalDate;
import java.time.Month;

import org.junit.jupiter.api.Test;

class EvidenceLineImplTest {

    @Test
    void testEvidenceLineImpl() {
        LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
        String curator = "som curator";
        String evidence = "ECO:0000269|PubMed:22481068";
    }
}
