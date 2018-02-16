package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.uniprot.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;

import java.util.Collections;
import java.util.List;

public class InternalSectionImpl implements InternalSection {
    public static InternalLine createInternalLine(InternalLineType type, String value) {
        return new InternalLineImpl(type, value);
    }
    public static SourceLine createSourceLine(String value){
        return new SourceLineImpl(value);
    }

    
    private final List<InternalLine> internalLines;
    private final List<EvidenceLine> evidenceLines;
    private final List<SourceLine> sourceLines;
    public InternalSectionImpl(List<InternalLine> internalLines, 
    		List<EvidenceLine> evidenceLines,
    		List<SourceLine> sourceLines){
        if ((internalLines == null) || internalLines.isEmpty()) {
            this.internalLines = Collections.emptyList();
        } else {
            this.internalLines = Collections.unmodifiableList(internalLines);
        }
        if ((evidenceLines == null) || evidenceLines.isEmpty()) {
            this.evidenceLines = Collections.emptyList();
        } else {
            this.evidenceLines = Collections.unmodifiableList(evidenceLines);
        }
        
        if ((sourceLines == null) || sourceLines.isEmpty()) {
            this.sourceLines = Collections.emptyList();
        } else {
            this.sourceLines = Collections.unmodifiableList(sourceLines);
        }
    }
    @Override
    public List<InternalLine> getInternalLines() {
       return internalLines;
    }

    @Override
    public List<SourceLine> getSourceLines() {
        return sourceLines;
    }
    
    @Override
    public List<EvidenceLine> getEvidenceLines() {
        return evidenceLines;
    }
    
    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((evidenceLines == null) ? 0 : evidenceLines.hashCode());
		result = prime * result + ((internalLines == null) ? 0 : internalLines.hashCode());
		result = prime * result + ((sourceLines == null) ? 0 : sourceLines.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		InternalSectionImpl other = (InternalSectionImpl) obj;
		if (evidenceLines == null) {
			if (other.evidenceLines != null)
				return false;
		} else if (!evidenceLines.equals(other.evidenceLines))
			return false;
		if (internalLines == null) {
			if (other.internalLines != null)
				return false;
		} else if (!internalLines.equals(other.internalLines))
			return false;
		if (sourceLines == null) {
			if (other.sourceLines != null)
				return false;
		} else if (!sourceLines.equals(other.sourceLines))
			return false;
		return true;
	}

	static class InternalLineImpl extends ValueImpl implements InternalLine{
        private final InternalLineType type;
        public InternalLineImpl(InternalLineType type, String value) {
            super(value);
            this.type = type;
        }

        @Override
        public InternalLineType getInternalLineType() {
            return type;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = super.hashCode();
            result = prime * result + ((type == null) ? 0 : type.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (!super.equals(obj))
                return false;
            if (getClass() != obj.getClass())
                return false;
            InternalLineImpl other = (InternalLineImpl) obj;
            if (type != other.type)
                return false;
            return true;
        }

    }
    static class SourceLineImpl extends ValueImpl implements SourceLine{

        public SourceLineImpl(String value) {
            super(value);
        }
        
    }
}
