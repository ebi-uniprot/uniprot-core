package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.uniprot.core.uniprot.comment.SubcellularLocationComment;

import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CCSubCellCommentLineBuilder;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

public class SubcellularLocationMap implements NamedValueMap {

    private final List<SubcellularLocationComment> sclComments;
    private final CCSubCellCommentLineBuilder lineBuilder = new CCSubCellCommentLineBuilder();
    public SubcellularLocationMap(List<SubcellularLocationComment> sclComments){
        this.sclComments = sclComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getSubCellLocComments(this.sclComments);
    }

    private Map<String, String> getSubCellLocComments(List<SubcellularLocationComment> sclComments) {
        Map<String, String> subcellularLocationMap = new HashMap<>();
        if ((sclComments != null) && !sclComments.isEmpty()) {

            String result = sclComments.stream().map(this::subcelllocationCommentToString).collect(Collectors.joining("; "));
            subcellularLocationMap.put("cc:subcellular_location", result);
        }
        return subcellularLocationMap;
    }

    private String subcelllocationCommentToString(SubcellularLocationComment subcellularLocationComment) { 	
    	return lineBuilder.buildString(subcellularLocationComment, true, true).replaceAll("\n", " ");
    }
}
