package org.uniprot.core.parser.tsv.uniprot.comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.parser.tsv.uniprot.NamedValueMap;
import org.uniprot.core.uniprotkb.comment.Interactant;
import org.uniprot.core.uniprotkb.comment.Interaction;
import org.uniprot.core.uniprotkb.comment.InteractionComment;
import org.uniprot.core.util.Utils;

public class InteractionMap implements NamedValueMap {

    private final List<InteractionComment> interactionComments;

    public InteractionMap(List<InteractionComment> interactionComments) {
        this.interactionComments = interactionComments;
    }

    @Override
    public Map<String, String> attributeValues() {
        return getInterActComments(this.interactionComments);
    }

    private Map<String, String> getInterActComments(List<InteractionComment> iaComments) {
        Map<String, String> interactionCommentMap = new HashMap<>();
        if ((iaComments != null)) {
            String result =
                    iaComments.stream()
                            .filter(InteractionComment::hasInteractions)
                            .flatMap(val -> val.getInteractions().stream())
                            .map(this::getInterAct)
                            .filter(Objects::nonNull)
                            .collect(Collectors.joining("; "));
            interactionCommentMap.put("cc_interaction", result);
        }
        return interactionCommentMap;
    }

    private String getInterAct(Interaction interAct) {
        Interactant secondInteractant = interAct.getInteractantTwo();
        if (!Utils.nullOrEmpty(secondInteractant.getChainId())) {
            return secondInteractant.getChainId()
                    + " ["
                    + secondInteractant.getUniProtKBAccession().getValue()
                    + "]";
        } else {
            return secondInteractant.getUniProtKBAccession().getValue();
        }
    }
}
