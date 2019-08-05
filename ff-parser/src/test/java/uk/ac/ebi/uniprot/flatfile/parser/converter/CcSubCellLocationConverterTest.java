package uk.ac.ebi.uniprot.flatfile.parser.converter;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.SubcellularLocationComment;

import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineConverter;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class CcSubCellLocationConverterTest {
	private final CcLineConverter converter = new CcLineConverter(new HashMap<>(), new HashMap<>());
	@Test
	void testEvidenceInLocationLevel() {
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.SUBCELLULAR_LOCATION;
		CcLineObject.SubcullarLocation subcellLocation = new CcLineObject.SubcullarLocation();
		CcLineObject.LocationObject locationObj = new CcLineObject.LocationObject ();
		CcLineObject.LocationValue location = new CcLineObject.LocationValue();
		location.value ="Cytoplasm";
		locationObj.subcellularLocation = location;
		List<String> evidences =new ArrayList<>();
		evidences.add("ECO:0000256|HAMAP-Rule:MF_01146");
		ccLineO.evidenceInfo.evidences.put(locationObj, evidences);
		subcellLocation.locations.add(locationObj);
		cc1.object =subcellLocation;
		ccLineO.ccs.add(cc1);
		List<Comment> comments = converter.convert(ccLineO);
		assertEquals(1, comments.size());
		Comment comment = comments.get(0);
		assertTrue(comment instanceof SubcellularLocationComment);
		SubcellularLocationComment scComment = (SubcellularLocationComment) comment;
		
		assertNotNull(scComment);
		
	}
	
	@Test
	void tesWithEvidence() {
		CcLineObject ccLineO = new CcLineObject();	
		CcLineObject.CC cc1 =new CcLineObject.CC();
		cc1.topic =CcLineObject.CCTopicEnum.SUBCELLULAR_LOCATION;
		CcLineObject.SubcullarLocation subcellLocation = new CcLineObject.SubcullarLocation();
		CcLineObject.LocationObject locationObj = new CcLineObject.LocationObject ();
		CcLineObject.LocationValue location = new CcLineObject.LocationValue();
		location.value ="Cytoplasm";
		locationObj.subcellularLocation = location;
		List<String> evidences =new ArrayList<>();
		evidences.add("ECO:0000256|HAMAP-Rule:MF_01146");
		ccLineO.evidenceInfo.evidences.put(locationObj.subcellularLocation, evidences);
		subcellLocation.locations.add(locationObj);
		cc1.object =subcellLocation;
		ccLineO.ccs.add(cc1);
		List<Comment> comments = converter.convert(ccLineO);
		assertEquals(1, comments.size());
		Comment comment = comments.get(0);
		assertTrue(comment instanceof SubcellularLocationComment);
		SubcellularLocationComment scComment = (SubcellularLocationComment) comment;
		
		assertNotNull(scComment);
		
	}
}
