package org.uniprot.core.scorer.uniprotkb.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.uniprot.core.flatfile.parser.impl.cc.CcLineTransformer;
import org.uniprot.core.scorer.uniprotkb.UniProtEntryScored;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.evidence.EvidenceDatabase;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;

import java.util.List;

class CommentScoreTestBase {
    private CcLineTransformer ccLineTransformer = new CcLineTransformer("", "");

    void verify(CommentType type, String line, double expectedScore) throws Exception {
        verify(type, line, expectedScore, false);
    }

    void verify(CommentType type, String line, double expectedScore, boolean isSp) {
        verify(type, line, expectedScore, isSp, null);
    }

    void verify(
            CommentType type,
            String line,
            double expectedScore,
            List<EvidenceDatabase> evidenceDatabases) {
        verify(type, line, expectedScore, false, evidenceDatabases);
    }

    void verify(
            CommentType type,
            String line,
            double expectedScore,
            boolean isSp,
            List<EvidenceDatabase> evidenceDatabases) {
        Comment comment =
                ccLineTransformer.transformNoHeader(line).stream()
                        .filter(c -> c.getCommentType().equals(type))
                        .findFirst()
                        .orElseThrow(
                                () ->
                                        new IllegalStateException(
                                                "Could not create comment of type: " + type));
        CommentScored scored = CommentScoredFactory.create(comment, evidenceDatabases);
        scored.setIsSwissProt(isSp);
        assertEquals(expectedScore, scored.score(), 0.001);
    }

    void verifyMulti(String line, double expectedScore, boolean isSp) throws Exception {
        List<Comment> comments = ccLineTransformer.transformNoHeader(line);
        UniProtKBEntryType entryType =
                isSp ? UniProtKBEntryType.SWISSPROT : UniProtKBEntryType.TREMBL;
        UniProtKBEntry entry =
                new UniProtKBEntryBuilder("P12345", "ID_12345", entryType)
                        .commentsSet(comments)
                        .build();

        UniProtEntryScored entryScored = new UniProtEntryScored(entry);
        assertEquals(expectedScore, entryScored.score(), 0.001);
    }

    static void main(String[] args) {
        CcLineTransformer ccLineTransformer = new CcLineTransformer("", "");
        String line = "ALLERGEN: Causes an allergic reaction in human. Binds to IgE and\n" + "IgG.";
        List<Comment> comments = ccLineTransformer.transformNoHeader(line);
        System.out.println(comments);
    }
}
