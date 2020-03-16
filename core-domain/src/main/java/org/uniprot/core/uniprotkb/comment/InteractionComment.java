package org.uniprot.core.uniprotkb.comment;

import java.util.List;

public interface InteractionComment extends Comment {
    List<Interaction> getInteractions();

    boolean hasInteractions();
}
