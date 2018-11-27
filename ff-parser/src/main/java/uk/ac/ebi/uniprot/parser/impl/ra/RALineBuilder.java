package uk.ac.ebi.uniprot.parser.impl.ra;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.citation.Author;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.RLine;

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
			return FFLineWrapper.buildLine(tokens, COMA, SPACE, SEMICOLON,linePrefix, LINE_LENGTH, includeFFMarkup);
		else{
			return FFLineWrapper.buildLine(tokens, COMA, SPACE, SEMICOLON, "", LINE_LENGTH, includeFFMarkup);
		}
		
	}

}
