package org.uniprot.core.xml.uniprot.citation;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;
import org.uniprot.core.uniprot.builder.ReferenceCommentBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.SourceDataType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;
import org.uniprot.core.xml.uniprot.citation.ReferenceCommentConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.uniprot.evidence.impl.EvidenceHelper.parseEvidenceLine;

class ReferenceCommentConverterTest {

    @Test
    void test() {
        List<Evidence> evidences = createEvidences();
        List<ReferenceComment> refComments = new ArrayList<>();
        refComments.add(createReferenceComment(ReferenceCommentType.STRAIN, "S1", evidences));
        refComments.add(createReferenceComment(ReferenceCommentType.STRAIN, "S241", Collections.emptyList()));

        refComments.add(createReferenceComment(ReferenceCommentType.PLASMID, "pls11", evidences));
        refComments.add(createReferenceComment(ReferenceCommentType.TISSUE, "S11", evidences));

        ReferenceCommentConverter converter = new ReferenceCommentConverter(new EvidenceIndexMapper());

        SourceDataType sourceType = converter.toXml(refComments);
        System.out.println(UniProtXmlTestHelper.toXmlString(sourceType, SourceDataType.class, "source"));
        List<ReferenceComment> converted = converter.fromXml(sourceType);
        assertEquals(refComments, converted);

    }

    private ReferenceComment createReferenceComment(ReferenceCommentType type, String value, List<Evidence> evidences) {
        return new ReferenceCommentBuilder()
                .value(value)
                .type(type)
                .evidences(evidences)
                .build();
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(parseEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }
}
