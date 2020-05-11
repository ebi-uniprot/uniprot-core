package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineConverter;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineObject;
import org.uniprot.core.uniprotkb.InternalSection;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.uniprotkb.evidence.EvidenceLine;

class SsLineConverterTest {
    private SsLineConverter converter = new SsLineConverter();

    @Test
    void testEvidence() {
        SsLineObject obj = new SsLineObject();
        // **EV ECO:0000313; ProtImp; -; 07-NOV-2006.\n"+
        // **EV ECO:0000256; HAMAP-Rule:MF_01417; -; 01-OCT-2010.\n"
        SsLineObject.EvLine ev1 = new SsLineObject.EvLine();
        ev1.setId("ECO:0000313");
        ev1.setDb("ProtImp");
        ev1.setAttr1("UP99");
        ev1.setAttr2("-");
        ev1.setDate(LocalDate.now());
        obj.getSsEVLines().add(ev1);
        SsLineObject.EvLine ev2 = new SsLineObject.EvLine();
        ev2.setId("ECO:0000256");
        ev2.setDb("HAMAP-Rule");
        ev2.setAttr1("MF_01417");
        ev2.setAttr2("XAB");
        ev2.setDate(LocalDate.now());
        obj.getSsEVLines().add(ev2);
        InternalSection is = converter.convert(obj);
        List<EvidenceLine> evidences = is.getEvidenceLines();
        assertEquals(2, evidences.size());
        EvidenceLine evidenceLine1 = evidences.get(0);
        EvidenceLine evidenceLine2 = evidences.get(1);
        Evidence evidence1 = parseEvidenceLine(evidenceLine1.getEvidence());
        assertEquals("UP99", evidence1.getEvidenceCrossReference().getId());
        assertEquals("ECO:0000313|ProtImp:UP99", evidenceLine1.getEvidence());
        assertEquals("ProtImp", evidence1.getEvidenceCrossReference().getDatabase().getName());
        assertEquals("ECO:0000313", evidence1.getEvidenceCode().getCode());
        assertEquals(ev1.getDate(), evidenceLine1.getCreateDate());
        assertEquals(ev1.getAttr2(), evidenceLine1.getCurator());

        Evidence evidence2 = parseEvidenceLine(evidenceLine2.getEvidence());
        assertEquals("MF_01417", evidence2.getEvidenceCrossReference().getId());
        assertEquals("ECO:0000256|HAMAP-Rule:MF_01417", evidenceLine2.getEvidence());
        assertEquals("HAMAP-Rule", evidence2.getEvidenceCrossReference().getDatabase().getName());
        assertEquals("ECO:0000256", evidence2.getEvidenceCode().getCode());
        assertEquals(ev2.getDate(), evidenceLine2.getCreateDate());
        assertEquals(ev2.getAttr2(), evidenceLine2.getCurator());
    }
}
