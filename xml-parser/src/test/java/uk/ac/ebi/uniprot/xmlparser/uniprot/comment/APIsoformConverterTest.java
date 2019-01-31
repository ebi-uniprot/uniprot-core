package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformSequenceStatus;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.APIsoformBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.IsoformNameBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.IsoformType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceHelper.createEvidences;

class APIsoformConverterTest {

    @Test
    void test() {
        APIsoform apisoform = createAPIsoform();
        APIsoformConverter converter = new APIsoformConverter(new EvidenceIndexMapper());
        IsoformType xmlIsoform = converter.toXml(apisoform);
        System.out.println(UniProtXmlTestHelper.toXmlString(xmlIsoform, IsoformType.class, "isoform"));
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
        return isoformBuilder.name(new IsoformNameBuilder(name, evidences).build())
                .synonyms(isoformSynonyms)
                .ids(asList("isoID1", "isoID2", "isoID3"))
                .sequenceStatus(IsoformSequenceStatus.DESCRIBED)
                .sequenceIds(asList("someSeqId1", "someSeqId2"))
                .build();
    }
}
