package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;

public interface AlternativeProductsComment extends Comment {
    public List<APEventType> getEvents();

    public List<APIsoform> getIsoforms();

    public Note getNote();
}
