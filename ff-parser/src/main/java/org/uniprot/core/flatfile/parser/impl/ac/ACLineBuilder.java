package org.uniprot.core.flatfile.parser.impl.ac;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEMICOLON;
import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.SEPARATOR_SEMICOLON;

import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprot.UniProtAccession;


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
