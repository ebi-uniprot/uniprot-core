package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubcellularLocationMap implements NamedValueMap {

    private final List<SubcellularLocationComment> sclComments;

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

            String result = sclComments.stream().map(subcellularLocationComment -> {
                String subcellularLocationStr = "";
                if (subcellularLocationComment.getMolecule() != null && !subcellularLocationComment.getMolecule().isEmpty()) {
                    subcellularLocationStr += subcellularLocationComment.getMolecule();
                }
                if (subcellularLocationComment.getNote() != null) {
                    subcellularLocationStr += EntryMapUtil.getNoteString(subcellularLocationComment.getNote());
                }
                if (subcellularLocationComment.getSubcellularLocations() != null && !subcellularLocationComment.getSubcellularLocations().isEmpty()) {
                    subcellularLocationStr += subcellularLocationComment.getSubcellularLocations().stream()
                            .map(subcellularLocation -> {
                                String locationStr = "";
                                if (subcellularLocation.getLocation() != null) {
                                    locationStr += EntryMapUtil.evidencedValueToString(subcellularLocation.getLocation());
                                }
                                if (subcellularLocation.getOrientation() != null) {
                                    locationStr += EntryMapUtil.evidencedValueToString(subcellularLocation.getOrientation());
                                }
                                if (subcellularLocation.getTopology() != null) {
                                    locationStr += EntryMapUtil.evidencedValueToString(subcellularLocation.getTopology());
                                }
                                return locationStr;
                            }).collect(Collectors.joining(". ")) + ".";
                }
                return subcellularLocationStr;
            }).collect(Collectors.joining(";  "));
            subcellularLocationMap.put("cc:subcellular_location", CommentType.SUBCELLULAR_LOCATION.toDisplayName() + ": " + result);
        }
        return subcellularLocationMap;
    }

}
