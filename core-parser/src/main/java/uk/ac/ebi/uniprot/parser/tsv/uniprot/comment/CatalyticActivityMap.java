package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.DBCrossReference;
import uk.ac.ebi.uniprot.domain.uniprot.comment.CatalyticActivityComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.PhysiologicalReaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.Reaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.ReactionReferenceType;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.EntryMapUtil;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

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
                    .collect(Collectors.joining(" "));

            catalyticActivityMap.put("cc:catalytic_activity",catalyticActivityString);
        }
        return catalyticActivityMap;
    }

    private String getCatalyticActivityString(CatalyticActivityComment catalyticActivityComment) {
        String result = "";
        if(catalyticActivityComment.hasReaction()){
            result+= " "+getReactionString(catalyticActivityComment.getReaction());
        }
        if(catalyticActivityComment.hasPhysiologicalReactions()){
            result +=" "+ getPhysiologicalReactionsString(catalyticActivityComment.getPhysiologicalReactions());
        }
        return result;
    }

    private String getReactionString(Reaction reaction) {
        String result = "Reaction=";
        if(reaction.hasName()){
            result+=reaction.getName();
        }
        if(reaction.hasReactionReferences()){
            result += reaction.getReactionReferences().stream()
                    .map(this::getReactionReferenceString)
                    .collect(Collectors.joining(", "));
        }
        if(reaction.hasEcNumber()){
            result+=reaction.getEcNumber();
        }
        if(reaction.hasEvidences()){
            result+= EntryMapUtil.evidencesToString(reaction.getEvidences());
        }
        return result;
    }

    private String getReactionReferenceString(DBCrossReference<ReactionReferenceType> reactionReference) {
        String result = "";
        if(reactionReference.hasDatabaseType()){
            result += " "+reactionReference.getDatabaseType().getName();
        }
        if(reactionReference.hasId()){
            result += ":"+reactionReference.getId();
        }
        return result;
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
