package uk.ac.ebi.uniprot.domain.uniprot.impl;

import uk.ac.ebi.uniprot.domain.citation.Citation;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceComment;
import uk.ac.ebi.uniprot.domain.uniprot.ReferenceCommentType;
import uk.ac.ebi.uniprot.domain.uniprot.UniProtReference;
import uk.ac.ebi.uniprot.domain.uniprot.evidence.Evidence;
import uk.ac.ebi.uniprot.domain.util.Utils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
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
        this.referencePositions = Utils.nonNullUnmodifiableList(referencePositions);
        this.referenceComments = Utils.nonNullUnmodifiableList(referenceComments);
        this.evidences = Utils.nonNullUnmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notEmpty(this.evidences);
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
    public boolean hasCitation() {
        return this.citation != null;
    }

    @Override
    public boolean hasReferenceComments() {
        return Utils.notEmpty(this.referenceComments);
    }

    @Override
    public boolean hastReferencePositions() {
        return Utils.notEmpty(this.referencePositions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniProtReferenceImpl that = (UniProtReferenceImpl) o;
        return Objects.equals(citation, that.citation) &&
                Objects.equals(referencePositions, that.referencePositions) &&
                Objects.equals(referenceComments, that.referenceComments) &&
                Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(citation, referencePositions, referenceComments, evidences);
    }
}
