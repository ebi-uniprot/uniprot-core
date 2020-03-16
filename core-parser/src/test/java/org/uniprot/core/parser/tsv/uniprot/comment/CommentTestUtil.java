package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.List;

import org.uniprot.core.flatfile.parser.impl.cc.CcLineTransformer;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.UniProtkbEntry;
import org.uniprot.core.uniprotkb.UniProtkbEntryType;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.impl.UniProtkbEntryBuilder;

class CommentTestUtil {

    static UniProtkbEntry createUniProtEntryFromCommentLine(String commentLine) {
        List<Comment> comments = new CcLineTransformer("", "").transformNoHeader(commentLine);
        return new UniProtkbEntryBuilder("P12345", "P12345_ID", UniProtkbEntryType.TREMBL)
                .commentsSet(comments)
                .sequence(new SequenceBuilder("AAAA").build())
                .build();
    }
}
