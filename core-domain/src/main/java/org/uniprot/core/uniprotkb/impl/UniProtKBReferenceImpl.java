package org.uniprot.core.uniprotkb.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.uniprot.core.citation.Citation;
import org.uniprot.core.uniprotkb.ReferenceComment;
import org.uniprot.core.uniprotkb.ReferenceCommentType;
import org.uniprot.core.uniprotkb.UniProtKBReference;
import org.uniprot.core.uniprotkb.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class UniProtKBReferenceImpl implements UniProtKBReference {
    private static final long serialVersionUID = 4132545544902388006L;

    private int referenceNumber;
    private Citation citation;
    private List<String> referencePositions;
    private List<ReferenceComment> referenceComments;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    UniProtKBReferenceImpl() {
        this.referencePositions = Collections.emptyList();
        this.referenceComments = Collections.emptyList();
        this.evidences = Collections.emptyList();
    }

    UniProtKBReferenceImpl(
            int referenceNumber,
            Citation citation,
            List<String> referencePositions,
            List<ReferenceComment> referenceComments,
            List<Evidence> evidences) {
        this.referenceNumber = referenceNumber;
        this.citation = citation;
        this.referencePositions = Utils.unmodifiableList(referencePositions);
        this.referenceComments = Utils.unmodifiableList(referenceComments);
        this.evidences = Utils.unmodifiableList(evidences);
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullNotEmpty(this.evidences);
    }

    @Override
    public int getReferenceNumber() {
        return referenceNumber;
    }

    @Override
    public Citation getCitation() {
        return citation;
    }

    @Override
    public List<ReferenceComment> getReferenceCommentsByType(ReferenceCommentType type) {
        return this.referenceComments.stream()
                .filter(val -> val.getType() == type)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getReferencePositions() {
        return referencePositions;
    }

    @Override
    public boolean hasReferenceNumber() {
        return referenceNumber > 0;
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
        return Utils.notNullNotEmpty(this.referenceComments);
    }

    @Override
    public boolean hasReferencePositions() {
        return Utils.notNullNotEmpty(this.referencePositions);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UniProtKBReferenceImpl that = (UniProtKBReferenceImpl) o;
        return Objects.equals(referenceNumber, that.referenceNumber)
                && Objects.equals(citation, that.citation)
                && Objects.equals(referencePositions, that.referencePositions)
                && Objects.equals(referenceComments, that.referenceComments)
                && Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(referenceNumber, citation, referencePositions, referenceComments, evidences);
    }
}
