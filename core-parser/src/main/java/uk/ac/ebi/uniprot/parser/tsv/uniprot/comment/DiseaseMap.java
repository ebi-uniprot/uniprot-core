package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        List<String> result = new ArrayList<>();
        result.add(diseaseComment.getCommentType().toDisplayName()+":");
        if(diseaseComment.getDisease() != null){
            Disease disease = diseaseComment.getDisease();
            if(disease.hasDiseaseId()){
                result.add(disease.getDiseaseId());
            }
            if(disease.hasAcronym()){
                result.add("("+disease.getAcronym()+")");
            }
            if(disease.hasReference()){
                DBCrossReference<DiseaseReferenceType> reference = disease.getReference();
                List<String> referenceStr = new ArrayList<>();
                if(reference.hasDatabaseType()) {
                    referenceStr.add(reference.getDatabaseType().getName());
                }
                if(reference.hasId()) {
                    referenceStr.add(reference.getId());
                }
                result.add("["+String.join(":",referenceStr)+"]:");
            }
            if(disease.hasDescription()){
                result.add(disease.getDescription());
            }
            if(disease.hasEvidences()){
                result.add(EntryMapUtil.evidencesToString(disease.getEvidences())+".");
            }
        }
        if(diseaseComment.hasNote()) {
            result.add(EntryMapUtil.getNoteString(diseaseComment.getNote()));
        }
        return String.join(" ",result);
    }
}
