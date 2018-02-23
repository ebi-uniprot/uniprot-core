package uk.ac.ebi.uniprot.ffwriter.line.impl;

import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineBuilder;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineWrapper;
import uk.ac.ebi.uniprot.ffwriter.line.FFLines;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.*;


public class ACLineBuilder extends FFLineBuilderAbstr< List<UniProtAccession> > implements
		FFLineBuilder< List<UniProtAccession> > {
	public ACLineBuilder(){
		super(LineType.AC);
	}

	@Override
	public String buildString(List<UniProtAccession> f) {
			return build(f, false).toString();
	}
	@Override
	public String buildStringWithEvidence(List<UniProtAccession> f) {
			return build(f, true).toString();
	}

	@Override
	protected FFLine buildLine(List<UniProtAccession> f, boolean showEvidence) {
		StringBuilder sb = build(f, true );
		List<String> lls = FFLineWrapper.buildLines(sb, SEPARATOR_SEMICOMA, linePrefix);
		return FFLines.create(lls);
	}
	private StringBuilder build(List<UniProtAccession> f,  boolean includeFFMarkup){

		StringBuilder sb = new StringBuilder();
		if(includeFFMarkup){
			sb.append(linePrefix);
		}
		boolean isFirst =true;
		for(UniProtAccession ac:f){
			if(!isFirst){
				sb.append(SEPARATOR_SEMICOMA);
			}
			sb.append(ac.getValue());
			isFirst =false;
		}
		sb.append(SEMI_COMA);
		return sb;
	}
}
