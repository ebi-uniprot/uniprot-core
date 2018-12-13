package uk.ac.ebi.uniprot.parser.impl.pe;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.ProteinExistence;
import uk.ac.ebi.uniprot.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLines;

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
		pe.append(f.toDisplayName());
		pe.append(SEMICOLON);
		lines.add(pe.toString());
		return FFLines.create(lines);
	}
}
