package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorReferenceType;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CofactorMap implements NamedValueMap {

    private final List<CofactorComment> cofactorComments;

    public CofactorMap(List<CofactorComment> cofactorComments){
        this.cofactorComments = cofactorComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getCofactorComments(this.cofactorComments);
    }


    private Map<String, String> getCofactorComments(List<CofactorComment> cfComments) {
        Map<String, String> cofactorCommentMap = new HashMap<>();
        if ((cfComments != null)) {
            String result = cfComments.stream().map(this::mapCofactorCommentToString).collect(Collectors.joining("; "));
            cofactorCommentMap.put("cc:cofactor", result+";");
        }
        return cofactorCommentMap;
    }

    private String mapCofactorCommentToString(CofactorComment cofactorComment) {
        List<String> result = new ArrayList<>();
        result.add(cofactorComment.getCommentType().toDisplayName()+":");
        if(cofactorComment.hasMolecule()) {
            result.add(cofactorComment.getMolecule()+":");
        }
        if(cofactorComment.hasCofactors()) {
            String cofactors = cofactorComment.getCofactors().stream().map(cofactor -> {
                List<String> cofactorString = new ArrayList<>();
                if(cofactor.hasName()){
                    cofactorString.add("Name="+cofactor.getName());
                }
                if(cofactor.hasCofactorReference()){
                    List<String> referenceString = new ArrayList<>();
                    DBCrossReference<CofactorReferenceType> reference = cofactor.getCofactorReference();
                    if(reference.hasDatabaseType()){
                        referenceString.add(cofactor.getCofactorReference().getDatabaseType().getName());
                    }
                    if(reference.hasId()){
                        referenceString.add(cofactor.getCofactorReference().getId());
                    }
                    cofactorString.add("Xref="+String.join(":",referenceString));
                }
                if(cofactor.hasEvidences()){
                    cofactorString.add("Evidence="+EntryMapUtil.evidencesToString(cofactor.getEvidences()));
                }
                return String.join("; ",cofactorString);
            }).collect(Collectors.joining("; ","",";"));
            result.add(cofactors);
        }
        if(cofactorComment.hasNote()) {
            result.add(EntryMapUtil.getNoteStringWithoutDot(cofactorComment.getNote()));
        }
        return String.join(" ",result);
    }

}
