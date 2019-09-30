package org.uniprot.core.flatfile.parser.impl.cc;

import static org.uniprot.core.flatfile.writer.impl.FFLineConstant.*;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.impl.FFLineWrapper;
import org.uniprot.core.uniprot.comment.FreeTextComment;
import org.uniprot.core.uniprot.evidence.EvidencedValue;

public class CCFreeTextCommentLineBuilder extends CCLineBuilderAbstr<FreeTextComment> {

    @Override
    protected List<String> buildCommentLines(
            FreeTextComment comment,
            boolean includeFFMarkings,
            boolean showEvidence,
            boolean includeCommentType) {
        StringBuilder sb = new StringBuilder();

        addFlatFileMarkingsIfRequired(includeFFMarkings, sb);
        if (includeCommentType) addCommentTypeName(comment, sb);
        boolean first = true;
        for (EvidencedValue text : comment.getTexts()) {
            if (!first) sb.append(SPACE);
            sb.append(text.getValue());
            //	sb.append(buildCommentStatus(text));
            appendIfNot(sb, '.');
            sb = addEvidence(text, sb, showEvidence, STOP);
            first = false;
        }
        List<String> lines = new ArrayList<>();
        if (includeFFMarkings) {
            lines.addAll(FFLineWrapper.buildLines(sb.toString(), SEPS, linePrefix, LINE_LENGTH));
        } else {
            lines.add(sb.toString());
        }
        return lines;
    }
}
