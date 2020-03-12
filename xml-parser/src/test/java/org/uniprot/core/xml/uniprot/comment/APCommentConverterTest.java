package org.uniprot.core.xml.uniprot.comment;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.uniprot.cv.evidence.EvidenceHelper.parseEvidenceLine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.*;
import org.uniprot.core.uniprot.comment.impl.APIsoformBuilder;
import org.uniprot.core.uniprot.comment.impl.AlternativeProductsCommentBuilder;
import org.uniprot.core.uniprot.comment.impl.IsoformNameBuilder;
import org.uniprot.core.uniprot.comment.impl.NoteBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.uniprot.evidence.EvidencedValue;
import org.uniprot.core.uniprot.evidence.impl.EvidencedValueBuilder;
import org.uniprot.core.xml.jaxb.uniprot.CommentType;
import org.uniprot.core.xml.uniprot.EvidenceIndexMapper;
import org.uniprot.core.xml.uniprot.UniProtXmlTestHelper;

class APCommentConverterTest {

    @Test
    void test() {
        AlternativeProductsCommentBuilder builder = new AlternativeProductsCommentBuilder();
        List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_PROMOTER_USAGE);
        events.add(APEventType.ALTERNATIVE_SPLICING);
        List<APIsoform> apIsoforms = asList(createAPIsoform1(), createAPIsoform2());
        List<EvidencedValue> texts = createEvidenceValues();
        Note apNote = new NoteBuilder(texts).build();
        AlternativeProductsComment comment =
                builder.eventsSet(events).isoformsSet(apIsoforms).note(apNote).build();
        APCommentConverter converter = new APCommentConverter(new EvidenceIndexMapper());

        CommentType xmlComment = converter.toXml(comment);
        System.out.println(
                UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
        AlternativeProductsComment converted = converter.fromXml(xmlComment);
        assertEquals(comment, converted);
    }

    private APIsoform createAPIsoform1() {
        List<Evidence> evidences = createEvidences();
        String name = "name 1";
        String syn1 = "synonym11";

        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(new IsoformNameBuilder(syn1, evidences).build());

        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        return isoformBuilder
                .name(new IsoformNameBuilder(name, evidences).build())
                .synonymsSet(isoformSynonyms)
                .isoformIdsSet(asList("isoID11", "isoID12"))
                .sequenceStatus(IsoformSequenceStatus.DISPLAYED)
                .build();
    }

    private APIsoform createAPIsoform2() {
        List<Evidence> evidences = createEvidences();
        String name = "name 2";
        String syn1 = "synonym21";
        String syn2 = "Synonym22";
        List<IsoformName> isoformSynonyms = new ArrayList<>();
        isoformSynonyms.add(new IsoformNameBuilder(syn1, evidences).build());
        isoformSynonyms.add(new IsoformNameBuilder(syn2, Collections.emptyList()).build());

        APIsoformBuilder isoformBuilder = new APIsoformBuilder();
        return isoformBuilder
                .name(new IsoformNameBuilder(name, evidences).build())
                .synonymsSet(isoformSynonyms)
                .isoformIdsSet(asList("isoID21", "isoID 23"))
                .sequenceStatus(IsoformSequenceStatus.DESCRIBED)
                .sequenceIdsSet(asList("someSeqId1", "someSeqId2"))
                .build();
    }

    private List<Evidence> createEvidences() {
        List<Evidence> evidences = new ArrayList<>();
        evidences.add(parseEvidenceLine("ECO:0000255|PROSITE-ProRule:PRU10028"));
        evidences.add(parseEvidenceLine("ECO:0000256|PIRNR:PIRNR001361"));
        return evidences;
    }

    private List<EvidencedValue> createEvidenceValues() {
        List<EvidencedValue> evidencedValues = new ArrayList<>();
        evidencedValues.add(new EvidencedValueBuilder("value1", Collections.emptyList()).build());
        evidencedValues.add(new EvidencedValueBuilder("value2", Collections.emptyList()).build());
        return evidencedValues;
    }
}
