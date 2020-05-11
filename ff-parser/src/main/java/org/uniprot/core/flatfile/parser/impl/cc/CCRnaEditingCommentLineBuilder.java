package org.uniprot.core.flatfile.parser.impl.cc;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.uniprotkb.comment.RnaEdPosition;
import org.uniprot.core.uniprotkb.comment.RnaEditingComment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jieluo "CC -!- RNA EDITING: Modified_positions=59, 78, 94, 98, 102, 121; Note=The |CC
 *     nonsense codon at position 59 is modified to a sense codon. The |CC stop codon at position
 *     121 is created by RNA editing.
 */
public class CCRnaEditingCommentLineBuilder extends CCLineBuilderAbstr<RnaEditingComment> {

    @Override
    protected List<String> buildCommentLines(
            RnaEditingComment comment,
            boolean includeFFMarkings,
            boolean showEvidence,
            boolean includeCommentType) {
        StringBuilder sb = new StringBuilder();
        if (includeFFMarkings) {
            addFlatFileMarkingsIfRequired(includeFFMarkings, sb);
        }
        if (includeCommentType) addCommentTypeName(comment, sb);
        addMolecule(comment, sb, true);
        int positionCount = 0;
        for (RnaEdPosition aPosition : comment.getPositions()) {
            if (aPosition.getPosition() != null && aPosition.getPosition().trim().length() >= 0) {
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
        if (isValidNote(comment.getNote())) {
            sb.append(SPACE);
            sb.append(NOTE);
            String freeTextStr = buildFreeText(comment.getNote(), showEvidence, STOP, SEMICOLON);
            sb.append(freeTextStr);
        }
        if (includeFFMarkings) {
            return FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix, LINE_LENGTH);
        } else {
            List<String> lines = new ArrayList<>();
            lines.add(sb.toString());
            return lines;
        }
    }
}
