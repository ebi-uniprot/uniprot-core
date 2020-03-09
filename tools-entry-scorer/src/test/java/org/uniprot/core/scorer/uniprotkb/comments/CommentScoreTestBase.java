package org.uniprot.core.scorer.uniprotkb.comments;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.uniprot.core.flatfile.parser.impl.cc.CcLineTransformer;
import org.uniprot.core.scorer.uniprotkb.UniProtEntryScored;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.evidence.EvidenceDatabase;
import org.uniprot.core.uniprot.impl.UniProtEntryBuilder;

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
        UniProtEntryType entryType = isSp ? UniProtEntryType.SWISSPROT : UniProtEntryType.TREMBL;
        UniProtEntry entry =
                new UniProtEntryBuilder("P12345", "ID_12345", entryType)
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
