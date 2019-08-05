package org.uniprot.core.flatfile.parser.impl.cc;

import com.google.common.base.Strings;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.uniprot.comment.WebResourceComment;

/**
 * 
 * @author jieluo
 *CC   -!- WEB RESOURCE: Name=CD40Lbase; Note=CD40L defect database;
 |CC       URL="http://bioinf.uta.fi/CD40Lbase/";
 */

public class CCWebResourceCommentLineBuilder extends CCLineBuilderAbstr<WebResourceComment> {


	@Override
	protected List<String> buildCommentLines(WebResourceComment comment, boolean includeFlatFileMarkings, boolean showEvidence, boolean includeCommentType){
		List<String> lines = new ArrayList<>();
		StringBuilder sb = new StringBuilder();
		if(includeFlatFileMarkings)
			addFlatFileMarkingsIfRequired(includeFlatFileMarkings, sb);
		if(includeCommentType) {
			sb.append(comment.getCommentType().toDisplayName());
			sb.append(": ");
		}
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
