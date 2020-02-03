package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineConverter;
import org.uniprot.core.flatfile.parser.impl.cc.cclineobject.*;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.SubcellularLocationComment;

class CcSubCellLocationConverterTest {
    private final CcLineConverter converter = new CcLineConverter(new HashMap<>(), new HashMap<>());

    @Test
    void testEvidenceInLocationLevel() {
        CcLineObject ccLineO = new CcLineObject();
        CC cc1 = new CC();
        cc1.setTopic(CC.CCTopicEnum.SUBCELLULAR_LOCATION);
        SubcullarLocation subcellLocation = new SubcullarLocation();
        LocationObject locationObj = new LocationObject();
        LocationValue location = new LocationValue();
        location.setValue("Cytoplasm");
        locationObj.setSubcellularLocation(location);
        List<String> evidences = new ArrayList<>();
        evidences.add("ECO:0000256|HAMAP-Rule:MF_01146");
        ccLineO.getEvidenceInfo().getEvidences().put(locationObj, evidences);
        subcellLocation.getLocations().add(locationObj);
        cc1.setObject(subcellLocation);
        ccLineO.getCcs().add(cc1);
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
        CC cc1 = new CC();
        cc1.setTopic(CC.CCTopicEnum.SUBCELLULAR_LOCATION);
        SubcullarLocation subcellLocation = new SubcullarLocation();
        LocationObject locationObj = new LocationObject();
        LocationValue location = new LocationValue();
        location.setValue("Cytoplasm");
        locationObj.setSubcellularLocation(location);
        List<String> evidences = new ArrayList<>();
        evidences.add("ECO:0000256|HAMAP-Rule:MF_01146");
        ccLineO.getEvidenceInfo()
                .getEvidences()
                .put(locationObj.getSubcellularLocation(), evidences);
        subcellLocation.getLocations().add(locationObj);
        cc1.setObject(subcellLocation);
        ccLineO.getCcs().add(cc1);
        List<Comment> comments = converter.convert(ccLineO);
        assertEquals(1, comments.size());
        Comment comment = comments.get(0);
        assertTrue(comment instanceof SubcellularLocationComment);
        SubcellularLocationComment scComment = (SubcellularLocationComment) comment;

        assertNotNull(scComment);
    }
}
