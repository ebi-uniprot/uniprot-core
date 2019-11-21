package org.uniprot.core.flatfile.parser.impl.cc;

import java.util.ArrayList;
import java.util.List;

import org.uniprot.core.flatfile.writer.FFLine;
import org.uniprot.core.flatfile.writer.FFLineBuilder;
import org.uniprot.core.flatfile.writer.LineType;
import org.uniprot.core.flatfile.writer.impl.FFLineBuilderAbstr;
import org.uniprot.core.flatfile.writer.impl.FFLines;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.HasMolecule;
import org.uniprot.core.uniprot.comment.SequenceCautionComment;

import com.google.common.base.Objects;

public class CCLineBuilder extends FFLineBuilderAbstr<List<Comment>>
        implements FFLineBuilder<List<Comment>> {
    public CCLineBuilder() {
        super(LineType.CC);
    }

    @Override
    public String buildString(List<Comment> f) {
        List<String> lines = buildLines(f, false, false);
        return FFLines.create(lines).toString();
    }

    @Override
    public String buildStringWithEvidence(List<Comment> f) {
        List<String> lines = buildLines(f, false, true);
        return FFLines.create(lines).toString();
    }

    @Override
    protected FFLine buildLine(List<Comment> f, boolean showEvidence) {
        List<String> lines = buildLines(f, true, showEvidence);
        return FFLines.create(lines);
    }

    private List<String> buildLines(
            List<Comment> f, boolean includeFFMarkings, boolean showEvidence) {
        List<String> lines = new ArrayList<>();

        //     boolean seqCautionStart = true;
        String molecule = null;
        int nSeqCaution = 0;
        for (Comment comment : f) {
            FFLineBuilder<Comment> fbuilder = CCLineBuilderFactory.create(comment);
            if (comment.getCommentType() == CommentType.SEQUENCE_CAUTION) {
                nSeqCaution += 1;
                //   seqCautionStart =sameSequenceCautionComment((SequenceCautionComment) comment,
                // seqCautionStart, molecule);
                FFLineBuilder<SequenceCautionComment> seqCautionBuilder =
                        CCLineBuilderFactory.create((SequenceCautionComment) comment);
                ((CCSequenceCautionCommentLineBuilder) seqCautionBuilder)
                        .setIsFirstSequenceCaution(nSeqCaution == 1);
                if (comment instanceof HasMolecule)
                    molecule = ((HasMolecule) comment).getMolecule();
            }
            if (includeFFMarkings) {
                if (showEvidence) lines.addAll(fbuilder.buildWithEvidence(comment).lines());
                else lines.addAll(fbuilder.build(comment).lines());
            } else {
                if (showEvidence) lines.add(fbuilder.buildStringWithEvidence(comment));
                else lines.add(fbuilder.buildString(comment));
            }
        }
        return lines;
    }

    private boolean sameSequenceCautionComment(
            SequenceCautionComment scCaution, boolean start, String molecule) {
        if (start) return true;
        else {
            return (!Objects.equal(scCaution.getMolecule(), molecule));
        }
    }
}
