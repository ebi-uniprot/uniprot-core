package uk.ac.ebi.uniprot.ffwriter.line.impl;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.FFLines;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;


public class PELineBuilder extends FFLineBuilderAbstr<ProteinExistence> {
	public PELineBuilder(){
		super(LineType.PE);
	}

	@Override
	public String buildString(ProteinExistence f) {
		return buildLine(f, false, false).toString();
	}

	@Override
	public String buildStringWithEvidence(ProteinExistence f) {
		return buildLine(f, false, true).toString();
	}

	
	@Override
	protected FFLine buildLine(ProteinExistence f, boolean showEvidence) {
		return buildLine(f, true, showEvidence);
	}

	private FFLine buildLine(ProteinExistence f, boolean includeFFMark, boolean showEvidence) {
		List<String> lines =new ArrayList<>();
		StringBuilder pe = new StringBuilder();
		if(includeFFMark)
			pe.append(linePrefix);
		pe.append(f.getDisplayName());
		pe.append(SEMI_COMA);
		lines.add(pe.toString());
		return FFLines.create(lines);
	}
}
