package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.CofactorComment;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

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
            cofactorCommentMap.put("cc:cofactor", result);
        }
        return cofactorCommentMap;
    }

    private String mapCofactorCommentToString(CofactorComment cofactorComment) {
        String result = "";
        if(cofactorComment.getMolecule() != null && !cofactorComment.getMolecule().isEmpty()) {
            result += cofactorComment.getMolecule();
        }
        if(cofactorComment.getCofactors() != null && !cofactorComment.getCofactors().isEmpty()) {
            result += cofactorComment.getCofactors().stream().map(cofactor -> {
                String cofactorString = "";
                if(cofactor.getName() != null && !cofactor.getName().isEmpty()){
                    cofactorString += " "+cofactor.getName();
                }
                if(cofactor.getCofactorReference() != null){
                    cofactorString +=" "+cofactor.getCofactorReference().getDatabaseType().getName();
                    cofactorString +=" "+cofactor.getCofactorReference().getId();
                }
                return cofactorString;
            }).collect(Collectors.joining("; "));
        }
        if(cofactorComment.getNote() != null) {
            result += EntryMapUtil.getNoteString(cofactorComment.getNote());
        }
        return result;
    }

}
