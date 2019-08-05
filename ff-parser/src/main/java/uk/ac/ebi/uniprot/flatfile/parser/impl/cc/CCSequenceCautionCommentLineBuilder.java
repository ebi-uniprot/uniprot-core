package uk.ac.ebi.uniprot.flatfile.parser.impl.cc;

import com.google.common.base.Strings;

import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.LineBuilderHelper;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.SequenceCautionComment;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.*;

/**
 * 	"CC   -!- SEQUENCE CAUTION:\n" +
 *	"CC       Sequence=CAA57511.1; Type=Frameshift; Positions=421, 589, 591; Note=The predicted gene.; Evidence={ECO:0000256|HAMAP-Rule:MF_00205, ECO:0000313|Ensembl:ENSP00000409133};"
 * @author jieluo
 *
 */
public class CCSequenceCautionCommentLineBuilder extends CCLineBuilderAbstr<SequenceCautionComment> {
	
	private boolean isFirstSequenceCauction =true;
	public void setIsFirstSequenceCaution(boolean isFirstSequenceCauction){
		this.isFirstSequenceCauction =isFirstSequenceCauction;
	}

	@Override
	protected List<String> buildCommentLines(SequenceCautionComment comment,  boolean includeFFMarkings, boolean showEvidence, boolean includeCommentType) {
		StringBuilder sb = new StringBuilder();
		List<String> lines = new ArrayList<>();	
		if (isFirstSequenceCauction) {
			if(includeCommentType)
				lines.add(buildStart(comment, includeFFMarkings));
		}
		if (includeFFMarkings)
			sb.append(this.linePrefix);
		boolean needSpace =false;
		if((comment.getSequence() !=null)
				&&(!comment.getSequence().isEmpty())){
			sb.append("Sequence=").append(comment.getSequence()).append(SEMICOLON);
			needSpace =true;
		}
		if(comment.getSequenceCautionType() !=null){
			if(needSpace)
				sb.append(SPACE);
			sb.append("Type=").append(comment.getSequenceCautionType().toDisplayName()).append(SEMICOLON);
			needSpace =true;
		}
		 if ((comment.getPositions() != null) && (comment.getPositions().size() > 0)) {
			 if(needSpace)
				 sb.append(SPACE);
			 sb.append("Positions=");
			 for (int i=0;i<comment.getPositions().size();i++){
				 sb.append(comment.getPositions().get(i));
				 if ((i+1)==comment.getPositions().size()){
					 sb.append(SEMICOLON);
				 }else{
					 sb.append(SEPARATOR_COMA);
				 }

			 }
			 needSpace =true;
		 }
		 if(!Strings.isNullOrEmpty(comment.getNote())) {
			 if(needSpace)
				 sb.append(SPACE);
			 sb.append(NOTE);
			 sb.append(comment.getNote());
			 sb.append(SEMICOLON);
		 }
	
			if (showEvidence && comment.getEvidences().size()>0) {
				sb.append(SPACE);
				sb.append("Evidence=");
				String evStr =  LineBuilderHelper.export(comment.getEvidences()).trim();
				sb.append(evStr).append(SEMICOLON);
			}
		
	//	List<String> lls = NewLineWrapperHelper.buildLines(sb, SEPARATOR, linePrefix);
		lines.add(sb.toString());
		return lines;
	}
}
