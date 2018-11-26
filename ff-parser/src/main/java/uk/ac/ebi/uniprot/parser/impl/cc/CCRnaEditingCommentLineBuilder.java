package uk.ac.ebi.uniprot.parser.impl.cc;

import static uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEdPosition;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.parser.ffwriter.impl.FFLineWrapper;

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
			boolean includeFFMarkings, boolean showEvidence) {
		StringBuilder sb = new StringBuilder();
		if (includeFFMarkings) {
			addFlatFileMarkingsIfRequired(includeFFMarkings, sb);
		}
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
		sb.append(SEMI_COMA);
		if(isValidNote(comment.getNote())) {
			sb.append(SPACE);
			sb.append(NOTE);
	        String freeTextStr= buildFreeText(comment.getNote(), showEvidence, STOP, SEMI_COMA);
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
