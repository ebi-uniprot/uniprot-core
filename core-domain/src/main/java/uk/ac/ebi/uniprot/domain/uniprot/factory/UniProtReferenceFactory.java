package uk.ac.ebi.uniprot.domain.uniprot.factory;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReferences;
import uk.ac.ebi.uniprot.domain.uniprot.evidences.Evidence;
import uk.ac.ebi.uniprot.domain.uniprot.impl.ReferenceCommentImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtReferenceImpl;
import uk.ac.ebi.uniprot.domain.uniprot.impl.UniProtReferencesImpl;

import java.util.List;

public enum UniProtReferenceFactory {
    INSTANCE;

    public  UniProtReferences createUniProtReferences(List<UniProtReference<? extends Citation>> references) {
        return new UniProtReferencesImpl(references);
    }
    public <T extends Citation> UniProtReference<T> createUniProtReference(T citation, List<String> referencePositions,
            List<ReferenceComment> referenceComments,
            List<Evidence> evidences) {
        return new UniProtReferenceImpl<>(citation, referencePositions, referenceComments, evidences);
    }
    public ReferenceComment createReferenceComment(ReferenceCommentType type, String value, List<Evidence> evidences){
        return new ReferenceCommentImpl(type, value, evidences);
    }
    
    public CitationFactory getCitationFactory(){
       return CitationFactory.INSTANCE;
    }
    
}

