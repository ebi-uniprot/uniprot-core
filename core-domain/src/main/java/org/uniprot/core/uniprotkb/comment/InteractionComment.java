package org.uniprot.core.uniprotkb.comment;

import org.uniprot.core.util.Utils;

import java.util.List;

public interface InteractionComment extends Comment {
    List<Interaction> getInteractions();

    default boolean hasInteractions() {
        return Utils.notNullNotEmpty(getInteractions());
    }
}
