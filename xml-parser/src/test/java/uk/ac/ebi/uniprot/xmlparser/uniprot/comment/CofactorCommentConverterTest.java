package uk.ac.ebi.uniprot.xmlparser.uniprot.comment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.EvidencedValue;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Cofactor;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;
import uk.ac.ebi.uniprot.domain.uniprot.comment.builder.CofactorCommentBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.factory.CommentFactory;
import uk.ac.ebi.uniprot.domain.uniprot.factory.UniProtFactory;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CofactorType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.CommentType;
import uk.ac.ebi.uniprot.xml.jaxb.uniprot.EvidencedStringType;
import uk.ac.ebi.uniprot.xmlparser.uniprot.EvidenceIndexMapper;
import uk.ac.ebi.uniprot.xmlparser.uniprot.UniProtXmlTestHelper;

class CofactorCommentConverterTest {
	CofactorCommentConverter converter ;
	 @BeforeEach
	    public void setUp() {

	        EvidenceIndexMapper evidenceReferenceHandler = new EvidenceIndexMapper();

	        Evidence evidence1 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060645");
	        Evidence evidence2 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060646");
	        Evidence evidence3 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060647");
	        Evidence evidence4 = UniProtFactory.INSTANCE.createEvidence("ECO:0000269|PubMed:9060648");
	        Map<Evidence, Integer> evIdMap = new HashMap<>();
	        evIdMap.put(evidence1, 1);
	        evIdMap.put(evidence2, 2);
	        evIdMap.put(evidence3, 3);
	        evIdMap.put(evidence4, 4);
	        evidenceReferenceHandler.reset(evIdMap);

	        this.converter = new CofactorCommentConverter(evidenceReferenceHandler);

	    }

	 
	@Test
	void test() {
		CofactorCommentBuilder builder = CofactorCommentBuilder.newInstance();
		  builder.molecule("Isoform 1");
	        List<String> evids = new ArrayList<>();
	        evids.add("ECO:0000269|PubMed:9060645");
	        List<Cofactor> cofactors =new ArrayList<>();
	        cofactors.add(create("Zn(2+)", CofactorReferenceType.CHEBI, "CHEBI:29105", evids));
	        evids = new ArrayList<>();
	        evids.add("ECO:0000269|PubMed:9060646");
	        cofactors.add(create("Co(2+)", CofactorReferenceType.CHEBI, "CHEBI:29106", evids));
	        builder.cofactors(cofactors);
	        evids = new ArrayList<>();
	        evids.add("ECO:0000269|PubMed:9060647");
	        builder.note(createNote("Binds 2 zinc ions", evids));
	        CofactorComment comment = builder.build();
	        CommentType xmlComment = converter.toXml(comment);
	        assertEquals("Isoform 1", xmlComment.getMolecule().getValue());
	        List<CofactorType> xmlCofactors = xmlComment.getCofactor();
	        assertEquals(2, xmlCofactors.size());
	        List<Integer> xmlEv = new ArrayList<>();
	        xmlEv.add(1);
	        assertCofactorType(xmlCofactors.get(0), "Zn(2+)", "ChEBI", "CHEBI:29105", xmlEv);
	        xmlEv = new ArrayList<>();
	        xmlEv.add(2);
	        assertCofactorType(xmlCofactors.get(1), "Co(2+)", "ChEBI", "CHEBI:29106", xmlEv);
	        assertEquals(1, xmlComment.getText().size());
	        EvidencedStringType note = xmlComment.getText().get(0);
	        assertNotNull(note);
	        assertEquals("Binds 2 zinc ions.", note.getValue());
	        xmlEv = new ArrayList<>();
	        xmlEv.add(3);
	        assertEquals(xmlEv, note.getEvidence());
	        CofactorComment convertedComment = converter.fromXml(xmlComment);
	        assertEquals(comment, convertedComment);
	        String createdXmlText =UniProtXmlTestHelper.toXmlString(xmlComment, CommentType.class, "comment");
	        System.out.println(createdXmlText);

	}
	  private void assertCofactorType(CofactorType cofactor, String name, String dbType, String dbId,
	            List<Integer> evids) {
	        assertEquals(name, cofactor.getName());
	        assertEquals(dbType, cofactor.getDbReference().getType());
	        assertEquals(dbId, cofactor.getDbReference().getId());
	        assertEquals(evids, cofactor.getEvidence());
	    }

	 private Note createNote(String val, List<String> evids) {
	        List<EvidencedValue> texts = new ArrayList<>();
	        texts.add(
	        UniProtFactory.INSTANCE.createEvidencedValue(val, createEvidence(evids)));
	        return CommentFactory.INSTANCE.createNote(texts);
	    }

	  private Cofactor create(String name, CofactorReferenceType type, String xrefId, List<String> evids) {

	        
	        DBCrossReference<CofactorReferenceType>  reference =
					UniProtFactory.INSTANCE.createDBCrossReference (type, xrefId); 
	        Cofactor cofactor =CofactorCommentBuilder.createCofactor(name, reference, createEvidence(evids));
	        
	        return cofactor;
	    }

	    private List<Evidence> createEvidence(List<String> evids) {
	        List<Evidence> evidences = new ArrayList<>();
	        for (String ev : evids) {
	            evidences.add( UniProtFactory.INSTANCE.createEvidence(ev));
	        }
	        return evidences;
	    }
}
