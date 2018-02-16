package uk.ac.ebi.uniprot.ffwriter.line.impl;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.FFLines;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLine;
import uk.ac.ebi.uniprot.domain.uniprot.InternalLineType;

public class SSInternalLineBuilder extends FFLineBuilderAbstr<List<InternalLine> >{

	public SSInternalLineBuilder(){
		super(LineType.STAR_STAR);
	}
	
	@Override
	public String buildString(List<InternalLine> f) {
		return buildLine(f, false).toString();
	}
	@Override
	public String buildStringWithEvidence(List<InternalLine> f) {
		return buildLine(f, true).toString();
	}

	
	@Override
	protected FFLine buildLine(List<InternalLine> internalLines, boolean showEvidence) {
		List<String> lines =new ArrayList<>();
		for (InternalLine line : internalLines) {

			if (InternalLineType.PROSITE != line.getInternalLineType()) {
				StringBuilder starStar =new StringBuilder();
				starStar.append("**");
				starStar.append(line.getInternalLineType());
				starStar.append(" ");
				starStar.append(line.getValue());
				lines.add(starStar.toString());
			}

		}
		return FFLines.create(lines);
	}

}
