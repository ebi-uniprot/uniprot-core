package uk.ac.ebi.uniprot.flatfile.parser.impl.cc;

import uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineWrapper;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.uniprot.comment.RnaEdPosition;
import org.uniprot.core.uniprot.comment.RnaEditingComment;

import static uk.ac.ebi.uniprot.flatfile.parser.ffwriter.impl.FFLineConstant.*;

/**
 * 
 * @author jieluo "CC -!- RNA EDITING: Modified_positions=59, 78, 94, 98, 102,
 *         121; Note=The |CC nonsense codon at position 59 is modified to a
 *         sense codon. The |CC stop codon at position 121 is created by RNA
 *         editing.
 */
public class CCRnaEditingCommentLineBuilder extends
		CCLineBuilderAbstr<RnaEditingComment> {

	@Override
	protected List<String> buildCommentLines(RnaEditingComment comment,
			boolean includeFFMarkings, boolean showEvidence, boolean includeCommentType) {
		StringBuilder sb = new StringBuilder();
		if (includeFFMarkings) {
			addFlatFileMarkingsIfRequired(includeFFMarkings, sb);
		}
		if(includeCommentType)
			addCommentTypeName(comment, sb);
		
		int positionCount = 0;
		for (RnaEdPosition aPosition : comment.getPositions()) {
			if (aPosition.getPosition() != null
					&& aPosition.getPosition().trim().length() >= 0) {
				if (positionCount == 0) {
					sb.append("Modified_positions=");
				} else {
					sb.append(", ");
				}
				sb.append(aPosition.getPosition());
			}
			sb = addEvidence(aPosition, sb, showEvidence, "");
			positionCount++;
		}
		if (comment.getPositions().isEmpty()) {
			sb.append("Modified_positions=");
			sb.append(comment.getLocationType().name());
		//	sb = addEvidence(comment, sb, showEvidence, "");
		}
		sb.append(SEMICOLON);
		if(isValidNote(comment.getNote())) {
			sb.append(SPACE);
			sb.append(NOTE);
	        String freeTextStr= buildFreeText(comment.getNote(), showEvidence, STOP, SEMICOLON);
	        sb.append(freeTextStr);   
		}
		if (includeFFMarkings) {
			return FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix,
					LINE_LENGTH);
		} else {
			List<String> lines = new ArrayList<>();
			lines.add(sb.toString());
			return lines;
		}
	}

}
