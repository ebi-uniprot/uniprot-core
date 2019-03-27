package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.flatfile.parser.impl.cc.CCDiseaseCommentLineBuilder;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

public class DiseaseMap implements NamedValueMap {

    private final List<DiseaseComment> diseaseComments;

    public DiseaseMap(List<DiseaseComment> diseaseComments){
        this.diseaseComments = diseaseComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getDiseaseComments(this.diseaseComments);
    }

    private Map<String, String> getDiseaseComments(List<DiseaseComment> dsComments) {
        Map<String, String> diseaseCommentMap = new HashMap<>();
        if ((dsComments != null)) {
            String result = dsComments.stream()
                    .map(this::mapDiseaseCommentToString)
                    .collect(Collectors.joining("; "));
            diseaseCommentMap.put("cc:disease", result);
        }
        return diseaseCommentMap;
    }

    private  String mapDiseaseCommentToString(DiseaseComment diseaseComment) {
    	CCDiseaseCommentLineBuilder builder = new CCDiseaseCommentLineBuilder();
    	return builder.buildString(diseaseComment, true, true).replaceAll("\n", " ");
    }
}
