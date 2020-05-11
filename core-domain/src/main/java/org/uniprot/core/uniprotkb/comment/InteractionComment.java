package org.uniprot.core.uniprotkb.comment;

import java.util.List;

import org.uniprot.core.util.Utils;

public interface InteractionComment extends Comment {
    List<Interaction> getInteractions();

    default boolean hasInteractions() {
        return Utils.notNullNotEmpty(getInteractions());
    }
}
