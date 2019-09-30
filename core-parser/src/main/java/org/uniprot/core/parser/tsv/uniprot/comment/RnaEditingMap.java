package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.parser.impl.cc.CCRnaEditingCommentLineBuilder;
import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.uniprot.comment.RnaEditingComment;

public class RnaEditingMap implements NamedValueMap {

    private final List<RnaEditingComment> rnaEditingComments;
    private final CCRnaEditingCommentLineBuilder lineBuilder = new CCRnaEditingCommentLineBuilder();

    public RnaEditingMap(List<RnaEditingComment> rnaEditingComments) {
        this.rnaEditingComments = rnaEditingComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getRnaEdComments(this.rnaEditingComments);
    }

    private Map<String, String> getRnaEdComments(List<RnaEditingComment> reComments) {
        Map<String, String> rnaEditingCommentMap = new HashMap<>();
        if ((reComments != null)) {
            String result =
                    reComments.stream()
                            .map(this::mapRnaEditingCommentToString)
                            .collect(Collectors.joining(";  "));
            rnaEditingCommentMap.put("cc:rna_editing", result);
        }
        return rnaEditingCommentMap;
    }

    private String mapRnaEditingCommentToString(RnaEditingComment rnaEditingComment) {
        return lineBuilder.buildString(rnaEditingComment, true, true).replaceAll("\n", " ");
    }
}
