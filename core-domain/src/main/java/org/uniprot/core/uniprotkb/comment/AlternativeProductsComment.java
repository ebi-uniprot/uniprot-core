package org.uniprot.core.uniprotkb.comment;

import java.util.List;

public interface AlternativeProductsComment extends Comment {
    List<APEventType> getEvents();

    List<APIsoform> getIsoforms();

    Note getNote();

    boolean hasEvents();

    boolean hasIsoforms();

    boolean hasNote();
}
