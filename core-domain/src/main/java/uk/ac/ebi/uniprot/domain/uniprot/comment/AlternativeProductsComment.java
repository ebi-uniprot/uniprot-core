package uk.ac.ebi.uniprot.domain.uniprot.comment;

import java.util.List;

public interface AlternativeProductsComment extends Comment {
    List<APEventType> getEvents();

    List<APIsoform> getIsoforms();

    Note getNote();

    boolean hasEvents();

    boolean hasIsoforms();

    boolean hasNote();
}
