package uk.ac.ebi.uniprot.parser.impl.cc;

import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SEMI_COMA;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SEPARATOR;
import static uk.ac.ebi.uniprot.ffwriter.line.FFLineConstant.SPACE;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.comments.WebResourceComment;
import uk.ac.ebi.uniprot.ffwriter.line.FFLineWrapper;

/**
 * 
 * @author jieluo
 *CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=CD40L defect database;
 |CC       URL="http://bioinf.uta.fi/CD40Lbase/";
 */

public class CCWebResourceCommentLineBuilder extends CCLineBuilderAbstr<WebResourceComment> {


	@Override
	protected List<String> buildCommentLines(WebResourceComment comment, boolean includeFlatFileMarkings, boolean showEvidence){
		List<String> lines = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		if(includeFlatFileMarkings)
			addFlatFileMarkingsIfRequired(includeFlatFileMarkings, sb);
		sb.append(comment.getCommentType().toDisplayName());
		sb.append(": ");
		sb.append(NAME);
		sb.append(comment.getResourceName());
		sb.append(SEMI_COMA);
		if(comment.getNote().isPresent()) {
			sb.append(SPACE);
			sb.append(NOTE);
			sb.append(comment.getNote().get());
			sb.append(SEMI_COMA);
		}
		if(includeFlatFileMarkings)
			lines.addAll(FFLineWrapper.buildLines(sb, SEPARATOR, linePrefix));
		else{
			lines.add(sb.toString());
		}
		if(comment.getResourceUrl().isPresent()) {
			sb = new StringBuilder();
			if (includeFlatFileMarkings)
				sb.append(linePrefix);
			if(comment.isFtp())
				sb.append("FTP=\"");
			else
				sb.append("URL=\"");
			sb.append(comment.getResourceUrl().get());
			sb.append("\";");
			lines.add(sb.toString());
		}
	
		return lines;
		
	}

}
