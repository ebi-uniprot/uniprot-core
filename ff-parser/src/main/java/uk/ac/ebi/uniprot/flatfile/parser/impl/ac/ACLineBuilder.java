package uk.ac.ebi.uniprot.flatfile.parser.impl.ac;

import uk.ac.ebi.uniprot.domain.uniprot.UniProtAccession;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLine;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.FFLineBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineBuilderAbstr;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLines;

import java.util.List;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.SEMICOLON;
import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.SEPARATOR_SEMICOLON;


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
		List<String> lls = FFLineWrapper.buildLines(sb, SEPARATOR_SEMICOLON, linePrefix);
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
				sb.append(SEPARATOR_SEMICOLON);
			}
			sb.append(ac.getValue());
			isFirst =false;
		}
		sb.append(SEMICOLON);
		return sb;
	}
}
