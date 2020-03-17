package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.List;

import org.uniprot.core.flatfile.parser.impl.cc.CcLineTransformer;
import org.uniprot.core.impl.SequenceBuilder;
import org.uniprot.core.uniprotkb.UniProtKBEntry;
import org.uniprot.core.uniprotkb.UniProtKBEntryType;
import org.uniprot.core.uniprotkb.comment.Comment;
import org.uniprot.core.uniprotkb.impl.UniProtKBEntryBuilder;

class CommentTestUtil {

    static UniProtKBEntry createUniProtEntryFromCommentLine(String commentLine) {
        List<Comment> comments = new CcLineTransformer("", "").transformNoHeader(commentLine);
        return new UniProtKBEntryBuilder("P12345", "P12345_ID", UniProtKBEntryType.TREMBL)
                .commentsSet(comments)
                .sequence(new SequenceBuilder("AAAA").build())
                .build();
    }
}
