package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionType;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SequenceCautionMap implements NamedValueMap {

    private final List<SequenceCautionComment> sequenceCautionComments;

    public SequenceCautionMap(List<SequenceCautionComment> sequenceCautionComments){
        this.sequenceCautionComments = sequenceCautionComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getSeqCautionComments(this.sequenceCautionComments);
    }

    private Map<String, String> getSeqCautionComments(List<SequenceCautionComment> scComments) {
        Map<String, String> sequenceCautionMap = new HashMap<>();
        if ((scComments != null) && !scComments.isEmpty()) {
            String nonErroneousPrediction = scComments.stream()
                    .filter(val -> !SequenceCautionType.ERRONEOUS_PREDICTION.equals(val.getSequenceCautionType()))
                    .map(this::sequenceCautionToString)
                    .collect(Collectors.joining(";  "));
            if (!nonErroneousPrediction.isEmpty()) {
                sequenceCautionMap.put("cc:sequence_caution", "SEQUENCE CAUTION:  " + nonErroneousPrediction);
            }
            String erroneousPrediction = scComments.stream()
                    .filter(val -> SequenceCautionType.ERRONEOUS_PREDICTION.equals(val.getSequenceCautionType()))
                    .map(this::sequenceCautionToString)
                    .collect(Collectors.joining(";  "));
            if (!erroneousPrediction.isEmpty()) {
                sequenceCautionMap.put("error_gmodel_pred", "SEQUENCE CAUTION:  " + erroneousPrediction);
            }
        }
        return sequenceCautionMap;
    }

    private String sequenceCautionToString(SequenceCautionComment sequenceCautionComment) {
        String sequenceCautionStr = "";
        if(sequenceCautionComment.getSequence() != null){
            sequenceCautionStr+= "Sequence="+sequenceCautionComment.getSequence()+";";
        }
        if(sequenceCautionComment.getSequenceCautionType()!= null){
            sequenceCautionStr+= " Type="+sequenceCautionComment.getSequenceCautionType().name()+";";
        }
        if(sequenceCautionComment.getPositions()!= null && !sequenceCautionComment.getPositions().isEmpty()){
            sequenceCautionStr+= " Positions="+String.join(", ", sequenceCautionComment.getPositions())+";";
        }
        if(sequenceCautionComment.getNote()!= null){
            sequenceCautionStr+= " Note="+sequenceCautionComment.getNote()+";";
        }
        if(sequenceCautionComment.getEvidences()!= null){
            sequenceCautionStr+= " Evidence="+ EntryMapUtil.evidencesToString(sequenceCautionComment.getEvidences()).trim()+";";
        }
        return sequenceCautionStr;
    }

}
