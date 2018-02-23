package uk.ac.ebi.uniprot.ffwriter.line.impl.rlines;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.ffwriter.line.LineType;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.*;

public class RGLineBuilder implements RLine< List<String> > {
	private final LineType lineType = LineType.RG;
	private final String linePrefix = lineType + DEFAUT_LINESPACE;
	@Override
	public List<String> buildLine(List<String> f,
			boolean includeFFMarkup, boolean showEvidence) {
		List<String> lines =new ArrayList<>();
		if((f==null) ||(f.size()==0))
			return lines;
		int count = 0;
		StringBuilder line = new StringBuilder();
		
		for(String authoringGroup : f){		
			StringBuilder sb =new StringBuilder();
			if(includeFFMarkup)
				sb.append(linePrefix);
			if(count>0)
				line.append(SPACE);
			sb.append(authoringGroup);
			sb.append(SEMI_COMA);	
			if(includeFFMarkup)
				lines.add(sb.toString());
			else
				line.append(sb);
			count++;
		
		}
		if(!includeFFMarkup)		
			lines.add(line.toString());
		return lines;
	}

}
