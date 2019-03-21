package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.common.Utils;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SequenceCautionComment;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.ArrayList;
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
        if (Utils.notEmpty(scComments)) {
            String sequenceCautions = scComments.stream()
                    .map(this::sequenceCautionToString)
                    .collect(Collectors.joining(";  "));
            sequenceCautionMap.put("cc:sequence_caution", "SEQUENCE CAUTION:  " + sequenceCautions);
        }
        return sequenceCautionMap;
    }

    private String sequenceCautionToString(SequenceCautionComment sequenceCautionComment) {
        List<String> sequenceCautionStr = new ArrayList<>();
        if(sequenceCautionComment.hasSequence()){
            sequenceCautionStr.add("Sequence="+sequenceCautionComment.getSequence());
        }
        if(sequenceCautionComment.hasSequenceCautionType()){
            sequenceCautionStr.add("Type="+sequenceCautionComment.getSequenceCautionType().name());
        }
        if(sequenceCautionComment.hasPositions()){
            sequenceCautionStr.add("Positions="+String.join(", ", sequenceCautionComment.getPositions()));
        }
        if(sequenceCautionComment.hasNote()){
            sequenceCautionStr.add("Note="+sequenceCautionComment.getNote());
        }
        if(sequenceCautionComment.hasEvidences()){
            sequenceCautionStr.add("Evidence="+ EntryMapUtil.evidencesToString(sequenceCautionComment.getEvidences()).trim());
        }
        return String.join("; ",sequenceCautionStr);
    }

}
