package org.uniprot.core.scorer.uniprotkb.comments;

import org.junit.jupiter.api.Test;
import org.uniprot.core.uniprot.comment.CommentType;

class CofactorScoredTest extends CommentScoreTestBase {
    @Test
    void shouldScore00() throws Exception {
        String line = "CC   -!- COFACTOR:\n" +
                "CC       Note=Does not require a metal cofactor.\n" +
                "CC       {ECO:0000269|PubMed:24450804};";
        verify(CommentType.COFACTOR, line, 0.0);
    }

    @Test
    void shouldScore10() throws Exception {
        String line = "CC   -!- COFACTOR: Isoform 1:\n" +
                "CC       Name=Zn(2+); Xref=ChEBI:CHEBI:29105;\n" +
                "CC         Evidence={ECO:0000305|PubMed:16683188};\n" +
                "CC       Note=Binds 2 zinc ions. {ECO:0000269|PubMed:16683188};";

        verify(CommentType.COFACTOR, line, 1.0);
    }

    @Test
    void shouldScore20() throws Exception {
        String line = "CC   -!- COFACTOR:\n" +
                "CC       Name=Mg(2+); Xref=ChEBI:CHEBI:18420;\n" +
                "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00086};\n" +
                "CC       Name=Co(2+); Xref=ChEBI:CHEBI:48828;\n" +
                "CC         Evidence={ECO:0000255|HAMAP-Rule:MF_00086};\n" +
                "CC       Note=Binds 2 divalent ions per subunit (magnesium or cobalt).\n" +
                "CC       {ECO:0000255|HAMAP-Rule:MF_00086};";

        verify(CommentType.COFACTOR, line, 2.0);
    }
}
