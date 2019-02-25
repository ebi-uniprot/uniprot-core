package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.Position;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.MassSpectrometryRange;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.ArrayList;
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
        if ((msComments != null && !msComments.isEmpty())) {
            String result = msComments.stream()
                    .map(this::mapMassSpectrometryCommentToString)
                    .collect(Collectors.joining("; "));
            massSpectrometryCommentMap.put("cc:mass_spectrometry", result+";");
        }
        return massSpectrometryCommentMap;
    }

    private String mapMassSpectrometryCommentToString(MassSpectrometryComment massSpectrometryComment) {
        List<String> result = new ArrayList<>();
        if(massSpectrometryComment.hasMolWeight()){
            result.add("Mass="+EntryMapUtil.formatFloat(massSpectrometryComment.getMolWeight()));
        }
        if(massSpectrometryComment.hasMolWeightError()){
            result.add("Mass_error="+EntryMapUtil.formatFloat(massSpectrometryComment.getMolWeightError()));
        }
        if(massSpectrometryComment.hasMethod()){
            result.add("Method="+massSpectrometryComment.getMethod().getValue());
        }
        if(massSpectrometryComment.hasRanges()){
            result.add(massSpectrometryComment.getRanges().stream().map(this::mapMassSpectrometryRangeToString)
                    .collect(Collectors.joining("; ")));
        }
        if(massSpectrometryComment.hasNote()){
            result.add("Note="+massSpectrometryComment.getNote());
        }
        if(massSpectrometryComment.hasEvidences()){
            result.add("Evidence="+EntryMapUtil.evidencesToString(massSpectrometryComment.getEvidences()));
        }
        return CommentType.MASS_SPECTROMETRY.toDisplayName()+": "+String.join("; ",result);
    }

    private String mapMassSpectrometryRangeToString(MassSpectrometryRange massSpectrometryRange) {
        List<String> range = new ArrayList<>();
        if(massSpectrometryRange.getRange() != null){
            Position start = massSpectrometryRange.getRange().getStart();
            if(start != null){
                range.add(""+start.getValue());
            }
            Position end = massSpectrometryRange.getRange().getEnd();
            if(end != null){
                range.add(""+end.getValue());
            }
        }
        return "Range="+String.join("-",range);
    }
}
