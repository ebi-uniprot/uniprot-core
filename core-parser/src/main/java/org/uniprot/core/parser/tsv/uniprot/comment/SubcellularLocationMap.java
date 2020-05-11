package org.uniprot.core.parser.tsv.uniprot.comment;

import org.uniprot.core.flatfile.parser.impl.cc.CCSubCellCommentLineBuilder;
import org.uniprot.core.parser.tsv.NamedValueMap;
import org.uniprot.core.uniprotkb.comment.SubcellularLocationComment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubcellularLocationMap implements NamedValueMap {

    private final List<SubcellularLocationComment> sclComments;
    private final CCSubCellCommentLineBuilder lineBuilder = new CCSubCellCommentLineBuilder();

    public SubcellularLocationMap(List<SubcellularLocationComment> sclComments) {
        this.sclComments = sclComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getSubCellLocComments(this.sclComments);
    }

    private Map<String, String> getSubCellLocComments(
            List<SubcellularLocationComment> sclComments) {
        Map<String, String> subcellularLocationMap = new HashMap<>();
        if ((sclComments != null) && !sclComments.isEmpty()) {

            String result =
                    sclComments.stream()
                            .map(this::subcelllocationCommentToString)
                            .collect(Collectors.joining("; "));
            subcellularLocationMap.put("cc_subcellular_location", result);
        }
        return subcellularLocationMap;
    }

    private String subcelllocationCommentToString(
            SubcellularLocationComment subcellularLocationComment) {
        return lineBuilder
                .buildString(subcellularLocationComment, true, true)
                .replaceAll("\n", " ");
    }
}
