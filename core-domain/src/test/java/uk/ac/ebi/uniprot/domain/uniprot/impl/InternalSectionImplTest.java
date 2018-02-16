package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

public class InternalSectionImplTest {

    @Test
    public void testCreateInternalLine() {
        InternalLineType type = InternalLineType.CP;
        String value = "some value";
        InternalLine internalLine= InternalSectionImpl.createInternalLine(type, value);
        assertEquals(type, internalLine.getInternalLineType());
        assertEquals(value, internalLine.getValue());
    }

    @Test
    public void testCreateSourceLine() {
        String value = "some source value";
        SourceLine sourceLine =InternalSectionImpl.createSourceLine(value);
        assertEquals(value, sourceLine.getValue());
    }

    @Test
    public void testInternalSectionImpl() {
        List<InternalLine> internalLines = new ArrayList<>();      
        List<SourceLine> sourceLines = new ArrayList<>();     
        internalLines.add(InternalSectionImpl.createInternalLine(InternalLineType.CX, "value1"));
        internalLines.add(InternalSectionImpl.createInternalLine(InternalLineType.EV, "value2"));
        sourceLines.add(InternalSectionImpl.createSourceLine("some value"));
        InternalSection is = new InternalSectionImpl(internalLines, null, sourceLines);
        assertEquals(internalLines, is.getInternalLines());
        assertEquals(sourceLines, is.getSourceLines());
    }

}
