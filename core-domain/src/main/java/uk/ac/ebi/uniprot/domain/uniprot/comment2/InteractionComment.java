package uk.ac.ebi.uniprot.domain.uniprot.comment2;

import java.util.List;

public interface InteractionComment extends Comment {
    public List<Interaction> getInteractions();
}
