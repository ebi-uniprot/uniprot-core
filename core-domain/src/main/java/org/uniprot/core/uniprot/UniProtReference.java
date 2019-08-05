package org.uniprot.core.uniprot;

import java.util.List;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprot.evidence.HasEvidences;

public interface UniProtReference extends HasEvidences {
    Citation getCitation();

    List<ReferenceComment> getTypedReferenceComments(ReferenceCommentType type);

    List<ReferenceComment> getReferenceComments();

    List<String> getReferencePositions();

    boolean hasCitation();

    boolean hasReferenceComments();

    boolean hasReferencePositions();

}
