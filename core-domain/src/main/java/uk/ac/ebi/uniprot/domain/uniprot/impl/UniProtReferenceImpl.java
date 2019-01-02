package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class UniProtReferenceImpl implements UniProtReference {
    private Citation citation;
    private List<String> referencePositions;
    private List<ReferenceComment> referenceComments;
    private List<Evidence> evidences;

    private UniProtReferenceImpl() {
        this.referencePositions = Collections.emptyList();
        this.referenceComments = Collections.emptyList();
        this.evidences = Collections.emptyList();
    }

    public UniProtReferenceImpl(Citation citation, List<String> referencePositions, List<ReferenceComment> referenceComments,
                                List<Evidence> evidences) {
        this.citation = citation;
        this.referencePositions = Utils.unmodifierList(referencePositions);
        this.referenceComments = Utils.unmodifierList(referenceComments);
        this.evidences = Utils.unmodifierList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public Citation getCitation() {
        return citation;
    }

    @Override
    public List<ReferenceComment> getTypedReferenceComments(ReferenceCommentType type) {
        return this.referenceComments.stream()
                .filter(val -> val.getType() == type)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getReferencePositions() {
        return referencePositions;
    }

    @Override
    public List<ReferenceComment> getReferenceComments() {
        return this.referenceComments;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((citation == null) ? 0 : citation.hashCode());
        result = prime * result + ((evidences == null) ? 0 : evidences.hashCode());
        result = prime * result + ((referenceComments == null) ? 0 : referenceComments.hashCode());
        result = prime * result + ((referencePositions == null) ? 0 : referencePositions.hashCode());
        return result;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UniProtReferenceImpl other = (UniProtReferenceImpl) obj;
        if (citation == null) {
            if (other.citation != null)
                return false;
        } else if (!citation.equals(other.citation))
            return false;
        if (evidences == null) {
            if (other.evidences != null)
                return false;
        } else if (!evidences.equals(other.evidences))
            return false;
        if (referenceComments == null) {
            if (other.referenceComments != null)
                return false;
        } else if (!referenceComments.equals(other.referenceComments))
            return false;
        if (referencePositions == null) {
            if (other.referencePositions != null)
                return false;
        } else if (!referencePositions.equals(other.referencePositions))
            return false;
        return true;
    }


}
