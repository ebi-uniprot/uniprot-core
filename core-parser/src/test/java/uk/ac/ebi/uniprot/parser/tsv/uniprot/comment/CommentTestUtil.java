package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.builder.SequenceBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntry;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtEntryType;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtAccessionBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtEntryBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.builder.UniProtIdBuilder;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Comment;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CcLineTransformer;

import java.util.List;

public class CommentTestUtil {

    static UniProtEntry createUniProtEntryFromCommentLine(String commentLine) {
        List<Comment> comments = new CcLineTransformer("", "").transformNoHeader(commentLine);
        return new UniProtEntryBuilder()
                .primaryAccession(new UniProtAccessionBuilder("P12345").build())
                .uniProtId(new UniProtIdBuilder("P12345_ID").build())
                .active()
                .entryType(UniProtEntryType.TREMBL)
                .comments(comments)
                .sequence(new SequenceBuilder("AAAA").build())
                .build();
    }
}
