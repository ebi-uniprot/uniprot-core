package uk.ac.ebi.uniprot.ffwriter.line.impl;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.ffwriter.line.FFLine;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineBuilder;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineWrapper;
import uk.ac.ebi.uniprot.ffwriter.line.FFLines;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;
import uk.ac.ebi.uniprot.domain.uniprot.Keyword;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.*;

public class KWLineBuilder extends FFLineBuilderAbstr<List<Keyword> > implements FFLineBuilder< List<Keyword> > {

	public KWLineBuilder(){
		super(LineType.KW);
	}
	@Override
	public String buildString(List<Keyword> f) {
		return FFLines.create( buildLines( f,  false,  false)).toString();
	}

	@Override
	public String buildStringWithEvidence(List<Keyword> f) {
		return FFLines.create( buildLines( f,  false,  true)).toString();
	}

	
	@Override
	protected FFLine buildLine(List<Keyword> f, boolean showEvidence) {
		return FFLines.create( buildLines( f,  true, showEvidence));
	}
	
	private List<String> buildLines(List<Keyword> f,  boolean includeFFMarkup, boolean showEvidence){
		List<String> lines =new ArrayList<> ();
		if(f.isEmpty())
			return lines;
		int counter =0;
		StringBuilder sb = new StringBuilder();
		if(includeFFMarkup)
			sb.append(linePrefix);
		boolean isFirst =true;
		String separator =SPACE;
		for(Keyword keyword: f) {
			StringBuilder item = new StringBuilder();
			counter++;
			item.append(keyword.getValue());
			addEvidences(item, keyword, showEvidence);

			if (f.size() == counter) {
				item.append(STOP);
			}else{
				item.append(SEMI_COMA);
			}
			if(isFirst){
				sb.append(item);
				isFirst =false;
			}else if(sb.length() +item.length()>=(LINE_LENGTH -1)){
				sb.append(separator);
				lines.add(sb.toString().trim());
				sb= new StringBuilder();
				if(includeFFMarkup)
					sb.append(linePrefix);
				sb.append(item);
			
			}else{
				sb.append(separator);
				sb.append(item);
			}
		
			if(sb.length()>LINE_LENGTH && includeFFMarkup){
				List<String> tempLines =FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix, LINE_LENGTH);
				for(int i =0; i<tempLines.size(); i++ ){
					if(i<(tempLines.size()-1)){
						lines.add(tempLines.get(i));
					}else{
						sb = new StringBuilder();
						sb.append(tempLines.get(i));
					}
				}	 
			}
		}
		if(sb.length()>0)
			lines.add(sb.toString());
		return lines;
	}
}
