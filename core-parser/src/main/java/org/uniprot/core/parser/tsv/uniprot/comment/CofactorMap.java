package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.flatfile.parser.impl.cc.CCCofactorCommentLineBuilder;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.comment.CofactorComment;

public class CofactorMap implements NamedValueMap {

    private final List<CofactorComment> cofactorComments;

    public CofactorMap(List<CofactorComment> cofactorComments) {
        this.cofactorComments = cofactorComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getCofactorComments(this.cofactorComments);
    }

    private Map<String, String> getCofactorComments(List<CofactorComment> cfComments) {
        Map<String, String> cofactorCommentMap = new HashMap<>();
        if ((cfComments != null)) {
            String result =
                    cfComments.stream()
                            .map(this::mapCofactorCommentToString)
                            .collect(Collectors.joining(" "));
            cofactorCommentMap.put("cc_cofactor", result);
        }
        return cofactorCommentMap;
    }

    private String mapCofactorCommentToString(CofactorComment cofactorComment) {
        CCCofactorCommentLineBuilder builder = new CCCofactorCommentLineBuilder();
        return builder.buildString(cofactorComment, true, true).replaceAll("\n", " ");
    }
}
