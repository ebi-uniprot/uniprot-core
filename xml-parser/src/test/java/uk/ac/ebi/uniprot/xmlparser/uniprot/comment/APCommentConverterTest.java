package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.*;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.APCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence2.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class APCommentConverterTest {

	@Test
	void test() {
        APCommentBuilder builder = APCommentBuilder.newInstance();
        List<APEventType> events = new ArrayList<>();
        events.add(APEventType.ALTERNATIVE_PROMOTER_USAGE);
        events.add(APEventType.ALTERNATIVE_SPLICING);
        List<APIsoform> apIsoforms = Arrays.asList(createAPIsoform1(), createAPIsoform2());
        List<EvidencedValue> texts = createEvidenceValues();
        Note apNote = CommentFactory.INSTANCE.createNote(texts);
        AlternativeProductsComment comment = builder.events(events)
                .isoforms(apIsoforms)
                .note(apNote)
                .build();
        APCommentConverter converter= new APCommentConverter(new EvidenceIndexMapper());
        
        CommentType xmlComment = converter.toXml(comment);
		System.out.println(UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment"));
		AlternativeProductsComment converted = converter.fromXml(xmlComment);
		assertEquals(comment, converted);
	}
	
	 private APIsoform createAPIsoform1() {
	        List<Evidence> evidences = createEvidences();
	        String name = "name 1";
	        String syn1 = "synonym11";

	        List<IsoformName> isoformSynonyms = new ArrayList<>();
	        isoformSynonyms.add(APCommentBuilder.createIsoformName(syn1, evidences));
	        List<IsoformId> isoformIds = new ArrayList<>();
	        isoformIds.add(APCommentBuilder.createIsoformId("isoID11"));
	        isoformIds.add(APCommentBuilder.createIsoformId("isoID12"));
	  

	        APCommentBuilder.APIsoformBuilder isoformBuilder = APCommentBuilder.APIsoformBuilder.newInstance();
	        return
	                isoformBuilder.isoformName(APCommentBuilder.createIsoformName(name, evidences))
	                        .isoformSynonyms(isoformSynonyms)
	                        .isoformIds(isoformIds)
	                        .isoformSequenceStatus(IsoformSequenceStatus.DISPLAYED)
	                        .build();
	    }

	 private APIsoform createAPIsoform2() {
	        List<Evidence> evidences = createEvidences();
	        String name = "name 2";
	        String syn1 = "synonym21";
	        String syn2 = "Synonym22";
	        List<IsoformName> isoformSynonyms = new ArrayList<>();
	        isoformSynonyms.add(APCommentBuilder.createIsoformName(syn1, evidences));
	        isoformSynonyms.add(APCommentBuilder.createIsoformName(syn2, Collections.emptyList()));
	        List<IsoformId> isoformIds = new ArrayList<>();
	        isoformIds.add(APCommentBuilder.createIsoformId("isoID21"));
	        isoformIds.add(APCommentBuilder.createIsoformId("isoID 23"));

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
	 
	  private List<EvidencedValue> createEvidenceValues() {
	        List<EvidencedValue> evidencedValues = new ArrayList<>();
	        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value1", Collections.emptyList()));
	        evidencedValues.add(UniProtFactory.INSTANCE.createEvidencedValue("value2", Collections.emptyList()));
	        return evidencedValues;
	    }
}
