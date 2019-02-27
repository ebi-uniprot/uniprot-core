package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CommentType;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocation;
import uk.ac.ebi.uniprot.domain.uniprot.comment.SubcellularLocationComment;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.ArrayList;
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
                List<String> subcellularLocationStr = new ArrayList<>();
                subcellularLocationStr.add(CommentType.SUBCELLULAR_LOCATION.toDisplayName() + ": ");
                if (subcellularLocationComment.hasMolecule()) {
                    subcellularLocationStr.add(subcellularLocationComment.getMolecule()+": ");
                }
                if (subcellularLocationComment.hasSubcellularLocations()) {
                    subcellularLocationStr.add(subcellularLocationComment.getSubcellularLocations().stream()
                            .map(this::getSubCellLocationsString)
                            .collect(Collectors.joining(". ","",".")));
                }
                if (subcellularLocationComment.hasNote()) {
                    subcellularLocationStr.add(" "+EntryMapUtil.getNoteString(subcellularLocationComment.getNote()));
                }
                return String.join("",subcellularLocationStr);
            }).collect(Collectors.joining("; "));
            subcellularLocationMap.put("cc:subcellular_location", result);
        }
        return subcellularLocationMap;
    }

    private String getSubCellLocationsString(SubcellularLocation subcellularLocation) {
        List<String> locationStr = new ArrayList<>();
        if (subcellularLocation.hasLocation()) {
            locationStr.add(EntryMapUtil.evidencedValueToString(subcellularLocation.getLocation()));
        }
        if (subcellularLocation.hasTopology()) {
            locationStr.add(EntryMapUtil.evidencedValueToString(subcellularLocation.getTopology()));
        }
        if (subcellularLocation.hasOrientation()) {
            locationStr.add(EntryMapUtil.evidencedValueToString(subcellularLocation.getOrientation()));
        }
        return String.join("; ",locationStr);
    }

}
