package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformId;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformName;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformSequenceStatus;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.APCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.IsoformType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class APIsoformConverterTest {

	@Test
	void test() {
		APIsoform apisoform =createAPIsoform();
		APIsoformConverter converter = new APIsoformConverter (new EvidenceIndexMapper());
		IsoformType xmlIsoform =converter.toXml(apisoform);
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
	        isoformSynonyms.add(APCommentBuilder.createIsoformName(syn1, evidences));
	        isoformSynonyms.add(APCommentBuilder.createIsoformName(syn2, Collections.emptyList()));
	        List<IsoformId> isoformIds = new ArrayList<>();
	        isoformIds.add(APCommentBuilder.createIsoformId("isoID1"));
	        isoformIds.add(APCommentBuilder.createIsoformId("isoID2"));
	        isoformIds.add(APCommentBuilder.createIsoformId("isoID3"));

	        APCommentBuilder.APIsoformBuilder isoformBuilder = APCommentBuilder.APIsoformBuilder.newInstance();
	        return
	                isoformBuilder.isoformName(APCommentBuilder.createIsoformName(name, evidences))
	                        .isoformSynonyms(isoformSynonyms)
	                        .isoformIds(isoformIds)
	                        .isoformSequenceStatus(IsoformSequenceStatus.DESCRIBED)
	                        .sequenceIds(Arrays.asList("someSeqId1", "someSeqId2"))
	                        .build();
	    }
	 private List<Evidence> createEvidences() {
	        List<Evidence> evidences = new ArrayList<>();
	        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000255|PROSITE-ProRule:PRU10028"));
	        evidences.add(UniProtFactory.INSTANCE.createEvidence("ECO:0000256|PIRNR:PIRNR001361"));
	        return evidences;
	    }
}
