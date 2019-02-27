package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.FreeTextComment;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.EvidencedValue;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FreeTextMap implements NamedValueMap {

    private final List<FreeTextComment> txtComments;
    private final CommentType type;

    public FreeTextMap(List<FreeTextComment> txtComments,CommentType type){
        this.txtComments = txtComments;
        this.type = type;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getTextComments(type,txtComments);
    }


    private Map<String, String> getTextComments(CommentType type, List<FreeTextComment> txtComments) {
        Map<String, String> txtCommentMap = new HashMap<>();
        if ((txtComments != null && !txtComments.isEmpty())) {
            String value = txtComments.stream()
                    .filter(FreeTextComment::hasTexts)
                    .map(this::getTextCommentString)
                    .collect(Collectors.joining("; "));
            String field = "cc:" + type.name().toLowerCase();
            txtCommentMap.put(field, value);
        }
        return txtCommentMap;
    }

    private String getTextCommentString(FreeTextComment comment) {
        String commentPrefix = comment.getCommentType().toDisplayName()+": ";
        return comment.getTexts().stream()
                .map(this::evidencedValueToString)
                .collect(Collectors.joining("; ",commentPrefix,""));
    }

    private String evidencedValueToString(EvidencedValue evidencedValue) {
        String result = evidencedValue.getValue()+".";
        if(evidencedValue.hasEvidences()){
            result += " "+EntryMapUtil.evidencesToString(evidencedValue.getEvidences())+".";
        }
        return result;
    }
}
