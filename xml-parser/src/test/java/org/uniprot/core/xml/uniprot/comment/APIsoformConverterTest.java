package org.uniprot.core.xml.uniprot.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.core.xml.uniprot.EvidenceHelper.createEvidences;

import static java.util.Arrays.asList;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprotkb.comment.APIsoform;
import org.uniprot.core.uniprotkb.comment.IsoformName;
import org.uniprot.core.uniprotkb.comment.IsoformSequenceStatus;
import org.uniprot.core.uniprotkb.comment.impl.APIsoformBuilder;
import org.uniprot.core.uniprotkb.comment.impl.IsoformNameBuilder;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.xml.jaxb.uniprot.IsoformType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class APIsoformConverterTest {

    @Test
    void test() {
        APIsoform apisoform = createAPIsoform();
        APIsoformConverter converter = new APIsoformConverter(new EvidenceIndexMapper());
        IsoformType xmlIsoform = converter.toXml(apisoform);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlIsoform, IsoformType.class, "isoform"));
        APIsoform converted = converter.fromXml(xmlIsoform);
        assertEquals(apisoform, converted);
    }

    private APIsoform createAPIsoform() {
        List<Evidence> evidences = createEvidences();
        String name = "Some name";
        String syn1 = "synonym1";
        String syn2 = "Synonym2";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(new IsoformNameBuilder(syn1, evidences).build());
        isoformSynonyms.add(new IsoformNameBuilder(syn2, Collections.emptyList()).build());

        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        return isoformBuilder
                .name(new IsoformNameBuilder(name, evidences).build())
                .synonymsSet(isoformSynonyms)
                .isoformIdsSet(asList("isoID1", "isoID2", "isoID3"))
                .sequenceStatus(IsoformSequenceStatus.DESCRIBED)
                .sequenceIdsSet(asList("someSeqId1", "someSeqId2"))
                .build();
    }
}
