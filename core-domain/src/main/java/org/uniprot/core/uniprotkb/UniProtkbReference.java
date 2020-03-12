package org.uniprot.core.uniprotkb;

import java.util.List;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

public interface UniProtkbReference extends HasEvidences {
    Citation getCitation();

    List<ReferenceComment> getReferenceCommentsByType(ReferenceCommentType type);

    List<ReferenceComment> getReferenceComments();

    List<String> getReferencePositions();

    boolean hasCitation();

    boolean hasReferenceComments();

    boolean hasReferencePositions();
}
