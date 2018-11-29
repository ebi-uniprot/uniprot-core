package uk.ac.ebi.uniprot.parser.transformer;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.ac.ebi.uniprot.domain.uniprot.comment.APIsoform;
import uk.ac.ebi.uniprot.domain.uniprot.comment.AlternativeProductsComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.IsoformSequenceStatus;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Note;



public class APCommentTransformerTest {
	private final APCommentTransformer transformer
		=new APCommentTransformer();
	@Test
	public void test1(){
		String ccLineString =
		        "Event=Alternative splicing; Named isoforms=6;\n" +
				"Comment=Additional isoforms seem to exist.;\n" +
		        "Name=1; Synonyms=A;\n" +
		        "IsoId=Q9V8R9-1; Sequence=Displayed;\n" +
		        "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is"
		        + " in conflict in positions: 33:I->T. No experimental confirmation available.;\n" +
		        "Name=2;\n" +
		        "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n" +
		        "Name=Bim-alpha3; Synonyms=BCL2-like 11 transcript variant 10, Bim-AD, BimAD;\n" +
		        "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" +
		        "Name=4; Synonyms=B;\n" +
		        "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" +
		        "Name=5;\n" +
		        "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n" +
		        "Note=No experimental confirmation available.;\n" +
		        "Name=6; Synonyms=D;\n" +
		        "IsoId=Q9V8R9-6; Sequence=Described;\n" +
		        "Note=No experimental confirmation available.;";
		
		String ccLineString2 ="ALTERNATIVE PRODUCTS:\n" +ccLineString;
		
		AlternativeProductsComment comment1= CommentTransformerHelper.transform(ccLineString2, CommentType.ALTERNATIVE_PRODUCTS);
		assertNotNull(comment1);
		AlternativeProductsComment comment =transformer.transform(CommentType.ALTERNATIVE_PRODUCTS, ccLineString);	
		assertNotNull(comment);
		assertEquals(comment1, comment);	
	
		AlternativeProductsComment comment2 =transformer.transform(ccLineString2);	
		assertEquals(comment2, comment);
		assertEquals(1, comment.getEvents().size());
		assertEquals("Alternative splicing", comment.getEvents().get(0).getName());
		assertEquals(1, comment.getNote().getTexts().size());
		assertEquals("Additional isoforms seem to exist.", comment.getNote().getTexts().get(0).getValue());
		assertEquals(6, comment.getIsoforms().size());
		APIsoform isoform3= comment.getIsoforms().get(2);
		assertEquals("Bim-alpha3", isoform3.getName().getValue());
		assertEquals(3, isoform3.getSynonyms().size());
		assertEquals("Bim-AD", isoform3.getSynonyms().get(1).getValue());
		assertEquals(1, isoform3.getIsoformIds().size());
		assertEquals("Q9V8R9-3", isoform3.getIsoformIds().get(0).getValue());
		assertEquals(3, isoform3.getSequenceIds().size());
		assertEquals("VSP_000478", isoform3.getSequenceIds().get(1));
		assertNull(isoform3.getNote());
		APIsoform isoform5= comment.getIsoforms().get(4);
		assertEquals(1, isoform5.getNote().getTexts().size());
		assertEquals("No experimental confirmation available.", isoform5.getNote().getTexts().get(0).getValue());
	}
	@Test
	public void testWithEvidence(){
		String ccLineStringEvidence ="ALTERNATIVE PRODUCTS:\n" +
		        "Event=Alternative splicing; Named isoforms=6;\n" +
		        "Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};"
		        + " Another additional isoforms also seem to exist. {ECO:0000269|PubMed:10433554};\n" +
		        "Name=1 {ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}; Synonyms=A {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" +
		        "IsoId=Q9V8R9-1; Sequence=Displayed;\n" +
		        "Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T."
		        + " No experimental confirmation available. {ECO:0000313|PDB:3OW2};\n" +
		        "Name=2;\n" +
		        "IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n" +
		        "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript"
		        + " variant 10 {ECO:0000313|EMBL:BAG16761.1}, Bim-AD, BimAD {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" +
		        "IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" +
		        "Name=4; Synonyms=B;\n" +
		        "IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" +
		        "Name=5;\n" +
		        "IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n" +
		        "Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};"
		        + " Another no experimental confirmation also available. {ECO:0000269|PubMed:1043355, ECO:0000313|EMBL:BAG16761.1};\n" +
		        "Name=6; Synonyms=D;\n" +
		        "IsoId=Q9V8R9-6; Sequence=Described;\n" +
		        "Note=No experimental confirmation.;";
		AlternativeProductsComment comment =
		transformer.transform(CommentType.ACTIVITY_REGULATION, ccLineStringEvidence);	
		
		assertEquals(1, comment.getEvents().size());
		assertEquals("Alternative splicing", comment.getEvents().get(0).getName());
		Note note = comment.getNote();
		assertEquals(2, note.getTexts().size());
		assertEquals("Additional isoforms seem to exist.", note.getTexts().get(0).getValue());
		assertEquals(2, note.getTexts().get(0).getEvidences().size());
		assertEquals("Another additional isoforms also seem to exist.", note.getTexts().get(1).getValue());
		assertEquals(1, note.getTexts().get(1).getEvidences().size());
		assertEquals("ECO:0000269|PubMed:10433554", comment.getNote().getTexts().get(1).getEvidences().get(0).getValue());
		assertEquals(6, comment.getIsoforms().size());
		APIsoform isoform1= comment.getIsoforms().get(0);
		assertEquals("1", isoform1.getName().getValue());
		assertEquals(2, isoform1.getName().getEvidences().size());
		assertEquals("ECO:0000313|EMBL:BAG16761.1", isoform1.getName().getEvidences().get(0).getValue());
		assertEquals("ECO:0000313|PDB:3OW2", isoform1.getName().getEvidences().get(1).getValue());
		assertEquals(IsoformSequenceStatus.DISPLAYED, isoform1.getIsoformSequenceStatus());
		assertEquals(0, isoform1.getSequenceIds().size());
		
		APIsoform isoform3= comment.getIsoforms().get(2);
		assertEquals("Bim-alpha3", isoform3.getName().getValue());
		assertEquals(3, isoform3.getSynonyms().size());
		assertEquals("Bim-AD", isoform3.getSynonyms().get(1).getValue());
		assertEquals(1, isoform3.getIsoformIds().size());
		assertEquals("Q9V8R9-3", isoform3.getIsoformIds().get(0).getValue());
		assertEquals(3, isoform3.getSequenceIds().size());
		assertEquals("VSP_000478", isoform3.getSequenceIds().get(1));
		assertNull(isoform3.getNote());
		APIsoform isoform5= comment.getIsoforms().get(4);
		assertEquals(2, isoform5.getNote().getTexts().size());
		assertEquals("No experimental confirmation available.", isoform5.getNote().getTexts().get(0).getValue());
		assertEquals(2, isoform5.getNote().getTexts().get(0).getEvidences().size());
		assertEquals("ECO:0000269|PubMed:10433554", isoform5.getNote().getTexts().get(0).getEvidences().get(0).getValue());
		assertEquals("ECO:0000313|EMBL:BAG16761.1", isoform5.getNote().getTexts().get(0).getEvidences().get(1).getValue());
		assertEquals("Another no experimental confirmation also available.", isoform5.getNote().getTexts().get(1).getValue());
		assertEquals(2, isoform5.getNote().getTexts().get(0).getEvidences().size());
		assertEquals("ECO:0000269|PubMed:1043355", isoform5.getNote().getTexts().get(1).getEvidences().get(0).getValue());
		assertEquals("ECO:0000313|EMBL:BAG16761.1", isoform5.getNote().getTexts().get(1).getEvidences().get(1).getValue());
		
	}
	@Test
	public void testWithEvidenceFormated(){
		String ccLineStringEvidence ="ALTERNATIVE PRODUCTS:\n" +
		        "Event=Alternative splicing; Named isoforms=6;\n" +
		        "  Comment=Additional isoforms seem to exist. {ECO:0000269|PubMed:10433554, ECO:0000303|Ref.6};"
		        + " Another additional isoforms also seem to exist. {ECO:0000269|PubMed:10433554};\n" +
		        "Name=1 {ECO:0000313|EMBL:BAG16761.1, ECO:0000313|PDB:3OW2}; Synonyms=A {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" +
		        "  IsoId=Q9V8R9-1; Sequence=Displayed;\n" +
		        "  Note=Does not exhibit APOBEC1 complementation activity. Ref.4 sequence is in conflict in positions: 33:I->T."
		        + " No experimental confirmation available. {ECO:0000313|PDB:3OW2};\n" +
		        "Name=2;\n" +
		        "  IsoId=Q9V8R9-2; Sequence=VSP_000476, VSP_000477, VSP_000479, VSP_000480, VSP_000481;\n" +
		        "Name=Bim-alpha3 {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2}; Synonyms=BCL2-like 11 transcript"
		        + " variant 10 {ECO:0000313|EMBL:BAG16761.1}, Bim-AD, BimAD {ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|PDB:3OW2};\n" +
		        "  IsoId=Q9V8R9-3; Sequence=VSP_000475, VSP_000478, VSP_000479;\n" +
		        "Name=4; Synonyms=B;\n" +
		        "  IsoId=Q9V8R9-4; Sequence=VSP_000476, VSP_000477, VSP_000479;\n" +
		        "Name=5;\n" +
		        "  IsoId=Q9V8R9-5; Sequence=VSP_000474, VSP_000478;\n" +
		        "Note=No experimental confirmation available. {ECO:0000269|PubMed:10433554, ECO:0000313|EMBL:BAG16761.1};"
		        + " Another no experimental confirmation also available. {ECO:0000269|PubMed:1043355, ECO:0000313|EMBL:BAG16761.1};\n" +
		        "Name=6; Synonyms=D;\n" +
		        "  IsoId=Q9V8R9-6; Sequence=Described;\n" +
		        "  Note=No experimental confirmation.;";
		AlternativeProductsComment comment =
		transformer.transform(CommentType.ALTERNATIVE_PRODUCTS, ccLineStringEvidence);	
		
		assertEquals(1, comment.getEvents().size());
		assertEquals("Alternative splicing", comment.getEvents().get(0).getName());
		assertEquals(2, comment.getNote().getTexts().size());
		assertEquals("Additional isoforms seem to exist.", comment.getNote().getTexts().get(0).getValue());
		assertEquals(2, comment.getNote().getTexts().get(0).getEvidences().size());
		assertEquals("Another additional isoforms also seem to exist.", comment.getNote().getTexts().get(1).getValue());
		assertEquals(1, comment.getNote().getTexts().get(1).getEvidences().size());
		assertEquals("ECO:0000269|PubMed:10433554", comment.getNote().getTexts().get(1).getEvidences().get(0).getValue());
		assertEquals(6, comment.getIsoforms().size());
		APIsoform isoform1= comment.getIsoforms().get(0);
		assertEquals("1", isoform1.getName().getValue());
		assertEquals(2, isoform1.getName().getEvidences().size());
		assertEquals("ECO:0000313|EMBL:BAG16761.1", isoform1.getName().getEvidences().get(0).getValue());
		assertEquals("ECO:0000313|PDB:3OW2", isoform1.getName().getEvidences().get(1).getValue());
		assertEquals(IsoformSequenceStatus.DISPLAYED, isoform1.getIsoformSequenceStatus());
		assertEquals(0, isoform1.getSequenceIds().size());
		
		APIsoform isoform3= comment.getIsoforms().get(2);
		assertEquals("Bim-alpha3", isoform3.getName().getValue());
		assertEquals(3, isoform3.getSynonyms().size());
		assertEquals("Bim-AD", isoform3.getSynonyms().get(1).getValue());
		assertEquals(1, isoform3.getIsoformIds().size());
		assertEquals("Q9V8R9-3", isoform3.getIsoformIds().get(0).getValue());
		assertEquals(3, isoform3.getSequenceIds().size());
		assertEquals("VSP_000478", isoform3.getSequenceIds().get(1));
		assertNull(isoform3.getNote());
		APIsoform isoform5= comment.getIsoforms().get(4);
		assertEquals(2, isoform5.getNote().getTexts().size());
		assertEquals("No experimental confirmation available.", isoform5.getNote().getTexts().get(0).getValue());
		assertEquals(2, isoform5.getNote().getTexts().get(0).getEvidences().size());
		assertEquals("ECO:0000269|PubMed:10433554", isoform5.getNote().getTexts().get(0).getEvidences().get(0).getValue());
		assertEquals("ECO:0000313|EMBL:BAG16761.1", isoform5.getNote().getTexts().get(0).getEvidences().get(1).getValue());
		assertEquals("Another no experimental confirmation also available.", isoform5.getNote().getTexts().get(1).getValue());
		assertEquals(2, isoform5.getNote().getTexts().get(0).getEvidences().size());
		assertEquals("ECO:0000269|PubMed:1043355", isoform5.getNote().getTexts().get(1).getEvidences().get(0).getValue());
		assertEquals("ECO:0000313|EMBL:BAG16761.1", isoform5.getNote().getTexts().get(1).getEvidences().get(1).getValue());
		
	}
}
