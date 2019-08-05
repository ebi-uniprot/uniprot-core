package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.List;

import org.uniprot.core.builder.SequenceBuilder;
import org.uniprot.core.flatfile.parser.impl.cc.CcLineTransformer;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.builder.UniProtAccessionBuilder;
import org.uniprot.core.uniprot.builder.UniProtEntryBuilder;
import org.uniprot.core.uniprot.builder.UniProtIdBuilder;
import org.uniprot.core.uniprot.comment.Comment;

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
