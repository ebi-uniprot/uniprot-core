package uk.ac.ebi.uniprot.ffwriter.line.impl.rlines;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineWrapper;
import uk.ac.ebi.uniprot.ffwriter.line.LineType;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.*;

public class RALineBuilder implements RLine< List<Author> > {
	private final LineType lineType = LineType.RA;
	private final String linePrefix = lineType + DEFAUT_LINESPACE;
	@Override
	public List<String> buildLine(List<Author> f, boolean includeFFMarkup,
			boolean showEvidence) {
		if((f==null)||(f.size()==0)){
			new ArrayList<>();
		}

		List<String> tokens =new ArrayList<>();
		for(Author author:f){
			tokens.add(author.getValue());
		}	
		if(includeFFMarkup)
			return FFLineWrapper.buildLine(tokens, COMA, SPACE, SEMI_COMA,linePrefix, LINE_LENGTH, includeFFMarkup);
		else{
			return FFLineWrapper.buildLine(tokens, COMA, SPACE, SEMI_COMA, "", LINE_LENGTH, includeFFMarkup);
		}
		
	}

}
