package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CatalyticActivityMap implements NamedValueMap {

    private final List<CatalyticActivityComment> catalyticActivityComments;

    public CatalyticActivityMap(List<CatalyticActivityComment> catalyticActivityComments) {
        this.catalyticActivityComments = catalyticActivityComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getCatalyticActivityComments(this.catalyticActivityComments);
    }


    private Map<String, String> getCatalyticActivityComments(List<CatalyticActivityComment> catalyticActivityComments){
        Map<String, String> catalyticActivityMap = new HashMap<>();
        if(catalyticActivityComments != null){
            String catalyticActivityString =  catalyticActivityComments.stream()
                    .map(this::getCatalyticActivityString)
                    .collect(Collectors.joining("; "));

            catalyticActivityMap.put("cc:catalytic_activity",catalyticActivityString+";");
        }
        return catalyticActivityMap;
    }

    private String getCatalyticActivityString(CatalyticActivityComment catalyticActivityComment) {
        String result = catalyticActivityComment.getCommentType().toDisplayName()+":";
        if(catalyticActivityComment.hasReaction()){
            result+= " "+getReactionString(catalyticActivityComment.getReaction());
        }
        if(catalyticActivityComment.hasPhysiologicalReactions()){
            result +=" "+ getPhysiologicalReactionsString(catalyticActivityComment.getPhysiologicalReactions());
        }
        return result;
    }

    private String getReactionString(Reaction reaction) {
        List<String> result = new ArrayList<>();
        if(reaction.hasName()){
            result.add("Reaction="+reaction.getName());
        }
        if(reaction.hasReactionReferences()){
            result.add("Xref="+reaction.getReactionReferences().stream()
                    .map(this::getReactionReferenceString)
                    .collect(Collectors.joining(", ")));
        }
        if(reaction.hasEcNumber()){
            result.add("EC="+reaction.getEcNumber().getValue());
        }
        if(reaction.hasEvidences()){
            result.add("Evidence="+EntryMapUtil.evidencesToString(reaction.getEvidences()));
        }
        return String.join("; ",result);
    }

    private String getReactionReferenceString(DBCrossReference<ReactionReferenceType> reactionReference) {
        List<String> result = new ArrayList<>();
        if(reactionReference.hasDatabaseType()){
            result.add(reactionReference.getDatabaseType().getName());
        }
        if(reactionReference.hasId()){
            result.add(reactionReference.getId());
        }
        return String.join(":",result);
    }

    private String getPhysiologicalReactionsString(List<PhysiologicalReaction> physiologicalReactions) {
        return physiologicalReactions.stream()
                    .map(this::getPhysiologicalReactionString)
                    .collect(Collectors.joining(" "));
    }

    private String getPhysiologicalReactionString(PhysiologicalReaction physiologicalReaction) {
        String result = "";
        if(physiologicalReaction.hasDirectionType()){
            result += " "+physiologicalReaction.getDirectionType().toDisplayName();
        }
        if(physiologicalReaction.hasReactionReference()){
            result += " "+getReactionReferenceString(physiologicalReaction.getReactionReference());
        }
        if(physiologicalReaction.hasEvidences()){
            result+= EntryMapUtil.evidencesToString(physiologicalReaction.getEvidences());
        }
        return result;
    }

}
