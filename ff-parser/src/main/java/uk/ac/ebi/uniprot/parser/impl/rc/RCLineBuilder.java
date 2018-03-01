package uk.ac.ebi.uniprot.parser.impl.rc;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.COMA;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.DEFAUT_LINESPACE;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPARATOR_AND;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SEPARATOR_COMA;
import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.SPACE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.parser.ffwriter.LineType;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.LineBuilder;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.LineBuilderHelper;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.RLine;

public class RCLineBuilder implements RLine< List<ReferenceComment> >{
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
		List<StringBuilder> typeBuilders = new ArrayList<StringBuilder>();
		boolean first = true;

		// iterator over the types in order
		for (ReferenceCommentType stype : order) {
			List<ReferenceComment> referenceComments = getReferenceComment(f, stype);
			if(referenceComments.isEmpty())
				continue;
			int counter = 0;
			first = true;
			StringBuilder typeBuilder = new StringBuilder();
			for(ReferenceComment referenceComment: referenceComments) {

				StringBuilder item = new StringBuilder();
				String separator;

				counter++;
				if(first) {
					separator =SPACE;
				}
				else if (referenceComments.size() == counter) {
					separator =COMA+SEPARATOR_AND;
				}else{
					separator =SEPARATOR_COMA;
				}

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
				//    lineBuilder.addItem(item.toString(), separator);
			}
			if(typeBuilder.length() !=0)
				typeBuilder.append(";");
			typeBuilders.add(typeBuilder);

		}

		for(StringBuilder typeBuilder:typeBuilders){
			lineBuilder.addItem(typeBuilder.toString(), SPACE);
		}
		lineBuilder.finish("");
		return lineBuilder.getLines();
	}
	
	
	private List<String> buildLineStr(List<ReferenceComment> f) {
		if(f.isEmpty()){
			return new ArrayList<>();
		}
	//	LineBuilder lineBuilder =new LineBuilder(linePrefix, lineType);
		List<StringBuilder> typeBuilders = new ArrayList<StringBuilder>();
		boolean first = true;

		// iterator over the types in order

		for (ReferenceCommentType stype : order) {
			List<ReferenceComment> referenceComments = getReferenceComment(f, stype);
			if(referenceComments.isEmpty())
				continue;
			int counter = 0;
			first = true;
			StringBuilder typeBuilder = new StringBuilder();
			for(ReferenceComment referenceComment: referenceComments) {

				StringBuilder item = new StringBuilder();
				String separator;

				counter++;
				if(first) {
					separator =SPACE;
				}
				else if (referenceComments.size() == counter) {
					separator =COMA+SEPARATOR_AND;
				}else{
					separator =SEPARATOR_COMA;
				}

				if (first) {
					item.append(stype.toString());
					item.append("=");
					first = false;
				}
				item.append(referenceComment.getValue());

				if(typeBuilder.length() !=0)
					typeBuilder.append(separator);
				typeBuilder.append(item);
				//    lineBuilder.addItem(item.toString(), separator);
			}
			if(typeBuilder.length() !=0)
				typeBuilder.append(";");
			typeBuilders.add(typeBuilder);

		}

		StringBuilder lineBuilder = new StringBuilder();
		int count =0;
		for(StringBuilder typeBuilder:typeBuilders){
			if(count>0)
				lineBuilder.append(SPACE);
			lineBuilder.append(typeBuilder.toString());
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
