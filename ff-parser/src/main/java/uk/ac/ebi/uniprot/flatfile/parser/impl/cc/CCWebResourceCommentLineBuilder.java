package uk.ac.ebi.uniprot.flatfile.parser.impl.cc;

import com.google.common.base.Strings;
import uk.ac.ebi.uniprot.domain.uniprot.comment.WebResourceComment;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineWrapper;

import java.util.ArrayList;
import java.util.List;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.*;

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
		sb.append(SEMICOLON);
		if(!Strings.isNullOrEmpty(comment.getNote()) ){
			sb.append(SPACE);
			sb.append(NOTE);
			sb.append(comment.getNote());
			sb.append(SEMICOLON);
		}
		if(includeFlatFileMarkings)
			lines.addAll(FFLineWrapper.buildLines(sb, SEPARATOR, linePrefix));
		else{
			lines.add(sb.toString());
		}
		if(!Strings.isNullOrEmpty(comment.getResourceUrl())) {
			sb = new StringBuilder();
			if (includeFlatFileMarkings)
				sb.append(linePrefix);
			if(comment.isFtp())
				sb.append("FTP=\"");
			else
				sb.append("URL=\"");
			sb.append(comment.getResourceUrl());
			sb.append("\";");
			lines.add(sb.toString());
		}
	
		return lines;
		
	}

}
