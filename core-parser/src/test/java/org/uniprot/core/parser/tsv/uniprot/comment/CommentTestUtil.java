package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.List;

import org.uniprot.core.flatfile.parser.impl.cc.CcLineTransformer;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprot.UniProtEntry;
import org.uniprot.core.uniprot.UniProtEntryType;
import org.uniprot.core.uniprot.comment.Comment;
import org.uniprot.core.uniprot.impl.UniProtEntryBuilder;

class CommentTestUtil {

    static UniProtEntry createUniProtEntryFromCommentLine(String commentLine) {
        List<Comment> comments = new CcLineTransformer("", "").transformNoHeader(commentLine);
        return new UniProtEntryBuilder("P12345", "P12345_ID", UniProtEntryType.TREMBL)
                .commentsSet(comments)
                .sequence(new SequenceBuilder("AAAA").build())
                .build();
    }
}
