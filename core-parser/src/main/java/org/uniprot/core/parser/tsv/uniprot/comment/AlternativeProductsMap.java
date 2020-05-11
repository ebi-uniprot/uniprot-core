package org.uniprot.core.parser.tsv.uniprot.comment;

import org.uniprot.core.flatfile.parser.impl.cc.CCAPCommentLineBuilder;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.comment.AlternativeProductsComment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AlternativeProductsMap implements NamedValueMap {

    private final List<AlternativeProductsComment> alternativeProductsComments;
    CCAPCommentLineBuilder lineBuilder = new CCAPCommentLineBuilder();

    public AlternativeProductsMap(List<AlternativeProductsComment> alternativeProductsComments) {
        this.alternativeProductsComments = alternativeProductsComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getAPComments(this.alternativeProductsComments);
    }

    private Map<String, String> getAPComments(List<AlternativeProductsComment> apComments) {
        Map<String, String> apCommentMap = new HashMap<>();
        if ((apComments != null)) {
            String result =
                    apComments.stream()
                            .map(this::getAlternativeProductsCommentsString2)
                            .collect(Collectors.joining(" "));
            apCommentMap.put("cc_alternative_products", "ALTERNATIVE PRODUCTS:  " + result);
        }
        return apCommentMap;
    }

    private String getAlternativeProductsCommentsString2(
            AlternativeProductsComment alternativeProductsComment) {
        return lineBuilder
                .buildString(alternativeProductsComment, true, false)
                .replaceAll("\n", " ");
    }
}
