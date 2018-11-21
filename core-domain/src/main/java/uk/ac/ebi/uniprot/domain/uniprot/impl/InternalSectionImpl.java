package uk.ac.ebi.uniprot.domain.uniprot.impl;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.ac.ebi.uniprot.domain.uniprot.EvidenceLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.domain.uniprot.SourceLine;
import uk.ac.ebi.uniprot.domain.util.Utils;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class InternalSectionImpl implements InternalSection {

   
    private final List<InternalLine> internalLines;
    private final List<EvidenceLine> evidenceLines;
    private final List<SourceLine> sourceLines;
    @JsonCreator
    public InternalSectionImpl(@JsonProperty("internalLines")  List<InternalLine> internalLines, 
    		@JsonProperty("evidenceLines")  List<EvidenceLine> evidenceLines,
    		@JsonProperty("sourceLines")  List<SourceLine> sourceLines){
    	this.internalLines =Utils.unmodifierList(internalLines);
    	this.evidenceLines =Utils.unmodifierList(evidenceLines);
    	this.sourceLines =Utils.unmodifierList(sourceLines);
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

}
