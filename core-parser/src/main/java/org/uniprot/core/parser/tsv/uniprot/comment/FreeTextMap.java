package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.parser.impl.cc.CCFreeTextCommentLineBuilder;
import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.uniprotkb.comment.CommentType;
import org.uniprot.core.uniprotkb.comment.FreeTextComment;

public class FreeTextMap implements NamedValueMap {

    private final List<FreeTextComment> txtComments;
    private final CommentType type;
    CCFreeTextCommentLineBuilder lineBuilder = new CCFreeTextCommentLineBuilder();

    public FreeTextMap(List<FreeTextComment> txtComments, CommentType type) {
        this.txtComments = txtComments;
        this.type = type;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getTextComments(type, txtComments);
    }

    private Map<String, String> getTextComments(
            CommentType type, List<FreeTextComment> txtComments) {
        Map<String, String> txtCommentMap = new HashMap<>();
        if ((txtComments != null && !txtComments.isEmpty())) {
            String value =
                    txtComments.stream()
                            .filter(FreeTextComment::hasTexts)
                            .map(this::getTextCommentString)
                            .collect(Collectors.joining("; "));
            String field = "cc_" + type.name().toLowerCase();
            txtCommentMap.put(field, value);
        }
        return txtCommentMap;
    }

    private String getTextCommentString(FreeTextComment comment) {
        return lineBuilder.buildStringWithEvidence(comment).replaceAll("\n", " ");
    }
}
