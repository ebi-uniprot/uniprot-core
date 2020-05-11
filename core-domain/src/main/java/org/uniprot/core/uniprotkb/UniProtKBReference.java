package org.uniprot.core.uniprotkb;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprotkb.evidence.HasEvidences;

import java.util.List;

public interface UniProtKBReference extends HasEvidences {
    Citation getCitation();

    List<ReferenceComment> getReferenceCommentsByType(ReferenceCommentType type);

    List<ReferenceComment> getReferenceComments();

    List<String> getReferencePositions();

    boolean hasCitation();

    boolean hasReferenceComments();

    boolean hasReferencePositions();
}
