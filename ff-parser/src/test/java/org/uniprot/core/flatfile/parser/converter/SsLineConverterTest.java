package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineConverter;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineObject;
import org.uniprot.core.uniprot.InternalSection;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidenceLine;

class SsLineConverterTest {
    private SsLineConverter converter = new SsLineConverter();

    @Test
    void testEvidence() {
        SsLineObject obj = new SsLineObject();
        // **EV ECO:0000313; ProtImp; -; 07-NOV-2006.\n"+
        // **EV ECO:0000256; HAMAP-Rule:MF_01417; -; 01-OCT-2010.\n"
        SsLineObject.EvLine ev1 = new SsLineObject.EvLine();
        ev1.id = "ECO:0000313";
        ev1.db = "ProtImp";
        ev1.attr1 = "UP99";
        ev1.attr2 = "-";
        ev1.date = LocalDate.now();
        obj.ssEVLines.add(ev1);
        SsLineObject.EvLine ev2 = new SsLineObject.EvLine();
        ev2.id = "ECO:0000256";
        ev2.db = "HAMAP-Rule";
        ev2.attr1 = "MF_01417";
        ev2.attr2 = "XAB";
        ev2.date = LocalDate.now();
        obj.ssEVLines.add(ev2);
        InternalSection is = converter.convert(obj);
        List<EvidenceLine> evidences = is.getEvidenceLines();
        assertEquals(2, evidences.size());
        EvidenceLine evidenceLine1 = evidences.get(0);
        EvidenceLine evidenceLine2 = evidences.get(1);
        Evidence evidence1 = parseEvidenceLine(evidenceLine1.getEvidence());
        assertEquals("UP99", evidence1.getSource().getId());
        assertEquals("ECO:0000313|ProtImp:UP99", evidenceLine1.getEvidence());
        assertEquals("ProtImp", evidence1.getSource().getDatabaseType().getName());
        assertEquals("ECO:0000313", evidence1.getEvidenceCode().getCode());
        assertEquals(ev1.date, evidenceLine1.getCreateDate());
        assertEquals(ev1.attr2, evidenceLine1.getCurator());

        Evidence evidence2 = parseEvidenceLine(evidenceLine2.getEvidence());
        assertEquals("MF_01417", evidence2.getSource().getId());
        assertEquals("ECO:0000256|HAMAP-Rule:MF_01417", evidenceLine2.getEvidence());
        assertEquals("HAMAP-Rule", evidence2.getSource().getDatabaseType().getName());
        assertEquals("ECO:0000256", evidence2.getEvidenceCode().getCode());
        assertEquals(ev2.date, evidenceLine2.getCreateDate());
        assertEquals(ev2.attr2, evidenceLine2.getCurator());
    }
}
