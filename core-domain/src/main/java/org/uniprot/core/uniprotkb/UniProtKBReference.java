package org.uniprot.core.uniprotkb;

import java.util.List;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface UniProtKBReference extends HasEvidences {
    int getReferenceNumber();

    Citation getCitation();

    List<ReferenceComment> getReferenceCommentsByType(ReferenceCommentType type);

    List<ReferenceComment> getReferenceComments();

    List<String> getReferencePositions();

    boolean hasReferenceNumber();

    boolean hasCitation();

    boolean hasReferenceComments();

    boolean hasReferencePositions();
}
