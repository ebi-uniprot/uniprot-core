package uk.ac.ebi.uniprot.flatfile.parser.impl.ra;

import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineWrapper;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.RLine;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.citation.Author;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.*;

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
