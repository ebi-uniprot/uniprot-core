package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.uniprot.comment.CofactorComment;

import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CCCofactorCommentLineBuilder;

public class CofactorMap implements NamedValueMap {

    private final List<CofactorComment> cofactorComments;

    public CofactorMap(List<CofactorComment> cofactorComments){
        this.cofactorComments = cofactorComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getCofactorComments(this.cofactorComments);
    }


    private Map<String, String> getCofactorComments(List<CofactorComment> cfComments) {
        Map<String, String> cofactorCommentMap = new HashMap<>();
        if ((cfComments != null)) {
            String result = cfComments.stream().map(this::mapCofactorCommentToString).collect(Collectors.joining(" "));
            cofactorCommentMap.put("cc:cofactor", result);
        }
        return cofactorCommentMap;
    }

    private String mapCofactorCommentToString(CofactorComment cofactorComment) {
    	CCCofactorCommentLineBuilder builder = new CCCofactorCommentLineBuilder();
    	return builder.buildString(cofactorComment, true, true).replaceAll("\n", " ");
    }

}
