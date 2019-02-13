package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.Position;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MassSpectrometryMap implements NamedValueMap {

    private final List<MassSpectrometryComment> massSpectrometryComments;

    public MassSpectrometryMap(List<MassSpectrometryComment> massSpectrometryComments){
        this.massSpectrometryComments = massSpectrometryComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getMassSpecComments(this.massSpectrometryComments);
    }

    private Map<String, String>  getMassSpecComments(List<MassSpectrometryComment> msComments) {
        Map<String, String> massSpectrometryCommentMap = new HashMap<>();
        if ((msComments != null)) {
            String result = msComments.stream().map(this::mapMassSpectrometryCommentToString).collect(Collectors.joining(";  "));
            massSpectrometryCommentMap.put("cc:mass_spectrometry", result);
        }
        return massSpectrometryCommentMap;
    }

    private String mapMassSpectrometryCommentToString(MassSpectrometryComment massSpectrometryComment) {
        String result = "";
        if(massSpectrometryComment.getMolWeight() != null){
            result += " "+massSpectrometryComment.getMolWeight();
        }
        if(massSpectrometryComment.getMolWeightError() != null){
            result += " "+massSpectrometryComment.getMolWeightError();
        }
        if(massSpectrometryComment.getMethod() != null){
            result += " "+massSpectrometryComment.getMethod().getValue();
        }
        if(massSpectrometryComment.getRanges() != null && !massSpectrometryComment.getRanges().isEmpty()){
            result += " "+massSpectrometryComment.getRanges().stream().map(this::mapMassSpectrometryRangeToString)
                    .collect(Collectors.joining("; "));
        }
        if(massSpectrometryComment.getNote() != null && !massSpectrometryComment.getNote().isEmpty()){
            result += " "+massSpectrometryComment.getNote();
        }
        if(massSpectrometryComment.getEvidences() != null && !massSpectrometryComment.getEvidences().isEmpty()){
            result += " "+ EntryMapUtil.evidencesToString(massSpectrometryComment.getEvidences());
        }
        return result;
    }

    private String mapMassSpectrometryRangeToString(MassSpectrometryRange massSpectrometryRange) {
        String range = "";
        if(massSpectrometryRange.getRange() != null){
            Position start = massSpectrometryRange.getRange().getStart();
            if(start != null){
                range += start.getValue();
            }
            Position end = massSpectrometryRange.getRange().getEnd();
            if(end != null){
                range += end.getValue();
            }
        }
        return range;
    }
}
