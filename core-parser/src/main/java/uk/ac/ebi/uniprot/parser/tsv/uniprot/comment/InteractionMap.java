package uk.ac.ebi.uniprot.parser.tsv.uniprot.comment;

import uk.ac.ebi.uniprot.domain.uniprot.comment.Interaction;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionComment;
import uk.ac.ebi.uniprot.domain.uniprot.comment.InteractionType;
import uk.ac.ebi.uniprot.parser.tsv.uniprot.NamedValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class InteractionMap implements NamedValueMap {

    private final List<InteractionComment> interactionComments;

    public InteractionMap(List<InteractionComment> interactionComments){
        this.interactionComments = interactionComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getInterActComments(this.interactionComments);
    }

    private Map<String, String> getInterActComments(List<InteractionComment> iaComments) {
        Map<String, String> interactionCommentMap = new HashMap<>();
        if ((iaComments != null)) {
            String result = iaComments.stream()
                    .filter(InteractionComment::hasInteractions)
                    .flatMap(val -> val.getInteractions().stream())
                    .map(this::getInterAct)
                    .filter(Objects::nonNull)
                    .collect(Collectors.joining("; "));
            interactionCommentMap.put("cc:interaction", result);
        }
        return interactionCommentMap;
    }

    private String getInterAct(Interaction interAct) {
        if (InteractionType.SELF.equals(interAct.getType())) {
            return "Itself";
        } else if(interAct.hasUniProtAccession()) {
            return interAct.getUniProtAccession().getValue();
        }
        return null;
    }
}
