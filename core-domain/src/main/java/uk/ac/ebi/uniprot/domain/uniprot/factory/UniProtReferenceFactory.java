package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ReferenceCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtReferenceImpl;

import java.util.List;

public enum UniProtReferenceFactory {
    INSTANCE;

    public UniProtReference createUniProtReference(Citation citation, List<String> referencePositions,
                                                   List<ReferenceComment> referenceComments,
                                                   List<Evidence> evidences) {
        return new UniProtReferenceImpl(citation, referencePositions, referenceComments, evidences);
    }

    public ReferenceComment createReferenceComment(ReferenceCommentType type, String value, List<Evidence> evidences) {
        return new ReferenceCommentImpl(type, value, evidences);
    }
}

