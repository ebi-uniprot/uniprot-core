package org.uniprot.core.flatfile.parser.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.flatfile.parser.impl.rc.RcLineConverter;
import org.uniprot.core.flatfile.parser.impl.rc.RcLineObject;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.evidence.Evidence;

class RcLineConverterTest {
    @Test
    void test() {
        // "RC   STRAIN=Holstein; TISSUE=Lymph node, and Mammary gland;\n";
        RcLineObject rcline = new RcLineObject();
        RcLineObject.RC rc1 = new RcLineObject.RC();
        rc1.tokenType = RcLineObject.RcTokenEnum.STRAIN;
        rc1.values.add("Holstein");
        rcline.rcs.add(rc1);
        RcLineObject.RC rc2 = new RcLineObject.RC();
        rc2.tokenType = RcLineObject.RcTokenEnum.TISSUE;
        rc2.values.add("Lymph node");
        rc2.values.add("Mammary gland");
        rcline.rcs.add(rc2);
        RcLineConverter converter = new RcLineConverter();
        List<ReferenceComment> sss = converter.convert(rcline);
        assertEquals(3, sss.size());
        ReferenceComment strain = sss.get(0);
        ReferenceComment tissue1 = sss.get(1);
        ReferenceComment tissue2 = sss.get(2);
        assertEquals(ReferenceCommentType.STRAIN, strain.getType());
        assertEquals("Holstein", strain.getValue());
        assertEquals(ReferenceCommentType.TISSUE, tissue1.getType());
        assertEquals("Lymph node", tissue1.getValue());
        assertEquals(ReferenceCommentType.TISSUE, tissue2.getType());
        assertEquals("Mammary gland", tissue2.getValue());
    }

    @Test
    void testEvidence() {
        // "RC   STRAIN=Holstein{EI1,EI2}; TISSUE=Lymph node, and Mammary gland{EI2,EI3};\n";
        RcLineObject rcline = new RcLineObject();
        RcLineObject.RC rc1 = new RcLineObject.RC();
        rc1.tokenType = RcLineObject.RcTokenEnum.STRAIN;
        rc1.values.add("Holstein");

        List<String> evIds = new ArrayList<String>();
        evIds.add("ECO:0000313|Proteomes:UP000007751");
        evIds.add("ECO:0000313|Proteomes:UP000007752");
        rc1.evidenceInfo.getEvidences().put("Holstein", evIds);
        rcline.rcs.add(rc1);

        RcLineObject.RC rc2 = new RcLineObject.RC();
        rc2.tokenType = RcLineObject.RcTokenEnum.TISSUE;
        rc2.values.add("Lymph node");
        rc2.values.add("Mammary gland");
        evIds = new ArrayList<String>();
        evIds.add("ECO:0000313|Proteomes:UP000007752");
        evIds.add("ECO:0000313|Proteomes:UP000007753");
        rc2.evidenceInfo.getEvidences().put("Mammary gland", evIds);
        rcline.rcs.add(rc2);
        RcLineConverter converter = new RcLineConverter();
        List<ReferenceComment> sss = converter.convert(rcline);
        assertEquals(3, sss.size());
        ReferenceComment strain = sss.get(0);
        ReferenceComment tissue1 = sss.get(1);
        ReferenceComment tissue2 = sss.get(2);
        assertEquals(ReferenceCommentType.STRAIN, strain.getType());
        assertEquals("Holstein", strain.getValue());
        assertEquals(ReferenceCommentType.TISSUE, tissue1.getType());
        assertEquals("Lymph node", tissue1.getValue());
        assertEquals(ReferenceCommentType.TISSUE, tissue2.getType());
        assertEquals("Mammary gland", tissue2.getValue());
        List<Evidence> eviIds = strain.getEvidences();
        assertEquals(2, eviIds.size());
        Evidence eviId1 = eviIds.get(0);
        Evidence eviId2 = eviIds.get(1);
        assertEquals("ECO:0000313|Proteomes:UP000007751", eviId1.getValue());
        assertEquals("ECO:0000313|Proteomes:UP000007752", eviId2.getValue());
        eviIds = tissue1.getEvidences();
        assertEquals(0, eviIds.size());

        eviIds = tissue2.getEvidences();
        assertEquals(2, eviIds.size());
        eviId1 = eviIds.get(0);
        eviId2 = eviIds.get(1);
        assertEquals("ECO:0000313|Proteomes:UP000007752", eviId1.getValue());
        assertEquals("ECO:0000313|Proteomes:UP000007753", eviId2.getValue());
    }
}
