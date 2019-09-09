package org.uniprot.core.flatfile.parser.converter;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.rn.RnLineConverter;
import org.uniprot.core.flatfile.parser.impl.rn.RnLineObject;
import org.uniprot.core.uniprot.evidence.Evidence;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RnLineConverterTest {
	@Test
	public void test(){
	//	 "RN   [11]\n";
		RnLineObject osO = new RnLineObject();
		osO.number=11;
		RnLineConverter converter = new RnLineConverter();
		List<Evidence>  evIds =converter.convert(osO);
		assertEquals(0, evIds.size());
	}
	@Test
	public void testEvidence(){
//		 "RN   [11]{[EI1][EI2]}\n";
		RnLineObject osO = new RnLineObject();
		osO.number=11;
		List<String> evIds = new ArrayList<String>();
		evIds.add("ECO:0000313|Proteomes:UP000007751");
		evIds.add("ECO:0000313|Proteomes:UP000007752");
		osO.evidenceInfo.evidences.put(osO.number, evIds);
		
		RnLineConverter converter = new RnLineConverter();
		List<Evidence>  eviIds =converter.convert(osO);
		
		assertEquals(2, eviIds.size());
		Evidence eviId = eviIds.get(0);
		Evidence eviId2 = eviIds.get(1);
		assertEquals("ECO:0000313|Proteomes:UP000007751", eviId.getValue());
		assertEquals("ECO:0000313|Proteomes:UP000007752", eviId2.getValue());
	}
}
