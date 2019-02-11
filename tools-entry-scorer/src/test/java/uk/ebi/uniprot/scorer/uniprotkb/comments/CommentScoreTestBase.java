package uk.ebi.uniprot.scorer.uniprotkb.comments;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.parser.impl.cc.CcLineTransformer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommentScoreTestBase {
    private CcLineTransformer ccLineTransformer = new CcLineTransformer(null);

    protected void verify(CommentType type, String line, double expectedScore) throws Exception {
        verify(type, line, expectedScore, false);
    }

    protected void verify(CommentType type, String line, double expectedScore, boolean isSp) throws Exception {
        Comment comment = ccLineTransformer.transformNoHeader(line).stream()
                .filter(c -> c.getCommentType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Could not create comment of type: " + type));
        CommentScored scored = CommentScoredFactory.create(comment);
        scored.setIsSwissProt(isSp);
        assertEquals(expectedScore, scored.score(), 0.001);
    }
//
//    protected void verifyMulti(String line, double expectedScore, boolean isSp) throws Exception{
//        SwissProtEntryText text = new SwissProtEntryText();
//        text.insertCCLine(line);
//        UniProtEntry entry = UniProtParser.parse(text.getText(), DefaultUniProtFactory.getInstance());
//        UniProtEntryScored scored = new UniProtEntryScored(entry);
//        assertEquals(expectedScore, scored.getEntryScore().getCommentScore(), 0.001);
//    }

    public static void main(String[] args) {
        CcLineTransformer ccLineTransformer = new CcLineTransformer(null);
        String line = "ALLERGEN: Causes an allergic reaction in human. Binds to IgE and\n" +
                "IgG.";
        List<Comment> comments = ccLineTransformer.transformNoHeader(line);
        System.out.println(comments);
    }
}