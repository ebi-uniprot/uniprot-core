package org.uniprot.core.uniprot.comment.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.uniprot.core.uniprot.comment.CommentType;
import org.uniprot.core.uniprot.comment.MassSpectrometryComment;
import org.uniprot.core.uniprot.comment.MassSpectrometryMethod;
import org.uniprot.core.uniprot.evidence.Evidence;
import org.uniprot.core.util.Utils;

public class MassSpectrometryCommentImpl extends CommentHasMoleculeImpl
        implements MassSpectrometryComment {
    private static final long serialVersionUID = 7080239485468338483L;
    private MassSpectrometryMethod method;
    private Float molWeight;
    private Float molWeightError;
    private String note;
    private List<Evidence> evidences;

    // no arg constructor for JSON deserialization
    MassSpectrometryCommentImpl() {
        super(CommentType.MASS_SPECTROMETRY, null);
        this.evidences = Collections.emptyList();
    }

    public MassSpectrometryCommentImpl(
            String molecule,
            MassSpectrometryMethod method,
            Float molWeight,
            Float molWeightError,
            String note,
            List<Evidence> evidences) {
        super(CommentType.MASS_SPECTROMETRY, molecule);
        this.method = method;
        this.molWeight = molWeight;

        this.molWeightError = molWeightError;
        this.note = note;
        this.evidences = Utils.unmodifiableList(evidences);
    }

    @Override
    public Float getMolWeightError() {
        return molWeightError;
    }

    @Override
    public Float getMolWeight() {
        return molWeight;
    }

    @Override
    public String getNote() {
        return note;
    }

    @Override
    public MassSpectrometryMethod getMethod() {
        return method;
    }

    @Override
    public List<Evidence> getEvidences() {
        return evidences;
    }

    @Override
    public boolean hasMolWeight() {
        return this.molWeight != null;
    }

    @Override
    public boolean hasMolWeightError() {
        return this.molWeightError != null && this.molWeightError > 0;
    }

    @Override
    public boolean hasNote() {
        return this.note != null;
    }

    @Override
    public boolean hasMethod() {
        return this.method != null;
    }

    @Override
    public boolean hasEvidences() {
        return Utils.notNullOrEmpty(this.evidences);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MassSpectrometryCommentImpl that = (MassSpectrometryCommentImpl) o;
        return method == that.method
                && Objects.equals(molWeight, that.molWeight)
                && Objects.equals(molWeightError, that.molWeightError)
                && Objects.equals(note, that.note)
                && Objects.equals(evidences, that.evidences);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), method, molWeight, molWeightError, note, evidences);
    }
}
