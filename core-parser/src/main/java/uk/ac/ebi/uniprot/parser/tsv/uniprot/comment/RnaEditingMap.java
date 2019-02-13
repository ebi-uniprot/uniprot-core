package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEdPosition;
import uk.ac.ebi.uniprot.domain.uniprot.comment.RnaEditingComment;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RnaEditingMap implements NamedValueMap {

    private final List<RnaEditingComment> rnaEditingComments;

    public RnaEditingMap(List<RnaEditingComment> rnaEditingComments){
        this.rnaEditingComments = rnaEditingComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getRnaEdComments(this.rnaEditingComments);
    }

    private Map<String, String> getRnaEdComments(List<RnaEditingComment> reComments) {
        Map<String, String> rnaEditingCommentMap = new HashMap<>();
        if ((reComments != null)) {
            String result = reComments.stream()
                    .map(this::mapRnaEditingCommentToString)
                    .collect(Collectors.joining(";  "));
            rnaEditingCommentMap.put("cc:rna_editing", result);
        }
        return rnaEditingCommentMap;
    }

    private  String mapRnaEditingCommentToString(RnaEditingComment rnaEditingComment) {
        String result = "";
        if(rnaEditingComment.getLocationType() != null){
            result += " "+rnaEditingComment.getLocationType().name();
        }
        if(rnaEditingComment.getPositions() != null){
            result += " "+rnaEditingComment.getPositions().stream()
                    .map(this::mapRnaEdPositionToString)
                    .collect(Collectors.joining("; "));
        }

        if(rnaEditingComment.getNote() != null){
            result += " "+ EntryMapUtil.getNoteString(rnaEditingComment.getNote());
        }
        return result;
    }

    private String mapRnaEdPositionToString(RnaEdPosition rnaEdPosition) {
        String rnaPositionString = rnaEdPosition.getPosition();
        if(rnaEdPosition.getPosition() != null && !rnaEdPosition.getPosition().isEmpty()){
            rnaPositionString += " "+EntryMapUtil.evidencesToString(rnaEdPosition.getEvidences());
        }
        return rnaPositionString;
    }
}
