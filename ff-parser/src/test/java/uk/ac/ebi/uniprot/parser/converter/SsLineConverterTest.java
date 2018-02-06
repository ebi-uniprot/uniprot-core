package uk.ac.ebi.uniprot.parser.converter;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.parser.impl.ss.SsLineConverter;
import uk.ac.ebi.uniprot.parser.impl.ss.SsLineObject;
import uk.ac.ebi.uniprot.parser.impl.ss.UniProtSsLineObject;

import java.time.LocalDate;

public class SsLineConverterTest {
	private SsLineConverter converter = new SsLineConverter();
	@Test
	public void testEvidence(){
		SsLineObject obj =new SsLineObject();
		//**EV ECO:0000313; ProtImp; -; 07-NOV-2006.\n"+
		//**EV ECO:0000256; HAMAP-Rule:MF_01417; -; 01-OCT-2010.\n"
		SsLineObject.EvLine ev1 =new SsLineObject.EvLine();
		ev1.id = "ECO:0000313";
		ev1.db ="ProtImp";
		ev1.attr_1 ="UP99";
		ev1.attr_2 ="-";
		ev1.date = LocalDate.now();
		obj.ssEVLines.add(ev1);
		SsLineObject.EvLine ev2 =new SsLineObject.EvLine();
		ev2.id = "ECO:0000256";
		ev2.db ="HAMAP-Rule";
		ev2.attr_1 ="MF_01417";
		ev2.attr_2 ="-";
		ev2.date =  LocalDate.now();
		obj.ssEVLines.add(ev2);
		UniProtSsLineObject ssLineObj =converter.convert(obj);
		List<Evidence> evidences = ssLineObj.evidences;
		assertEquals(2, evidences.size());
		Evidence evidence1 =evidences.get(0);
		Evidence evidence2 =evidences.get(1);
		assertEquals("UP99", evidence1.getAttribute());
		assertEquals("ECO:0000313|ProtImp:UP99", evidence1.getValue());
		assertEquals("ProtImp", evidence1.getType().getValue());
		assertEquals("ECO:0000313", evidence1.getEvidenceCode().getCodeValue());
		
		assertEquals("MF_01417", evidence2.getAttribute());
		assertEquals("ECO:0000256|HAMAP-Rule:MF_01417", evidence2.getValue());
		assertEquals("HAMAP-Rule", evidence2.getType().getValue());
		assertEquals("ECO:0000256", evidence2.getEvidenceCode().getCodeValue());
	}
}
