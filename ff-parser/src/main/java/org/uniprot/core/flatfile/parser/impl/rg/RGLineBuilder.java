package org.uniprot.core.flatfile.parser.impl.rg;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.RLine;

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
			sb.append(SEMICOLON);	
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
