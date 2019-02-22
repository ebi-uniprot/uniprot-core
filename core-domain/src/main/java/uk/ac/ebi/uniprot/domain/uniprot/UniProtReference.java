package uk.ac.ebi.uniprot.domain.uniprot;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.HasEvidences;

import java.util.List;

public interface UniProtReference extends HasEvidences {
    Citation getCitation();

    List<ReferenceComment> getTypedReferenceComments(ReferenceCommentType type);

    List<ReferenceComment> getReferenceComments();

    List<String> getReferencePositions();

    boolean hasCitation();

    boolean hasReferenceComments();

    boolean hasReferencePositions();

}
