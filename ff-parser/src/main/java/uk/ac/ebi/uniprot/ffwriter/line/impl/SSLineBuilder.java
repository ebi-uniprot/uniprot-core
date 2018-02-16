package uk.ac.ebi.uniprot.ffwriter.line.impl;

import java.util.ArrayList;
import java.util.List;
import uk.ac.ebi.uniprot.domain.uniprot.InternalSection;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.FFLines;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;

public class SSLineBuilder extends FFLineBuilderAbstr<InternalSection> {
	private static final String INTERNAL_SECTION ="**   #################    INTERNAL SECTION    ##################";
	private static final String START_LINE ="**";
	private  SSSourceLineBuilder sourceLineBuilder =new SSSourceLineBuilder();
	private  SSEvidenceLineBuilder evidenceLineBuilder =new SSEvidenceLineBuilder();
	private  SSInternalLineBuilder internalLineBuilder =new SSInternalLineBuilder();
	public SSLineBuilder(){
		super(LineType.STAR_STAR);
	}
	@Override
	public String buildString(InternalSection f) {
		return buildLine(f, false).toString();
	}
	
	@Override
	public String buildStringWithEvidence(InternalSection f) {
		return buildLine(f, true).toString();
	}


	@Override
	protected FFLine buildLine(InternalSection f, boolean showEvidence) {
		FFLine sourceLines = sourceLineBuilder.buildWithEvidence(f.getSourceLines());
		FFLine evidenceLines = evidenceLineBuilder.buildWithEvidence(f.getEvidenceLines());		
		FFLine internalLines = internalLineBuilder.buildWithEvidence(f.getInternalLines());
		evidenceLines.add(internalLines);
		
		List<String> lines = new ArrayList<>();
		if(sourceLines.isEmpty() && evidenceLines.isEmpty()){
			return FFLines.create(lines);
		}
		lines.add(START_LINE);
		lines.addAll(sourceLines.lines());
		if(!evidenceLines.isEmpty()){
			lines.add(INTERNAL_SECTION);
			lines.addAll(evidenceLines.lines());
		}
		return FFLines.create(lines);
	}

}
