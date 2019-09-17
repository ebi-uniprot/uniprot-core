package org.uniprot.core.flatfile.parser.converter;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineConverter;
import org.uniprot.core.flatfile.parser.impl.ss.SsLineObject;
import org.uniprot.core.uniprot.InternalSection;
import org.uniprot.core.uniprot.evidence.EvidenceLine;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SsLineConverterTest {
    private SsLineConverter converter = new SsLineConverter();

    @Test
    void testEvidence() {
        SsLineObject obj = new SsLineObject();
        //**EV ECO:0000313; ProtImp; -; 07-NOV-2006.\n"+
        //**EV ECO:0000256; HAMAP-Rule:MF_01417; -; 01-OCT-2010.\n"
        SsLineObject.EvLine ev1 = new SsLineObject.EvLine();
        ev1.id = "ECO:0000313";
        ev1.db = "ProtImp";
        ev1.attr_1 = "UP99";
        ev1.attr_2 = "-";
        ev1.date = LocalDate.now();
        obj.ssEVLines.add(ev1);
        SsLineObject.EvLine ev2 = new SsLineObject.EvLine();
        ev2.id = "ECO:0000256";
        ev2.db = "HAMAP-Rule";
        ev2.attr_1 = "MF_01417";
        ev2.attr_2 = "XAB";
        ev2.date = LocalDate.now();
        obj.ssEVLines.add(ev2);
        InternalSection is = converter.convert(obj);
        List<EvidenceLine> evidences = is.getEvidenceLines();
        assertEquals(2, evidences.size());
        EvidenceLine evidence1 = evidences.get(0);
        EvidenceLine evidence2 = evidences.get(1);
        assertEquals("UP99", evidence1.toEvidence().getSource().getId());
        assertEquals("ECO:0000313|ProtImp:UP99", evidence1.getEvidence());
        assertEquals("ProtImp", evidence1.toEvidence().getSource().getDatabaseType().getName());
        assertEquals("ECO:0000313", evidence1.toEvidence().getEvidenceCode().getCode());
        assertEquals(ev1.date, evidence1.getCreateDate());
        assertEquals(ev1.attr_2, evidence1.getCurator());


        assertEquals("MF_01417", evidence2.toEvidence().getSource().getId());
        assertEquals("ECO:0000256|HAMAP-Rule:MF_01417", evidence2.getEvidence());
        assertEquals("HAMAP-Rule", evidence2.toEvidence().getSource().getDatabaseType().getName());
        assertEquals("ECO:0000256", evidence2.toEvidence().getEvidenceCode().getCode());
        assertEquals(ev2.date, evidence2.getCreateDate());
        assertEquals(ev2.attr_2, evidence2.getCurator());

    }
}
