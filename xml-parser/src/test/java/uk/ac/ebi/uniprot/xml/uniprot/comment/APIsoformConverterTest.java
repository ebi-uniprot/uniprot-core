package uk.ac.ebi.uniprot.xml.uniprot.comment;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.APIsoform;
import org.uniprot.core.uniprot.comment.IsoformName;
import org.uniprot.core.uniprot.comment.IsoformSequenceStatus;
import org.uniprot.core.uniprot.comment.builder.APIsoformBuilder;
import org.uniprot.core.uniprot.comment.builder.IsoformNameBuilder;
import org.uniprot.core.uniprot.evidence.Evidence;

import uk.ac.ebi.uniprot.xml.jaxb.uniprot.IsoformType;
import uk.ac.ebi.uniprot.xml.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xml.uniprot.UniProtXmlTestHelper;
import uk.ac.ebi.uniprot.xml.uniprot.comment.APIsoformConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static uk.ac.ebi.uniprot.xml.uniprot.EvidenceHelper.createEvidences;

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
