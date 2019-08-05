package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineTransformer;
import uk.ebi.uniprot.scorer.uniprotkb.UniProtEntryScored;

import java.util.List;

import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;
import org.uniprot.core.uniprot.builder.UniProtEntryBuilder;
import org.uniprot.core.uniprot.builder.UniProtIdBuilder;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.evidence.EvidenceType;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentScoreTestBase {
    private CcLineTransformer ccLineTransformer = new CcLineTransformer("", "");

    void verify(CommentType type, String line, double expectedScore) throws Exception {
        verify(type, line, expectedScore, false);
    }

    void verify(CommentType type, String line, double expectedScore, boolean isSp) {
        verify(type, line, expectedScore, isSp, null);
    }

    void verify(CommentType type, String line, double expectedScore, List<EvidenceType> evidenceTypes) {
        verify(type, line, expectedScore, false, evidenceTypes);
    }

    void verify(CommentType type, String line, double expectedScore, boolean isSp, List<EvidenceType> evidenceTypes) {
        Comment comment = ccLineTransformer.transformNoHeader(line).stream()
                .filter(c -> c.getCommentType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not create comment of type: " + type));
        CommentScored scored = CommentScoredFactory.create(comment, evidenceTypes);
        scored.setIsSwissProt(isSp);
        assertEquals(expectedScore, scored.score(), 0.001);
    }

    void verifyMulti(String line, double expectedScore, boolean isSp) throws Exception {
        List<Comment> comments = ccLineTransformer.transformNoHeader(line);
        UniProtEntry entry = new UniProtEntryBuilder()
                .primaryAccession(new UniProtAccessionBuilder("P12345").build())
                .uniProtId(new UniProtIdBuilder("ID_12345").build())
                .active()
                .entryType(isSp ? UniProtEntryType.SWISSPROT : UniProtEntryType.TREMBL)
                .comments(comments)
                .build();

        UniProtEntryScored entryScored = new UniProtEntryScored(entry);
        assertEquals(expectedScore, entryScored.score(), 0.001);
    }

    public static void main(String[] args) {
        CcLineTransformer ccLineTransformer = new CcLineTransformer("", "");
        String line = "ALLERGEN: Causes an allergic reaction in human. Binds to IgE and\n" +
                "IgG.";
        List<Comment> comments = ccLineTransformer.transformNoHeader(line);
        System.out.println(comments);
    }
}