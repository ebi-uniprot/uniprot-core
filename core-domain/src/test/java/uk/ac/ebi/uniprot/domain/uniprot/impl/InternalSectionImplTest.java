package uk.ac.ebi.uniprot.domain.uniprot.impl;

import org.junit.jupiter.api.Test;
import uk.ac.ebi.uniprot.domain.TestHelper;
import uk.ac.ebi.uniprot.domain.uniprot.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;



public class InternalSectionImplTest {
	
	@Test
	void testEvidenceLineImpl() {
		LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
    	String curator ="som curator";
    	String evidence ="ECO:0000269|PubMed:22481068";
    	EvidenceLine evline = new  EvidenceLineImpl(evidence,  createDate,  curator ) ;
    	assertEquals(createDate, evline.getCreateDate());
    	assertEquals(curator, evline.getCurator());
    	assertEquals(evidence, evline.getEvidence());
    	assertEquals(evidence, evline.toEvidence().getValue());
    	TestHelper.verifyJson(evline);
    	
	}
	

    @Test
     void testCreateInternalLine() {
        InternalLineType type = InternalLineType.CP;
        String value = "some value";
        InternalLine internalLine= new InternalLineImpl(type, value);
        assertEquals(type, internalLine.getType());
        assertEquals(value, internalLine.getValue());
        TestHelper.verifyJson(internalLine);
    }

    @Test
    public void testCreateSourceLine() {
        String value = "some source value";
        SourceLine sourceLine =new SourceLineImpl(value);
        assertEquals(value, sourceLine.getValue());
        TestHelper.verifyJson(sourceLine);
    }

    @Test
    public void testInternalSectionImplOnlyIL() {
        List<InternalLine> internalLines = new ArrayList<>();      
        List<SourceLine> sourceLines = new ArrayList<>();     
        internalLines.add( new InternalLineImpl(InternalLineType.CX, "value1"));
        internalLines.add( new InternalLineImpl(InternalLineType.EV, "value2"));
        sourceLines.add(new SourceLineImpl("some value"));
        InternalSection is = new InternalSectionImpl(internalLines, null, null);
        assertEquals(internalLines, is.getInternalLines());
        assertTrue(is.getSourceLines().isEmpty());
        assertTrue( is.getEvidenceLines().isEmpty());
        TestHelper.verifyJson(is);
    }
    
    @Test
    public void testInternalSectionImplOnlyEL() {
    	List<EvidenceLine> evlines = new ArrayList<>();
    	LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
    	String curator ="som curator";
    	evlines.add(new EvidenceLineImpl("ECO:0000269|PubMed:22481068",  createDate,  curator ) );
        InternalSection is = new InternalSectionImpl(null, evlines, null);
        assertTrue(is.getSourceLines().isEmpty());
        assertTrue( is.getInternalLines().isEmpty());
        assertEquals(evlines, is.getEvidenceLines());
        TestHelper.verifyJson(is);
    }
    @Test
    public void testInternalSectionImpl() {
        List<InternalLine> internalLines = new ArrayList<>();      
        List<SourceLine> sourceLines = new ArrayList<>();     
        internalLines.add(new InternalLineImpl(InternalLineType.CX, "value1"));
        internalLines.add(new InternalLineImpl(InternalLineType.EV, "value2"));
        sourceLines.add(new SourceLineImpl("some value"));
        
        List<EvidenceLine> evlines = new ArrayList<>();
    	LocalDate createDate = LocalDate.of(2015, Month.AUGUST, 2);
    	String curator ="som curator";
    	evlines.add(new EvidenceLineImpl("ECO:0000269|PubMed:22481068",  createDate,  curator ) ); 
        InternalSection is = new InternalSectionImpl(internalLines, evlines, sourceLines);
        assertEquals(internalLines, is.getInternalLines());
        assertEquals(sourceLines, is.getSourceLines());
        assertEquals(evlines, is.getEvidenceLines());
        TestHelper.verifyJson(is);
    }
}
