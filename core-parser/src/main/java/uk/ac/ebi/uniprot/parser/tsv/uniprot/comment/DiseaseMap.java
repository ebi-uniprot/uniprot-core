package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Disease;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.DiseaseReferenceType;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

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
            String result = dsComments.stream().map(this::mapDiseaseCommentToString).collect(Collectors.joining("; "));
            diseaseCommentMap.put("cc:disease", result);
        }
        return diseaseCommentMap;
    }

    private  String mapDiseaseCommentToString(DiseaseComment diseaseComment) {
        String result = "";
        if(diseaseComment.getDisease() != null){
            Disease disease = diseaseComment.getDisease();
            if(disease.getDiseaseId() != null && !disease.getDiseaseId().isEmpty()){
                result += " "+disease.getDiseaseId();
            }
            if(disease.getDiseaseAccession() != null && !disease.getDiseaseAccession().isEmpty()){
                result += " "+disease.getDiseaseAccession();
            }
            if(disease.getAcronym() != null && !disease.getAcronym().isEmpty()){
                result += " "+disease.getAcronym();
            }
            if(disease.getDescription() != null && !disease.getDescription().isEmpty()){
                result += " "+disease.getDescription();
            }
            if(disease.getReference() != null){
                DBCrossReference<DiseaseReferenceType> reference = disease.getReference();
                if(reference.getDatabaseType() != null) {
                    result += " " + reference.getDatabaseType().getName();
                }
                if(reference.getId() != null && !reference.getId().isEmpty()) {
                    result += " " + reference.getId();
                }
            }
        }
        if(diseaseComment.getNote() != null) {
            result += EntryMapUtil.getNoteString(diseaseComment.getNote());
        }
        return result;
    }
}
