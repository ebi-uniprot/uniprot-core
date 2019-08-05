package org.uniprot.core.uniprot.comment;

import java.util.List;

public interface InteractionComment extends Comment {
    public List<Interaction> getInteractions();

    boolean hasInteractions();
}
