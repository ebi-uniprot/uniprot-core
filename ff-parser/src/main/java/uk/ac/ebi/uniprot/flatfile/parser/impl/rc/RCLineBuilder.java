package uk.ac.ebi.uniprot.flatfile.parser.impl.rc;

import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.LineBuilder;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.LineBuilderHelper;
import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.RLine;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.ReferenceComment;
import org.uniprot.core.uniprot.ReferenceCommentType;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.*;

public class RCLineBuilder implements RLine<List<ReferenceComment>> {
	private final LineType lineType = LineType.RC;
	 private final String linePrefix = lineType + DEFAUT_LINESPACE;
	 private static ReferenceCommentType[] order = { ReferenceCommentType.STRAIN,
			 ReferenceCommentType.PLASMID, ReferenceCommentType.TRANSPOSON,
			 ReferenceCommentType.TISSUE };

	public List<String> buildLine(List<ReferenceComment> f,
			boolean includeFFMarkup, boolean showEvidence) {
		if(!includeFFMarkup)
			return buildLineStr(f);
		if(f.isEmpty()){
			return new ArrayList<>();
		}
		LineBuilder lineBuilder =new LineBuilder(linePrefix, lineType);
		List<StringBuilder> typeBuilders = buildRefComments(f, showEvidence);
		for(StringBuilder typeBuilder:typeBuilders){
			lineBuilder.addItem(typeBuilder.toString(), SPACE);
		}
		lineBuilder.finish("");
		return lineBuilder.getLines();
	}
	
	private List<StringBuilder> buildRefComments(List<ReferenceComment> f, boolean showEvidence){
		List<StringBuilder> typeBuilders = new ArrayList<>();
		for (ReferenceCommentType stype : order) {
			List<ReferenceComment> referenceComments = getReferenceComment(f, stype);
			if(referenceComments.isEmpty())
				continue;
			StringBuilder typeBuilder = 
					buildRefComments(stype, referenceComments, showEvidence);
			typeBuilders.add(typeBuilder);

		}
		return typeBuilders;
	}
	
	private String getSeparater(boolean first, boolean last) {
		if(first)
			return SPACE;
		else if(last) {
			return COMA+SEPARATOR_AND;
		}else
			return SEPARATOR_COMA;
	}
	
	
	private StringBuilder buildRefComments( ReferenceCommentType stype, List<ReferenceComment> referenceComments,
			boolean showEvidence) {
		int counter = 0;
		boolean first = true;
		StringBuilder typeBuilder = new StringBuilder();
		for(ReferenceComment referenceComment: referenceComments) {
			StringBuilder item = new StringBuilder();			
			counter++;
			String separator = getSeparater(first,referenceComments.size() == counter );
			
			if (first) {
				item.append(stype.toString());
				item.append("=");
				first = false;
			}
			item.append(referenceComment.getValue());
			if (showEvidence) {				
				item.append(LineBuilderHelper.export(referenceComment.getEvidences()));			
			}

			if(typeBuilder.length() !=0)
				typeBuilder.append(separator);
			typeBuilder.append(item);
		}
		if(typeBuilder.length() !=0)
			typeBuilder.append(";");
		
		return typeBuilder;
	}
	
	private List<String> buildLineStr(List<ReferenceComment> f) {
		if(f.isEmpty()){
			return new ArrayList<>();
		}
		List<StringBuilder> typeBuilders = buildRefComments(f, false);
		StringBuilder lineBuilder = new StringBuilder();
		int count =0;
		for(StringBuilder typeBuilder:typeBuilders){
			if(count>0)
				lineBuilder.append(SPACE);
			lineBuilder.append(typeBuilder.toString());
			count++;
		}
		List<String> lines = new ArrayList<>();
		lines.add(lineBuilder.toString());
		return  lines;
	}
	

	private List<ReferenceComment> getReferenceComment(List<ReferenceComment> sss, ReferenceCommentType stype){
		return
				sss.stream().filter(val -> val.getType() ==stype)
				.collect(Collectors.toList());
		
	}
}
